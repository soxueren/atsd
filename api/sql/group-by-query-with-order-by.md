## GROUP BY Query with ORDER BY

> Query

```sql
SELECT entity, avg(value) FROM mpstat.cpu_busy 
 WHERE time > now - 1*hour 
 GROUP BY entity 
 ORDER BY avg(value) 
 DESC limit 5
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "avg(value)",
            "label": "avg(value)",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml007",
            18.775111111111112
        ],
        [
            "nurswgvml006",
            15.119598214285714
        ],
        [
            "awsswgvml001",
            11.185288888888888
        ],
        [
            "nurswgvml010",
            9.196577777777778
        ],
        [
            "nurswgvml011",
            3.2876233183856503
        ]
    ]
}
```

**SQL Console Response**

| entity       | avg(value)         | 
|--------------|--------------------| 
| nurswgvml007 | 46.547288888888865 | 
| nurswgvml006 | 21.85551111111111  | 
| awsswgvml001 | 13.554488888888887 | 
| nurswgvml010 | 7.88160714285714   | 
| nurswgvml011 | 2.8021973094170405 | 
