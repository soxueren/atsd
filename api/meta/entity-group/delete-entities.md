# Entity Group: Delete Entities

## Description

Remove specified entities from members of the specified entity group.

To delete all entities, submit an empty array `[]` with [replace entities](replace-entities.md) method.

> Entity group must have an empty expression in order to be manageable with this method.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| POST | `/api/v1/entity-groups/{group}/entities/delete` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| group |string|Entity group name.|

### Payload

An array of entity names to be removed as members.

```json
[
  "entity-1",
  "entity-2"
]
```

## Response

### Fields

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/entity-groups/nmon-aix/entities/delete
```

#### Payload

```json
[
  "nurswgvml010", 
  "nurswgvml011"
]
```

#### curl

```
curl https://atsd_host:8443/api/v1/entity-groups/nmon-aix/entities/delete \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '["nurswgvml010", "nurswgvml011"]'
```

### Response

None.

## Additional examples
