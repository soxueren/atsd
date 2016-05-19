
# Insert Forecast Deviation 


## Request
### URI
```elm
POST https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[
    {
        "entity": "nurswgvml007",
        "metric": "mpstat.cpu_busy",
        "type": "FORECAST",
        "forecastName":"cpu_busy_local)",
        "data": [
            {
                "t":1462427358127,
                "v":25.0,
                "s":7.0
            }
        ]
    }
]
```
## Response 
```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "FORECAST_DEVIATION",
            "aggregate": {
                "type": "DETAIL"
            },
            "forecastName": "test",
            "data": [
                {
                    "t": 1462427358127,
                    "v": 7
                }
            ]
        }
    ]
}
```
