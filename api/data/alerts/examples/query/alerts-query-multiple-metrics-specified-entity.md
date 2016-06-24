# Multiple Metrics for Specified Entity

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
      "disk_used_percent",
      "nmon.cpu_total.busy%"
    ],
    "entity": "nurswgvml007",
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
    "tags": {
      "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
      "mount_point": "/"
    },
    "repeatCount": 1064,
    "textValue": "65.638",
    "metric": "disk_used_percent",
    "severity": "CRITICAL",
    "rule": "disk_low_alert",
    "openDate": "2016-06-24T10:28:48.387Z",
    "lastEventDate": "2016-06-24T14:55:00.432Z",
    "acknowledged": false,
    "openValue": 65.5204,
    "value": 65.6383,
    "id": 5
  },
  {
    "entity": "nurswgvml007",
    "tags": {},
    "repeatCount": 0,
    "textValue": "33.4",
    "metric": "nmon.cpu_total.busy%",
    "severity": "WARNING",
    "rule": "nmon_cpu_alert",
    "openDate": "2016-06-24T14:54:51.733Z",
    "lastEventDate": "2016-06-24T14:54:51.733Z",
    "acknowledged": false,
    "openValue": 33.4,
    "value": 33.4,
    "id": 143
  }
]
```

