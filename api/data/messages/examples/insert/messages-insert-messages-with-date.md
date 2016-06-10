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
    "startDate":"2016-06-10T15:00:00Z",
    "endDate":"now"
}]
```

## Response
```
```
