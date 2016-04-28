### SLA Example

> Request

```json
    {
        "queries": [
            {
                "startDate": "2015-02-22T13:37:00Z",
                "endDate": "2015-02-23T13:37:00Z",
                "timeFormat": "iso",
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "type": "history",
                "aggregate": {
                    "types": [
                        "THRESHOLD_COUNT",
                        "THRESHOLD_DURATION",
                        "THRESHOLD_PERCENT"
                    ],
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    },
                    "workingMinutes": {
                        "start": 540,
                        "end": 1080
                    },
                    "threshold": {
                        "max": 80
                    },
                    "calendar": {
                        "name": "my_calendar"
                    }
                }
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
                    "type": "THRESHOLD_COUNT",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 0
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_DURATION",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 0
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_PERCENT",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 100
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 100
                    }
                ]
            }
        ]
    }
```

