# Metrics: List
## Description 
Return a list of metrics and their properties.
## Path
```
/api/v1/metrics
```
## Method
```
GET 
```

## Request

### Parameters

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Filter to include metrics that match the specified expression.<br>Use `name` variable for metric name. `*` wildcard is supported in `like` expressions. <br> Expression must be URL-encoded.|
|active|no|Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response, use `tags=*` as a wildcard (returns all existing tags)|
|minInsertDate|no|return metrics with `lastInsertTime` equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return metrics with `lastInsertTime` less than specified time, accepts iso date format|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

## Response 
### Fields
|**Field**|**Description**|
|---|---|
|name|Metric name (unique)|
|label|Metric label|
|enabled|Enabled status. Incoming data is discarded for disabled metrics|
|dataType|[Data type](#data-types)|
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

### Data Types

|**Type**|**Size, bytes**|
|---|:---|
|short|2|
|integer|4|
|long|8|
|float|4|
|double|8|
|decimal|variable|

Default data type for new metrics, when auto-created, is **float**. 

### Errors

|  Status Code  |  Description  |
|---------------|---------------|
| 500 |TypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'com.axibase.tsd.model.TimeFormat';|
| 500 |TypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'|

## Example 

### Request

#### URI

```
https://atsd_host:8443/api/v1/metrics?limit=2
```

#### curl

```css
curl --insecure https://atsd_host:8443/api/v1/metrics?limit=2 \
  -v -u {username}:{password} \
  -X GET
```

### Response

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



## Additional Examples
* [List metrics name by name](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metrics-by-name.md)
* [List metrics with tag table](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metrics-with-tag-table.md)
* [List metric with parameter 'timeFormat'](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metric-with-timeformat.md)
* [List active metrics](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-active-metrics.md)
* [List metrics by 'maxInsertTime'](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metrics-by-maxinserttime.md)
* [List metrics by 'minInsertTime'](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metrics-by-mininserttime.md)
* [List metrics by name and tag](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/list-metrics-by-name-and-tag.md)



