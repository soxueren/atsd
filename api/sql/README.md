# Overview

Axibase Time Series Database supports SQL query language for retrieving time series data.

The SQL statements can be executed interactively via the SQL console as well as on schedule. 

Scheduled execution allows for generated report files to be distributed to email subscribers or stored on a local file system.

The data returned by SQL statements can be exported in the following formats:

|**Endpoint**|**Formats**|
|:---|:---|
|API  |CSV, JSON|
|Web Interface  |CSV, JSON, HTML|
|Scheduler|CSV, JSON, Excel|

## Syntax

The database supports only `SELECT` statements at this time. 

The `SELECT` statement consists of a `SELECT` expression, a `FROM` query, a `WHERE` clause, and other optional clauses for grouping, filtering, and ordering the results.

```sql
SELECT datetime, entity, value -- SELECT expression
  FROM "mpstat.cpu_busy" -- query
  WHERE datetime > now - 1 * HOUR -- clause(s)
```

### SELECT expression

The SELECT expression consists of one or multiple columns and expressions applied to query results.

### Query

The `FROM` query can reference virtual tables that correspond to metric names.

Virtual table represents a subset of records for the given metric stored by the database in the shared physical table.

```sql
SELECT datetime, entity, value 
  FROM "mpstat.cpu_busy" 
  WHERE datetime > now - 1*MINUTE
```

In the example above, "mpstat.cpu_busy" table contains records for `mpstat.cpu_busy` metric.

Virtual tables are currently supported only for series. Access to properties, messages, and alerts is currently not available.

### WHERE Clause

The `WHERE` clause is a condition which rows must satisfy in order to match the query. 

Columns referenced in the `WHERE` clause are replaced by their value for the given row. The clause is then evaluated for each row, and if true, the row is included in the result set.  

Typically the `WHERE` clause includes a [time condition](#time-condition) for which the data must be analyzed, although this is not required.

The clause can be built from multiple conditions each comparing values using common comparison operators: `<, >, <=, >=, =, and !=`. The result of comparison is a boolean value, true/false, whereas conditions can be combined by the logical operators `AND`, `OR`, and `NOT`.  `AND` takes precedence over `OR` and `NOT` takes precedence of both. Arithmetic operators such as `*`, `-`, `+`, `/` may be applied to values before they are compared.

```sql
SELECT entity, datetime, value, tags.*
  FROM disk_used
WHERE datetime > now - 15 * minute
  AND (entity IN ('nurswgvml007', 'nurswgvml010') 
       OR tags.file_system LIKE '/dev/*'
       OR value/1024 > 100000)
```

### Other Clauses

* **JOIN / OUTER JOIN**
* **GROUP BY**
* **HAVING**
* **ORDER BY**
* **LIMIT** [offset,] count

### Functions

* **ROW_NUMBER** returns row index within each partition.
* **LAST_TIME** returns last insert time in millisecond for each series

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

The full list of predefined columns may be requested with `SELECT *` syntax, except for queries with `GROUP BY` clause.

```sql
SELECT * FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

```ls
| cpu_busy.entity | memfree.entity | cpu_busy.time | memfree.time  | cpu_busy.value | memfree.value | 
|-----------------|----------------|---------------|---------------|----------------|---------------| 
| nurswgvml006    | nurswgvml006   | 1466082001000 | 1466082001000 | 37.1           | null          | 
| nurswgvml006    | nurswgvml006   | 1466082012000 | 1466082012000 | null           | 67932.0       | 
| nurswgvml006    | nurswgvml006   | 1466082017000 | 1466082017000 | 16.0           | null          | 
| nurswgvml006    | nurswgvml006   | 1466082027000 | 1466082027000 | null           | 73620.0       | 
```

### Series Tag Columns

Tag values are referenced in `SELECT` expression by specifying `tags.*`, `tags`, or `tags.{tag-name}` as column name.

`tags` is a map object whose properties can be accessed with key. When specified in SELECT expression, tags.* creates multiple columns for each key in the map. If the property is not present, the `tags.{tag-name}` expression returns NULL.

* `tags.*` expands to multiple columns
* `tags` concatenates tags to `name1=value1;name2=value2` format
* `tags.{tag-name}` creates a column for a specific tag

```sql
SELECT datetime, entity, value, tags.*, tags, tags.mount_point, tags.file_system
  FROM disk_used 
WHERE entity = 'nurswgvml010' AND datetime > now - 1*MINUTE
  ORDER BY time
```

```ls
| datetime                 | entity       | value      | tags.mount_point | tags.file_system | tags                                   | tags.mount_point | tags.file_system | 
| 2016-06-18T11:22:35.000Z | nurswgvml010 | 6478200.0  | /                | /dev/sda1        | file_system=/dev/sda1;mount_point=/    | /                | /dev/sda1        | 
| 2016-06-18T11:22:35.000Z | nurswgvml010 | 30440664.0 | /app             | /dev/sdb1        | file_system=/dev/sdb1;mount_point=/app | /app             | /dev/sdb1        | 
```

`tags` and `tags.{tag-name}` syntax can also be used in `WHERE`, `ORDER`, `GROUP BY` and other clauses.

```sql
SELECT entity, count(value), tags.*
 FROM disk_used
WHERE datetime > now - 5 * minute
 AND entity = 'nurswgvml010'
 AND tags.mount_point = '/'
 GROUP BY entity, tags
```

```ls
| entity       | count(value) | tags.mount_point | tags.file_system | 
|--------------|--------------|------------------|------------------| 
| nurswgvml010 | 20.0         | /                | /dev/sda1        | 
```

### Entity Tag Columns

Entity tag values can be included in `SELECT` expression by specifying `entity.tags.{tag-name}` as column name.

`tags` is a map object whose properties can be accessed with key. If the property is not present, the `entity.tags.{tag-name}` expression returns NULL.

```sql
SELECT entity, entity.tags.os, entity.tags.app, avg(value) 
  FROM "mpstat.cpu_busy"
WHERE time > current_hour
  GROUP BY entity 
```

```ls
| entity       | entity.tags.os | entity.tags.app       | avg(value) | 
|--------------|----------------|-----------------------|------------| 
| nurswgvml006 | Linux          | Hadoop/HBASE          | 99.8       | 
| nurswgvml007 | Linux          | ATSD                  | 16.0       | 
| nurswgvml010 | Linux          | SVN, Jenkins, Redmine | 3.9        | 
| nurswgvml011 | Linux          | HMC Simulator, mysql  | 5.9        | 
| nurswgvml102 | Linux          | Router                | 1.2        | 
| nurswgvml502 | null           | null                  | 3.1        | 
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

Versioning columns (`version_status`, `version_source`, `version_time`, `version_datetime`) are currently not supported.

## Aliases

Table and column aliases can be unquoted or enclosed in quotes or double-quotes.

Unquoted alias should start with letter [a-zA-Z], followed by letter, digit or underscore.

`AS` keyword is optional.

```sql
SELECT tbl.value*100 AS "cpu_percent", tbl.time 'sample-time'
  FROM "mpstat.cpu_busy" tbl 
WHERE datetime > now - 1*MINUTE
```

For aliased columns, the underlying column and table names, or expression text are included in table schema section of the metadata.

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

`?` and `*` wildcards are supported in `LIKE` expressions with backslash available as an escape character.

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
SELECT datetime, entity, value 
  FROM "mpstat.cpu_busy" 
WHERE time >= 1465685363345 AND datetime < '2016-06-10T14:00:15.020Z'
```

Both columns support [End Time](/end-time-syntax.md) syntax.

```sql
SELECT datetime, entity, value 
  FROM "mpstat.cpu_busy" 
WHERE time >= previous_minute
```

## Period

Period is a repeating time interval used to group detailed values occurred in the period into buckets in order to apply aggregation functions.

Period syntax:

```sql
PERIOD({count} {unit} [, interpolation [, align]])
```

| **Name** | **Description** |
|:---|:---|
| count  | [**Required**] Number of time units contained in the period. |
| unit  | [**Required**] [Time unit](/api/series/time-unit.md) such as `MINUTE`, `HOUR`, `DAY`. |
| interpolation  | Interpolation function, such as `LINEAR`. Default: `NONE`. Refer to [interpolation](#interpolation). |
| align| Alignment of the period's start/end. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`.<br>Refer to [period alignment](#period-alignment).|


```sql
SELECT entity, date_format(PERIOD(5 minute, NONE, END_TIME)), AVG(value) 
  FROM gc_invocations_per_minute 
  WHERE time >= current_hour AND time < next_hour
  GROUP BY entity, PERIOD(5 minute, NONE, END_TIME)
```

The period specified in `GROUP BY` clause can be entered without _align_ and _interpolation_ fields in `SELECT` statement:

```sql
SELECT entity, date_format(PERIOD(5 minute)), AVG(value) 
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
| entity       | Cpu_Avg | 
|--------------|---------| 
| nurswgvml006 | 99.8    | 
| nurswgvml007 | 15.2    | 
| nurswgvml011 | 5.7     | 
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
| datetime                 | Cpu_Avg | 
|--------------------------|---------| 
| 2016-06-18T22:00:00.000Z | 43.2    | 
| 2016-06-18T22:05:00.000Z | 35.3    | 
| 2016-06-18T22:10:00.000Z | 5.0     | 
```

### HAVING filter

The `HAVING` clause enables filtering of grouped rows.

```sql
SELECT entity, avg(value) AS Cpu_Avg 
  FROM "mpstat.cpu_busy"
WHERE entity IN ('nurswgvml007', 'nurswgvml006', 'nurswgvml011') 
  AND time > current_hour
GROUP BY entity
  HAVING avg(value) > 10
```

```ls
| entity       | Cpu_Avg | 
|--------------|---------| 
| nurswgvml006 | 99.8    | 
| nurswgvml007 | 14.3    | 
```

## Partitioning

Partitioning is implemented with `ROW_NUMBER()` function which returns sequential number of a row within a partition of result set, starting at 1 for the first row in each partition.

Partition is a subset of all rows in the resultset grouped by equal values of partitioning columns.

For example, assuming that the below resultset was partitioned by entity and then ordered by time within each partition, the row numbers would be as follows: 

```ls
|--------------|--------------------------|------:| ROW_NUMBER
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  |     1
| nurswgvml006 | 2016-06-18T12:00:21.000Z | 8.1   |     2
| nurswgvml007 | 2016-06-18T12:00:03.000Z | 18.2  |     1
| nurswgvml007 | 2016-06-18T12:00:19.000Z | 67.7  |     2
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   |     1
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 |     1
| nurswgvml011 | 2016-06-18T12:00:26.000Z | 4.0   |     2
| nurswgvml011 | 2016-06-18T12:00:29.000Z | 0.0   |     3
```

### ROW_NUMBER Syntax

```sql
ROW_NUMBER({partitioning columns} ORDER BY {ordering columns [direction]})
```

* {partitioning columns} can be `entity`, `tags`, or `entity, tags`
* {ordering columns [direction]} can be any in the `FROM` clause with optional ASC|DESC direction.

Examples:

* `ROW_NUMBER(entity ORDER BY time)`
* `ROW_NUMBER(entity, tags ORDER BY time DESC)`
* `ROW_NUMBER(entity, tags ORDER BY time DESC, avg(value))`
 
 The returned number can be used to filter rows within each partition, for example to return only top-N records from each partition:

```sql
SELECT entity, datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= "2016-06-18T12:00:00.000Z" AND datetime < "2016-06-18T12:00:30.000Z"
  WITH ROW_NUMBER(entity ORDER BY time) <= 1
  ORDER BY entity, time
```

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|-------| 
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  | 
| nurswgvml007 | 2016-06-18T12:00:03.000Z | 18.2  | 
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   | 
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 | 
| nurswgvml102 | 2016-06-18T12:00:02.000Z | 0.0   | 
| nurswgvml502 | 2016-06-18T12:00:01.000Z | 13.7  | 
```

## Ordering

The default sort order is undefined. Ordering can be applied by specifying `ORDER BY` clause.

```sql
SELECT entity, avg(value) FROM "mpstat.cpu_busy"
  WHERE time > current_day
  GROUP BY entity 
ORDER BY avg(value) DESC, entity
```

```ls
| entity       | avg(value) | 
|--------------|------------| 
| nurswgvml006 | 19.2       | 
| nurswgvml007 | 13.2       | 
| nurswgvml011 | 5.1        | 
| nurswgvml010 | 4.3        | 
| nurswgvml502 | 4.3        | 
| nurswgvml102 | 1.2        | 
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
| entity       | avg(value) | 
|--------------|------------| 
| nurswgvml006 | 19.3       | 
| nurswgvml007 | 13.2       | 
```

## Joins

Data for multiple virtual tables can be merged with `JOIN` and `OUTER JOIN` clauses.

The syntax follows the SQL-92 notation using the JOIN clause as opposed to enumerating columns in WHERE clause according to ANSI-89.

Since joined tables always contain the same predefined columns, join condition doesn't have to be specified explicitly, similar to NATURAL JOIN in standard SQL:

| **ATSD SQL** | **Standard SQL Equivalent** |
|:---|---|
| JOIN | JOIN ON t1.time AND t2.time AND t1.entity = t2.entity AND t1.tags = t2.tags |
| JOIN USING entity | JOIN ON t1.time AND t2.time AND t1.entity = t2.entity |
| OUTER JOIN | FULL OUTER JOIN ON t1.time AND t2.time AND t1.entity = t2.entity AND t1.tags = t2.tags |
| OUTER JOIN USING entity | FULL OUTER JOIN ON t1.time AND t2.time AND t1.entity = t2.entity |

Because join queries combine rows from multiple virtual tables with the same columns, it is necessary to disambiguate references to these columns in the `SELECT` expression by prepending table name followed by dot in front of the column name.

### JOIN

`JOIN` clause allows merging records for multiple metrics into one resultset, even if data collected for the underlying series is not synchronized on time.

The default `JOIN` condition is time, entity and series tags. The condition can be modified with `USING entity` clause in which case series tags are ignored, and records are joined on time and entity instead.

```sql
SELECT datetime, entity, t1.value, t2.value, t3.value
  FROM "cpu_system" t1
  JOIN "cpu_user" t2 
  JOIN "cpu_iowait" t3
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

In this particular case, since timestamps for each of these metrics are identical, being collected by the same script, `JOIN` produces merged rows for all the detailed records.

```ls
| datetime                 | entity       | t1.value | t2.value | t3.value | 
|--------------------------|--------------|----------|----------|----------| 
| 2016-06-16T13:00:01.000Z | nurswgvml006 | 13.3     | 21.0     | 2.9      | 
| 2016-06-16T13:00:17.000Z | nurswgvml006 | 1.0      | 2.0      | 13.0     | 
| 2016-06-16T13:00:33.000Z | nurswgvml006 | 0.0      | 1.0      | 0.0      | 
```

This is typically the case when multiple metrics are inserted with one command or when time is controlled externally, as in the example above, where metrics 'cpu_system', 'cpu_user', 'cpu_iowait' are all timestamped by the same collector script with the same time during each `mpstat` command invocation.

However, when merging records for irregular metrics collected by independent sources, `JOIN` results may contain a small subset of rows with coincidentally identical times.

```sql
SELECT datetime, entity, t1.value as cpu, t2.value as mem
  FROM "mpstat.cpu_busy" t1 
  JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

The result contains only 2 records out of 75 total. This is because for `JOIN` to merge detailed records from multiple metrics into one row, the records should have the same time. 

```ls
| datetime                 | entity       | cpu  | mem     | 
|--------------------------|--------------|------|---------| 
| 2016-06-16T13:02:57.000Z | nurswgvml006 | 16.0 | 74588.0 | 
| 2016-06-16T13:07:17.000Z | nurswgvml006 | 16.0 | 73232.0 | 
```


Similarly, multiple tables can be merged for series with tags, without the need to enumerate all possible tags in the join condition.

```sql
SELECT datetime, entity, t1.value, t2.value, t1.tags.*
  FROM "df.disk_used" t1
  JOIN "df.disk_used_percent" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

```ls	
| datetime                 | entity       | t1.value     | t2.value | t1.tags.file_system             | t1.tags.mount_point | 
|--------------------------|--------------|--------------|----------|---------------------------------|---------------------| 
| 2016-06-16T13:00:14.000Z | nurswgvml006 | 1743057408.0 | 83.1     | //u113452.nurstr003/backup | /mnt/u113452             | 
| 2016-06-16T13:00:29.000Z | nurswgvml006 | 1743057408.0 | 83.1     | //u113452.nurstr003/backup | /mnt/u113452             | 
| 2016-06-16T13:00:44.000Z | nurswgvml006 | 1743057408.0 | 83.1     | //u113452.nurstr003/backup | /mnt/u113452             | 
| 2016-06-16T13:00:59.000Z | nurswgvml006 | 1743057408.0 | 83.1     | //u113452.nurstr003/backup | /mnt/u113452             | 
```

### JOIN with USING entity

`USING entity` modifies the default `JOIN` condition. 

When `USING entity` is specified, rows are joined by entity and time instead of entity, time, and series tags.

This allows merging of virtual tables with different tag columns, including merging a table without tag column with a series containing multiple tag columns.

`USING entity` is supported by both inner and outer JOIN.

```sql
SELECT entity, datetime, AVG(t1.value), AVG(t2.value), tags.*
  FROM mpstat.cpu_busy t1
JOIN USING entity disk_used t2
  WHERE time > current_hour
  AND entity = 'nurswgvml007' 
GROUP BY entity, tags, period(1 minute)
```

```ls
| entity       | datetime                 | AVG(t1.value) | AVG(t2.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------:|--------------:|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-18T10:03:00.000Z | 100.0         | 1744011571.0  | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007 | 2016-06-18T10:03:00.000Z | 100.0         | 8686400.0     | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
```

### OUTER JOIN

To combine all records from joined tables, use `OUTER JOIN` which returns rows with equal time, entity, and tags as well as rows from one table for which no rows from the other satisfy the join condition.

```sql
SELECT datetime, entity, t1.value as cpu, t2.value as mem
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

`OUTER JOIN` for detailed records, without period aggregation, produces rows that have NULLs in value columns because the underlying metric didn't record any value at the specified time.

```ls
| datetime                 | entity       | cpu  | mem     | 
|--------------------------|--------------|------|---------| 
| 2016-06-16T13:00:01.000Z | nurswgvml006 | 37.1 | null    | 
| 2016-06-16T13:00:12.000Z | nurswgvml006 | null | 67932.0 | 
| 2016-06-16T13:00:17.000Z | nurswgvml006 | 16.0 | null    | 
| 2016-06-16T13:00:27.000Z | nurswgvml006 | null | 73620.0 | 
| 2016-06-16T13:00:33.000Z | nurswgvml006 | 1.0  | null    | 
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
| datetime                 | entity       | avg_cpu | avg_mem  | 
|--------------------------|--------------|---------|----------| 
| 2016-06-16T13:02:00.000Z | nurswgvml006 | 9.5     | 72620.0  | 
| 2016-06-16T13:03:00.000Z | nurswgvml006 | 6.1     | 70799.0  | 
| 2016-06-16T13:04:00.000Z | nurswgvml006 | 15.1    | 71461.0  | 
```

Choice of statistical function to use for value columns depends on the use case. 

`AVG` and percentile functions would smooth the values, whereas `LAST` or `FIRST` functions would select a subset of raw values in a downsampling effect.

```sql
SELECT datetime, entity, LAST(t1.value) as cpu, LAST(t2.value) as mem
  FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "meminfo.memfree" t2
WHERE datetime >= '2016-06-16T13:02:40.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
  GROUP BY entity, period(1 MINUTE)
```

>  Note that records returned by a `JOIN USING entity` condition include series with last insert date greater than start date specified in the query.

## Query Performance

The most efficient query path is **metric+entity+date+tags**.

Query execution speed can be improved by adopting the following guidelines for the `WHERE` clause:

* Specify start time and end time whenever possible to limit the scan range.
* Specify entity name whenever possible to avoid a scan of all rows in the virtual table.

## Query URL

API SQL endpoint is located at: `https://atsd_server:8443/sql?q={QUERY}`

The `{QUERY}` parameter value must be URL-encoded.

### Query URL Example

```elm
https://atsd_server:8443/sql?q=SELECT+time%2C+value%2C+entity+FROM+mpstat.cpu_busy+WHERE+time+%3E%3D+previous_hour
```

## Authorization

The rows returned for the SQL query are filtered by the server according to the user's entity read permissions.

This means that the same query executed by users with different permissions may produce different results.

Scheduled queries are always executed under administrative permissions.

## Keywords

|             |             |             |             |
|-------------|-------------|-------------|-------------| 
| AND         | AS          | ASC         | BETWEEN     | 
| BY          | DESC        | FROM        | GROUP       | 
| HAVING      | IN          | INNER       | INTERPOLATE | 
| JOIN        | LIKE        | LIMIT       | NOT         | 
| OR          | ORDER       | OUTER       | PERIOD      | 
| REGEX       | ROW_NUMBER  | SELECT      | USING       | 
| VALUE       | WHERE       | WITH        |             | 

## Aggregation Functions

The following functions aggregate values in a column by producing a single value from a list of values appearing in a column.

|                |                |                |                | 
|----------------|----------------|----------------|----------------| 
| SUM            | AVG            | MIN            | MAX            | 
| COUNT          | COUNTER        | DELTA          | FIRST          | 
| LAST           | MAX_VALUE_TIME | MIN_VALUE_TIME | PERCENTILE     | 
| STDDEV         | WAVG           | WTAVG          |                |


The functions accept `value` as column name or a numeric expression as a parameter, for example  `avg(value)` or `avg(t1.value + t2.value)`.
The PERCENTILE function accepts `percentile` parameter (0 to 100) and `value` column name, for example `percentile(75, value)`.
The COUNT function counts the number of rows in the resultset and accepts *, for example `COUNT(*)`.


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
| value | time          | date_format(time)        | date_format(time,'yyyy-MM-dd'T'HH:mm:ss.SSSZ') | date_format(time,'yyyy-MM-dd HH:mm:ss.SSS') | 
|-------|---------------|--------------------------|------------------------------------------------|---------------------------------------------| 
| 7.1   | 1466287841000 | 2016-06-18T22:10:41.000Z | 2016-06-18T22:10:41.000+0000                   | 2016-06-18 22:10:41.000                     | 
```

## Case Sensitivity

SQL keywords are case-insensitive.

Entity names, metric names, and tag names are also case-insensitive. 

Tag values are **case-sensitive**.

```
SELECT metric, entity, datetime, value, tags.*
  FROM Disk_Used
WHERE datetime > now - 5 * minute
  AND entity = 'NurSwgvml007' 
  AND tags.file_system = '/dev/mapper/vg_nurswgvml007-lv_root'
```

```ls
| metric    | entity       | datetime                 | value     | tags.mount_point | tags.file_system                    | 
|-----------|--------------|--------------------------|-----------|------------------|-------------------------------------| 
| disk_used | nurswgvml007 | 2016-06-19T06:12:26.000Z | 8715136.0 | /                | /dev/mapper/vg_nurswgvml007-lv_root | 
```

Changing the case of tag value condition `tags.file_system = '/DEV/mapper/vg_nurswgvml007-lv_root'` would cause an error **TAG_VALUE not found**.

## Unsupported Standard SQL Features

While the [differences](https://github.com/axibase/atsd-jdbc#database-capabilities) between SQL dialect implemented in ATSD and standard SQL are numerous, the following exceptions to widely used constructs are worth mentioning:

* Subqueries are not supported.
* `WITH` operator is supported only in `WITH ROW_NUMBER` clause.
* `UNION`, `EXCEPT` and `INTERSECT` operators are not supported.
* Ordering with column numbers, for example `ORDER BY 2, 1` is not supported.
* `DISTINCT` operator are not supported.

In particular cases it's possible to emulate DISTINCT operator with GROUP BY clause, for example, the following query would produce the same results as DISTINCT:

```sql
SELECT entity
  FROM "mpstat.cpu_busy"
WHERE time > now - 1 * MINUTE
  GROUP BY entity
```

```sql
SELECT DISTINCT entity
  FROM "mpstat.cpu_busy"
WHERE time > now - 1 * MINUTE
```

## Non-standard Features

* Numeric operations on NULL produce NaN instead of NULL in standard SQL

```sql
SELECT datetime, entity, t1.value, t2.value, t1.value-t2.value
FROM "mpstat.cpu_busy" t1 
  OUTER JOIN "mmeinfo.memfree" t2
WHERE datetime >= '2016-06-16T13:00:00.000Z' AND datetime < '2016-06-16T13:10:00.000Z'
  AND entity = 'nurswgvml006'
```

```ls
| datetime                 | entity       | t1.value | t2.value | t1.value-t2.value | 
|--------------------------|--------------|----------|----------|-------------------| 
| 2016-06-16T13:00:01.000Z | nurswgvml006 | 37.1     | null     | NaN               | 
| 2016-06-16T13:00:12.000Z | nurswgvml006 | null     | 67932.0  | NaN               | 
```

## Examples

- [Alias](examples/alias.md)
- [Average Value](examples/average-value.md)
- [Counter Aggregator](examples/counter-aggregator.md)
- [Datetime Format](examples/datetime-format.md)
- [Select All](examples/select-all.md)
- [Select Entity Tags As Columns](examples/select-entity-tags-as-columns.md)
- [Time Condition](examples/time-condition.md)
- [Order By Time](examples/order-by-time.md)
- [Filter by Tag](examples/filter-by-tag.md)
- [All Tags](examples/all-tags.md)
- [Select series without Tag](examples/filter-null-tag.md)
- [Group by Query with Order By](examples/group-by-query-with-order-by.md)
- [Grouped Average](examples/grouped-average.md)
- [Group with Having](examples/group-having.md)
- [Last Time](examples/last-time.md)
- [Max Value Time](examples/max-value-time.md)
- [Grouped and Having](examples/grouped-having.md)
- [Join](examples/join.md)
- [Join Using Entity](examples/join-using-entity.md)
- [Outer Join With Aggregation](examples/outer-join-with-aggregation.md)
- [Outer Join](examples/outer-join.md)
- [Partitioning using Row Number Function](examples/row-number.md)
- [Top-N Query using Row Number Function](examples/row-number-top-N-tags.md)



