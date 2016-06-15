# Discarded duplicate tags messages
## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "type": "application",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "severity": "MAJOR",
    "source": "atsd",
    "tags": {"path": "/", "name": "sda"},
    "date": "2016-06-15T10:52:00Z"
},{
    "entity": "nurswgvml007",
    "type": "application",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8882: failed.",
    "severity": "MAJOR",
    "source": "atsd",
    "tags": {"path": "/", "name": "sda"},
    "date": "2016-06-15T10:52:00Z"
}]
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
    "type": "application",
    "source": "atsd",
    "severity": "MAJOR",
    "tags": {
      "name": "sda",
      "path": "/"
    },
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8882: failed.",
    "date": "2016-06-15T10:52:00.000Z"
  }
]
```
