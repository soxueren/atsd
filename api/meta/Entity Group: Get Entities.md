## Entity Group: Get Entities

### Request Parameters

```
GET /api/v1/entity-groups/{group}/entities
```

> Request

```
http://atsd_server.com:8088/api/v1/entity-groups/nur-entities-name/entities?tags=*&limit=3
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|active|no| Filter entities by `last_insert_time`. If `active = true`, only entities with positive `last_insert_time` are included in the response|
|expression|no|Use `name` variable for entity name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity tags to be included in the response|
|limit|no|Limit response to first N entities, ordered by name.|

### Response Parameters

> Response

```json
[
    {
        "name": "atsd_server.com",
        "enabled": true,
        "tags": {}
    },
    {
        "name": "nurswgvml003",
        "enabled": true,
        "lastInsertTime": 1426066431000,
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "ip": "10.102.0.2",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml004",
        "enabled": true,
        "tags": {}
    }
]
```

| **Name**                                 | **Description**                                                                             |
|------------------------------------------|---------------------------------------------------------------------------------------------|
| name                                     | Entity name (unique)                                                                        |
| enabled                                  | Enabled status. Incoming data is discarded for disabled entities                            |
| lastInsertTime                           | Last time value was received by ATSD for this entity. Time specified in epoch milliseconds. |
| tags  | User-defined tags as requested by `add_tags` parameter                                                                          |
