# Insert multiple samples
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
      { "t": 1462427358127, "v": 17.7 },
      { "t": 1462427358744, "v": 17.8 }
    ]
}]
```

## Response
```
```
