# Fetch metric with parameter 'timeFormat'

## Request

### URI
```
GET https://atsd_host:8443/api/v1/metrics?timeformat=iso
```
## Response 
```json
[
   {
      "name":"df.disk_size",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-13T12:15:55.000Z",
      "versioned":false
   },
   {
      "name":"df.disk_used",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-13T12:15:55.000Z",
      "versioned":false
   },
   {
      "name":"df.disk_used_percent",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-13T12:15:55.000Z",
      "versioned":false
   }
]
```
