# Stats-Query With Multiple Entities

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
    "entities": [
      "nurswgvml007",
      "nurswgvml006"
    ],
    "metric": "message-count",
    "groupKeys": [
      "entity"
    ],
    "aggregate": {
      "types": [
        "COUNT"
      ],
      "period": {
        "count": 1,
        "unit": "MONTH"
      }
    },
    "startDate": "2016-05-15T14:13:57.383Z",
    "endDate": "2016-06-21T14:13:57.383Z"
  }
]
```

## Response

### Payload
```json
[
  {
    "entity": "nurswgvml006",
    "metric": "message-count",
    "tags": {},
    "type": "HISTORY",
    "aggregate": {
      "type": "COUNT",
      "period": {
        "count": 1,
        "unit": "MONTH"
      }
    },
    "data": [
      {
        "d": "2016-06-01T00:00:00.000Z",
        "v": 127216
      }
    ]
  },
  {
    "entity": "nurswgvml007",
    "metric": "message-count",
    "tags": {},
    "type": "HISTORY",
    "aggregate": {
      "type": "COUNT",
      "period": {
        "count": 1,
        "unit": "MONTH"
      }
    },
    "data": [
      {
        "d": "2016-06-01T00:00:00.000Z",
        "v": 90848
      }
    ]
  }
]
```

