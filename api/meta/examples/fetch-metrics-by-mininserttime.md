# Fetch metrics by minInsertTime

Fetch three metrics with `lastInsertTime` qual or greater than 2016-05-13T08:13:40

## Request
### URI
```
GET https://atsd_host:8443/api/v1/metrics?minInsertDate=2016-05-13T08:13:40
```
## Response
```json
[
   {
      "name":"properties_pool_active_count",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142625873,
      "versioned":false
   },
   {
      "name":"properties_queue_size",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142625873,
      "versioned":false
   },
   {
      "name":"properties_rejected_count",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463142625873,
      "versioned":false
   }
]
```
