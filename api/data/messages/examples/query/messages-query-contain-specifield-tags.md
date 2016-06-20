# Query Messages that Contain the Specified Tag

## Description

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
    "tags": {"name" : ["*"]},
    "startDate": "2016-06-15T14:13:57.383Z",
    "endDate": "2016-06-18T14:13:57.383Z"
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
    "tags": {
      "name": "sd"
    },
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8882: failed.",
    "date": "2016-06-17T14:45:28.125Z"
  },
  {
    "entity": "nurswgvml007",
    "type": "default",
    "source": "default",
    "severity": "NORMAL",
    "tags": {
      "name": "sda"
    },
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-17T14:45:27.125Z"
  }
]
```
