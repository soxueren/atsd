## Metric: Get

```
GET /api/v1/metrics/{metric}
```

> Request

```
http://atsd_server.com:8088/api/v1/metrics/cpu_busy
```

> Response

```json
{
    "name": "cpu_busy",
    "enabled": true,
    "dataType": "FLOAT",
    "counter": false,
    "persistent": true,
    "tags": {
        "source": "iostat",
        "table": "System"
    },
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "minValue": 0.0,
    "maxValue": 100.0,
    "invalidAction": "TRANSFORM",
    "lastInsertTime": 1423662246000
}
```

Displays metric properties and its tags.

**Response Fields:**

See: [Get Metrics](#metrics:-list)
