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

|**Parameter**|**Type**|:**Description**|
|---|---|---|
|expression|string|Include metrics that match an expression containing `name` and `tags.{tag-name}` variables. <br>`LIKE` operator supports `*` wildcard, for example `name LIKE 'cpu*'`.|
|minInsertDate|iso_date|Include metrics with `lastInsertDate` equal or greater than specified date.|
|maxInsertDate|iso_date|Include metrics with `lastInsertDate` less than specified date.|
|tags|string|Comma-separated list of metric tags to be included in the response.<br>Set `tags=*` to include all metric tags.|
|limit|integer|Limit response to first N metrics, ordered by name.|

_All parameters are optional. Expression must be URL-encoded._

## Response 

### Fields
|**Field**|**Description**|
|---|:---|
|name|Metric name|
|label|Metric label|
|description |Metric description|
|tags|Array of metric tag name:value objects.|
|enabled|Enabled status. Incoming data is discarded for disabled metrics.|
|dataType|[Data type](#data-types)|
|timePrecision|seconds or milliseconds|
|persistent |Persistence status. Non-persistent metrics are not stored in the database and are only processed by the rule engine.|
|versioned| If set to true, enables versioning for the specified metric. When metrics is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.|
|counter|Metrics represents a continuously incrementing counter.|
|filter |Persistent filter. If specified series insert commands that do not match the filter are discarded.|
|minValue |Minimum value. If value is less than Minimum value, Invalid Action is triggered|
|maxValue|Maximum value. If value is greater than Maximum value, Invalid Action is triggered|
|invalidAction |None - retain value as is. <br>Discard - don't process the incoming put, discard it.<br> Transform - set value to `min_value` or `max_value`.<br> `Raise_Error` - log error in ATSD log.|
|retentionInterval|Number of days to retain values for this metric in the database|
|lastInsertDate|Last time a value was received for this metric by any series. ISO date.|

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
  --verbose --user {username}:{password} \
  --request GET
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



