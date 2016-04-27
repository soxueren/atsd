## Basic Query

Query for one metric, one entity and detailed values for time range

> Request

```sql
SELECT time, value, entity FROM mpstat.cpu_busy WHERE entity = 'nurswgvml007' AND time >= previous_day AND time < now
```

> Response

```json
{
    "columns": [
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
            1438128005000,
            35.96,
            "nurswgvml007"
        ],
        [
            1438128021000,
            35.96,
            "nurswgvml007"
        ],
        [
            1438128037000,
            32.26,
            "nurswgvml007"
        ],
        [
            1438128053000,
            4,
            "nurswgvml007"
        ]
    ]
}
```

**SQL Console Response**

| time          | value | entity       | 
|---------------|-------|--------------| 
| 1445904004000 | 100.0 | nurswgvml007 | 
| 1445904020000 | 100.0 | nurswgvml007 | 
| 1445904036000 | 100.0 | nurswgvml007 | 
| 1445904052000 | 60.0  | nurswgvml007 | 
| 1445904068000 | 100.0 | nurswgvml007 | 
| 1445904084000 | 16.83 | nurswgvml007 | 
| 1445904101000 | 100.0 | nurswgvml007 | 
| 1445904117000 | 24.24 | nurswgvml007 | 
| 1445904133000 | 100.0 | nurswgvml007 | 
