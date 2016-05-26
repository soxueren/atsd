# Delete All

## Description

This action removes all entities from the entity group.

## Request

### URI

```elm
PATCH https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities
```

### Payload

```json
[
  {
    "action" : "delete-all"
  }
]
```
