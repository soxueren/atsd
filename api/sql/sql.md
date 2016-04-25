# SQL

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

## Query Format

> Request

```
SELECT * FROM {metric} WHERE entity = {entity} AND tags.{tagkey} = '/' AND time > {startTime} and time < {endTime}
```

## Basic Query

Query for one metric, one entity and detailed values for time range

> Request

```sql
SELECT time, value, entity FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time >= previous_day AND time < now
```

> Response

```json
{
    "columns": [
        {
            "name": "time",
            "label": "time",
            "metric": "mpstat.cpu_busy",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "label": "value",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            1438128005000,
            35.96,
            "nurswgvml007"
        ],
        [
            1438128021000,
            35.96,
            "nurswgvml007"
        ],
        [
            1438128037000,
            32.26,
            "nurswgvml007"
        ],
        [
            1438128053000,
            4,
            "nurswgvml007"
        ]
    ]
}
```

**SQL Console Response**

| time          | value | entity       | 
|---------------|-------|--------------| 
| 1445904004000 | 100.0 | nurswgvml007 | 
| 1445904020000 | 100.0 | nurswgvml007 | 
| 1445904036000 | 100.0 | nurswgvml007 | 
| 1445904052000 | 60.0  | nurswgvml007 | 
| 1445904068000 | 100.0 | nurswgvml007 | 
| 1445904084000 | 16.83 | nurswgvml007 | 
| 1445904101000 | 100.0 | nurswgvml007 | 
| 1445904117000 | 24.24 | nurswgvml007 | 
| 1445904133000 | 100.0 | nurswgvml007 | 

## Average Value Query

Average value for one metric, one entity

> Request

```sql
SELECT avg(value) AS CPU_Avg FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time >= previous_day AND time < now
```

> Response

```json
{
    "columns": [
        {
            "name": "avg(value)",
            "label": "CPU_Avg",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            31.101191881047953
        ]
    ]
}
```

## Max Value Time

Time when the metric value reached its maximum over the last hour

> Request

```sql
SELECT entity, MAX(value),
date_format(MAX_VALUE_TIME(value), 'yyyy-MM-dd HH:mm:ss') AS "Max Time"
FROM mpstat.cpu_busy WHERE time > current_hour GROUP BY entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "MAX(value)",
            "metric": "mpstat.cpu_busy",
            "label": "MAX(value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "date_format(MAX_VALUE_TIME(value),'yyyy-MM-dd HH:mm:ss')",
            "metric": "mpstat.cpu_busy",
            "label": "Max Time",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            100,
            "2015-10-28 14:03:09"
        ],
        [
            "nurswgvml003",
            15.84,
            "2015-10-28 14:01:45"
        ],
        [
            "nurswgvml006",
            100,
            "2015-10-28 14:00:51"
        ],
        [
            "nurswgvml007",
            100,
            "2015-10-28 14:03:06"
        ],
        [
            "nurswgvml010",
            88.49,
            "2015-10-28 14:10:00"
        ],
        [
            "nurswgvml011",
            11.11,
            "2015-10-28 14:05:01"
        ],
        [
            "nurswgvml102",
            41,
            "2015-10-28 14:04:53"
        ]
    ]
}
```

**SQL Console Response**

| entity       | MAX(value) | Max Time            | 
|--------------|------------|---------------------| 
| awsswgvml001 | 100.0      | 2015-10-28 14:03:09 | 
| nurswgvml003 | 15.84      | 2015-10-28 14:01:45 | 
| nurswgvml006 | 100.0      | 2015-10-28 14:00:51 | 
| nurswgvml007 | 100.0      | 2015-10-28 14:03:06 | 
| nurswgvml010 | 88.49      | 2015-10-28 14:10:00 | 
| nurswgvml011 | 11.11      | 2015-10-28 14:05:01 | 
| nurswgvml102 | 41.0       | 2015-10-28 14:04:53 | 

## Grouped Average

5 minute average value for one metric, two entities, sorted by entity name

> Request

```sql
SELECT entity, period(5 MINUTE) AS "period", avg(value) AS CPU_Avg FROM mpstat.cpu_busy 
  WHERE entity IN ('nurswgvml007', 'nurswgvml011') AND time between now - 1 * hour and now 
  GROUP BY entity, period(5 MINUTE)
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "period(5 MINUTE)",
            "metric": "mpstat.cpu_busy",
            "label": "period",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "avg(value)",
            "metric": "mpstat.cpu_busy",
            "label": "CPU_Avg",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml007",
            1446037200000,
            75.47466666666666
        ],
        [
            "nurswgvml007",
            1446037500000,
            24.44388888888889
        ],
        [
            "nurswgvml007",
            1446037800000,
            19.04421052631579
        ]
    ]
}
```

**SQL Console Response**

| entity       | period        | CPU_Avg            | 
|--------------|---------------|--------------------| 
| nurswgvml007 | 1446037200000 | 76.94454545454546  | 
| nurswgvml007 | 1446037500000 | 24.44388888888889  | 
| nurswgvml007 | 1446037800000 | 19.04421052631579  | 
| nurswgvml007 | 1446038100000 | 17.453157894736844 | 
| nurswgvml007 | 1446038400000 | 17.373157894736842 | 
| nurswgvml007 | 1446038700000 | 19.232222222222223 | 
| nurswgvml007 | 1446039000000 | 35.061052631578946 | 
| nurswgvml007 | 1446039300000 | 28.737894736842104 | 
| nurswgvml007 | 1446039600000 | 24.24578947368421  | 
| nurswgvml007 | 1446039900000 | 15.909444444444444 | 
| nurswgvml007 | 1446040200000 | 16.4               | 
 

## Counter Aggregator

> Request

```sql
select datetime, count(value), max(value), counter(value) from log_event_total_counter where entity = 'nurswgvml201' and tags.level = 'ERROR' 
and datetime >= '2015-09-30T09:00:00Z' and datetime < '2015-09-30T10:00:00Z' GROUP by period(5 minute)
```

```json
{
    "columns": [
        {
            "name": "datetime",
            "label": "datetime",
            "metric": "log_event_total_counter",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "count(value)",
            "label": "count(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "max(value)",
            "label": "max(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "counter(value)",
            "label": "counter(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "2015-09-30T09:00:00Z",
            5,
            3,
            1
        ],
        [
            "2015-09-30T09:05:00Z",
            4,
            3,
            0
        ],
        [
            "2015-09-30T09:10:00Z",
            4,
            3,
            0
        ]
    ]
}
```

**SQL Console Response**

| datetime             | count(value) | max(value) | counter(value) | 
|----------------------|--------------|------------|----------------| 
| 2015-09-30T09:00:00Z | 5.0          | 3.0        | 1.0            | 
| 2015-09-30T09:05:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:10:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:15:00Z | 6.0          | 5.0        | 5.0            | 
| 2015-09-30T09:20:00Z | 5.0          | 8.0        | 3.0            | 
| 2015-09-30T09:25:00Z | 4.0          | 3.0        | 3.0            | 

## Filter by Tag

One metric, one entity, filter by tag, detailed values for time range

> Request

```sql
SELECT time, value, tags.file_system FROM df.df.disk_used_percent WHERE entity = 'nurswgvml007'
  AND tags.file_system LIKE '/d%' AND time between now - 1 * hour and now
```

> Response

```json
{
    "columns": [
        {
            "name": "time",
            "metric": "df.df.disk_used_percent",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "metric": "df.df.disk_used_percent",
            "label": "value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "tags.file_system",
            "metric": "df.df.disk_used_percent",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            1446033175000,
            72.2701,
            "/dev/mapper/vg_nurswgvml007-lv_root"
        ],
        [
            1446033190000,
            72.2717,
            "/dev/mapper/vg_nurswgvml007-lv_root"
        ],
        [
            1446033205000,
            72.2738,
            "/dev/mapper/vg_nurswgvml007-lv_root"
        ]
    ]
}
```

**SQL Console Response**

| time          | value   | tags.file_system                    | 
|---------------|---------|-------------------------------------| 
| 1446033205000 | 72.2738 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033220000 | 72.275  | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033236000 | 72.2697 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033282000 | 72.2749 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033297000 | 72.2763 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033312000 | 72.2706 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033327000 | 72.2722 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033342000 | 72.2736 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033357000 | 72.2747 | /dev/mapper/vg_nurswgvml007-lv_root | 

## Select Entity Tags as Columns

> Request

```sql
select entity, entity.tags.os as os, entity.tags.ip as ip from df.disk_used where time between now - 1*minute and now group by entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "label": "entity",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "entity.tags.os",
            "label": "os",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "entity.tags.ip",
            "label": "ip",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "nurswgvml003",
            "Linux",
            "10.102.0.2"
        ],
        [
            "nurswgvml006",
            "Linux",
            "10.102.0.5"
        ],
        [
            "nurswgvml007",
            "Linux",
            "10.102.0.6"
        ],
        [
            "nurswgvml010",
            "Linux",
            "10.102.0.9"
        ],
        [
            "nurswgvml011",
            "Linux",
            "10.102.0.10"
        ],
        [
            "nurswgvml102",
            "Linux",
            "10.102.0.1"
        ]
    ]
}
```

**SQL Console Response**

| entity       | os    | ip          | 
|--------------|-------|-------------| 
| nurswgvml003 | Linux | 10.102.0.2  | 
| nurswgvml006 | Linux | 10.102.0.5  | 
| nurswgvml007 | Linux | 10.102.0.6  | 
| nurswgvml010 | Linux | 10.102.0.9  | 
| nurswgvml011 | Linux | 10.102.0.10 | 
| nurswgvml102 | Linux | 10.102.0.1  | 

## Time Condition

> Request

```sql
SELECT time, value FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time > 1428352721000 AND time < 1428352721000
```

> Response - returns an array with 1 record:

```json
{
    "columns": [
        {
            "name": "time",
            "label": "time",
            "metric": "mpstat.cpu_busy",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "label": "value",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            1438263969000,
            43.96
        ]
    ]
}
```

## Datetime Format

`datetime` is used to return time in ISO format without offset: `2015-04-07T08:14:28.231Z`

> Request

```sql
SELECT datetime, time, value, entity FROM mpstat.cpu_busy WHERE entity LIKE '%00%' AND datetime BETWEEN '2015-04-09T14:00:00Z' AND '2015-04-09T14:05:00Z'
```

> Response

```json
{
    "columns": [
        {
            "name": "datetime",
            "label": "datetime",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "label": "time",
            "metric": "mpstat.cpu_busy",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "label": "value",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "2015-04-09T14:00:01Z",
            1428588001000,
            3.8,
            "nurswgvml007"
        ],
        [
            "2015-04-09T14:00:18Z",
            1428588018000,
            14,
            "nurswgvml007"
        ],
        [
            "2015-04-09T14:00:34Z",
            1428588034000,
            16.83,
            "nurswgvml006"
        ]
    ]
}
```

**SQL Console Response**

| datetime             | time          | value | entity       | 
|----------------------|---------------|-------|--------------| 
| 2015-04-09T14:00:01Z | 1428588001000 | 3.8   | nurswgvml007 | 
| 2015-04-09T14:00:18Z | 1428588018000 | 14.0  | nurswgvml007 | 
| 2015-04-09T14:00:34Z | 1428588034000 | 16.83 | nurswgvml007 | 
| 2015-04-09T14:00:50Z | 1428588050000 | 10.2  | nurswgvml007 | 
| 2015-04-09T14:01:06Z | 1428588066000 | 4.04  | nurswgvml007 | 
| 2015-04-09T14:01:22Z | 1428588082000 | 9.0   | nurswgvml007 | 
| 2015-04-09T14:01:38Z | 1428588098000 | 2.0   | nurswgvml007 | 
| 2015-04-09T14:01:54Z | 1428588114000 | 8.0   | nurswgvml007 | 
| 2015-04-09T14:02:10Z | 1428588130000 | 10.23 | nurswgvml007 | 
| 2015-04-09T14:02:26Z | 1428588146000 | 14.0  | nurswgvml007 | 
| 2015-04-09T14:02:42Z | 1428588162000 | 20.2  | nurswgvml007 | 


## ORDER BY Time

> Request

```sql
SELECT time, value FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time between now - 1 * hour and now ORDER BY time
```

> Response

```json
{
    "columns": [
        {
            "name": "time",
            "metric": "mpstat.cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "metric": "mpstat.cpu_busy",
            "label": "value",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            1446033764000,
            48.96
        ],
        [
            1446033780000,
            42.42
        ],
        [
            1446033796000,
            12.24
        ]
    ]
}
```

**SQL Console Response**

| time          | value | 
|---------------|-------| 
| 1446033812000 | 22.45 | 
| 1446033828000 | 12.33 | 
| 1446033844000 | 19.0  | 
| 1446033860000 | 7.07  | 
| 1446033876000 | 13.13 | 
| 1446033892000 | 24.24 | 
| 1446033908000 | 27.27 | 
| 1446033924000 | 34.0  | 
| 1446033940000 | 9.0   | 
| 1446033956000 | 11.11 | 
| 1446033972000 | 40.4  | 

## SELECT All

> Request

```sql
SELECT * FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time between now - 1 * hour and now
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "mpstat.cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "metric": "mpstat.cpu_busy",
            "label": "value",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml007",
            1446034164000,
            10
        ],
        [
            "nurswgvml007",
            1446034180000,
            8.08
        ],
        [
            "nurswgvml007",
            1446034196000,
            17
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | value | 
|--------------|---------------|-------| 
| nurswgvml007 | 1446034244000 | 35.71 | 
| nurswgvml007 | 1446034260000 | 39.78 | 
| nurswgvml007 | 1446034276000 | 16.0  | 
| nurswgvml007 | 1446034292000 | 10.1  | 
| nurswgvml007 | 1446034308000 | 9.0   | 
| nurswgvml007 | 1446034324000 | 12.12 | 
| nurswgvml007 | 1446034340000 | 10.31 | 
| nurswgvml007 | 1446034356000 | 8.08  | 
| nurswgvml007 | 1446034372000 | 10.1  | 
| nurswgvml007 | 1446034388000 | 12.87 | 
| nurswgvml007 | 1446034404000 | 9.09  | 
| nurswgvml007 | 1446034420000 | 8.25  | 

## Alias

An `alias` can be set in single (`'alias'`) or double qoatations (`"alias"`).

Unquoted `alias` must begin with a letter followed by letters, underscores, digits (0-9).

> Request

```sql
SELECT time, value, entity, metric AS "measurement" FROM mpstat.cpu_busy WHERE entity = 'nurswgvml006' AND time between now - 5 * minute and now
```

> OR

```sql
SELECT time, value, entity, metric AS 'measurement' FROM mpstat.cpu_busy WHERE entity = 'nurswgvml006' AND time between now - 5 * minute and now
```

> Response

```json
{
    "columns": [
        {
            "name": "time",
            "metric": "mpstat.cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "metric": "mpstat.cpu_busy",
            "label": "value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "metric",
            "metric": "mpstat.cpu_busy",
            "label": "measurement",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            1446038385000,
            9.09,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ],
        [
            1446038401000,
            8,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ],
        [
            1446038417000,
            8,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ]
    ]
}
```

**SQL Console Response**

| time          | value | entity       | measurement | 
|---------------|-------|--------------|-------------| 
| 1446038417000 | 8.0   | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038433000 | 100.0 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038449000 | 30.3  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038465000 | 17.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038481000 | 11.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038497000 | 90.82 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038513000 | 19.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038529000 | 27.27 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038545000 | 12.12 | nurswgvml006 | mpstat.cpu_busy    | 

## Entity with Tags

> Request

```sql
SELECT count(*), entity, tags.*, period (30 minute) FROM df.disk_used 
WHERE entity = 'nurswgvml102' AND tags.mount_point = '/' 
AND tags.file_system = '/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a' 
AND datetime BETWEEN '2015-07-08T16:00:00Z' AND '2015-07-08T16:30:00Z' 
GROUP BY entity, tags, period (30 minute)
```

> Response

```json
{
    "columns": [
        {
            "name": "count(*)",
            "label": "count(*)",
            "metric": "df.disk_used",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "label": "entity",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "label": "tags.file_system",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.mount_point",
            "label": "tags.mount_point",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "period(30 MINUTE)",
            "label": "period(30 MINUTE)",
            "metric": "df.disk_used",
            "type": "LONG",
            "numeric": true
        }
    ],
    "rows": [
        [
            120,
            "nurswgvml102",
            "/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a",
            "/",
            1436371200000
        ]
    ]
}
```

**SQL Console Response**

| count(*) | entity       | tags.mount_point | tags.file_system                                       | period(30 MINUTE) | 
|----------|--------------|------------------|--------------------------------------------------------|-------------------| 
| 120.0    | nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 1436371200000     | 

## GROUP BY Query with HAVING

> Request

```sql
select entity, avg(value), count(*) from mpstat.cpu_busy where time > now - 1* hour group by entity having avg(value) > 10 and count(*) > 200
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "avg(value)",
            "label": "avg(value)",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "count(*)",
            "label": "count(*)",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            21.5463111111111,
            225
        ],
        [
            "nurswgvml006",
            13.945223214285715,
            224
        ],
        [
            "nurswgvml007",
            18.323777777777778,
            225
        ],
        [
            "nurswgvml010",
            10.029553571428574,
            224
        ]
    ]
}
```

**SQL Console Response**

| entity       | avg(value)         | count(*) | 
|--------------|--------------------|----------| 
| awsswgvml001 | 13.290133333333332 | 225.0    | 
| nurswgvml006 | 21.918035714285715 | 224.0    | 
| nurswgvml007 | 47.27337777777781  | 225.0    | 

## GROUP BY Query with ORDER BY

> Request

```sql
select entity, avg(value) from mpstat.cpu_busy where time > now - 1*hour group by entity order by avg(value) desc limit 5
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "avg(value)",
            "label": "avg(value)",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml007",
            18.775111111111112
        ],
        [
            "nurswgvml006",
            15.119598214285714
        ],
        [
            "awsswgvml001",
            11.185288888888888
        ],
        [
            "nurswgvml010",
            9.196577777777778
        ],
        [
            "nurswgvml011",
            3.2876233183856503
        ]
    ]
}
```

**SQL Console Response**

| entity       | avg(value)         | 
|--------------|--------------------| 
| nurswgvml007 | 46.547288888888865 | 
| nurswgvml006 | 21.85551111111111  | 
| awsswgvml001 | 13.554488888888887 | 
| nurswgvml010 | 7.88160714285714   | 
| nurswgvml011 | 2.8021973094170405 | 

## ROW_NUMBER with ORDER BY time

`tags.*` - every tag will have its own column in the response.

> Request

```sql
SELECT entity, tags.*, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY time DESC) <=1
  ORDER BY entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "df.disk_used",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.mount_point",
            "metric": "df.disk_used",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "df.disk_used",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "datetime",
            "metric": "df.disk_used",
            "label": "datetime",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "Avg(value)",
            "metric": "df.disk_used",
            "label": "Avg(value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T08:30:00Z",
            2422637
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T08:30:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:30:00Z",
            24745145
        ],
        [
            "nurswgvml003",
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff",
            "2015-10-27T08:30:00Z",
            2424204.5714285714
        ],
        [
            "nurswgvml003",
            "/home/store",
            "/dev/sdb1",
            "2015-10-27T08:30:00Z",
            139204048
        ],
        [
            "nurswgvml003",
            "/",
            "rootfs",
            "2015-10-27T08:30:00Z",
            2424204.5714285714
        ],
        [
            "nurswgvml006",
            "/",
            "/dev/mapper/vg_nurswgvml006-lv_root",
            "2015-10-27T08:30:00Z",
            8311659.428571428
        ],
        [
            "nurswgvml006",
            "/media/datadrive",
            "/dev/sdc1",
            "2015-10-27T08:30:00Z",
            47807372
        ],
        [
            "nurswgvml007",
            "/",
            "/dev/mapper/vg_nurswgvml007-lv_root",
            "2015-10-27T08:30:00Z",
            9581887.42857143
        ],
        [
            "nurswgvml007",
            "/mnt/share",
            "10.102.0.2:/home/store/share",
            "2015-10-27T08:30:00Z",
            139204032
        ]
    ]
}
```

**SQL Console Response**

| entity       | tags.mount_point | tags.file_system                                       | datetime             | Avg(value)          | 
|--------------|------------------|--------------------------------------------------------|----------------------|---------------------| 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:30:00Z | 2433897.6363636362  | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T13:30:00Z | 6.5373272E7         | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T13:30:00Z | 2.4670692E7         | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:30:00Z | 2428761.913043478   | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-28T13:30:00Z | 1.39204048E8        | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:30:00Z | 2428761.913043478   | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 2015-10-28T13:30:00Z | 8313054.260869565   | 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 2015-10-28T13:30:00Z | 4.837551686956522E7 | 


## ROW_NUMBER with ORDER BY avg

`tags.*` - every tag will have its own column in the response.

> Request

```sql
SELECT entity, tags.*, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY avg(value) DESC) <=5
  ORDER BY entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "df.disk_used",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.mount_point",
            "metric": "df.disk_used",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "df.disk_used",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "datetime",
            "metric": "df.disk_used",
            "label": "datetime",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "Avg(value)",
            "metric": "df.disk_used",
            "label": "Avg(value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T06:15:00Z",
            2423001.2
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T06:00:00Z",
            2422891.7333333334
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T05:45:00Z",
            2422842.6666666665
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T05:30:00Z",
            2422774.2666666666
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T08:45:00Z",
            2422758.0606060605
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:00:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:15:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:30:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:45:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T05:00:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:45:00Z",
            24865433.454545453
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:30:00Z",
            24724325.266666666
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T07:00:00Z",
            24695767.4
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:15:00Z",
            24682546.8
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T06:45:00Z",
            24680499.4
        ]
    ]
}
```

**SQL Console Response**

| entity       | tags.mount_point | tags.file_system                                       | datetime             | Avg(value)           | 
|--------------|------------------|--------------------------------------------------------|----------------------|----------------------| 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:30:00Z | 2433910.3636363638   | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:15:00Z | 2433866.6            | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:00:00Z | 2433769.1333333333   | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T12:45:00Z | 2433680.6            | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T12:30:00Z | 2433610.7333333334   | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:00:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:15:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:30:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:45:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T05:00:00Z | 6.6684048E7          | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T11:45:00Z | 2.4721712068965517E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-27T15:30:00Z | 2.46928654E7         | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-27T15:15:00Z | 2.4675727533333335E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T08:45:00Z | 2.4672177866666667E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T09:00:00Z | 2.4671984666666668E7 | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:30:00Z | 2428775.8181818184   | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T12:30:00Z | 2428582.466666667    | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T13:30:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T13:45:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:00:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:15:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:30:00Z | 1.39204048E8         | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:30:00Z | 2428775.8181818184   | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T12:30:00Z | 2428582.466666667    | 

## ROW_NUMBER with ORDER BY time & avg

`tags` - concatenates all tags in one column.

> Request

```sql
SELECT entity, tags, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY time DESC, avg(value) ASC) <=5
  ORDER BY entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "df.disk_used",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags",
            "metric": "df.disk_used",
            "label": "tags",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "datetime",
            "metric": "df.disk_used",
            "label": "datetime",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "Avg(value)",
            "metric": "df.disk_used",
            "label": "Avg(value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            "file_system=/dev/xvda1;mount_point=/",
            "2015-10-27T09:30:00Z",
            2423047.6363636362
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvda1;mount_point=/",
            "2015-10-27T09:15:00Z",
            2422970.3333333335
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvda1;mount_point=/",
            "2015-10-27T09:00:00Z",
            2422834.1333333333
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvda1;mount_point=/",
            "2015-10-27T08:45:00Z",
            2422771.0508474577
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvda1;mount_point=/",
            "2015-10-27T08:30:00Z",
            2422685.4
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdf;mount_point=/backup",
            "2015-10-27T09:30:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdf;mount_point=/backup",
            "2015-10-27T09:15:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdf;mount_point=/backup",
            "2015-10-27T09:00:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdf;mount_point=/backup",
            "2015-10-27T08:45:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdf;mount_point=/backup",
            "2015-10-27T08:30:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdh1;mount_point=/data",
            "2015-10-27T09:30:00Z",
            24648080
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdh1;mount_point=/data",
            "2015-10-27T09:15:00Z",
            24612871.133333333
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdh1;mount_point=/data",
            "2015-10-27T09:00:00Z",
            24668438.666666668
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdh1;mount_point=/data",
            "2015-10-27T08:45:00Z",
            24779230.237288136
        ],
        [
            "awsswgvml001",
            "file_system=/dev/xvdh1;mount_point=/data",
            "2015-10-27T08:30:00Z",
            24724325.266666666
        ]
    ]
}
```

**SQL Console Response**

| entity       | tags                                                                             | datetime             | Avg(value)           | 
|--------------|----------------------------------------------------------------------------------|----------------------|----------------------| 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:30:00Z | 2433912.1666666665   | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:15:00Z | 2433866.6            | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:00:00Z | 2433769.1333333333   | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T12:45:00Z | 2433680.6            | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T12:30:00Z | 2433610.7333333334   | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:30:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:15:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:00:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T12:45:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T12:30:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:30:00Z | 2.4670714E7          | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:15:00Z | 2.4670594733333334E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:00:00Z | 2.4670553466666665E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T12:45:00Z | 2.4670378466666665E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T12:30:00Z | 2.4670224266666666E7 | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:30:00Z | 2428777.8775510206   | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T12:30:00Z | 2428582.466666667    | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:30:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:15:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:00:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T12:45:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T12:30:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:30:00Z | 2428777.8775510206   | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T12:30:00Z | 2428582.466666667    | 

## Not Equal Operator

In this example the "not equal" operator `!=`, is used to exclude values equal to zero from the results.

> Request

```sql
SELECT entity, tags.mount_point AS mp, tags.file_system as FS,
    MIN(disk_used.value), MAX(disk_used.value),
    FIRST(disk_used.value), LAST(disk_used.value),
    DELTA(disk_used.value), COUNT(disk_used.value),
    AVG(cpu_busy.value) FROM cpu_busy
JOIN USING entity disk_used
    WHERE time > now - 60 * minute
    GROUP BY entity, tags.mount_point, tags.file_system
HAVING DELTA(disk_used.value) != 0
    ORDER BY DELTA(disk_used.value) DESC
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.mount_point",
            "metric": "cpu_busy",
            "label": "mp",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "cpu_busy",
            "label": "FS",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "MIN(disk_used.value)",
            "metric": "disk_used",
            "label": "MIN(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "MAX(disk_used.value)",
            "metric": "disk_used",
            "label": "MAX(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "FIRST(disk_used.value)",
            "metric": "disk_used",
            "label": "FIRST(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "LAST(disk_used.value)",
            "metric": "disk_used",
            "label": "LAST(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "DELTA(disk_used.value)",
            "metric": "disk_used",
            "label": "DELTA(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "COUNT(disk_used.value)",
            "metric": "disk_used",
            "label": "COUNT(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml010",
            "/app",
            "/dev/sdb1",
            26556944,
            26592488,
            26556944,
            26592488,
            35544,
            30,
            1.0096666666666665
        ],
        [
            "nurswgvml006",
            "/media/datadrive",
            "/dev/sdc1",
            50512204,
            50780556,
            50697624,
            50726476,
            28852,
            16,
            26.4475
        ],
        [
            "nurswgvml007",
            "/",
            "/dev/mapper/vg_nurswgvml007-lv_root",
            8853224,
            8855812,
            8853304,
            8855812,
            2508,
            14,
            21.777857142857144
        ],
        [
            "nurswgvml011",
            "/mnt/backup",
            "10.102.0.2:/backup",
            2482496,
            2483392,
            2482880,
            2483392,
            512,
            15,
            1.728
        ]
    ]
}
```

**SQL Console Response**

| entity       | mp               | FS                                                     | MIN(disk_used.value) | MAX(disk_used.value) | FIRST(disk_used.value) | LAST(disk_used.value) | DELTA(disk_used.value) | COUNT(disk_used.value) | AVG(cpu_busy.value) | 
|--------------|------------------|--------------------------------------------------------|----------------------|----------------------|------------------------|-----------------------|------------------------|------------------------|---------------------| 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 5.0512204E7          | 5.0780556E7          | 5.0675844E7            | 5.0760228E7           | 84384.0                | 16.0                   | 26.3225             | 
| nurswgvml007 | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 8853224.0            | 8856120.0            | 8853304.0              | 8856120.0             | 2816.0                 | 15.0                   | 21.787333333333333  | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 8088372.0            | 8089452.0            | 8088528.0              | 8089148.0             | 620.0                  | 16.0                   | 26.3225             | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2482628.0            | 2483504.0            | 2482884.0              | 2483336.0             | 452.0                  | 15.0                   | 2.260666666666667   | 
| nurswgvml003 | /                | rootfs                                                 | 2482628.0            | 2483504.0            | 2482884.0              | 2483336.0             | 452.0                  | 15.0                   | 2.260666666666667   | 
| nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 1821076.0            | 1821236.0            | 1821076.0              | 1821236.0             | 160.0                  | 15.0                   | 1.2666666666666666  | 
| nurswgvml102 | /                | rootfs                                                 | 1821076.0            | 1821236.0            | 1821076.0              | 1821236.0             | 160.0                  | 15.0                   | 1.2666666666666666  | 
| nurswgvml010 | /                | /dev/sda1                                              | 5834992.0            | 5835140.0            | 5834992.0              | 5835140.0             | 148.0                  | 30.0                   | 0.9929999999999998  | 
| nurswgvml011 | /                | /dev/sda1                                              | 7056300.0            | 7057324.0            | 7056748.0              | 7056676.0             | -72.0                  | 15.0                   | 1.8646666666666667  | 
| nurswgvml011 | /mnt/backup      | 10.102.0.2:/backup                                     | 2482496.0            | 2483392.0            | 2482944.0              | 2482688.0             | -256.0                 | 15.0                   | 1.8646666666666667  | 

## Inner Join

Result table contains rows with table1.entity = table2.entity and table1.time = table2.time and table1.tags = table2.tags

> Request

```sql
SELECT *
FROM cpu_busy
JOIN cpu_idle
WHERE time > now - 1 * hour
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "cpu_busy.value",
            "metric": "cpu_busy",
            "label": "cpu_busy.value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "cpu_idle.value",
            "metric": "cpu_idle",
            "label": "cpu_idle.value",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447400404000,
            4.04,
            95.96
        ],
        [
            "awsswgvml001",
            1447400465000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400526000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400587000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400648000,
            1,
            99
        ],
        [
            "awsswgvml001",
            1447400709000,
            29.41,
            70.59
        ],
        [
            "awsswgvml001",
            1447400770000,
            0,
            100
        ],
        [
            "awsswgvml001",
            1447400831000,
            1,
            99
        ],
        [
            "awsswgvml001",
            1447400892000,
            1.03,
            98.97
        ],
        [
            "awsswgvml001",
            1447400953000,
            3.03,
            96.97
        ],
        [
            "awsswgvml001",
            1447401014000,
            14.42,
            85.58
        ],
        [
            "awsswgvml001",
            1447401075000,
            9.09,
            90.91
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | cpu_busy.value | cpu_idle.value | 
|--------------|---------------|----------------|----------------| 
| awsswgvml001 | 1447400465000 | 100.0          | 0.0            | 
| awsswgvml001 | 1447400526000 | 100.0          | 0.0            | 
| awsswgvml001 | 1447400587000 | 100.0          | 0.0            | 
| awsswgvml001 | 1447400648000 | 1.0            | 99.0           | 
| awsswgvml001 | 1447400709000 | 29.41          | 70.59          | 
| awsswgvml001 | 1447400770000 | 0.0            | 100.0          | 
| awsswgvml001 | 1447400831000 | 1.0            | 99.0           | 
| awsswgvml001 | 1447400892000 | 1.03           | 98.97          | 
| awsswgvml001 | 1447400953000 | 3.03           | 96.97          | 
| awsswgvml001 | 1447401014000 | 14.42          | 85.58          | 

## Outer Join

Inner join + tables rows that is not belonging to inner join table with null value columns.

> Request

```sql
SELECT *
FROM cpu_busy
OUTER JOIN disk_used
WHERE time > now - 1 * hour
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "cpu_busy.value",
            "metric": "cpu_busy",
            "label": "cpu_busy.value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "disk_used.value",
            "metric": "disk_used",
            "label": "disk_used.value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "tags.mount_point",
            "metric": "cpu_busy",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "cpu_busy",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447401314000,
            null,
            2317456,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401374000,
            null,
            2317460,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401435000,
            null,
            2317460,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401495000,
            null,
            2317464,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401555000,
            null,
            2317464,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401615000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401675000,
            null,
            2317472,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401735000,
            null,
            2317472,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401795000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401855000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | cpu_busy.value | disk_used.value | tags.mount_point | tags.file_system | 
|--------------|---------------|----------------|-----------------|------------------|------------------| 
| awsswgvml001 | 1447401374000 |                | 2317460.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401435000 |                | 2317460.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401495000 |                | 2317464.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401555000 |                | 2317464.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401374000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401435000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401495000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401555000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401615000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401563000 | 56.6           |                 |                  |                  | 
| awsswgvml001 | 1447401624000 | 2.0            |                 |                  |                  | 
| awsswgvml001 | 1447401685000 | 4.0            |                 |                  |                  | 
| awsswgvml001 | 1447401746000 | 11.0           |                 |                  |                  | 
| awsswgvml001 | 1447401807000 | 10.78          |                 |                  |                  | 


## Outer Join with Aggregation


> Request

```sql
SELECT entity, time, AVG(cpu_busy.value), AVG(disk_used.value)
FROM cpu_busy
OUTER JOIN disk_used
WHERE time > now - 1 * hour
GROUP BY entity, period(15 minute)
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "AVG(disk_used.value)",
            "metric": "disk_used",
            "label": "AVG(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447402500000,
            1.396,
            31993319.733333334
        ],
        [
            "awsswgvml001",
            1447403400000,
            5.485,
            31993651.82222222
        ],
        [
            "awsswgvml001",
            1447404300000,
            7.332,
            31993992.266666666
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | AVG(cpu_busy.value) | AVG(disk_used.value) | 
|--------------|---------------|---------------------|----------------------| 
| awsswgvml001 | 1447402500000 | 1.28                | 3.199332495238095E7  | 
| awsswgvml001 | 1447403400000 | 5.485               | 3.199365182222222E7  | 
| awsswgvml001 | 1447404300000 | 7.332               | 3.1993992266666666E7 | 
| awsswgvml001 | 1447405200000 | 28.466666666666665  | 3.20101648E7         | 
| awsswgvml001 | 1447406100000 | 1.2                 | 3.2004019777777776E7 | 
| nurswgvml003 | 1447402500000 | 2.987037037037037   | 4.801549866666667E7  | 
| nurswgvml003 | 1447403400000 | 2.1580357142857145  | 4.801548062222222E7  | 
| nurswgvml003 | 1447404300000 | 3.0348214285714286  | 4.80155496E7         | 
| nurswgvml003 | 1447405200000 | 8.422105263157892   | 4.80155768E7         | 
| nurswgvml003 | 1447406100000 | 2.734137931034483   | 4.801559333333333E7  | 
| nurswgvml006 | 1447402500000 | 16.794615384615387  | 2.9060381172413792E7 | 
| nurswgvml006 | 1447403400000 | 21.149649122807016  | 2.9056498733333334E7 | 
| nurswgvml006 | 1447404300000 | 18.680714285714284  | 2.9060813933333334E7 | 
| nurswgvml006 | 1447405200000 | 25.231785714285714  | 2.9066732745762713E7 | 
| nurswgvml006 | 1447406100000 | 8.349655172413794   | 2.90752549375E7      | 
| nurswgvml007 | 1447402500000 | 17.73037037037037   | 7.403412664285715E7  | 
| nurswgvml007 | 1447403400000 | 14.423035714285714  | 7.403452726666667E7  | 
| nurswgvml007 | 1447404300000 | 17.5325             | 7.403496163333334E7  | 
| nurswgvml007 | 1447405200000 | 40.00928571428571   | 7.40353264E7         | 
| nurswgvml007 | 1447406100000 | 22.875333333333334  | 7.403567283870968E7  | 
| nurswgvml010 | 1447402500000 | 2.4629629629629632  | 1.644483957142857E7  | 
| nurswgvml010 | 1447403400000 | 3.9226785714285715  | 1.644485875862069E7  | 
| nurswgvml010 | 1447404300000 | 11.564649122807014  | 1.6438349350649351E7 | 
| nurswgvml010 | 1447405200000 | 12.874743589743579  | 1.6592446566666666E7 | 
| nurswgvml010 | 1447406100000 | 3.013793103448276   | 1.6646093125E7       | 
| nurswgvml011 | 1447402500000 | 2.592962962962963   | 4.954640542528735E7  | 
| nurswgvml011 | 1447403400000 | 2.0717857142857143  | 4.954648657777778E7  | 
| nurswgvml011 | 1447404300000 | 2.1269642857142856  | 4.954651208888889E7  | 
| nurswgvml011 | 1447405200000 | 9.215357142857142   | 4.954641938983051E7  | 
| nurswgvml011 | 1447406100000 | 1.9589655172413794  | 4.954655225E7        | 
| nurswgvml102 | 1447402500000 | 0.8840740740740741  | 4.761813370114943E7  | 
| nurswgvml102 | 1447403400000 | 0.6244642857142857  | 4.7618148755555555E7 | 
| nurswgvml102 | 1447404300000 | 0.8946428571428571  | 4.7618172222222224E7 | 
| nurswgvml102 | 1447405200000 | 1.0666071428571429  | 4.7618203777777776E7 | 
| nurswgvml102 | 1447406100000 | 0.8646666666666667  | 4.761823621505377E7  | 

## Row Number Function

This function numbers rows according to grouping columns (example: `entity, tags`), orders rows (example `time DESC`), and then filters each row with predicate based on `row_number` function value (`WITH row_number(entity, tags ORDER BY time DESC) <= 3`).

> Request

```sql
SELECT entity, time, AVG(cpu_busy.value)
FROM cpu_busy
WHERE time > now - 1 * hour
GROUP BY entity, period(15 minute)
WITH row_number(entity, tags ORDER BY time DESC) <= 3
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447407000000,
            5.095000000000001
        ],
        [
            "awsswgvml001",
            1447406100000,
            2.1333333333333337
        ],
        [
            "awsswgvml001",
            1447405200000,
            28.466666666666665
        ],
        [
            "nurswgvml003",
            1447407000000,
            6.43608695652174
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | AVG(cpu_busy.value) | 
|--------------|---------------|---------------------| 
| awsswgvml001 | 1447407000000 | 5.095000000000001   | 
| awsswgvml001 | 1447406100000 | 2.1333333333333337  | 
| awsswgvml001 | 1447405200000 | 28.466666666666665  | 
| nurswgvml003 | 1447407000000 | 5.808846153846154   | 
| nurswgvml003 | 1447406100000 | 6.670714285714286   | 
| nurswgvml003 | 1447405200000 | 8.422105263157892   | 
| nurswgvml006 | 1447407000000 | 8.224230769230768   | 
| nurswgvml006 | 1447406100000 | 9.618571428571428   | 
| nurswgvml006 | 1447405200000 | 25.231785714285714  | 
| nurswgvml007 | 1447407000000 | 18.276296296296298  | 
| nurswgvml007 | 1447406100000 | 19.083214285714284  | 
| nurswgvml007 | 1447405200000 | 40.00928571428571   | 
| nurswgvml010 | 1447407000000 | 2.4067307692307693  | 
| nurswgvml010 | 1447406100000 | 3.472410714285714   | 
| nurswgvml010 | 1447405200000 | 12.874743589743579  | 
| nurswgvml011 | 1447407000000 | 4.12                | 
| nurswgvml011 | 1447406100000 | 1.98                | 
| nurswgvml011 | 1447405200000 | 9.215357142857142   | 
| nurswgvml102 | 1447407000000 | 1.0792307692307692  | 
| nurswgvml102 | 1447406100000 | 0.9268421052631579  | 
| nurswgvml102 | 1447405200000 | 1.0666071428571429  | 

## Last Time

Returns the last time of a stored value in a table for a key (metric + entity + tags).

> Request

```sql
SELECT entity, datetime, AVG(cpu_busy.value)
FROM cpu_busy
WHERE time > now - 1 * hour 
GROUP BY entity, period(15 minute)
WITH time > last_time - 30 * minute
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "datetime",
            "metric": "cpu_busy",
            "label": "datetime",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            "2015-11-13T09:15:00Z",
            2.416666666666667
        ],
        [
            "awsswgvml001",
            "2015-11-13T09:30:00Z",
            2.9978571428571428
        ],
        [
            "awsswgvml001",
            "2015-11-13T09:45:00Z",
            1.245
        ],
        [
            "nurswgvml003",
            "2015-11-13T09:15:00Z",
            9.274705882352942
        ],
        [
            "nurswgvml003",
            "2015-11-13T09:30:00Z",
            3.659107142857143
        ],
        [
            "nurswgvml003",
            "2015-11-13T09:45:00Z",
            1.0004347826086957
        ]
    ]
}
```

**SQL Console Response**

| entity       | datetime             | AVG(cpu_busy.value) | 
|--------------|----------------------|---------------------| 
| awsswgvml001 | 2015-11-13T09:15:00Z | 4.0                 | 
| awsswgvml001 | 2015-11-13T09:30:00Z | 2.9978571428571428  | 
| awsswgvml001 | 2015-11-13T09:45:00Z | 1.3618181818181818  | 
| nurswgvml003 | 2015-11-13T09:15:00Z | 9.485               | 
| nurswgvml003 | 2015-11-13T09:30:00Z | 3.659107142857143   | 
| nurswgvml003 | 2015-11-13T09:45:00Z | 0.9069767441860465  | 
| nurswgvml006 | 2015-11-13T09:15:00Z | 14.61357142857143   | 
| nurswgvml006 | 2015-11-13T09:30:00Z | 19.994285714285716  | 
| nurswgvml006 | 2015-11-13T09:45:00Z | 26.842790697674417  | 
| nurswgvml007 | 2015-11-13T09:15:00Z | 13.569285714285714  | 
| nurswgvml007 | 2015-11-13T09:30:00Z | 16.40140350877193   | 
| nurswgvml007 | 2015-11-13T09:45:00Z | 14.703095238095237  | 
| nurswgvml010 | 2015-11-13T09:15:00Z | 3.5299999999999994  | 
| nurswgvml010 | 2015-11-13T09:30:00Z | 2.8894642857142854  | 
| nurswgvml010 | 2015-11-13T09:45:00Z | 3.54280701754386    | 
| nurswgvml011 | 2015-11-13T09:15:00Z | 1.404               | 
| nurswgvml011 | 2015-11-13T09:30:00Z | 2.895178571428571   | 
| nurswgvml011 | 2015-11-13T09:45:00Z | 1.9095348837209303  | 
| nurswgvml102 | 2015-11-13T09:15:00Z | 0.7986666666666666  | 
| nurswgvml102 | 2015-11-13T09:30:00Z | 1.0714285714285714  | 
| nurswgvml102 | 2015-11-13T09:45:00Z | 0.8595348837209302  | 

## Using Entity

Calculate Cartesian product for table tags and fill each row with metric values based on entity and time.

> Request

```sql
SELECT entity, disk_used.time, cpu_busy.time, AVG(cpu_busy.value), AVG(disk_used.value), tags.*
FROM cpu_busy
JOIN USING entity disk_used
WHERE time > now - 1 * hour
GROUP BY entity, tags, period(15 minute)
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "disk_used",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "AVG(disk_used.value)",
            "metric": "disk_used",
            "label": "AVG(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "tags.mount_point",
            "metric": "cpu_busy",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "cpu_busy",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "nurswgvml003",
            1447405200000,
            1447405200000,
            4.5,
            2421396,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447406100000,
            1447406100000,
            26.237499999999997,
            2431103,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447407000000,
            1447407000000,
            2.235,
            2431021,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447407900000,
            1447407900000,
            1.67,
            2414688,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447408800000,
            1447408800000,
            3.92,
            2414638,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml102",
            1447405200000,
            1447405200000,
            1.99,
            1825316,
            "/",
            "/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a"
        ],
        [
            "nurswgvml102",
            1447406100000,
            1447406100000,
            0.75,
            1825347,
            "/",
            "/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a"
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | time          | AVG(cpu_busy.value) | AVG(disk_used.value) | tags.mount_point | tags.file_system                                       | 
|--------------|---------------|---------------|---------------------|----------------------|------------------|--------------------------------------------------------| 
| nurswgvml003 | 1447405200000 | 1447405200000 | 4.5                 | 2421396.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447406100000 | 1447406100000 | 26.237499999999997  | 2431103.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447407000000 | 1447407000000 | 2.235               | 2431021.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447407900000 | 1447407900000 | 1.67                | 2414688.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447408800000 | 1447408800000 | 3.92                | 2414638.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml102 | 1447405200000 | 1447405200000 | 1.99                | 1825316.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447406100000 | 1447406100000 | 0.75                | 1825347.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447407000000 | 1447407000000 | 0.9966666666666667  | 1825384.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447407900000 | 1447407900000 | 0.5025              | 1825418.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447408800000 | 1447408800000 | 1.9900000000000002  | 1825452.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml006 | 1447405200000 | 1447405200000 | 18.384999999999998  | 7976100.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447406100000 | 1447406100000 | 10.2375             | 7976356.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447407000000 | 1447407000000 | 39.693333333333335  | 7976576.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447407900000 | 1447407900000 | 10.0125             | 7976867.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447408800000 | 1447408800000 | 28.035              | 7977008.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml007 | 1447405200000 | 1447405200000 | 26.26               | 8867000.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447406100000 | 1447406100000 | 17.0375             | 8867733.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447407000000 | 1447407000000 | 15.406666666666666  | 8868142.666666666    | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447407900000 | 1447407900000 | 13.559999999999999  | 8868494.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447408800000 | 1447408800000 | 39.03               | 8868948.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml010 | 1447405200000 | 1447405200000 | 17.653333333333332  | 5840140.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447406100000 | 1447406100000 | 14.18125            | 5840176.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447407000000 | 1447407000000 | 13.951666666666666  | 5840225.333333333    | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447407900000 | 1447407900000 | 22.338              | 5840268.8            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447408800000 | 1447408800000 | 26.314999999999998  | 5840300.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447405200000 | 1447405200000 | 2.5149999999999997  | 7013828.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447406100000 | 1447406100000 | 5.234999999999999   | 7014201.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447407000000 | 1447407000000 | 2.25                | 7014283.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447407900000 | 1447407900000 | 2.34                | 7014312.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447408800000 | 1447408800000 | 2.9850000000000003  | 7014716.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447405200000 | 1447405200000 | 17.653333333333332  | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447406100000 | 1447406100000 | 14.18125            | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447407000000 | 1447407000000 | 13.951666666666666  | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447407900000 | 1447407900000 | 22.338              | 2.7452024E7          | /app             | /dev/sdb1                                              | 

