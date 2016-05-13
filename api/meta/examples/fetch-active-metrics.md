# Fetch active metrics 

Fetch all active metrics with name like '*email*'
## Request
### URI
```
https://atsd_host:8443/api/v1/metrics?active=true&expression=name%20like%20%27*email*%27
```
## Response
```json
[
   {
      "name":"email_notifications_per_minute",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463125772552,
      "versioned":false
   }
]
```
