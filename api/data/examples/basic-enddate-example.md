### Basic endDate Example

> Request

```json
{
    "queries": [
        {
            "endDate": "now",
            "interval": {
                "count": 5,
                "unit": "MINUTE"
            },
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "timeFormat": "iso"
        }
    ]
}
```

> Response

```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-07T12:16:05.000Z",
                    "v": 73.68
                },
                {
                    "d": "2015-09-07T12:16:21.000Z",
                    "v": 52.58
                },
                {
                    "d": "2015-09-07T12:16:37.000Z",
                    "v": 44.33
                },
                {
                    "d": "2015-09-07T12:16:53.000Z",
                    "v": 40.43
                }
            ]
        }
    ]
}
```
