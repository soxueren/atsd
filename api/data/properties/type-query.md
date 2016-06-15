# Properties: Type Query

## Description

Returns an array of property types for the entity. 

## Request 

### Path

```elm
/api/v1/properties/{entity}/types
```

### Method 

```
GET
```

### Headers

None.

### Parameters

None.

### Fields

None.

## Response

An array of property type names.

### Fields

| **Field** | **Description** |
|:---|:---|
| type | Property type name. |

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_server:8443/api/v1/properties/nurswgvml007/types
```

#### curl

```elm
curl  https://atsd_server:8443/api/v1/properties/nurswgvml007/types \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
[
    "disk",
    "system",
    "nmon.process"
]
```

