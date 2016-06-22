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
        "count": 10,
        "unit": "MINUTE"
      }
    },
    "startDate": "2016-06-20T14:00:00.000Z",
    "endDate": "2016-06-20T15:00:00.000Z",
    "requestId": 1
  },
  {
    "entities": "nurswgvml006",
    "metric": "message-count",
    "aggregate": {
      "types": [
        "COUNT"
      ],
      "period": {
        "count": 10,
        "unit": "MINUTE"
      }
    },
    "startDate": "2016-06-20T16:00:00.000Z",
    "endDate": "2016-06-20T17:00:00.000Z",
    "requestId": 2
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
        "count": 10,
        "unit": "MINUTE"
      }
    },
    "data": [
      {
        "d": "2016-06-20T14:00:00.000Z",
        "v": 40
      },
      {
        "d": "2016-06-20T14:10:00.000Z",
        "v": 15
      },
      {
        "d": "2016-06-20T14:20:00.000Z",
        "v": 40
      },
      {
        "d": "2016-06-20T14:30:00.000Z",
        "v": 20
      },
      {
        "d": "2016-06-20T14:40:00.000Z",
        "v": 23
      },
      {
        "d": "2016-06-20T14:50:00.000Z",
        "v": 38
      }
    ]
  },
  {
    "requestId": "2",
    "entity": "nurswgvml006",
    "metric": "message-count",
    "tags": {},
    "type": "HISTORY",
    "aggregate": {
      "type": "COUNT",
      "period": {
        "count": 10,
        "unit": "MINUTE"
      }
    },
    "data": [
      {
        "d": "2016-06-20T16:00:00.000Z",
        "v": 41
      },
      {
        "d": "2016-06-20T16:10:00.000Z",
        "v": 15
      },
      {
        "d": "2016-06-20T16:20:00.000Z",
        "v": 9
      },
      {
        "d": "2016-06-20T16:30:00.000Z",
        "v": 14
      },
      {
        "d": "2016-06-20T16:40:00.000Z",
        "v": 25
      },
      {
        "d": "2016-06-20T16:50:00.000Z",
        "v": 25
      }
    ]
  }
]
```

