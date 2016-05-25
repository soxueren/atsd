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

### Fields

| **Field** | **Description** |
|:---|:---|
| type | Property type name. |

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_server:8443/api/v1/properties/i-943a8878/types
```

#### curl

```elm
curl  https://atsd_server:8443/api/v1/properties/i-943a8878/types \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
[
    "groupset",
    "aws_ec2.instance",
    "tagset",
    "aws_ec2.blockdevicemapping",
    "blockdevicemapping",
    "aws_ec2.tagset",
    "instance",
    "aws_ec2.groupset"
]
```

