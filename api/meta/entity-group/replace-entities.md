# Entity Group: Set (Replace) Entities

## Description

Replace entities in the entity group with the specified collection.

## Request

### Path

```elm
/api/v1/entity-groups/{group}/entities
```

### Method

```
PUT
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

None.

### Fields

| **Field**  | **Required** | **Description**                                                                                |
|----------------|--------------|------------------------------------------------------------------------------------------------|
| createEntities | no       | Automatically create new entities from the submitted list if such entities don't already exist. Default value: true|

<aside class="notice">
All existing entities that are not included in the collection will be removed.
If the specified collection is empty, all entities are removed from the group (replace with empty collection).
</aside>

## Response

### Fields

None.

### Errors

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


