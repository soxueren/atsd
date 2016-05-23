# Properties: Property Types

## Description

Returns an array of properties for entity and type. 

## Path

```elm
/api/v1/entities/{entity}/property-types
```

```elm
/api/v1/properties/{entity}/types
```

## Method

```
GET 
```

## Response

### Fields

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |

## Example

### Request

#### URI

```eml
GET https://atsd_server:8443/api/v1/entities/i-943a8878/property-types
```

#### curl

```elm
curl  https://atsd_server:8443/api/v1/entities/i-943a8878/property-types \
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

> Request

```
http://atsd_server:8088/api/v1/properties/nurswgvml007/types
```

> Response

```json
[
    "tcollector.proc.net.tcp",
    "disk",
    "cpu",
    "sw.vmw.vm",
    "network",
    "process",
    "jfs",
    "system",
    "network_status",
    "vmware.vm",
    "ws-test-1",
    "ping_status",
    "tcollector",
    "configuration"
]
```

