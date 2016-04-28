## Counter Aggregator

> Request

```sql
SELECT datetime, count(value), max(value), counter(value) FROM log_event_total_counter 
WHERE entity = 'nurswgvml201' and tags.level = 'ERROR' 
AND datetime >= '2015-09-30T09:00:00Z' and datetime < '2015-09-30T10:00:00Z' 
GROUP BY period(5 minute)
```

```json
{
    "columns": [
        {
            "name": "datetime",
            "label": "datetime",
            "metric": "log_event_total_counter",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "count(value)",
            "label": "count(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "max(value)",
            "label": "max(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "counter(value)",
            "label": "counter(value)",
            "metric": "log_event_total_counter",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "2015-09-30T09:00:00Z",
            5,
            3,
            1
        ],
        [
            "2015-09-30T09:05:00Z",
            4,
            3,
            0
        ],
        [
            "2015-09-30T09:10:00Z",
            4,
            3,
            0
        ]
    ]
}
```

**SQL Console Response**

| datetime             | count(value) | max(value) | counter(value) | 
|----------------------|--------------|------------|----------------| 
| 2015-09-30T09:00:00Z | 5.0          | 3.0        | 1.0            | 
| 2015-09-30T09:05:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:10:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:15:00Z | 6.0          | 5.0        | 5.0            | 
| 2015-09-30T09:20:00Z | 5.0          | 8.0        | 3.0            | 
| 2015-09-30T09:25:00Z | 4.0          | 3.0        | 3.0            | 
