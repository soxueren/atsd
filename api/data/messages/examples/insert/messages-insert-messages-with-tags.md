# Message Insert With Tags

## Description

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
    "tags": {"path": "/", "name": "sda"},
    "source": "atsd"
}]
```

## Response
```
```
