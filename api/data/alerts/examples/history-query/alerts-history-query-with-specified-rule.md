# History Query with Specified Rule

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/alerts/history/query
```
### Payload

```json
[
  {
    "rule": "nmon_cpu_alert",
    "startDate": "2016-06-27T18:00:00Z",
    "interval": {
            "count": 300,
            "unit": "HOUR"
        },
    "limit": 3
  }
]
```

## Response

### Payload
```json
[
  {
    "date": "2016-06-28T07:21:16.000Z",
    "entity": "nurswgvml501",
    "metric": "nmon.cpu_total.busy%",
    "type": "CANCEL",
    "value": 2.6,
    "alert": "Cancel alert for nurswgvml501, nmon.cpu_total.busy%, {}",
    "severity": "UNDEFINED",
    "rule": "nmon_cpu_alert",
    "ruleExpression": "max() > 10",
    "window": "length(3)",
    "repeatCount": 2,
    "alertDuration": 180077,
    "alertOpenDate": "2016-06-28T07:18:16.695Z",
    "receivedDate": "2016-06-28T07:21:16.772Z"
  },
  {
    "date": "2016-06-28T07:18:16.000Z",
    "entity": "nurswgvml501",
    "metric": "nmon.cpu_total.busy%",
    "type": "OPEN",
    "value": 11.5,
    "alert": "Open alert for nurswgvml501, nmon.cpu_total.busy%, {}. Value: 11.5",
    "severity": "WARNING",
    "rule": "nmon_cpu_alert",
    "ruleExpression": "max() > 10",
    "window": "length(3)",
    "repeatCount": 0,
    "alertDuration": 0,
    "alertOpenDate": "2016-06-28T07:18:16.695Z",
    "receivedDate": "2016-06-28T07:18:16.695Z"
  },
  {
    "date": "2016-06-28T07:15:38.000Z",
    "entity": "nurswgvml201",
    "metric": "nmon.cpu_total.busy%",
    "type": "OPEN",
    "value": 21.4,
    "alert": "Open alert for nurswgvml201, nmon.cpu_total.busy%, {}. Value: 21.4",
    "severity": "WARNING",
    "rule": "nmon_cpu_alert",
    "ruleExpression": "max() > 10",
    "window": "length(3)",
    "repeatCount": 0,
    "alertDuration": 0,
    "alertOpenDate": "2016-06-28T07:15:38.410Z",
    "receivedDate": "2016-06-28T07:15:38.410Z"
  }
]
```

