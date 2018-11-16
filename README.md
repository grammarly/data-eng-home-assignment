# Take Home Assignment

Imagine you have a table available to you as a Spark dataframe. The table holds entries, each of which is a recorded 
user action within some app.

The table columns describe certain details about the event. The table will have **at least** the following columns:

- `ts` (long, not null): event epoch timestamp in seconds

Your task is to build a query engine that will build time-aggregation reports for this table
	- e.g. in the simplest case, produce counts of events for each calendar date.

Features:

- You are provided with a date range (`fromDate` (inclusive) and `toDate` (inclusive)) – the output dataframe should limit aggregation to the given date range
- You are provided with a `slotDays` (integer) – the output dataframe should provide event counts for each slot
    - slot 0: `fromDate` until `fromDate + slotDays`
    - slot 1: `fromDate + slotDays` until `fromDate + 2 * slotDays`
    - ...
    - slot n: `fromDate + n * slotDays` to `toDate`
- You are provided with an optional `filter` expression – when present, only count events for which `filter` 
is true
- You are provided with an optional `distinct` expression – when present, only count the first event for each unique `distinct` value and slot (earliest by `ts`)
- (stretch) You are provided with an optional `by` expression – when present, output the individual count for each (by,slot) combination, rather than only counting slot

Assume all dates and times are in Pacific Time (`America/Los_Angeles`) timezone.

Using the Scala programming language is not required, but will be a **big** plus (feel free to use this project 
as a starting point). We recommend using IntelliJ IDEA with the Scala plugin (available in a [free trial 
of the Ultimate edition](https://www.jetbrains.com/idea/download)).
