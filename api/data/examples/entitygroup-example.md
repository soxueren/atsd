### EntityGroup Example

> Request

```json
{
    "queries": [
        {
            "entityGroup": "nur-entities-name",
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
                    "d": "2015-09-22T11:36:28.000Z",
                    "v": 7
                },
                {
                    "d": "2015-09-22T11:36:44.000Z",
                    "v": 5.1
                },
                {
                    "d": "2015-09-22T11:37:00.000Z",
                    "v": 56.52
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
                    "d": "2015-09-22T11:36:24.000Z",
                    "v": 7.07
                },
                {
                    "d": "2015-09-22T11:36:40.000Z",
                    "v": 6.86
                },
                {
                    "d": "2015-09-22T11:36:56.000Z",
                    "v": 3
                }
            ]
        },
        {
            "entity": "nurswgvml102",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:36:34.000Z",
                    "v": 1.01
                },
                {
                    "d": "2015-09-22T11:36:50.000Z",
                    "v": 0
                },
                {
                    "d": "2015-09-22T11:37:06.000Z",
                    "v": 1
                }
            ]
        }
    ]
}
```

