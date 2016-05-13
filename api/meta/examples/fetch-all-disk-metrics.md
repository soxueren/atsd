## Fetch all 'disk' metrics

Fetch all metrics whose name includes `disk`, including all tags.

### Request
```
http://atsd_server:8088/api/v1/metrics?tags=*&expression=name%20like%20%27*disk*%27
```

### Response

```json
[
    {
        "name": "aws_ec2.diskreadbytes.average",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.maximum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.minimum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    }
]
```
