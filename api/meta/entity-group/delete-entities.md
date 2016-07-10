# Entity Group: Delete Entities

## Description

Remove specified entities from members of the specified entity group.

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
| action | string | **[Required]** Must be set to `delete` for this action. |
| entities | array | An array of entity names to be removed as members. |

## Response

### Fields

None.

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

None.

## Additional examples

* [Delete all entities](examples/delete-all-entities.md)
* [Multiple Actions](examples/multiple-actions.md)
