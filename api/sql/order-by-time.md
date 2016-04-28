## ORDER BY Time

> Request

```sql
SELECT time, value FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' 
 AND time BETWEEN now - 1 * hour AND now 
 ORDER BY time
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
        }
    ],
    "rows": [
        [
            1446033764000,
            48.96
        ],
        [
            1446033780000,
            42.42
        ],
        [
            1446033796000,
            12.24
        ]
    ]
}
```

**SQL Console Response**

| time          | value | 
|---------------|-------| 
| 1446033812000 | 22.45 | 
| 1446033828000 | 12.33 | 
| 1446033844000 | 19.0  | 
| 1446033860000 | 7.07  | 
| 1446033876000 | 13.13 | 
| 1446033892000 | 24.24 | 
| 1446033908000 | 27.27 | 
| 1446033924000 | 34.0  | 
| 1446033940000 | 9.0   | 
| 1446033956000 | 11.11 | 
| 1446033972000 | 40.4  | 
