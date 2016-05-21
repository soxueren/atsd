# Metrics: List

## Description 

Return a list of metrics, their properties, and optional metric tags.

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

|**Parameter**|**Type**|**Description**|
|:---|:---|:---|
|expression|string|Include metrics that match an [expression](../expression.md) filter.|
|minInsertDate|iso_date|Include metrics with `lastInsertDate` equal or greater than `minInsertDate`.|
|maxInsertDate|iso_date|Include metrics with `lastInsertDate` less than `maxInsertDate`.|
|tags|string|Comma-separated list of metric tags to be included in the response.<br>For example, `tags=table,unit`<br>Specify `tags=*` to include all metric tags.|
|limit|integer|Limit response to first N metrics, ordered by name.|

_All parameters are optional. Expression must be URL-encoded._

## Response 

### Fields

|**Field**|**Description**|
|:---|:---|
|name|Metric name.|
|label|Metric label.|
|description |Metric description.|
|tags|An object containing tags as names and values.<br>For example, `"tags": {"table": "axibase-collector"}`|
|dataType|[Data Type](#data-types).|
|timePrecision|SECONDS or MILLISECONDS|
|enabled|Enabled status. Incoming data is discarded for disabled metrics.|
|persistent |Persistence status. Non-persistent metrics are not stored in the database and are only processed by the rule engine.|
|filter |Persistence filter [expression](../expression.md). Discards series that do not match this filter.|
|lastInsertDate|Last time a value was received for this metric by any series. ISO date.|
|retentionInterval|Number of days to retain values for this metric in the database|
|versioned| If set to true, enables versioning for the specified metric. <br>When metrics is versioned, the database retains the history of series value changes for the same timestamp along with `version_source` and `version_status`.|
|minValue|Minimum value for [Invalid Action](#invalid-actions) trigger.|
|maxValue|Maximum value for [Invalid Action](#invalid-actions) trigger.|
|invalidAction |[Invalid Action](#invalid-actions) type.|

### Data Types

|**Type**|**Storage Size, bytes**|
|:---|:---|
|SHORT|2|
|INTEGER|4|
|LONG|8|
|FLOAT|4|
|DOUBLE|8|
|DECIMAL|variable|

Default data type for new metrics, when auto-created, is **float**. 

### Invalid Actions

 Invalid Action is triggered if the received series value is less than Minimum value, or if it's greater than Maximum value.

|**Action**|**Description**|
|:---|:---|
|NONE|Retain value as is.|
|DISCARD|Don't process the received value, discard it.|
|TRANSFORM|Set value to `min_value` or `max_value`, if value is outside of range.|
|RAISE_ERROR|Log ERROR event in the database log.|

### Errors

|  Status Code  |  Description  |
|:---------------|:---------------|
| 500 |TypeMismatchException: <br>Failed to convert value of type 'java.lang.String' to required type 'com.axibase.tsd.model.TimeFormat';|
| 500 |TypeMismatchException: <br>Failed to convert value of type 'java.lang.String' to required type 'int'|

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
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2016-05-19T00:15:02.000Z",
        "versioned": true
    },
    {
        "name": "temperature",
        "enabled": true,
        "dataType": "FLOAT",
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2016-05-18T00:35:12.000Z",        
        "versioned": false
    }
]
```

## Additional Examples

* [List metrics by name](../examples/list-metrics-by-name.md)
* [List metrics by name and tag](../examples/list-metrics-by-name-and-tag.md)
* [List metrics with tag `table`](../examples/list-metrics-with-tag-table.md)
* [List metrics by maxInsertDate](../examples/list-metrics-by-maxinsertdate.md)
* [List metrics by minInsertDate](../examples/list-metrics-by-mininsertdate.md)




