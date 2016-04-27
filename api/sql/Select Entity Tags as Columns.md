## Select Entity Tags as Columns

> Request

```sql
select entity, entity.tags.os as os, entity.tags.ip as ip from df.disk_used where time between now - 1*minute and now group by entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "label": "entity",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "entity.tags.os",
            "label": "os",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "entity.tags.ip",
            "label": "ip",
            "metric": "df.disk_used",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "nurswgvml003",
            "Linux",
            "10.102.0.2"
        ],
        [
            "nurswgvml006",
            "Linux",
            "10.102.0.5"
        ],
        [
            "nurswgvml007",
            "Linux",
            "10.102.0.6"
        ],
        [
            "nurswgvml010",
            "Linux",
            "10.102.0.9"
        ],
        [
            "nurswgvml011",
            "Linux",
            "10.102.0.10"
        ],
        [
            "nurswgvml102",
            "Linux",
            "10.102.0.1"
        ]
    ]
}
```

**SQL Console Response**

| entity       | os    | ip          | 
|--------------|-------|-------------| 
| nurswgvml003 | Linux | 10.102.0.2  | 
| nurswgvml006 | Linux | 10.102.0.5  | 
| nurswgvml007 | Linux | 10.102.0.6  | 
| nurswgvml010 | Linux | 10.102.0.9  | 
| nurswgvml011 | Linux | 10.102.0.10 | 
| nurswgvml102 | Linux | 10.102.0.1  | 
