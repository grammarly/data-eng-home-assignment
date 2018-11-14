import java.time.LocalDate

import org.apache.spark.sql.{Column, DataFrame, SparkSession}

case class TimeAggQuery(fromDate: LocalDate,
                        toDate: LocalDate,
                        stepDays: Int,
                        filter: Option[Column],
                        distinct: Option[Column],
                        by: Option[Column])

class TimeAggQueryExecutor(spark: SparkSession) {

  import spark.implicits._

  def execute(input: DataFrame, query: TimeAggQuery): DataFrame = {

    ??? // TODO: add the actual implementation here

  }

}
