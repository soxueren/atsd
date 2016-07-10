# Entity Group: Set (Replace) Entities

## Description

Replace entities in the entity group with the specified entity list.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| PUT | `/api/v1/entity-groups/{group}/entities` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| group |string|Entity group name.|

### Fields

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| createEntities | boolean | Automatically create new entities from the submitted list if such entities don't exist. Default: true. |
| entities | array | An array of entity names to be added as members. |

* All existing members that are not included in the request will be removed from members.
* If the entity list in the request is empty, all entities are removed from the group and are replaced with an empty list.

## Response

### Fields

None.

## Example

### Request

#### URI

```elm
PUT https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities
```

#### Payload

```json
[
{"name":"nurswgvml007"},
{"name":"nurswgvml006"}
]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PUT \
  --data '[{"name":"nurswgvml007"},{"name":"nurswgvml006"}]'
  ```
### Response

None.

## Additional examples


