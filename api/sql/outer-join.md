## Outer Join

Inner join + tables rows that is not belonging to inner join table with null value columns.

> Query

```sql
SELECT *
 FROM cpu_busy
 OUTER JOIN disk_used
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
            "name": "disk_used.value",
            "metric": "disk_used",
            "label": "disk_used.value",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "tags.mount_point",
            "metric": "cpu_busy",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "cpu_busy",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            1447401314000,
            null,
            2317456,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401374000,
            null,
            2317460,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401435000,
            null,
            2317460,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401495000,
            null,
            2317464,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401555000,
            null,
            2317464,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401615000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401675000,
            null,
            2317472,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401735000,
            null,
            2317472,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401795000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ],
        [
            "awsswgvml001",
            1447401855000,
            null,
            2317476,
            "/",
            "/dev/xvda1"
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | cpu_busy.value | disk_used.value | tags.mount_point | tags.file_system | 
|--------------|---------------|----------------|-----------------|------------------|------------------| 
| awsswgvml001 | 1447401374000 |                | 2317460.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401435000 |                | 2317460.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401495000 |                | 2317464.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401555000 |                | 2317464.0       | /                | /dev/xvda1       | 
| awsswgvml001 | 1447401374000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401435000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401495000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401555000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401615000 |                | 6.8282976E7     | /backup          | /dev/xvdf        | 
| awsswgvml001 | 1447401563000 | 56.6           |                 |                  |                  | 
| awsswgvml001 | 1447401624000 | 2.0            |                 |                  |                  | 
| awsswgvml001 | 1447401685000 | 4.0            |                 |                  |                  | 
| awsswgvml001 | 1447401746000 | 11.0           |                 |                  |                  | 
| awsswgvml001 | 1447401807000 | 10.78          |                 |                  |                  | 

