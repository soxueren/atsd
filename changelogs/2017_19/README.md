Weekly Change Log: May 08, 2017 - May 14, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|----------------------
| 4185 | export | Bug | Fixed a compatibility issue with Java 8 |
| 4176 | UI | Bug | Fixed an interface issue that resulted in a broken page while toggling Tag select |
| 4174 | csv | Bug | Fixed a compatibility issue that resulted from an update of the Java 8 [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino) engine |
| 4172 | rule engine | Bug | Fixed invalid behavior in [Alerts History](https://nur.axibase.com/rules/alerts/search) caused by [Freemarker](https://axibase.com/products/axibase-time-series-database/visualization/freemarker/) template error.
| [4166](Issue-4166) | UI | Feature | User-definable time formatting enabled. |
| [4165](#Issue-4165) | UI | Feature | Added additional SQL [syntax](https://github.com/axibase/atsd/tree/master/api/sql#syntax), [keyword](https://github.com/axibase/atsd/tree/master/api/sql#keywords), and [aggregation](https://github.com/axibase/atsd/tree/master/api/sql#aggregation-functions) options. |
| 4159 | UI | Bug | Removed an unneeded sorting option in [System Information](https://nur.axibase.com/admin/system-information). |
| 4158 | sql | Bug | Fixed an error which prevented immediate execution of SQL queries in the [SQL Console](https://github.com/axibase/atsd/tree/master/api/sql) |
| 4156 | export | Bug | Fixed error notification protocol which informed user of an [Output Path](https://github.com/axibase/atsd/tree/master/api/sql#query-control-messages) error. |
| [4146](#Issue-4146) | sql | Feature | Added a feature to include non-empty periods with start times earlier than the interval selected in calculations. |
| [4140](#Issue-4140) | UI | Feature | Truncated redundant links to dropdown menu. |
| 3838 | sql | Bug | Fixed an error caused by referring to a column alias using the [`GROUP BY`](https://github.com/axibase/atsd/tree/master/api/sql#grouping) command. |

### ATSD

#### Issue 4166

`TIME_FORMAT` command renamed as [`DATE_FORMAT`](https://github.com/axibase/atsd/blob/master/api/sql/examples/datetime-format.md).
Default formatting declared, making user input optional. Addition of new formats:
* `MMM-dd,E` where `E` displays the day of the week by three-letter abbreviation.
* `MMM-dd, EEEE` where `EEEE` displays the day of the week by full name.

![4166](Images/4166.png)

#### Issue 4165

[`FROM atsd_series`](https://github.com/axibase/atsd/blob/master/api/sql/examples/select-atsd_series.md) command allows a built-in table to be queried directly.

Keywords:

```
|-------------|-------------|-------------|-------------|
| AND         | AS          | ASC         | BETWEEN     |
| BY          | CASE        | CAST        | DESC        |
| ELSE        | FROM        | GROUP       | HAVING      |
| IN          | INNER       | INTERPOLATE | ISNULL      |
| JOIN        | LAG         | LAST_TIME   | LEAD        |
| LIKE        | LIMIT       | LOOKUP      | NOT         |
| OFFSET      | OPTION      | OR          | ORDER       |
| OUTER       | PERIOD      | REGEX       | ROW_NUMBER  |
| SELECT      | THEN        | USING       | VALUE       |
| WHEN        | WHERE       | WITH        |             |
|-------------|-------------|-------------|-------------|
```

Aggregation Functions:
```
|----------------|----------------|----------------|----------------|
| AVG            | CORREL         | COUNT          | COUNTER        |
| DELTA          | FIRST          | LAST           | MAX            |
| MAX_VALUE_TIME | MEDIAN         | MIN            | MIN_VALUE_TIME |
| PERCENTILE     | SUM            | STDDEV         | WAVG           |
| WTAVG          |                |                |                |
|----------------|----------------|----------------|----------------|
```

#### Issue 4146

Given the following data:
```
series e:eg-1 m:eg-1=1 d:2017-05-01T00:30:00Z
series e:eg-1 m:eg-1=2 d:2017-05-01T01:00:00Z
series e:eg-1 m:eg-1=3 d:2017-05-01T01:30:00Z
series e:eg-1 m:eg-1=4 d:2017-05-01T01:45:00Z
series e:eg-1 m:eg-1=5 d:2017-05-02T02:00:00Z
series e:eg-1 m:eg-1=6 d:2017-05-03T03:00:00Z
```
And the following command: 
```
SELECT datetime, avg(value), count(value)
  FROM 'eg-1'
WHERE datetime >= '2017-05-01T00:05:00Z' AND datetime < '2017-05-04T00:00:00Z'
GROUP BY PERIOD(1 HOUR)
```
Data outside the `datetime` range was not be considered, even for calculations
such as `avg(val)`.

#### Issue 4140

![4140](Images/4140.1.png)

These links have been compressed to a dropdown menu, as shown below:

![4104](Images/4104.2.png)

