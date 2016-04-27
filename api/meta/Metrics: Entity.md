## Metrics: Entity

```
GET /api/v1/entities/{entity}/metrics
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/metrics?timeFormat=iso&limit=2
```

Returns a list of metrics collected by the entity. The list is based on memory cache which is rebuilt on ATSD restart.

### Request Parameters

> Response

```json
[
    {
        "name": "active",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-09-04T16:10:21.000Z"
    },
    {
        "name": "active(anon)",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-09-04T16:10:21.000Z"
    }
]
```

|**Parameter**|**Required**|**Description**|
|---|---|---|---|
|active|no| Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|expression|no|Use name variable for entity name. Use * placeholder in like expressions|
|tags|no|Specify metric tags to be included in the response|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

**Response Fields:**

See: [Metrics: List](#metrics:-list)
