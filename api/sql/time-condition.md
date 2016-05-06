## Time Condition

> Query

```sql
SELECT time, value FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' 
 AND time > 1428352721000 
 AND time < 1428352721000
```

> Response - returns an array with 1 record:

```json
{
    "columns": [
        {
            "name": "time",
            "label": "time",
            "metric": "mpstat.cpu_busy",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "label": "value",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            1438263969000,
            43.96
        ]
    ]
}
```
