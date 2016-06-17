# Query With Features Field "limit"

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/insert
```
### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-17T08:01:14.232Z"
  },
  {
    "entity": "nurswgvml007",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-17T08:02:14.232Z"
  },
  {
    "entity": "nurswgvml007",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-17T08:03:14.232Z"
  }
]
```

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/query
```
### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "limit": 2.9,
    "startDate": "2016-06-16T00:00:00.000Z",
    "endDate": "2016-06-18T00:00:00.000Z"
  }
]
```

## Response

### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "type": "default",
    "source": "default",
    "severity": "NORMAL",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8883: failed.",
    "date": "2016-06-17T08:03:14.232Z"
  },
  {
    "entity": "nurswgvml007",
    "type": "default",
    "source": "default",
    "severity": "NORMAL",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8882: failed.",
    "date": "2016-06-17T08:02:14.232Z"
  }
]
```
