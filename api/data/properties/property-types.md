## Properties: Property Types

Returns an array of properties for entity and type. 

```
GET /api/v1/entities/{entity}/property-types
```

```
GET /api/v1/properties/{entity}/types
```

> Request

```
http://atsd_server:8088/api/v1/entities/i-943a8878/property-types
```

> Response

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

**RESPONSE FIELDS:**

| **Name**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
