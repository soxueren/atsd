# Series Insert in Scientific Notation

## Request

### URI
```elm
POST http://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "metric": "mpstat.cpu_busy",
    "data": [
      { "t": 1462427358127, "v": 1.234e1 }
    ]
}]
```

## Response
```
```
