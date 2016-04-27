## Metric: Create or Replace

```
PUT /api/v1/metrics/{metric}
```

Create a metric with specified properties and tags or replace an existing metric.
This method creates a new metric or replaces an existing metric. 

### Request Fields

> Request

```json
{
    "enabled": true,
    "counter": false,
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM",
    "versioned": true
}
```

|**Field**|**Description**|
|---|---|
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
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"table": "axibase-collector"}`|
|versioned| If set to true, enables versioning for the specified metric. When metrics is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.|

<aside class="notice">
If only a subset of fields is provided for an existing metric, the remaining properties and tags will be deleted.
</aside>
