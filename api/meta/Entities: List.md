## Entities: List

### Request Parameters

```
GET /api/v1/entities
```

> Request

```
http://atsd_server.com:8088/api/v1/entities?limit=2&expression=name%20like%20%27nur*%27
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|active|no|Filter entities by last insert time. If `active = true`, only entities with positive `last_insert_time` are included in the response|
|expression|no|Use `name` variable for entity name. Use `*` placeholder in `like` expresions|
|tags|no|Specify entity tags to be included in the response|
|limit|no|Limit response to first N entities, ordered by name.|

### Response Fields

> Response

```json
[{
    "name":"atsd_server.com","enabled":true,"lastInsertTime":1423063786839
},
{
    "name":"nurswgvml003","enabled":true,"lastInsertTime":1423063794000
}]
```

| **Name**                            | **Description**                                                                             |
|---|---|
| name                                | Entity name (unique)                                                                        |
| enabled                             | Enabled status. Incoming data is discarded for disabled entities                            |
| lastInsertTime                      | Last time value was received by ATSD for this entity. Time specified in epoch milliseconds. |
| tags as requested by tags parameter | User-defined tags                                                                           |

### Sample Request 

Fetch entities starting with `nur` and with tag `app` containing `hbase` (case insensitive)

```
http://atsd_server.com:8088/api/v1/entities?limit=2&tags=app&expression=name%20like%20%27nur%27%20and%20lower%28tags.app%29%20like%20%27hbase%27
```

> Expression

```
name like 'nur*' and `lower(tags.app)` like '*hbase*'
```

> Response

```json
[{
    "name": "nurswgvml006",
    "enabled": true,
    "lastInsertTime": 1423144296000,
    "tags": {
        "app": "Hadoop/HBASE"
    }
},
{
    "name": "nurswgvml203",
    "enabled": true,
    "tags": {
        "app": "Hadoop/Hbase master node"
    }
}]
```
<aside class="success">
Note: 'lower(text)' is a utility function. Alternatively, any Java string functions can be used to modify values, for example: 'tags.app.toLowerCase()'
</aside>
