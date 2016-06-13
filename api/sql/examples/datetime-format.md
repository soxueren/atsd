## Datetime Format

`datetime` is used to return time in ISO format without offset: `2015-04-07T08:14:28.231Z`

> Query

```sql
SELECT datetime, time, value, entity FROM mpstat.cpu_busy 
 WHERE entity LIKE '%00%' AND datetime BETWEEN '2015-04-09T14:00:00Z' AND '2015-04-09T14:05:00Z'
```

> Response

```json
{
    "columns": [
        {
            "name": "datetime",
            "label": "datetime",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "label": "time",
            "metric": "mpstat.cpu_busy",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "value",
            "label": "value",
            "metric": "mpstat.cpu_busy",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "entity",
            "label": "entity",
            "metric": "mpstat.cpu_busy",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "2015-04-09T14:00:01Z",
            1428588001000,
            3.8,
            "nurswgvml007"
        ],
        [
            "2015-04-09T14:00:18Z",
            1428588018000,
            14,
            "nurswgvml007"
        ],
        [
            "2015-04-09T14:00:34Z",
            1428588034000,
            16.83,
            "nurswgvml006"
        ]
    ]
}
```

**SQL Console Response**

| datetime             | time          | value |
|----------------------|---------------|-------|
| 2015-04-09T14:00:01Z | 1428588001000 | 3.8   |
| 2015-04-09T14:00:18Z | 1428588018000 | 14.0  |
| 2015-04-09T14:00:34Z | 1428588034000 | 16.83 |
| 2015-04-09T14:00:50Z | 1428588050000 | 10.2  |
| 2015-04-09T14:01:06Z | 1428588066000 | 4.04  |
| 2015-04-09T14:01:22Z | 1428588082000 | 9.0   |
| 2015-04-09T14:01:38Z | 1428588098000 | 2.0   |
| 2015-04-09T14:01:54Z | 1428588114000 | 8.0   |
| 2015-04-09T14:02:10Z | 1428588130000 | 10.23 |
| 2015-04-09T14:02:26Z | 1428588146000 | 14.0  |
| 2015-04-09T14:02:42Z | 1428588162000 | 20.2  |

