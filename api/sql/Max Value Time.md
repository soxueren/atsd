## Max Value Time

Time when the metric value reached its maximum over the last hour

> Request

```sql
SELECT entity, MAX(value),
date_format(MAX_VALUE_TIME(value), 'yyyy-MM-dd HH:mm:ss') AS "Max Time"
FROM mpstat.cpu_busy WHERE time > current_hour GROUP BY entity
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
            "name": "MAX(value)",
            "metric": "mpstat.cpu_busy",
            "label": "MAX(value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "date_format(MAX_VALUE_TIME(value),'yyyy-MM-dd HH:mm:ss')",
            "metric": "mpstat.cpu_busy",
            "label": "Max Time",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            100,
            "2015-10-28 14:03:09"
        ],
        [
            "nurswgvml003",
            15.84,
            "2015-10-28 14:01:45"
        ],
        [
            "nurswgvml006",
            100,
            "2015-10-28 14:00:51"
        ],
        [
            "nurswgvml007",
            100,
            "2015-10-28 14:03:06"
        ],
        [
            "nurswgvml010",
            88.49,
            "2015-10-28 14:10:00"
        ],
        [
            "nurswgvml011",
            11.11,
            "2015-10-28 14:05:01"
        ],
        [
            "nurswgvml102",
            41,
            "2015-10-28 14:04:53"
        ]
    ]
}
```

**SQL Console Response**

| entity       | MAX(value) | Max Time            | 
|--------------|------------|---------------------| 
| awsswgvml001 | 100.0      | 2015-10-28 14:03:09 | 
| nurswgvml003 | 15.84      | 2015-10-28 14:01:45 | 
| nurswgvml006 | 100.0      | 2015-10-28 14:00:51 | 
| nurswgvml007 | 100.0      | 2015-10-28 14:03:06 | 
| nurswgvml010 | 88.49      | 2015-10-28 14:10:00 | 
| nurswgvml011 | 11.11      | 2015-10-28 14:05:01 | 
| nurswgvml102 | 41.0       | 2015-10-28 14:04:53 | 
