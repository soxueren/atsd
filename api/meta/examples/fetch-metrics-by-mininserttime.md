# Fetch metrics by minInsertTime

Fetch three metrics with lastInsertTime qual or greater than 2016-05-13T08:13:40, timeFormat = iso

## Request
### URI
```
https://atsd_host:8443/api/v1/metrics?minInsertDate=2016-05-13T08:13:40&timeFormat=iso&limit=3
```
## Response
```json
[
   {
      "name":"api_command_malformed_per_second",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-13T08:16:32.731Z",
      "versioned":false
   },
   {
      "name":"df.disk_size",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-13T08:16:34.000Z",
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
      "lastInsertDate":"2016-05-13T08:16:34.000Z",
      "versioned":false
   }
]
```
