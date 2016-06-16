# Message With Null Or Without Source Value

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
    "source": "",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed."
  }
]
```

## Response

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/query
```
### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "type": "default",
    "source": "default",
    "severity": "NORMAL",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-15T14:13:57.383Z"
  }
]
```
