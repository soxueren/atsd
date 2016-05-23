# Entity: Property Types

## Description

Returns an array of property types for the entity. 

## Request

### Path

```elm
/api/v1/entities/{entity}/property-types
```

### Method

```
GET 
```

### Headers

None.

### Parameters

| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| startTime        | no        | Return only property types that have been collected after the specified time. |

### Fields

None.

## Response

### Fields


| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entities/nurswgvml007/property-types
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities/nurswgvml007/property-types \
  --insecure --verbose --user {username}:{password} \
  --request GET
  ```
### Response

```json
[
   "configuration", 
   "system",
   "process"
]
```

## Additional examples




