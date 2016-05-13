# Fetch active metrics 

## Request
### URI
```
https://atsd_host:8443/api/v1/metrics?active=true
```

## Response
```json
[
   {
      "name":"meminfo.active(file)",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463141890000,
      "versioned":false
   },
   {
      "name":"meminfo.anonhugepages",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463141890000,
      "versioned":false
   },
   {
      "name":"meminfo.anonpages",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertTime":1463141890000,
      "versioned":false
   }
]
```
