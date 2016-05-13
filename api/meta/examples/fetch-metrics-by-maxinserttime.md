# Fetch metrics by maxInsertTime

Fetch all metrics with `lastInsertTime` less than 2016-05-14T08:13:40 
## Request
### URI
```elm
GET https://atsd_host:8443/api/v1/metrics?maxInsertDate=2016-05-14T08:13:40
```

## Response
```json
[
   {
      "name":"meminfo.buffers",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142416000,
      "versioned":false
   },
   {
      "name":"meminfo.cached",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142416000,
      "versioned":false
   },
   {
      "name":"meminfo.cmafree",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142416000,
      "versioned":false
   }
]
```
