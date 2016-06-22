# Stats-Query With Type DETAIL Aggregate Field 

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
        "DETAIL"
      ]
    },
    "startDate": "2016-06-20T14:00:00.000Z",
    "endDate": "2016-06-20T14:10:00.000Z"
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
      "type": "DETAIL"
    },
    "data": [
      {
        "d": "2016-06-20T14:00:00.000Z",
        "v": 40
      }
    ]
  }
]
```

