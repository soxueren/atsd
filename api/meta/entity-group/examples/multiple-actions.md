# Multiple actions

## Descriprion

This method supports multiple actions for the same entity group. It can be used to delete and add entities within one request.

<aside class="notice">
The server cannot execute a request containing multiple actions atomically. The server will abort processing on first error, previously executed actions will not be rolled back.
</aside>

## Request

### URI

```elm
PATCH https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities
```

### Payload

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

## Response

None.
