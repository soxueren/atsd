# Filter Alerts for Severities

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/alerts/query
```
### Payload

```json
[
  {
    "metrics": [
      "nmon.cpu_total.busy%",
      "message"
    ],
    "entity": "nurswgvml007",
    "severities": [
      "MINOR",
      "NORMAL"
    ],
    "startDate": "2016-06-20T04:00:00Z",
    "endDate": "2016-06-25T05:00:00Z"
  }
]
```

## Response

### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "tags": {},
    "repeatCount": 0,
    "textValue": "netstat",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:00:30.970Z",
    "lastEventDate": "2016-06-24T15:00:30.970Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": "netstat",
    "id": 155
  }
]
```

