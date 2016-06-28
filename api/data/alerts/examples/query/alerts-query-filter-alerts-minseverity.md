# Filter Alerts for minSeverity

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
    "minSeverity": "1",
    "startDate": "2016-06-23T04:00:00Z",
    "endDate": "2016-06-28T05:00:00Z"
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
    "repeatCount": 1,
    "textValue": "22.6",
    "metric": "nmon.cpu_total.busy%",
    "severity": "WARNING",
    "rule": "nmon_cpu_alert",
    "openDate": "2016-06-27T11:24:36.193Z",
    "lastEventDate": "2016-06-27T11:25:36.233Z",
    "acknowledged": false,
    "openValue": 21.2,
    "value": 22.6,
    "id": 408
  }
]
```

