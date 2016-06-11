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

Data for multiple metrics can be merged by joining virtual tables with JOIN clause.

```sql
SELECT datetime, entity, t1.value, t2.value, t3.value
  FROM "cpu_system" t1
  JOIN "cpu_user" t2 
  JOIN "cpu_iowait" t3
  WHERE datetime > now - 1*MINUTE
```

Virtual tables are provided only for series. Access to other types of data (properties, messages, alerts) and meta-data is currently not available.

### Predefined Columns

Since the underlying data is physically stored in the same shared partitioned table, all virtual tables have the same set of pre-defined columns:

|**Name**|**Type**|**Description**|
|:---|:---|:---|
|`metric`|string|Metric name, same as virtual table name.|
|`entity`|string|Entity name.|
|`tags` *or* `tags.{name}`|object|Object containing series tags, where name is tag name and a value is tag value.<br>`tags.*` syntax is supported only in SELECT clause.|
|`time`|long|Time in Unix milliseconds|
|`date`|string|Date in ISO 8601 format|
|`value`|number|Recorded value|

New columns can be created with expressions:

```sql
SELECT datetime, entity, t1.value + t2.value as cpu_sysusr
  FROM "cpu_system" t1
  JOIN "cpu_user" t2
  WHERE datetime > now - 1*MINUTE
```

## Time Condition

Time condition is specified in WHERE clause using `time` or `datetime` columns.

The `time` column accepts Unix milliseconds whereas `datetime` column accepts literal date in ISO 8601 format.

```sql
SELECT datetime, entity, value FROM "cpu_busy" WHERE time >= 1465685363345 AND datetime < '2016-06-10T14:00:15.020Z'
```

Both columns support [End Time](/end-time-syntax.md) syntax.

```sql
SELECT datetime, entity, value FROM "cpu_busy" WHERE time >= previous_minute
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

- [Alias](alias.md)
- [Average Value](average-value-query.md)
- [Basic](basic-query.md)
- [Counter Aggregator](counter-aggregator.md)
- [Datetime Format](datetime-format.md)
- [Entity with Tags](entity-with-tags.md)
- [Filter by Tag](filter-by-tag.md)
- [Group by Query with Order By](group-by-query-with-order-by.md)
- [Grouped Average](grouped-average.md)
- [Inner Join](inner-join.md)
- [Last Time](last-time.md)
- [Max Value Time](max-value-time.md)
- [Not Equal Operator](not-equal-operator.md)
- [Order By Time](order-by-time.md)
- [Outer Join With Aggregation](outer-join-with-aggregation.md)
- [Outer Join](outer-join.md)
- [Query Format](query-format.md)
- [Row Number Function](row-number-function.md)
- [Row Number With Order By Avg](row-number-with-order-by-avg.md)
- [Row Number With Order By Time & Avg](row-number-with-order-by-time&avg.md)
- [Row Number With Order By Time](row-number-with-order-by-time.md)
- [Select All](select-all.md)
- [Select Entity Tags As Columns](select-entity-tags-as-columns.md)
- [Time Condition](time-condition.md)
- [Using Entity](using-entity.md)

