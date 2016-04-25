## Metrics: List

### Request Parameters

```
GET /api/v1/metrics
```

> Request

```
http://atsd_server.com:8088/api/v1/metrics?limit=2
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for metric name. Use `*` placeholder in `like` expresions|
|active|no|Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response|
|limit|no|Limit response to first N metrics, ordered by name.|

### Response Fields

> Response

```json
[{
    "name": "%_privileged_time",
    "enabled": true,
    "counter": false,
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM"
},
{
    "name": "%_processor_time",
    "enabled": true,
    "counter": false,
    "label": "% Processor Time",
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM"
}]
```

|**Name**|**Description**|
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
|tags as requested by tags parameter|User-defined tags|

### Sample Request

Fetch metrics with tag `table` and include this tag in results:

```
http://atsd_server.com:8088/api/v1/metrics?tags=table&limit=2&expression=tags.table%20!=%20%27%27
```

```
expression=tags.table != ''
```

> Response

```json
[{
    "name": "collector-csv-job-exec-time",
    "enabled": true,
    "counter": false,
    "persistent": true,
    "tags": {
        "table": "axibase-collector"
    },
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "NONE"
},
{
    "name": "collector-http-connection",
    "enabled": true,
    "counter": false,
    "persistent": true,
    "tags": {
        "table": "axibase-collector"
    },
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "NONE",
    "lastInsertTime": 1422975131595
}]
```

### Sample Request

Fetch metrics starting with `nmon` and with tag `table` starting with `CPU`

```
http://atsd_server.com:8088/api/v1/metrics?active=true&tags=table&limit=2&expression=name%20like%20%27nmon*%27%20and%20tags.table%20like%20%27*CPU*%27
```

> Expression:

```
name like 'nmon*' and `tags.table` like '*CPU*'
```

> Response

```json
[{
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
    "lastInsertTime": 1423143844000
},
{
    "name": "nmon.cpu.sys%",
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
    "lastInsertTime": 1423143844000
}]
```
