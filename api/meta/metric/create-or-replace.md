## Metric: Create or Replace
### Method 
```
PUT /api/v1/metrics/{metric}
```

Create a metric with specified properties and tags or replace an existing metric.

If the metric exists, all of its current properties and tags will be overwritten with fields specified in the request.

In order to update only specific fields specified in the request, use [Metric: Update](update.md) method.

### Basic Example


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
### Request Fields

Refer to Response Fields specified in [Metrics List](list.md#fields) method.




