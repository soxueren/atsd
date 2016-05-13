# Insert Named Forecast

## Request
### URI
```
POST https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[
    {
        "entity": "nurswgvml007",
        "metric": "df.disk_used_percent",
        "type": "FORECAST",
        "data": [
            {
                "t": 1462427358127,
                "v": 9497228.587367011
            }
        ]
    }
]
```
## Response 
```
```
