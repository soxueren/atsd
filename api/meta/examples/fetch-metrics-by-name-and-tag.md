# Fetch metrics by name and tag

Fetch metrics starting with `nmon` and with tag `table` starting with `CPU`

## Request 
### URI 
```
/api/v1/metrics?timeFormat=iso&active=true&tags=table&limit=2&expression=name%20like%20%27nmon*%27%20and%20tags.table%20like%20%27*CPU*%27
```

### Expression

```
name like 'nmon*' and `tags.table` like '*CPU*'
```

## Response

```json
[
   {
        "name": "nmon.cpu.busy%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    },
    {
        "name": "nmon.cpu.idle%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    }
]
```
