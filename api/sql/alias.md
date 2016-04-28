## Alias

An `alias` can be set in single (`'alias'`) or double qoatations (`"alias"`).

Unquoted `alias` must begin with a letter followed by letters, underscores, digits (0-9).

> Request

```sql
SELECT time, value, entity, metric AS "measurement" FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml006' AND time BETWEEN now - 5 * minute AND now
```

> OR

```sql
SELECT time, value, entity, metric AS 'measurement' FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml006' AND time BETWEEN now - 5 * minute AND now
```

> Response

```json
{
    "columns": [
        {
            "name": "time",
            "metric": "mpstat.cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "metric": "mpstat.cpu_busy",
            "label": "value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "metric",
            "metric": "mpstat.cpu_busy",
            "label": "measurement",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            1446038385000,
            9.09,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ],
        [
            1446038401000,
            8,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ],
        [
            1446038417000,
            8,
            "nurswgvml006",
            "mpstat.cpu_busy"
        ]
    ]
}
```

**SQL Console Response**

| time          | value | entity       | measurement | 
|---------------|-------|--------------|-------------| 
| 1446038417000 | 8.0   | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038433000 | 100.0 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038449000 | 30.3  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038465000 | 17.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038481000 | 11.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038497000 | 90.82 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038513000 | 19.0  | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038529000 | 27.27 | nurswgvml006 | mpstat.cpu_busy    | 
| 1446038545000 | 12.12 | nurswgvml006 | mpstat.cpu_busy    | 
