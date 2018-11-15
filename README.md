# Take Home Assignment

Imagine you have a table available to you as a Spark dataframe. The table holds entries, each of which is a recorded 
user action within some app.

The table columns describe certain details about the event. The table will have,**at least**, the following columns:

- ts (long, not null): event epoch timestamp in seconds

You are building a query engine that will allow building time-aggregation reports for this table, e.g. in the 
simplest case produce counts of events for each calendar date.

Features:

- You are provided with the date range (`fromDate` (inclusive) and `toDate` (inclusive)) – output dataframe should limit 
aggregation to the given date range
- You are provided with the `slotDays` (integer) – output dataframe should provide event counts for each slot
    - slot 0: `fromDate` until `fromDate + slotDays`
    - slot 1: `fromDate + slotDays` until `fromDate + 2 * slotDays`
    - ...
    - slot n: `fromDate + n * slotDays` to `toDate`
- You are provided with the optional `filter` column – when counting only take into account events for which `filter` 
is true
- You are provided with the optional `distinct` expression, which requires just the first event taken into account 
for each unique `distinct` value and slot (earliest by `ts`)
- You are provided with the `by`: output individual count for each by-slot combinations, instead of just slot

Assume all dates and times are in PST (`America/Los_Angeles`) timezone.

Using Scala programming language is not required, but will be a **big** plus (feel free to use this project 
as a starting point). We recommend using IntelliJ IDEA with the Scala plugin (available in a free trial 
of the Ultimate edition).
