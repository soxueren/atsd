# Message Insert With Date
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
    "source": "atsd",
    "date": "2016-06-14T09:12:00Z"
}]
```

## Response
```
```
