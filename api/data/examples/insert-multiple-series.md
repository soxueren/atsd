#Insert Multiple Series Example

## Request 
### URI
### Payload
### curl 
## Response
```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 17.8 }
    ]
},{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 42.2 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 41.8 }
    ]
}]
```
