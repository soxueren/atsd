# Insert Multiple Series

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
      { "t": 1462427359238, "v": 17.7  }
    ]
},{
    "entity": "nurswth2309",
    "metric": "mpstat.cpu_busy",
    "data": [
      { "t": 1462497512219, "v": 27.8  }
    ]
}]
```

## Response
```
```
