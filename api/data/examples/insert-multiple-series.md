#Insert Multiple Series Example

## Request 
### URI
```
https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 17.8 }
    ]
},{
    "entity": "nurswth2309",
    "metric": "df.disk_used_percent",
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 42.2 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 41.8 }
    ]
}]
```

## Response
