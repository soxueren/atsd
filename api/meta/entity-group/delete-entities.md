## Entity Group: Delete Entities

Delete entities from entity group.

### `delete` 

```
PATCH /api/v1/entity-groups/{group}/entities
```

> Request

```json
[
  {
    "action" : "delete",
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  }
]
```

`delete` action removes specified entities from the entity group:

### `delete-all`

`delete-all` action removes all entities from the entity group:

> Request

```json
[
  {
    "action" : "delete-all"
  }
]
```

### Multiple actions

> Request

```json
[
  {
    "action" : "delete",
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  },
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

This method supports multiple actions for the same entity group. It can be used to delete and add entities within one request.

<aside class="notice">
The server cannot execute a request containing multiple actions atomically. The server will abort processing on first error, previously executed actions will not be rolled back.
</aside>
