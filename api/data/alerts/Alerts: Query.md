## Alerts: Query

### Request Parameters

```
POST /api/v1/alerts
```

> Request

```json
{
   "queries": [
   {
      "metrics": ["loadavg.5m", "message"],
      "entities" : ["awsswgvml001"],
      "minSeverity" : 2,
      "severities": [2, 6, 7]
   }
   ]
}
```

> Response

```json
[{
    "value": 0.05,
    "message": null,
    "id": 7,
    "tags": {

    },
    "textValue": "0.05",
    "metric": "loadavg.5m",
    "entity": "awsswgvml001",
    "severity": 3,
    "rule": "loadavg - min.interval",
    "repeatCount": 1323,
    "openTime": 1423669251150,
    "lastEventTime": 1423689085375,
    "acknowledged": false,
    "openValue": 0.05
},
{
    "value": 0.0,
    "message": "nodejs app.js 8888 > saver.log &",
    "id": 10,
    "tags": {

    },
    "textValue": "nodejs app.js 8888 > saver.log &",
    "metric": "message",
    "entity": "awsswgvml001",
    "severity": 2,
    "rule": "alert-app",
    "repeatCount": 4,
    "openTime": 1423669500060,
    "lastEventTime": 1423670078855,
    "acknowledged": false,
    "openValue": 0.0
    }]
```

| Field       | Required | Description              |
|-------------|----|----------------------|
| metrics     | no | an array of metric names |
| entities    | no | an array of entity names |
| rules       | no | an array of rules        |
| severities  | no | an array of severities   |
| minSeverity | no | Minimal severity filter  |

**Severity codes**

| **Code** | **Description** |
|---|---|
| 0 | undefined |
| 1 | unknown |
| 2 | normal |
| 3 | warning |
| 4 | minor |
| 5 | major |
| 6 | critical |
| 7 | fatal |
