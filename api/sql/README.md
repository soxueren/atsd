## Overview

Axibase Time Series Database supports an SQL like language to query the database. SQL is supported in the API and an SQL console is also available in the ATSD UI.

SQL Queries in ATSD return JSON format.

SQL Queries in ATSD UI SQL Console return HTML tables or CSV Files.

### SQL URL

The URL must be encoded.

`atsd_server:8088/sql`

> Example URL

```
http://atsd_server:8088/sql?q=SELECT+time%2C+value%2C+entity+FROM+mpstat.cpu_busy+WHERE+entity+%3D+%27nurswgvml007%27+AND+time+%3E%3D+previous_day+AND+time+%3C+now
```

### Supported Aggregation Functions

* `count(value)`
* `counter(value)`
* `min(value)`
* `max(value)`
* `avg(value)`
* `sum(value)`
* `percentile(percent, value)`
* `stddev(value)`
* `first(value)`
* `last(value)`
* `delta(value)`
* `wavg(value)`
* `wtavg(value)`
* `min_value_time(value)`
* `max_value_time(value)`

### Supported Operators

* GROUP BY time, period(interval), {entity}, tags.{tagkey}
* WHERE columns: entity, tags.{tagkey}, time
* LIMIT (offset), count
* ORDER BY column DESC | ASC
* ORDER BY queries that contain GROUP BY
* HAVING filter for grouped values
* ROW_NUMBER returns row index (starting with 1) within the group. The function can be used to limit the number of rows returned for each group.
* JOIN
* OUTER JOIN
* last_time

### Not Supported Operators

* INTERPOLATE can follow GROUP BY period(interval) function, e.g. period(1 DAY) interpolate(LINEAR). interpolate - LINEAR, VALUE(0), PREVIOUS, NEXT

### Supported Time Formats

Supported time formats include:

|column|format|example|
|------|------|-------|
|time  |long  |`1408007200000`|
|datetime|ISO format|`'2015-04-09T14:00:00Z'`|
|time *or* datetime|endtime|`previous_day` *or* `now`|

### Date Format

Function that returns time values in ISO format.

Usage:

`date_format(time)`

`date_format(max_value_time(value))`

`date_format(time, 'yyyy-MM-dd HH:mm:ss')`

### Predefined Columns

|Name|Description|
|----|-----------|
|`entity`|an entity name, such as server name|
|`metric`|a metric name of the requested time series|
|`tags` *or* `tags.`|an object with named keys, where a key is a tag name and a value is tag value. `tags.*` is only supported in SELECT clause.|
|`time` *or* `datatime`|time in milliseconds or ISO format|
|`value`|recorded value|


<aside class="notice">
<a href='http://axibase.com/products/axibase-time-series-database/visualization/end-time/' target="_blank">Learn End Time syntax here.</a>
</aside>

### Case sensitivity

<aside class="notice">
Same as in API queries: case-sensitive tag values, case-insensitive everything else
</aside>

