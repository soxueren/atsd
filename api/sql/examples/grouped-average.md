## Grouped Average

5 minute average value for one metric, two entities, sorted by entity name

> Query

```sql
SELECT entity, period(5 MINUTE) AS "period", avg(value) AS CPU_Avg FROM mpstat.cpu_busy 
  WHERE entity IN ('nurswgvml007', 'nurswgvml011') AND time between now - 1 * hour AND now 
  GROUP BY entity, period(5 MINUTE)
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
            "name": "period(5 MINUTE)",
            "metric": "mpstat.cpu_busy",
            "label": "period",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "avg(value)",
            "metric": "mpstat.cpu_busy",
            "label": "CPU_Avg",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "nurswgvml007",
            1446037200000,
            75.47466666666666
        ],
        [
            "nurswgvml007",
            1446037500000,
            24.44388888888889
        ],
        [
            "nurswgvml007",
            1446037800000,
            19.04421052631579
        ]
    ]
}
```

**SQL Console Response**

| entity       | period        | CPU_Avg            | 
|--------------|---------------|--------------------| 
| nurswgvml007 | 1446037200000 | 76.94454545454546  | 
| nurswgvml007 | 1446037500000 | 24.44388888888889  | 
| nurswgvml007 | 1446037800000 | 19.04421052631579  | 
| nurswgvml007 | 1446038100000 | 17.453157894736844 | 
| nurswgvml007 | 1446038400000 | 17.373157894736842 | 
| nurswgvml007 | 1446038700000 | 19.232222222222223 | 
| nurswgvml007 | 1446039000000 | 35.061052631578946 | 
| nurswgvml007 | 1446039300000 | 28.737894736842104 | 
| nurswgvml007 | 1446039600000 | 24.24578947368421  | 
| nurswgvml007 | 1446039900000 | 15.909444444444444 | 
| nurswgvml007 | 1446040200000 | 16.4               | 
 
