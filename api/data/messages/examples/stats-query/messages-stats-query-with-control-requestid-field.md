# Stats-Query With Control requestId Field

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/stats/query
```
### Payload

```json
[
  {
    "entities": "nurswgvml006",
    "metric": "message-count",
    "aggregate": {
      "types": [
        "COUNT"
      ],
      "period": {
        "count": 3,
        "unit": "MINUTE"
      }
    },
    "startDate": "2016-06-20T14:00:00.000Z",
    "endDate": "2016-06-20T14:10:00.000Z",
    "requestId": 1
  }
]
```

## Response

### Payload
```json
[
  {
    "requestId": "1",
    "entity": "nurswgvml006",
    "metric": "message-count",
    "tags": {},
    "type": "HISTORY",
    "aggregate": {
      "type": "COUNT",
      "period": {
        "count": 3,
        "unit": "MINUTE"
      }
    },
    "data": [
      {
        "d": "2016-06-20T14:00:00.000Z",
        "v": 4
      },
      {
        "d": "2016-06-20T14:03:00.000Z",
        "v": 30
      },
      {
        "d": "2016-06-20T14:06:00.000Z",
        "v": 6
      }
    ]
  }
]
```

