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

### Errors

## Example

### Request

#### URI

#### Payload

```json
[
{"name":"nurswgvml007"},
{"name":"nurswgvml006"}
]
```


#### curl

### Response

## Additional examples




### Method

### Basic Example
> Request



