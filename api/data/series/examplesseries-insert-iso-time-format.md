# Series Insert in ISO time format
## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "metric": "mpstat.cpu_busy",
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 }
    ]
}]
```

## Response
```
```
