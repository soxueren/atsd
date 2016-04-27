## Metrics: List

### Request Parameters

```
GET /api/v1/metrics
```

> Request

```
http://atsd_server:8088/api/v1/metrics?limit=2
```

```
http://atsd_server:8088/api/v1/entities/{entity}?timeFormat=iso
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for metric name. Use `*` placeholder in `like` expresions|
|active|no|Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response, use `tags=*` as a wildcard (returns all existing tags)|
|minInsertDate|no|return metrics with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return metrics with lastInsertTime less than specified time, accepts iso date format|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields

> Response

```json
 [
    {
        "name": "m-vers",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1445205600000,
        "versioned": true
    },
    {
        "name": "24h_average",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "versioned": false
    }
]
```

|**Field**|**Description**|
|---|---|
|name|Metric name (unique)|
|label|Metric label|
|enabled|Enabled status. Incoming data is discarded for disabled metrics|
|dataType|short, integer, float, long, double|
|timePrecision|seconds, milliseconds|
|persistent |Persistence status. Non-persistent metrics are not stored in the database and are only used in rule engine.|
|counter|Metrics with continuously incrementing value should be defined as counters|
|filter |If filter is specified, metric puts that do not match the filter are discarded|
|minValue |Minimum value. If value is less than Minimum value, Invalid Action is triggered|
|maxValue|Maximum value. If value is greater than Maximum value, Invalid Action is triggered|
|invalidAction |None - retain value as is; Discard - don't process the incoming put, discard it; Transform - set value to `min_value` or `max_value`; `Raise_Error` - log error in ATSD log|
|description |Metric description|
|retentionInterval|Number of days to retain values for this metric in the database|
|lastInsertTime|Last time value was received by ATSD for this metric. Time specified in epoch milliseconds.|
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
|tags as requested by tags parameter|User-defined tags|
|versioned| If set to true, enables versioning for the specified metric. When metrics is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.|


 <aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>

### Examples

 ### Fetch all 'disk' metrics

Fetch all metrics whos name includes `disk`, including all tags.

```
http://atsd_server:8088/api/v1/metrics?tags=*&expression=name%20like%20%27*disk*%27
```

> Response

```json
[
    {
        "name": "aws_ec2.diskreadbytes.average",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.maximum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.minimum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    }
]
```

### Fetch metrics with tag 'table'

```
http://atsd_server:8088/api/v1/metrics?timeFormat=iso&tags=table&limit=2&expression=tags.table%20!=%20%27%27

```

```
expression=tags.table != ''
```

> Response

```json
[
    {
        "name": "collector-csv-job-exec-time",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "versioned": false
    },
     {
        "name": "collector-http-connection",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:57:22.649Z",
        "versioned": false
    }
]
```

### Fetch metrics by name and tag

Fetch metrics starting with `nmon` and with tag `table` starting with `CPU`

```
http://atsd_server:8088/api/v1/metrics?timeFormat=iso&active=true&tags=table&limit=2&expression=name%20like%20%27nmon*%27%20and%20tags.table%20like%20%27*CPU*%27
```

> Expression:

```
name like 'nmon*' and `tags.table` like '*CPU*'
```

> Response

```json
[
   {
        "name": "nmon.cpu.busy%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    },
    {
        "name": "nmon.cpu.idle%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    }
]
```
