## Fetch metrics with tag 'table'

### Request
```
/api/v1/metrics?timeFormat=iso&tags=table&limit=2&expression=tags.table%20!=%20%27%27

```
### Expression
```
expression=tags.table != ''
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
        "lastInsertDate": "2015-11-18T14:57:22.649Z",
        "versioned": false
    }
]
```
