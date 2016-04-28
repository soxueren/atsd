## Row Number Function

This function numbers rows according to grouping columns (example: `entity, tags`), orders rows (example `time DESC`), and then filters each row with predicate based on `row_number` function value (`WITH row_number(entity, tags ORDER BY time DESC) <= 3`).

> Request

```sql
SELECT entity, time, AVG(cpu_busy.value)
 FROM cpu_busy
 WHERE time > now - 1 * hour
 GROUP BY entity, period(15 minute)
 WITH row_number(entity, tags ORDER BY time DESC) <= 3
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447407000000,
            5.095000000000001
        ],
        [
            "awsswgvml001",
            1447406100000,
            2.1333333333333337
        ],
        [
            "awsswgvml001",
            1447405200000,
            28.466666666666665
        ],
        [
            "nurswgvml003",
            1447407000000,
            6.43608695652174
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | AVG(cpu_busy.value) | 
|--------------|---------------|---------------------| 
| awsswgvml001 | 1447407000000 | 5.095000000000001   | 
| awsswgvml001 | 1447406100000 | 2.1333333333333337  | 
| awsswgvml001 | 1447405200000 | 28.466666666666665  | 
| nurswgvml003 | 1447407000000 | 5.808846153846154   | 
| nurswgvml003 | 1447406100000 | 6.670714285714286   | 
| nurswgvml003 | 1447405200000 | 8.422105263157892   | 
| nurswgvml006 | 1447407000000 | 8.224230769230768   | 
| nurswgvml006 | 1447406100000 | 9.618571428571428   | 
| nurswgvml006 | 1447405200000 | 25.231785714285714  | 
| nurswgvml007 | 1447407000000 | 18.276296296296298  | 
| nurswgvml007 | 1447406100000 | 19.083214285714284  | 
| nurswgvml007 | 1447405200000 | 40.00928571428571   | 
| nurswgvml010 | 1447407000000 | 2.4067307692307693  | 
| nurswgvml010 | 1447406100000 | 3.472410714285714   | 
| nurswgvml010 | 1447405200000 | 12.874743589743579  | 
| nurswgvml011 | 1447407000000 | 4.12                | 
| nurswgvml011 | 1447406100000 | 1.98                | 
| nurswgvml011 | 1447405200000 | 9.215357142857142   | 
| nurswgvml102 | 1447407000000 | 1.0792307692307692  | 
| nurswgvml102 | 1447406100000 | 0.9268421052631579  | 
| nurswgvml102 | 1447405200000 | 1.0666071428571429  | 
