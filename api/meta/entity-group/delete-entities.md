# Entity Group: Delete Entities

## Description

Delete entities from entity group.

## Request

### Path

```elm
 /api/v1/entity-groups/{group}/entities
```

### Method

```
PATCH
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

### Fields

## Response

### Fields

None.

### Errors

## Example

### Request

#### URI

```elm
PATCH https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities
```

#### Payload

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

#### curl

```
curl https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PATCH \
  --data '[{"action" : "delete","entities" : [{"name":"nurswgvml007"},{"name":"nurswgvml006"}] }]'
```

### Response

## Additional examples

* [Delete all entities](./examples/delete-all-entities.md)
* [Multiple Actions](/examples/multiple-actions.md)
