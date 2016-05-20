# Properties: Query for Entity and Type
## Description
Returns properties for entity and type. 
### Path 
```
/api/v1/properties/{entity}/types/{type}
```

```
/api/v1/entities/{entity}/property-types/{property-type}
```

### Methods

```
GET 
```

### Basic Examples
> Request

```
http://atsd_server:8088/api/v1/properties/nurswgvml007/types/system?timeFormat=iso
```

> Response

```json
[
   {
       "type": "system",
       "entity": "nurswgvml007",
       "key": {},
       "tags": {
           "cpu_total.busy": "1",
           "cpu_total.idle%": "93.6",
           "cpu_total.sys%": "1.1",
           "cpu_total.user%": "4.7",
           "cpu_total.wait%": "0.6",
           "memory_mb.active": "946.2",
           "memory_mb.bigfree": "-1.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

> Request

```
http://atsd_server:8088/api/v1/entities/i-943a8878/property-types/aws_ec2.instance?timeFormat=iso
```

> Response

```json
[
    {
        "type": "aws_ec2.instance",
        "entity": "i-943a8878",
        "key": {},
        "tags": {
            "amilaunchindex": "0",
            "architecture": "x86_64",
            "clienttoken": "TqxBb1417594114891",
            "dnsname": "ec2-75-101-140-203.compute-1.amazonaws.com",
            "ebsoptimized": "false",
            "hypervisor": "xen",
            "imageid": "ami-08389d60",
            "instancestate.code": "16",
            "instancestate.name": "running",
            "instancetype": "m1.small",
            "ipaddress": "75.101.140.203",
            "keyname": "basepair",
            "launchtime": "2014-12-03T08:08:35.000Z",
            "monitoring.state": "disabled",
            "placement.availabilityzone": "us-east-1d",
            "placement.tenancy": "default",
            "privatednsname": "ip-10-111-164-53.ec2.internal",
            "privateipaddress": "10.111.164.53",
            "rootdevicename": "/dev/sda1",
            "rootdevicetype": "ebs",
            "virtualizationtype": "paravirtual"
        },
        "date": "2015-09-04T14:30:30Z"
    }
]
```
### Request Fields
| **Parameter**  | **Required** | **Description**  |
|---|---|---|---|---|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
| entity | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
| key | JSON object containing `name=values` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |
| date | date and time in ISO format |
