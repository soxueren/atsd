## Average Value Query

Average value for one metric, one entity

> Request

```sql
SELECT avg(value) AS CPU_Avg FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time >= previous_day AND time < now
```

> Response

```json
{
    "columns": [
        {
            "name": "avg(value)",
            "label": "CPU_Avg",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            31.101191881047953
        ]
    ]
}
```
