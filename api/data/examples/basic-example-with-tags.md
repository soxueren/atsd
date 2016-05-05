### Basic Example with Tags

> Request

```json
{
    "queries": [
        {
            "startDate": "2015-02-22T13:37:00Z",
            "endDate": "2015-02-23T13:37:00Z",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "metric": "Busy_CPU_Detail",
            "tags": {
                "CPU_ID": "-1"
            },
            "type": "HISTORY"
        }
    ]
}
```

> Response

```json
    {
      "series": [
        {
          "entity": "NURSWGVML007",
          "metric": "Busy_CPU_Detail",
          "tags": { "CPU_ID": "-1"},
          "type": "HISTORY",
          "data": [
            { "d": "2015-02-22T14:00:53Z", "v": 8.62},
            { "d": "2015-02-22T14:30:53Z", "v": 8.69}
          ]
        }
      ]
    }
```

