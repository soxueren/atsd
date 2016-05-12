# Example 1 - another format of time
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
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 }
    ]
}]
```

## Response
