## Metric: Get

```
GET /api/v1/metrics/{metric}
```

> Request

```
http://atsd_server:8088/api/v1/metrics/mpstat.cpu_busy?timeFormat=iso
```

> Response

```json
{
    "name": "mpstat.cpu_busy",
    "enabled": true,
    "dataType": "FLOAT",
    "counter": false,
    "persistent": true,
    "tags": {},
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "NONE",
    "lastInsertDate": "2015-10-20T12:13:26.000Z",
    "versioned": false
}
```

Displays metric properties and its tags.

**Response Fields:**

See: [Metrics: List](#metrics:-list)
