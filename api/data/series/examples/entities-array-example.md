### Entities Array Example

> Request

```json
{
    "queries": [
        {
            "entities": [
                "nurswgvml006",
                "nurswgvml007"
            ],
            "metric": "mpstat.cpu_busy",
            "interval": {
                "count": 5,
                "unit": "MINUTE"
            },
            "endDate": "now",
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
                    "d": "2015-09-22T11:34:20.000Z",
                    "v": 4.04
                },
                {
                    "d": "2015-09-22T11:34:36.000Z",
                    "v": 6.06
                },
                {
                    "d": "2015-09-22T11:34:52.000Z",
                    "v": 6
                }
            ]
        },
        {
            "entity": "nurswgvml006",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:34:16.000Z",
                    "v": 5.05
                },
                {
                    "d": "2015-09-22T11:34:32.000Z",
                    "v": 3
                },
                {
                    "d": "2015-09-22T11:34:48.000Z",
                    "v": 1.01
                }
            ]
        }
    ]
}
```

