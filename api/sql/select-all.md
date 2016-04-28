## SELECT All

> Request

```sql
SELECT * FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' AND time between now - 1 * hour AND now
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "mpstat.cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
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
            "nurswgvml007",
            1446034164000,
            10
        ],
        [
            "nurswgvml007",
            1446034180000,
            8.08
        ],
        [
            "nurswgvml007",
            1446034196000,
            17
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | value | 
|--------------|---------------|-------| 
| nurswgvml007 | 1446034244000 | 35.71 | 
| nurswgvml007 | 1446034260000 | 39.78 | 
| nurswgvml007 | 1446034276000 | 16.0  | 
| nurswgvml007 | 1446034292000 | 10.1  | 
| nurswgvml007 | 1446034308000 | 9.0   | 
| nurswgvml007 | 1446034324000 | 12.12 | 
| nurswgvml007 | 1446034340000 | 10.31 | 
| nurswgvml007 | 1446034356000 | 8.08  | 
| nurswgvml007 | 1446034372000 | 10.1  | 
| nurswgvml007 | 1446034388000 | 12.87 | 
| nurswgvml007 | 1446034404000 | 9.09  | 
| nurswgvml007 | 1446034420000 | 8.25  | 
