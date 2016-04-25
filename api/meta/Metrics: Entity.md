## Metrics: Entity

```
GET /api/v1/entities/{entity}/metrics
```

> Request

```
http://atsd_server.com:8088/api/v1/entities/nurswgvml007/metrics?limit=2
```

Returns a list of metrics collected by the entity. The list is based on memory cache which is rebuilt on ATSD restart.

### Request Parameters

> Response

```json
[{
    "name": "active",
    "enabled": true,
    "counter": false,
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM",
    "lastInsertTime": 1423143844000
},
{
    "name": "active(anon)",
    "enabled": true,
    "counter": false,
    "label": "% Processor Time",
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM",
    "lastInsertTime": 1423143844000
}]
```

|**Parameter**|**Required**|**Description**|
|---|---|---|---|
|active|no| Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response|
|limit|no|Limit response to first N metrics, ordered by name.|

**Response Fields:**

See: [Metrics: List](#metrics:-list)
