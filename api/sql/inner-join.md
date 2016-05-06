## Inner Join

Result table contains rows with table1.entity = table2.entity and table1.time = table2.time and table1.tags = table2.tags

> Query

```sql
SELECT *
FROM cpu_busy
JOIN cpu_idle
WHERE time > now - 1 * hour
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
            "name": "cpu_busy.value",
            "metric": "cpu_busy",
            "label": "cpu_busy.value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "cpu_idle.value",
            "metric": "cpu_idle",
            "label": "cpu_idle.value",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447400404000,
            4.04,
            95.96
        ],
        [
            "awsswgvml001",
            1447400465000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400526000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400587000,
            100,
            0
        ],
        [
            "awsswgvml001",
            1447400648000,
            1,
            99
        ],
        [
            "awsswgvml001",
            1447400709000,
            29.41,
            70.59
        ],
        [
            "awsswgvml001",
            1447400770000,
            0,
            100
        ],
        [
            "awsswgvml001",
            1447400831000,
            1,
            99
        ],
        [
            "awsswgvml001",
            1447400892000,
            1.03,
            98.97
        ],
        [
            "awsswgvml001",
            1447400953000,
            3.03,
            96.97
        ],
        [
            "awsswgvml001",
            1447401014000,
            14.42,
            85.58
        ],
        [
            "awsswgvml001",
            1447401075000,
            9.09,
            90.91
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | cpu_busy.value | 
|--------------|---------------|----------------|
| awsswgvml001 | 1447400465000 | 100.0          | 
| awsswgvml001 | 1447400526000 | 100.0          | 
| awsswgvml001 | 1447400587000 | 100.0          | 
| awsswgvml001 | 1447400648000 | 1.0            | 
| awsswgvml001 | 1447400709000 | 29.41          | 
| awsswgvml001 | 1447400770000 | 0.0            | 
| awsswgvml001 | 1447400831000 | 1.0            | 
| awsswgvml001 | 1447400892000 | 1.03           | 
| awsswgvml001 | 1447400953000 | 3.03           |
| awsswgvml001 | 1447401014000 | 14.42          | 
