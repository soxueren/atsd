## Metrics: List

### Path
```
/api/v1/metrics
```
### Method
```
GET 
```

### Request

#### Parameters

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for metric name. Use `*` placeholder in `like` expresions|
|active|no|Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response, use `tags=*` as a wildcard (returns all existing tags)|
|minInsertDate|no|return metrics with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return metrics with lastInsertTime less than specified time, accepts iso date format|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response 
#### Fields
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

### Example 

> Request

```
/api/v1/metrics?limit=2
```



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



### Additional Examples
* [Fetch all disk metrics](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/fetch-all-disk-metrics.md)
* [Fetch metrics with tag table](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/fetch-metrics-with-tag-table.md)
* [Fetch metrics by name and tag](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/fetch-metrics-by-name-and-tag.md)
* [Fetch metric with parameter 'timeFormat'](https://github.com/axibase/atsd-docs/blob/master/api/meta/examples/fetch-metric-with-timeformat.md)




