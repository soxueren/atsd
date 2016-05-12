# Insert Named Forecast

## Request
### URI
```
https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[
    {
        "entity": "duckduckgo",
        "metric": "direct.queries",
        "tags": {},
        "forecastName": "DuckDuckGo2",
        "type": "FORECAST",
        "data": [
            {
                "d": "2015-06-17T00:00:00.000Z",
                "v": 9497228.587367011
            },
            {
                "d": "2015-06-18T00:00:00.000Z",
                "v": 9517253.496233052
            },
            {
                "d": "2015-06-19T00:00:00.000Z",
                "v": 9227410.099153783
            }
        ]
    }
]
```
## Response 
