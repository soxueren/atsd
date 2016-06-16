# Period

| **Name**  | **Type** | **Description** |
|:---|:---|:---|
| count  | number | Number of time units contained in the period. |
| unit  | string | [Time unit](time-unit.md) such as `MINUTE`, `HOUR`, `DAY`. |
| align| string | Alignment of the period's start/end. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`.|

## Alignment

By default, periods are aligned to calendar grid according based on period's time unit.

For example, `period(1 HOUR)` starts at 0 minutes of each hour in the timespan.

For time units `DAY`, `WEEK`, `MONTH`, `QUARTER`, and `YEAR` the start of the day is determined according to server time.

The default `CALENDAR` alignment can be changed to `START_TIME`, `END_TIME`, or `FIRST_VALUE_TIME`.

In case of `START_TIME` and `FIRST_VALUE_TIME`, start of the first period is determined according to the start of the selection interval or time of the first value, respectively.

In case of `END_TIME`, end of the last period is determined according to the end of the selection interval.
