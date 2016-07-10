# Multiple actions

## Descriprion

The PATCH method provides a way to execute multiple membership actions for the same entity group in one request.

Note that the server cannot execute a request containing multiple actions atomically. The server will abort processing on first error, and previously completed actions will not be rolled back.

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
