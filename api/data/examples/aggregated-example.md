### Aggregated Example

> Request

```json
    {
       "queries": [
       {
        "startDate": "2015-02-05T09:53:00Z",
        "endDate": "2015-02-05T09:54:00Z",
        "timeFormat": "iso",
        "requestId": "r-1",
        "entity": "Entity1",
        "metric": "Metric1",
        "tags": {"tag1":["value1"], "tag2":["value2","Value3"]},
        "type": "history",
        "group": {
            "type": "AVG",
            "interpolate": "STEP"
        },
        "rate" : {
              "period": { "count" : 1, "unit": "HOUR" }
         },
         "aggregate": {
              "types": ["AVG", "MAX"],
              "period": { "count" : 1, "unit": "HOUR" },
              "interpolate": "NONE"
         }
       },{
        "startDate": "2015-02-05T09:53:20Z",
        "endDate": "2015-02-05T09:53:29Z",
        "timeFormat": "iso",
        "requestId": "r-2",
        "entity": "Entity2",
        "metric": "Metric2"
        }
      ]
    }
```
