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
    "tags": {"path": "/", "name": "sda"}
},{
    "entity": "nurswgvml007",
    "type": "application",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "severity": "MAJOR",
    "source": "atsd",
    "tags": {"path": "/", "name": "sda"}
}]
```

## Response
```
```
