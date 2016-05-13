# Insert multiple samples
## Request
### URI
```
POST https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "data": [
      { "t": 1462427358127, "v": 17.7 },
      { "t": 1462427358744, "v": 17.8 }
    ]
}]
```

## Response
```
```
