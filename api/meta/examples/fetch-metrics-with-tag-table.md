# Fetch metrics with tag 'table'

## Request
### URI
```elm
GET https://atsd_host:8443/api/v1/metrics?tags=table&expression=tags.table%20!=%20%27%27

```
### Expression
```
tags=table&expression=tags.table != ''

```
### Response


```json
[
    {
        "name": "collector-csv-job-exec-time",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "versioned": false
    },
     {
        "name": "collector-http-connection",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": 1447858020000,
        "versioned": false
    }
]
```
