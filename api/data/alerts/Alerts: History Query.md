## Alerts: History Query

### Request Parameters

```
POST /api/v1/alerts/history
```

> Request

```json
{
   "queries": [
   {
      "startTime":1421311064724,
      "endTime":1421311064724,
      "metric": "cpu_busy",
      "entity" : "host",
      "entityGroup":"group",
      "rule":"cpu_busy_monitor",
      "limit" : 10
   }
   ]
}
```

> Response

```json
[
   {
        "alert": null,
        "alertDuration": 45000,
        "alertOpenTime": 1422224160000,
        "entity": "nurswgvml006",
        "metric": "disk_used_percent",
        "receivedTime": 1422224206474,
        "repeatCount": "2",
        "rule": "disk_threshold",
        "ruleExpression": "new_maximum() && threshold_linear_time(99) < 120",
        "ruleFilter": null,
        "schedule": null,
        "severity": "UNDEFINED",
        "tags": null,
        "time": 1422224206000,
        "type": "CANCEL",
        "value": 57.3671,
        "window": null
   }
]
```

|**Name**| **Required** | **Description** |
|---|---|---|
| startTime| yes |Unix timestamp, default 0|
|endTime| yes | Unix timestamp, default `Long.MAX_VALUE`|
|metric| yes |a metric name of the requested time series |
|entity| yes |an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
|entityGroup| no | group of entities |
|rule| yes | alert rule |
|limit| no | default 1000|
