# List metrics by maxInsertTime

List all metrics with `lastInsertTime` less than 2016-05-19T08:13:40.000Z
## Request
### URI
```elm
GET https://atsd_host:8443/api/v1/metrics?maxInsertDate=2016-05-19T08:13:40.000Z&timeFormat=iso
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
      "lastInsertTime":"2016-05-18T22:50:00.000Z",
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
      "lastInsertTime":"2016-05-18T22:50:00.000Z",
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
      "lastInsertTime":"2016-05-05T05:50:18.312Z",
      "versioned":false
   }
]
```
