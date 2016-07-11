# Entity Group: Add Entities

## Description

Add entities as members to the specified entity group.

Entity group must have an empty expression in order to be manageable with this method.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| PATCH | `/api/v1/entity-groups/{group}/entities` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| group |string|Entity group name.|

### Fields

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| action | string | **[Required]** Must be set to `add` for this action. |
| createEntities | boolean | Automatically create new entities from the submitted list if such entities don't exist. Default: true. |
| entities | array | An array of entity names to be added as members. |

## Response

### Fields

None.

### Response

If createEntities is false, and the request contains a non-existing entity, the following error will be raised:

```json
{"error":"com.axibase.tsd.service.DictionaryNotFoundException: ENTITY not found for name: 'e-111'"}
```

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
  --data '[{"action" : "add","entities" : [{"name":"nurswgvml010"},{"name":"nurswgvml011"}]}]'
  ```
  
### Response

None.

## Additional examples

* [Multiple Actions](examples/multiple-actions.md)
