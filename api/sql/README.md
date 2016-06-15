## Overview

Axibase Time Series Database supports SQL for retrieving time series data from the database.

The data can be exported in the following formats:

|**Endpoint**|**Formats**|
|:---|:---|
|API  |CSV, JSON|
|Web Interface  |CSV, JSON, HTML|
|Scheduler|CSV, JSON, Excel|

The SQL queries can be executed both in adhoc manner as well as on schedule. 

Scheduled execution allows for produced report files to be distributed to email subscribers or stored on a local file system.

## Syntax

### Commands

Only SELECT commands are supported at this time.

### Virtual Tables

SELECT commands can reference virtual tables in the FROM clause whereas such tables must correspond to metric names.

Each virtual table represents a subset of records stored in a shared partition table for the underlying metric.

```sql
SELECT datetime, entity, value FROM "cpu_busy" WHERE datetime > now - 1*MINUTE
```

In the example above, "cpu_busy" table contains records for `cpu_busy` metric.

Virtual tables are provided only for series. Access to other types of data (properties, messages, alerts) and metadata (entities, metrics) is currently not available.

### Joins

Data for multiple metrics can be merged by joining virtual tables with JOIN clause(s).

```sql
SELECT datetime, entity, t1.value, t2.value, t3.value
  FROM "cpu_system" t1
  JOIN "cpu_user" t2 
  JOIN "cpu_iowait" t3
  WHERE datetime > now - 1*MINUTE
```

In absence of columns in the JOIN clause, the records are joined by entity, record time, and all tags (if specified) by default.

For the JOIN to work for detailed data, records must have exactly the same time. 

This is typically the case when multiple metrics are inserted with one command or when time is controlled externally, as in the example above, where metrics 'cpu_system', 'cpu_user', 'cpu_iowait' are all timestamped externally by the same collector script with the same time during each `mpstat` command invocation.

To merge metrics with different times, use OUTER JOIN with period aggregation, aligned to calendar, in order to regularize timestamps of merged series.

```sql
SELECT datetime, entity, avg(t1.value), avg(t2.value)
  FROM "cpu_busy" t1
  OUTER JOIN "memfree" t2
WHERE datetime BETWEEN '2016-06-13T09:20:00.000Z' AND '2016-06-13T09:30:00.000Z'
  GROUP BY entity, period(1 MINUTE)
  LIMIT 5
```

### Predefined Columns

Since the underlying data is physically stored in the same shared partitioned table, all virtual tables have the same set of pre-defined columns:

|**Name**|**Type**|**Description**|
|:---|:---|:---|
|`metric`|string|Metric name, same as virtual table name.|
|`entity`|string|Entity name.|
|`tags` *or* `tags.{name}`|object|Object containing series tags, where name is tag name and a value is tag value.<br>`tags.*` syntax is supported only in SELECT clause.|
|`time`|long|Time in Unix milliseconds|
|`datetime`|string|Date in ISO 8601 format|
|`value`|number|Recorded value|

New columns can be created with expressions:

```sql
SELECT datetime, entity, t1.value + t2.value AS cpu_sysusr
  FROM "cpu_system" t1
  JOIN "cpu_user" t2
  WHERE datetime > now - 1*MINUTE
```

### Tag Columns

Tags values can be included in resultset by specifying tags.* or tags.{tag-name} as column name.

```sql
SELECT datetime, entity, value, tags.* 
  FROM "disk_used_percent" WHERE datetime > now - 1*MINUTE
```

```sql
SELECT datetime, entity, value, tags.mount_point, tags.file_system 
  FROM "disk_used_percent" WHERE datetime > now - 1*MINUTE
```

### Versioning Columns

Versioning columns (version_tatus, version_source, version_time) are currently not supported.

## Aliases

Column and table aliases can be added with quoted or double-quoted literal value or with `AS` keyword.

```
SELECT tbl.value*100 AS "cpu_percent", tbl.time "sample-time" 
  FROM "cpu_busy" tbl 
  WHERE datetime > now - 1*MINUTE
```

The underlying column and table names, or expression text are included in table schema section of the metadata.

## Arithmetic Operators

Arithmetic operators, including `+`, `-`, `*`, `/`, and `%` can be applied to one or multiple numeric data type columns.

```sql
SELECT datetime, sum(value), sum(value + 100) / 2 
  FROM gc_invocations_per_minute 
  WHERE time > now - 10 * minute 
  GROUP BY period(2 minute)
```

```sql
SELECT avg(metric1.value*2), sum(metric1.value + metric2.value) 
  FROM metric1 
  JOIN metric2
  WHERE time > now - 10 * minute 
```

## Wildcards

`?` and `*` wildcards are supported in `LIKE` expressions. 
Literal symbols `?` and `*` should be escaped with single backslash.

```sql
SELECT datetime, entity, value, tags.mount_point, tags.file_system 
  FROM "disk_used_percent" 
  WHERE tags.file_system LIKE '/dev/*'
  AND datetime > now - 1*MINUTE
```

## Time Condition

Time condition is specified in WHERE clause using `time` or `datetime` columns.

The `time` column accepts Unix milliseconds whereas `datetime` column accepts literal date in ISO 8601 format.

```sql
SELECT datetime, entity, value FROM "cpu_busy" 
  WHERE time >= 1465685363345 AND datetime < '2016-06-10T14:00:15.020Z'
```

Both columns support [End Time](/end-time-syntax.md) syntax.

```sql
SELECT datetime, entity, value FROM "cpu_busy" 
  WHERE time >= previous_minute
```

## Interpolation

> Pending #1475

By the default, if a period specified in `GROUP BY` clause doesn't contain any detailed values, it will not be included in the results.

The behaviour can be changed by specifying an interpolation function.

| **Name** | **Description** |
|:---|:---|
| NONE | No interpolation. Periods without any raw values are excluded from results |
| PREVIOUS | Set value for the period based on the previous period's value |
| NEXT | Set value for the period based on the period period's value |
| LINEAR | Calculate period value using linear interpolation between previous and next period values |
| VALUE| Set value for the period to a specific number |

```sql
SELECT entity, period(5 MINUTE) 
  FROM cpu_busy WHERE time > current_hour 
  GROUP BY entity, period(5 MINUTE, LINEAR)
```

## Period

Period is a repeating time interval used to group detailed values occurred in the period into buckets in order to apply aggregation functions.

The period contains the following fields:

| **Name** | **Type**| **Description** |
|:---|:---|:---|
| count  | number | [**Required**] Number of time units contained in the period. |
| unit  | string | [**Required**] Time unit such as `MINUTE`, `HOUR`, or `DAY`. |
| align| string | Alignment of the period's start/end. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`, .|

> For `START_TIME` and `END_TIME` align options, WHERE clause must contain start and end time of the selection interval, respectively. 

```
period({count} {unit} [, align])
```

```sql
SELECT datetime, sum(value) 
  FROM gc_invocations_per_minute 
  WHERE time > current_hour 
  GROUP BY period(5 minute, FIRST_VALUE_TIME)
```

## Query URL

API SQL endpoint is located at: `atsd_server:8088/sql`

The `q` parameter value must be URL-encoded.

### Query URL Example

```elm
https://atsd_server:8443/sql?q=SELECT+time%2C+value%2C+entity+FROM+mpstat.cpu_busy+WHERE+entity+%3D+%27nurswgvml007%27+AND+time+%3E%3D+previous_day+AND+time+%3C+now
```

## Authorization

The rows returned for the SQL query are filtered by the server according to the user's entity read permissions.

This means that the same query executed by users with different permissions may produce different results.

Scheduled queries are always executed under administrative permissions.

## Keywords

* AND 
* AS 
* ASC
* BETWEEN
* BY
* DESC
* FROM
* GROUP
* HAVING
* IN
* INNER
* INTERPOLATE
* JOIN 
* LIKE 
* LIMIT 
* NOT 
* OR
* ORDER 
* OUTER 
* PERIOD 
* REGEX 
* ROW_NUMBER
* SELECT 
* USING
* VALUE 
* WHERE 
* WITH 

## Processing Sequence

* **FROM** retrieves records from tables.
* **ON** joins tables.
* **WHERE** filters out records.
* **GROUP BY** assigns records to buckets.
* **HAVING** filters out the buckets.
* **SELECT** creates rows containing columns.
* **ORDER BY** sorts rows.
* **LIMIT** selects a subset of rows.

## Aggregation Functions

* AVG
* COUNT
* COUNTER
* DELTA
* FIRST
* LAST
* MAX
* MAX_VALUE_TIME
* MIN
* MIN_VALUE_TIME
* PERCENTILE
* STDDEV
* SUM
* WAVG
* WTAVG

The numeric aggregation functions accept `value` as a parameter, for example  `avg(value)`.
The PERCENTILE function accepts `percentile` parameter (0 to 100) and `value` parameter, for example `percentile(75, value)`.

## Supported Operators

* GROUP BY time, period(interval), {entity}, tags.{tagkey}
* WHERE columns: entity, tags.{tagkey}, time
* LIMIT (offset), count
* ORDER BY column DESC | ASC
* ORDER BY queries that contain GROUP BY
* HAVING filter for grouped values
* ROW_NUMBER returns row index (starting with 1) within the group. The function can be used to limit the number of rows returned for each group.
* JOIN
* OUTER JOIN
* LAST_TIME

## Not Supported Operators

* INTERPOLATE can follow GROUP BY period(interval) function, e.g. period(1 DAY) interpolate(LINEAR). interpolate - LINEAR, VALUE(0), PREVIOUS, NEXT

## Date and Time Formats

Supported time formats include:

|**Column**|**Type**|**Description**|**Example**|
|:---|:---|:---|:---|
|time  |long  |Unix milliseconds since 1970-01-01T00:00:00Z|`1408007200000`|
|datetime|string|ISO 8601 date | `2016-06-10T14:00:15.020Z`|

## Time Formatting Functions

`date_format(long milliseconds[, string format])` - Formats Unix millisecond time to string in user-defined date format. If format is not specified, ISO 8601 format is applied.

Examples:

* `date_format(time)`
* `date_format(max_value_time(value))`
* `date_format(time, 'yyyy-MM-dd HH:mm:ss')`


## Case Sensitivity

Keywords are case-insensitive.

Entity name, metric name, tag names and property key names are case-insensitive. 

Tag values and property values are case-sensitive.

## Examples

- [Alias](examples/alias.md)
- [Average Value](examples/average-value-query.md)
- [Basic](examples/basic-query.md)
- [Counter Aggregator](examples/counter-aggregator.md)
- [Datetime Format](examples/datetime-format.md)
- [Entity with Tags](examples/entity-with-tags.md)
- [Filter by Tag](examples/filter-by-tag.md)
- [Group by Query with Order By](examples/group-by-query-with-order-by.md)
- [Grouped Average](examples/grouped-average.md)
- [Inner Join](examples/inner-join.md)
- [Last Time](examples/last-time.md)
- [Max Value Time](examples/max-value-time.md)
- [Not Equal Operator](examples/not-equal-operator.md)
- [Order By Time](examples/order-by-time.md)
- [Outer Join With Aggregation](examples/outer-join-with-aggregation.md)
- [Outer Join](examples/outer-join.md)
- [Query Format](examples/query-format.md)
- [Row Number Function](examples/row-number-function.md)
- [Row Number With Order By Avg](examples/row-number-with-order-by-avg.md)
- [Row Number With Order By Time & Avg](examples/row-number-with-order-by-time&avg.md)
- [Row Number With Order By Time](examples/row-number-with-order-by-time.md)
- [Select All](examples/select-all.md)
- [Select Entity Tags As Columns](examples/select-entity-tags-as-columns.md)
- [Time Condition](examples/time-condition.md)
- [Using Entity](examples/using-entity.md)

