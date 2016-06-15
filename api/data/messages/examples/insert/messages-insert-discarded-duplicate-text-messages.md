# Discarded duplicate text messages

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
    "severity": "MAJOR",
    "source": "atsd",
    "date": "2016-06-14T14:52:00Z"
},{
    "entity": "nurswgvml007",
    "type": "application",
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "severity": "MAJOR",
    "source": "atsd",
    "date": "2016-06-14T14:52:00Z"
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
    "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
    "date": "2016-06-14T14:52:00.000Z"
  }
]
```
