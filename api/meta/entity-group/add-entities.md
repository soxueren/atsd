# Entity Group: Add Entities

## Description

Add specified entities to entity group.

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

### Parameters

### Fields

| **Field**  | **Required** | **Description**                                                                                |
|----------------|--------------|-------------------|------------------------------------------------------------------------------------------------|
| createEntities | no       | Automatically create new entities from the submitted list if such entities don't already exist. Default value: true |

## Response

### Fields

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

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PATCH \
  --data '[{"action" : "add","createEntities": true,"entities" : [{"name":"nurswgvml010"},{"name":"nurswgvml011"}]}]'
  ```
  
### Response

None.

## Additional examples
* [Add multiple entities](./examples/add-multiple-entities.md)
