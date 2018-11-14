import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

case class InRow(ts: Long, userId: String, foo: String)

case class OutRow(slot: Int, by: String, count: Long)

class TimeAggQueryExecutorTest extends FunSuite {

  lazy val spark: SparkSession = SparkSession.builder
    .master("local[2]")
    .appName("test")
    .config("spark.sql.shuffle.partitions", "4")
    .config("spark.sql.broadcastTimeout", "50")
    .getOrCreate()


  lazy val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  implicit def stringToTs(str: String): Long = LocalDateTime.parse(str, dateTimeFormatter).atZone(ZoneId.of("America/Los_Angeles")).toEpochSecond


  lazy val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  implicit def stringToLocalDate(str: String): LocalDate = LocalDate.parse(str, dateFormatter)


  test("testExample") {

    import spark.implicits._

    val query = TimeAggQuery(
      fromDate = "2018-01-01",
      toDate = "2018-01-14",
      stepDays = 7,
      filter = Some($"userId" =!= "u4"),
      distinct = Some($"userId"),
      by = Some($"foo")
    )

    val in: Seq[InRow] = Seq(
      InRow("2017-01-01 10:00:00", "this is out of scope", "because of the date"),

      InRow("2018-01-01 10:00:00", "u1", "x"),  // user u1 gives +1 to (slot=0, by=x), and +1 to (slot=1, by=y)
      InRow("2018-01-02 10:00:00", "u1", "y"),
      InRow("2018-01-03 10:00:00", "u1", "z"),
      InRow("2018-01-10 10:00:00", "u1", "y"),

      InRow("2018-01-02 10:00:00", "u2", "x"),  // user u2 gives +1 to (slot=0, by=x), and +1 to (slot=1, by=x)
      InRow("2018-01-11 10:00:00", "u2", "x"),

      InRow("2018-01-03 10:00:00", "u3", "y"),  // user u3 gives +1 to (slot=0, by=y)

      InRow("2018-01-01 10:00:00", "u4", "x"),  // does not satisfy the filter

      InRow("2018-02-02 10:00:00", "this is out of scope", "because of the date")
    )

    val expected: Seq[OutRow] = Seq(
      OutRow(slot = 0, by = "x", count = 2),
      OutRow(slot = 0, by = "y", count = 1),
      OutRow(slot = 1, by = "x", count = 1),
      OutRow(slot = 1, by = "y", count = 1)
    )

    val actual: Seq[OutRow] = new TimeAggQueryExecutor(spark).execute(in.toDF, query).as[OutRow].collect()

    assert(expected == actual)

  }

}
