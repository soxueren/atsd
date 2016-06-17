# Overview

Axibase Time Series Database supports SQL for retrieving time series data from the database.

The data can be exported in the following formats:

|**Endpoint**|**Formats**|
|:---|:---|
|API  |CSV, JSON|
|Web Interface  |CSV, JSON, HTML|
|Scheduler|CSV, JSON, Excel|

The SQL statements can be executed both in adhoc manner as well as on schedule. 

Scheduled execution allows for generated report files to be distributed to email subscribers or stored on a local file system.

## Syntax

The database supports only `SELECT` statements at this time. 

The `SELECT` statement consists of a select expression and a query with optional clauses.

```sql
SELECT datetime, entity, value -- SELECT expression
  FROM "mpstat.cpu_busy" -- query
  WHERE datetime > now - 1*MINUTE -- clause(s)
```

### SELECT expression

The SELECT expression consists of one or multiple columns and expressions applied to query results.

### Query

The query can reference virtual tables in `FROM` clause where each table must correspond to a metric name.

Virtual table represents a subset of records for the given metric, stored by the database in the shared physical table.

```sql
SELECT datetime, entity, value 
  FROM "mpstat.cpu_busy" 
  WHERE datetime > now - 1*MINUTE
```

In the example above, "mpstat.cpu_busy" table contains records for `mpstat.cpu_busy` metric.

Virtual tables are currently supported only for series. Access to properties, messages, and alerts is currently not available.

### Clauses

* **WHERE** columns: entity, tags.{name}, time
* **ORDER BY**
* **LIMIT** (offset), count
* **GROUP BY** time, period, entity, tags.{name}
* **HAVING** filter for grouped rows
* **JOIN**
* **OUTER JOIN**
* **ROW_NUMBER** returns row index (starting with 1) within the group. The function can be used to limit the number of rows returned for each group.
* **LAST_TIME**

### Processing Sequence

* **FROM** retrieves records from virtual tables.
* **JOIN** merges records from different tables.
* **WHERE** filters out records.
* **GROUP BY** assigns records to groups (sets, buckets).
* **HAVING** filters out the buckets.
* **SELECT** creates rows containing columns.
* **ORDER BY** sorts rows.
* **LIMIT** selects a subset of rows.

## Columns

### Predefined Columns

Since the underlying data is physically stored in the same shared partitioned table, all virtual tables have the same set of pre-defined columns:

|**Name**|**Type**|**Description**|
|:---|:---|:---|
|`time`|long|Record time in Unix milliseconds since 1970-01-01T00:00:00Z, for example `1408007200000`.<br>In `GROUP BY` query with `PERIOD`, time column returns period start time, same as `PERIOD()` column specified in `GROUP BY` clause.|
|`datetime`|string|Record time in ISO 8601 format, for example `2016-06-10T14:00:15.020Z`.<br>In `GROUP BY` query with `PERIOD`, datetime column returns period start time in ISO format, same as `date_format(PERIOD())` column specified in `GROUP BY` clause.|
|`period`|long|Period start time in Unix milliseconds since 1970-01-01T00:00:00Z, for example `1408007200000`.|
|`value`|number|Series value.|
|`metric`|string|Metric name, same as virtual table name.|
|`entity`|string|Entity name.|
|`tags`|string|All series tags, concatenated to `name1=value;name2=value` format.<br>In addition, `SELECT` statement supports `tags.*` syntax which expands to multiple columns.|
|`tags.{name}`|string|Series tag value.|
|`entity.tags.{name}`|string|Entity tag value.|

New columns can be created by applying functions and arithmetic expressions to existing columns.

```sql
SELECT datetime, entity, t1.value + t2.value AS cpu_sysusr
  FROM "cpu_system" t1
  JOIN "cpu_user" t2
  WHERE datetime > now - 1*MINUTE
```

> `SELECT *` syntax is not supported with `GROUP BY` clause.

```
SELECT *
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

```ls
cpu_busy.entity | memfree.entity | cpu_busy.time | memfree.time | cpu_busy.value | memfree.value
nurswgvml006 | nurswgvml006 | 1466082161000 | 1466082161000 | 3.0 | null
nurswgvml006 | nurswgvml006 | 1466082162000 | 1466082162000 | null | 70652.0
nurswgvml006 | nurswgvml006 | 1466082177000 | 1466082177000 | 16.0 | 74588.0
nurswgvml006 | nurswgvml006 | 1466082192000 | 1466082192000 | null | 72536.0
nurswgvml006 | nurswgvml006 | 1466082193000 | 1466082193000 | 7.1 | null
```

### Series Tag Columns

Tags values are referenced in `SELECT` expression by specifying `tags.*`, `tags`, or `tags.{tag-name}` as column name.

`tags` and `tags.{tag-name}` syntax can also be used in `WHERE`, `ORDER`, `GROUP BY` and other clauses.

```sql
SELECT datetime, entity, value, tags.* 
  FROM "df.disk_used_percent" WHERE datetime > now - 1*MINUTE
```

```sql
SELECT datetime, entity, value, tags.mount_point, tags.file_system 
  FROM "df.disk_used_percent" WHERE datetime > now - 1*MINUTE
```

### Entity Tag Columns

Entity tag values can be included in `SELECT` expression by specifying `entity.tags.{tag-name}` as column name.

```sql
SELECT entity, entity.tags.os, entity.tags.app, avg(value) 
  FROM "mpstat.cpu_busy"
WHERE time > current_hour
  GROUP BY entity 
```

```ls
entity | entity.tags.os | entity.tags.app | avg(value)
nurswgvml006 | Linux | Hadoop/HBASE | 21.7
nurswgvml007 | Linux | ATSD | 9.7
nurswgvml010 | Linux | SVN, Jenkins, Redmine | 6.6
nurswgvml011 | Linux | HMC Simulator, mysql | 5.4
nurswgvml102 | Linux | Router | 1.2
nurswgvml502 | null | null | 4.9
```

### Group By Columns

In `GROUP BY` query, `datetime` and `PERIOD()` columns return the same value, period's start time, in ISO format. In this case, `date_format(PERIOD(5 minute))` can be omitted.

```sql
SELECT entity, datetime, date_format(PERIOD(5 minute)), AVG(value) 
  FROM gc_invocations_per_minute 
WHERE time >= current_hour AND time < next_hour
  GROUP BY entity, PERIOD(5 minute)
```

Columns referenced in the `SELECT` statement must be included in the `GROUP BY` clause.


### Versioning Columns

Versioning columns (version_tatus, version_source, version_time) are currently not supported.

## Aliases

Table and column aliases can be added with quoted or double-quoted literal value or with `AS` keyword.

```
SELECT tbl.value*100 AS "cpu_percent", tbl.time 'sample-time'
  FROM "mpstat.cpu_busy" tbl 
WHERE datetime > now - 1*MINUTE
```

The underlying column and table names, or expression text are included in table schema section of the metadata.

Alias should start with letter [a-zA-Z], followed by letter, digit or underscore.

## Arithmetic Operators

Arithmetic operators, including `+`, `-`, `*`, and `/` can be applied to one or multiple numeric data type columns.

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
  FROM "df.disk_used_percent" 
  WHERE tags.file_system LIKE '/dev/*'
  AND datetime > now - 1*MINUTE
```

## Time Condition

Time condition is specified in `WHERE` clause using `time` or `datetime` columns.

The `time` column accepts Unix milliseconds whereas `datetime` column accepts literal date in ISO 8601 format.

```sql
SELECT datetime, entity, value FROM "mpstat.cpu_busy" 
  WHERE time >= 1465685363345 AND datetime < '2016-06-10T14:00:15.020Z'
```

Both columns support [End Time](/end-time-syntax.md) syntax.

```sql
SELECT datetime, entity, value FROM "mpstat.cpu_busy" 
  WHERE time >= previous_minute
```

## Period

Period is a repeating time interval used to group detailed values occurred in the period into buckets in order to apply aggregation functions.

The period contains the following fields:

| **Name** | **Description** |
|:---|:---|
| count  | [**Required**] Number of time units contained in the period. |
| unit  | [**Required**] [Time unit](/api/series/time-unit.md) such as `MINUTE`, `HOUR`, `DAY`. |
| interpolation  | Interpolation function, such as `LINEAR`. Default: `NONE`. Refer to [interpolation](#interpolation). |
| align| Alignment of the period's start/end. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`.<br>Refer to [period alignment](#period-alignment).|

```sql
PERIOD({count} {unit} [, interpolation [, align]])
```

```sql
SELECT entity, date_format(PERIOD(5 minute, NONE, END_TIME)), AVG(value) 
  FROM gc_invocations_per_minute 
  WHERE time >= current_hour AND time < next_hour
  GROUP BY entity, PERIOD(5 minute, NONE, END_TIME)
```

The period specified in `GROUP BY` clause can be entered without _align_ and _interpolation_ fields in `SELECT` statement:

```sql
SELECT entity, date_format(PERIOD(5 minute), AVG(value) 
  FROM gc_invocations_per_minute 
  WHERE time >= current_hour AND time < next_hour
  GROUP BY entity, PERIOD(5 minute, NONE, END_TIME)
```

### Period Alignment

By default, periods are aligned to calendar grid according based on period's time unit.

For example, `period(1 HOUR)` starts at 0 minutes of each hour in the timespan.

For DAY, WEEK, MONTH, QUARTER, and YEAR units the start of the day is determined according to server timezone.

The default `CALENDAR` alignment can be changed to `START_TIME`, `END_TIME`, or `FIRST_VALUE_TIME`.

In case of `START_TIME` and `FIRST_VALUE_TIME`, start of the first period is determined according to the start of the selection interval or time of the first value, respectively.

In case of `END_TIME`, end of the last period is determined according to the end of the selection interval.

For `START_TIME` and `END_TIME` options, `WHERE` clause must contain start and end time of the selection interval, respectively.

```sql
SELECT entity, date_format(PERIOD(5 MINUTE)), COUNT(value) 
  FROM "mpstat.cpu_busy"
WHERE datetime >= now-1*HOUR AND datetime < now
  AND entity = 'nurswgvml006'
GROUP BY entity, PERIOD(5 MINUTE, NONE, END_TIME)
```

## Interpolation

By the default, if a period specified in `GROUP BY` clause doesn't contain any detailed values or the period has been filtered out with `HAVING` clause, it will be excluded from the results.

The behaviour can be changed by referencing an interpolation function as part of `PERIOD` clause.

| **Name** | **Description** |
|:---|:---|
| NONE | No interpolation. Periods without any raw values are excluded from results. |
| PREVIOUS | Set value for the period based on the previous period's value. |
| NEXT | Set value for the period based on the next period's value. |
| LINEAR | Calculate period value using linear interpolation between previous and next period values. |
| VALUE| Set value for the period to a specific number. |

```sql
SELECT entity, period(5 MINUTE), avg(value)
  FROM "mpstat.cpu_busy" WHERE time > current_hour 
GROUP BY entity, period(5 MINUTE, LINEAR)
```

> The interpolation function is applied after HAVING filter.

[Interpolation Examples in Chartlab](https://apps.axibase.com/chartlab/d8c03f11/3/)

## Grouping

`GROUP BY` clause groups records into rows that have matching values for the specified grouping columns.

```sql
SELECT entity, avg(value) AS Cpu_Avg 
  FROM "mpstat.cpu_busy"
WHERE entity IN ('nurswgvml007', 'nurswgvml006', 'nurswgvml011') 
  AND time > current_hour
GROUP BY entity
```

```ls
entity | Cpu_Avg
nurswgvml006 | 7.81
nurswgvml007 | 9.68
```

ATSD provides a special grouping column `PERIOD` which calculates the start of the interval to which the record belongs.

```sql
SELECT datetime, avg(value) AS Cpu_Avg 
  FROM "mpstat.cpu_busy"
WHERE entity IN ('nurswgvml007', 'nurswgvml006', 'nurswgvml011') 
  AND time > current_hour
GROUP BY period(5 MINUTE)
```

```ls
datetime | Cpu_Avg
2016-06-17T11:00:00.000Z | 8.15
2016-06-17T11:05:00.000Z | 5.39
2016-06-17T11:10:00.000Z | 4.96
```

### HAVING filter

The `HAVING` clause enables filtering of grouped rows.

```sql
SELECT entity, avg(value) AS Cpu_Avg 
  FROM "mpstat.cpu_busy"
WHERE entity IN ('nurswgvml007', 'nurswgvml006', 'nurswgvml011') 
  AND time > current_hour
GROUP BY entity
  HAVING avg(value) > 8
```

```ls
entity | Cpu_Avg
nurswgvml007 | 9.68
```

## Ordering

Rows returned in the resultset can be sorted in ascending or descending order with `ORDER BY` clause.

```sql
SELECT entity, avg(value) FROM "mpstat.cpu_busy"
  WHERE time > current_day
  GROUP BY entity 
ORDER BY avg(value) DESC
```

```ls
entity | avg(value)
nurswgvml006 | 22.9
nurswgvml007 | 9.9
nurswgvml010 | 6.5
nurswgvml011 | 5.5
```

In combination with `LIMIT`, ordering can be used to execute **top-N** queries.

```sql
SELECT entity, avg(value) FROM "mpstat.cpu_busy"
  WHERE time > current_day
  GROUP BY entity 
ORDER BY avg(value) DESC
  LIMIT 2
```

```ls
| entity | avg(value) |
|nurswgvml006 | 22.9 |
|nurswgvml007 | 9.9 |
```

## Joins

Data for multiple tables (metrics) can be merged by joining virtual tables with `JOIN`, also known as `INNER JOIN`, and `OUTER JOIN` clauses.

### JOIN

Unlike standard SQL, `JOIN` clauses in ATSD do not support any conditions (columns) on which to perform a join. Instead the rows are joined by entity, row (or period) time, and all series tags (if present).

```sql
SELECT datetime, entity, t1.value, t2.value, t3.value
  FROM "cpu_system" t1
  JOIN "cpu_user" t2 
  JOIN "cpu_iowait" t3
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

Since timestamps for each of metric are identical in this particular case, `JOIN` produces merged rows for all the detailed records which have equivalent values for entity and time.

```ls
datetime | entity | t1.value | t2.value | t3.value
2016-06-16T13:00:01.000Z | nurswgvml006 | 13.3 | 21.0 | 2.9
2016-06-16T13:00:17.000Z | nurswgvml006 | 1.0 | 2.0 | 13.0
2016-06-16T13:00:33.000Z | nurswgvml006 | 0.0 | 1.0 | 0.0
```

However, when merging records for irregular metrics collected by independent scripts, `JOIN` results will contain a small subset of rows with coincidentally identical times.

```sql
SELECT datetime, entity, t1.value as cpu, t2.value as mem
  FROM "mpstat.cpu_busy" t1 
  JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

The result contains only 2 records out of 75. This is because for `JOIN` to merge detailed records from multiple metrics into one row, the records should have the same time. 

```ls
datetime | entity | cpu | mem
2016-06-16T13:02:57.000Z | nurswgvml006 | 16.0 | 74588.0
2016-06-16T13:07:17.000Z | nurswgvml006 | 16.0 | 73232.0
```

This is typically the case when multiple metrics are inserted with one command or when time is controlled externally, as in the example above, where metrics 'cpu_system', 'cpu_user', 'cpu_iowait' are all timestamped by the same collector script with the same time during each `mpstat` command invocation.

Similarly, multiple metrics can be merged for series with tags, without the need to enumerate all possible tags in the join condition.

```sql
SELECT datetime, entity, t1.value, t2.value, t1.tags.file_system, t1.tags.mount_point
  FROM "df.disk_used" t1
  JOIN "df.disk_used_percent" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

```ls	
datetime | entity | t1.value | t2.value | t1.tags.file_system | t1.tags.mount_point
2016-06-16T13:00:14.000Z | nurswgvml006 | 1743057372 | 83 | //u113452.nurstr021/backup | /mnt/u113452
2016-06-16T13:00:29.000Z | nurswgvml006 | 1743057372 | 83 | //u113452.nurstr021/backup | /mnt/u113452
2016-06-16T13:00:44.000Z | nurswgvml006 | 1743057372 | 83 | //u113452.nurstr021/backup | /mnt/u113452
```

### OUTER JOIN

To combine all records from joined tables, use `OUTER JOIN` which similarly to `JOIN` returns rows equal entity, series tags, and time values and also returns those rows from one table for which no rows from the other satisfy the join condition.

```sql
SELECT datetime, entity, t1.value as cpu, t2.value as mem
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

`OUTER JOIN` for detailed records, without period aggregation, produces rows that have NULLs in value columns because the underlying metric didn't record any value at the specified time.

```ls
datetime | entity | cpu | mem
2016-06-16T13:02:41.000Z | nurswgvml006 | 3.0 | null
2016-06-16T13:02:42.000Z | nurswgvml006 | null | 70652.0
2016-06-16T13:02:57.000Z | nurswgvml006 | 16.0 | 74588.0
2016-06-16T13:03:12.000Z | nurswgvml006 | null | 72536.0
2016-06-16T13:03:13.000Z | nurswgvml006 | 7.1 | null
2016-06-16T13:03:27.000Z | nurswgvml006 | null | 71676.0
```

To regularize the series, apply `GROUP BY` with period aggregation and apply one of statistical functions to return one value for the period, for each series.

```sql
SELECT datetime, entity, avg(t1.value) as avg_cpu, avg(t2.value) as avg_mem
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:02:40.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
  GROUP BY entity, period(1 MINUTE)
```

```ls
datetime | entity | avg_cpu | avg_mem
2016-06-16T13:02:00.000Z | nurswgvml006 | 9.5 | 72620.0
2016-06-16T13:03:00.000Z | nurswgvml006 | 6.1 | 70799.0
2016-06-16T13:04:00.000Z | nurswgvml006 | 15.1 | 71461.0
2016-06-16T13:05:00.000Z | nurswgvml006 | 93.3 | 69193.0
```

Choice of statistical function(s) depends on the use case. While `AVG` and percentile functions would smooth the values, `LAST` or `FIRST` functions can be applied to select a subset of raw values.

```sql
SELECT datetime, entity, LAST(t1.value) as cpu, LAST(t2.value) as mem
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:02:40.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
  GROUP BY entity, period(1 MINUTE)
```

## Query URL

API SQL endpoint is located at: `https://atsd_server:8443/sql?q={QUERY}`

The `{QUERY}` parameter value must be URL-encoded.

### Query URL Example

```elm
https://atsd_server:8443/sql?q=SELECT+time%2C+value%2C+entity+FROM+mpstat.cpu_busy+WHERE+time+%3E%3D+previous_hour
```

## Authorization

The rows returned for the SQL query are filtered on the server according to the user's entity read permissions.

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

The functions accept `value` or a numeric expression as a parameter, for example  `avg(value)` or `avg(t1.value + t2.value)`.
The PERCENTILE function accepts `percentile` parameter (0 to 100) and `value` parameter, for example `percentile(75, value)`.

## Time Formatting Functions

`date_format` function formats Unix millisecond time to a string in user-defined date format. 

```java
date_format(long milliseconds[, time format])
```

If the time format argument is not provided, ISO 8601 format is applied.

Examples:

* `date_format(time)`
* `date_format(max_value_time(value))`
* `date_format(time, 'yyyy-MM-dd HH:mm:ss')`

```sql
SELECT value, time, date_format(time), 
  date_format(time, "yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
  date_format(time, 'yyyy-MM-dd HH:mm:ss.SSS')
FROM "mpstat.cpu_busy"
  WHERE datetime > now - 5 * minute
  LIMIT 1
```

```ls
value | time | date_format(time) | date_format(time,'yyyy-MM-dd'T'HH:mm:ss.SSSZ') | date_format(time,'yyyy-MM-dd HH:mm:ss.SSS')
5.0 | 1466166325000 | 2016-06-17T12:25:25.000Z | 2016-06-17T12:25:25.000+0000 | 2016-06-17 12:25:25.000
```

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
- [Select All](examples/select-all.md)
- [Select Entity Tags As Columns](examples/select-entity-tags-as-columns.md)
- [Time Condition](examples/time-condition.md)
- [Order By Time](examples/order-by-time.md)
- [Entity with Tags](examples/entity-with-tags.md)
- [Filter by Tag](examples/filter-by-tag.md)
- [Group by Query with Order By](examples/group-by-query-with-order-by.md)
- [Grouped Average](examples/grouped-average.md)
- [Last Time](examples/last-time.md)
- [Max Value Time](examples/max-value-time.md)
- [Not Equal Operator](examples/not-equal-operator.md)
- [Join](examples/inner-join.md)
- [Join Using Entity](examples/using-entity.md)
- [Outer Join With Aggregation](examples/outer-join-with-aggregation.md)
- [Outer Join](examples/outer-join.md)
- [Row Number Function](examples/row-number-function.md)
- [Row Number With Order By Avg](examples/row-number-with-order-by-avg.md)
- [Row Number With Order By Time & Avg](examples/row-number-with-order-by-time&avg.md)
- [Row Number With Order By Time](examples/row-number-with-order-by-time.md)



