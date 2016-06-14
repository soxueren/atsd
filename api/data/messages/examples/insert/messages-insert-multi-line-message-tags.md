# Message Insert With Tags
## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/insert
```
### Payload
```json
[{
    "entity": "nurswgvml025",
    "type": "application",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "tags": {"path": "/path1 \n /path2", "name": "sda1 \n sda2"},
    "source": "atsd"
}]
```

## Response
```
```
