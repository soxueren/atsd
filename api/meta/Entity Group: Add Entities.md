## Entity Group: Add Entities

Add specified entities to entity group.

### `add` action parameters

```
PATCH /api/v1/entity-groups/{group}/entities
```

> Request

```json
[
  {
    "action" : "add",
    "createEntities": true,
    "entities" : 
        [
            {"name":"nurswgvml010"},
            {"name":"nurswgvml011"}
        ]
  }
]
```

| **Parameter**  | **Required** | **Description**                                                                                |
|----------------|--------------|-------------------|------------------------------------------------------------------------------------------------|
| createEntities | no       | Automatically create new entities from the submitted list if such entities don't already exist. Default value: true |

### Multiple actions

> Request

```json
[
  {
    "action" : "add",
    "createEntities": true,
    "entities" : 
        [
            {"name":"nurswgvml010"},
            {"name":"nurswgvml011"}
        ]
  },
  {
    "action" : "delete",
    "deleteAll": false,
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  }
]
```

This method supports multiple actions for the same entity group. It can be used to delete and add entities within one request.

<aside class="notice">
The server cannot execute multiple actions atomically. The server will abort processing on first error, previously executed actions will not be rolled back.
</aside>
