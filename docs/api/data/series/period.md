# Period

| **Name**  | **Type** | **Description** |
|:---|:---|:---|
| count  | number | Number of time units contained in the period. |
| unit  | string | [Time unit](time-unit.md) such as `MINUTE`, `HOUR`, `DAY`. |
| align | string | Alignment of the period's start/end time. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`.|

## Examples

```json
"period": { "count": 1, "unit": "HOUR" }
```

```json
"period": { "count": 5, "unit": "MINUTE" }
```

```json
"period": { "count": 5, "unit": "MINUTE", "align": "END_TIME" }
```

## Alignment

By default, periods are aligned to calendar grid according to time unit specified in the period.

For example, `period(1 HOUR)` starts at 0 minutes/0 seconds of each hour within the selection interval.

The default `CALENDAR` alignment can be changed to `START_TIME`, `END_TIME`, or `FIRST_VALUE_TIME`.

| **Alignment** | **Description**|
|:---|:---|
| CALENDAR | Period start is rounded down to the nearest time unit. |
| START_TIME | First period begins at start time specified in the query. |
| FIRST_VALUE_TIME | First period begins at the time of first retrieved value. |
| END_TIME | Last period ends on end time specified in the query. |

### `CALENDAR` Alignment

In `CALENDAR` alignment mode, start time is rounded down and incremented by period until period start is equal or greater than `startDate`.

* If period unit is `SECOND`, `MILLISECOND`, or `NANOSECOND`, start time is rounded down to `0m:0s` of the nearest hour containing `startDate`.
* If period unit is `MINUTE`, start time is rounded down to `0m:0s` of the nearest hour containing `startDate`.
* If period unit is `HOUR`, start time is rounded down to `0h:0m:0s` of the nearest day containing `startDate`.
* If period unit is `DAY`, start time is rounded down to `0h:0m:0s` of the first day of the month containing `startDate`.
* If period unit is `WEEK`, start time is rounded down to `0h:0m:0s` of the first Monday of the month containing `startDate`.
* If period unit is `MONTH` or `QUARTER`, start time is rounded down to `0h:0m:0s` on January, 1st of the year containing `startDate`.
* If period unit is `YEAR`, start time is rounded down to `0h:0m:0s`  on January, 1st of the year containing `startDate`.

The first period returned in results is determined by adding period duration to rounded start time, until period start time is greater or equal `startDate`.

Start of the day is determined according to server timezone for `DAY`, `WEEK`, `MONTH`, `QUARTER`, and `YEAR` units.

#### Example 1

`30 SECOND`-period with `startDate` of `2016-06-20T15:00:45Z` and `endDate` of `2016-06-20T15:02:45Z`.

* Start time `15:00:45Z` is rounded to `15:00:00Z` as the nearest hour containing `startDate`.
* Starting with `15:00:00Z`, increment by period duration of `30 SECOND` to determine period start time.
* First period starts at `15:00:30Z`. It starts earlier than `startDate` and is therefore ignored.
* Next period starts at `15:01:00Z` which is >= `startDate` and therefore will be included in results.
* The first period will contain data for `[2016-06-20T15:01:00Z - 2016-06-20T15:01:30T)`.
* Subsequent periods will be incremented by `30 SECOND` and included until their start time is less than `endDate`.
* The last period will be `[2016-06-20T15:02:30Z - 2016-06-20T15:03:00T)` however it will contain partial data until `endDate`: `[2016-06-20T15:02:30Z - 2016-06-20T15:02:45T)`.

#### Example 2

`45 MINUTE`-period with `startDate` of `2016-06-20T15:05:00Z`.

* Time is rounded to `15:00:00Z` and then incremented by 45 minutes until period start time is >= `2016-06-20T15:05:00Z`.
* The period that first satisfies this condition is `[2016-06-20T15:45:00Z - 2016-06-20T16:30:00Z)`.

#### Example 3

`365 DAY`-period with `startDate` of `2014-12-21T00:00:00Z` and `endDate` of `2016-12-20T00:00:00Z`.

* Since time unit is `DAY`, time is rounded to 1st day of the month containing `startDate`, which is `2014-12-01T00:00:00Z`.
* The first period is `[2014-12-01T00:00:00Z - 2015-12-01T00:00:00Z)` and its start time is **outside** of the selection interval.
* The next period is `[2015-12-01T00:00:00Z - 2016-12-01T00:00:00Z)` and its start time is within the selection interval.
* The 3rd period is `[2016-12-01T00:00:00Z - 2017-12-01T00:00:00Z)` and its start time is also within the selection interval, although it will contain data within `[2016-12-01T00:00:00Z - 2016-12-20T00:00:00Z)` interval being limited by `endDate`.

#### Calculation Examples

```ls
| Period     | Start Date            | End Date              | 1st Period            | 2nd Period            | Last Period          | 
|-----------:|-----------------------|-----------------------|-----------------------|-----------------------|----------------------| 
| 15 SECOND  | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:05:00Z  | 2016-06-20T15:05:15Z  | 2016-06-23T23:59:45Z | 
| 90 SECOND  | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:06:00Z  | 2016-06-20T15:07:30Z  | 2016-06-23T23:58:30Z |
| 1 MINUTE   | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:05:00Z  | 2016-06-20T15:06:00Z  | 2016-06-23T23:59:00Z |
| 3 MINUTE   | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:06:00Z  | 2016-06-20T15:09:00Z  | 2016-06-23T23:57:00Z |
| 37 MINUTE  | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:37:00Z  | 2016-06-20T16:14:00Z  | 2016-06-23T23:47:00Z |
| 45 MINUTE  | 2016-06-20T15:05:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:45:00Z  | 2016-06-20T16:30:00Z  | 2016-06-23T23:15:00Z | 
| 45 MINUTE  | 2016-06-20T15:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T15:00:00Z  | 2016-06-20T15:45:00Z  | 2016-06-23T23:15:00Z | 
| 1 HOUR     | 2016-06-20T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T16:00:00Z  | 2016-06-20T17:00:00Z  | 2016-06-23T23:00:00Z | 
| 1 HOUR     | 2016-06-20T16:05:00Z  | 2016-06-23T23:55:00Z  | 2016-06-20T17:00:00Z  | 2016-06-20T18:00:00Z  | 2016-06-23T23:00:00Z | 
| 1 HOUR     | 2016-06-20T16:30:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T17:00:00Z  | 2016-06-20T18:00:00Z  | 2016-06-23T23:00:00Z | 
| 7 HOUR     | 2016-06-20T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T21:00:00Z  | 2016-06-21T04:00:00Z  | 2016-06-23T19:00:00Z | 
| 10 HOUR    | 2016-06-20T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-20T20:00:00Z  | 2016-06-21T06:00:00Z  | 2016-06-23T18:00:00Z | 
| 1 DAY      | 2016-06-01T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-02T00:00:00Z  | 2016-06-03T00:00:00Z  | 2016-06-23T00:00:00Z | 
| 2 DAY      | 2016-06-01T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-03T00:00:00Z  | 2016-06-05T00:00:00Z  | 2016-06-23T00:00:00Z | 
| 5 DAY      | 2016-06-01T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-06T00:00:00Z  | 2016-06-11T00:00:00Z  | 2016-06-21T00:00:00Z | 
| 10 DAY     | 2016-06-03T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-10T00:00:00Z  | 2016-06-20T00:00:00Z  | 2016-06-20T00:00:00Z | 
| 365 DAY    | 2016-06-03T16:00:00Z  | 2017-06-24T00:00:00Z  | 2017-06-01T00:00:00Z  | -                     | -                    |
| 1 WEEK     | 2016-06-01T16:00:00Z  | 2016-06-24T00:00:00Z  | 2016-06-06T00:00:00Z  | 2016-06-13T00:00:00Z  | 2016-06-20T00:00:00Z | 
| 1 WEEK     | 2016-05-01T16:00:00Z  | 2016-05-24T00:00:00Z  | 2016-05-02T00:00:00Z  | 2016-05-09T00:00:00Z  | 2016-05-23T00:00:00Z | 
| 1 WEEK     | 2016-06-01T00:00:00Z  | 2016-06-02T00:00:00Z  | - 1st Monday Jun-06.  | -                     | -                    | 
```

