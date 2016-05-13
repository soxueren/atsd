# Insert named forecast

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
        "forecastName": "arima",
        "data": [
            {
                "t": 1462427361327,
                "v": 9497228.587367011
            }
        ]
    }
]
```
## Response 
```
```
