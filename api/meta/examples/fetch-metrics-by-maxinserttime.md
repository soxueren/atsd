# Fetch metrics by maxInsertTime

Fetch all metrics with `lastInsertTime` less than 2016-05-13T08:13:40 and name like '*second*', timeFormat=iso
## Request
### URI
```
https://atsd_host:8443/api/v1/metrics?maxInsertDate=2016-05-13T08:13:40&timeFormat=iso&expression=name%20like%20%27*second*%27
```

## Response
```json
[
   {
      "name":"message_gets_per_second",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-06T15:54:51.431Z",
      "versioned":false
   },
   {
      "name":"property_gets_per_second",
      "enabled":true,
      "dataType":"FLOAT",
      "counter":false,
      "persistent":true,
      "timePrecision":"MILLISECONDS",
      "retentionInterval":0,
      "invalidAction":"NONE",
      "lastInsertDate":"2016-05-06T15:54:51.431Z",
      "versioned":false
   }
]
```
