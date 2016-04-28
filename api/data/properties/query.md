## Properties: Query

### Method

```
POST /api/v1/properties
```
### Basic Request Example
> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "system",
      "entity": "nurswgvml007",
      "key": {}
     }
   ]
}
```
### Request Fields
| **Field**  | **Required** | **Description**  |
|---|---|---|---|---|
| entity    | no*          | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards            |
| entities | no* | an array of entities |
| entityGroup | no* | If `entityGroup` field is specified in the query, properties of the specified type for entities in this group are returned. `entityGroup` is used only if entity field is missing or if entity field is an empty string. `entityGroup` is supported both for regular types and reserved `$entity_tags` type. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| startTime | no           |   start of the selection interval. Default value: `endTime - 1 hour`                                                                                                                        |
| endTime   | no           | end of the selection interval. Default value: `current server time`                                                                                                                             | 
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
| limit     | no           | maximum number of data samples returned. Default value: 0                                                                                                                 | 
| type      | yes          | type of data properties. Supports reserved `$entity_tags` type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.                                                                                                                              |
| key      | no           | JSON object containing `name=values` that uniquely identify the property record                                                                                   |
| keyExpression | no | expression for matching properties with specified keys |

<aside class="notice">
'$entity_tags' is a reserved property type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.
</aside>

<aside class="notice">
* Mutually exclusive fields. Entities or an Entity should be specified in the request using ONE of the following fields: entity, entities, entityGroup.
</aside>

### Basic Response Example

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
### Response Fields
| **Field**  | **Description**  |
|---|---|
| type | property type name |
| entity | entity name |
| key | JSON object containing `name=value` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |
| date | date and time in ISO format |

### Examples

### Retreive Entity Tags

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "$entity_tags",
      "entity": "nurswgvml007"
     }
   ]
}
```

>Response

```json


    [
        {
            "type": "$entity_tags",
            "entity": "nurswgvml007",
            "key":
            {
            },
            "tags":
            {
                "alias": "007",
                "app": "ATSD",
                "ip": "10.102.0.6",
                "loc_area": "dc2",
                "loc_code": "nur,nur",
                "os": "Linux"
            },
            "date": "2015-09-08T09:06:32Z"
        }
    ]
```

### Entity Tags for entityGroup

> Request

```json
{
    "queries": [
        {
            "entityGroup": "nur-entities-name",
            "type": "$entity_tags",
            "timeFormat": "iso"
        }
    ]
}
```

> Response

```json
[
    {
        "type": "$entity_tags",
        "entity": "nurswgvml003",
        "key": {},
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "app-test": "1",
            "ip": "10.102.0.2",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    },
    {
        "type": "$entity_tags",
        "entity": "nurswgvml006",
        "key": {},
        "tags": {
            "app": "Hadoop/HBASE",
            "ip": "10.102.0.5",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    },
    {
        "type": "$entity_tags",
        "entity": "nurswgvml007",
        "key": {},
        "tags": {
            "alias": "007",
            "app": "ATSD",
            "ip": "10.102.0.6",
            "loc_area": "dc2",
            "loc_code": "nur,nur",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    }
]
```

### Properties for type using expression Example

> Request

```json
{
    "queries": [
        {
            "type": "manager2",
            "entity": "host2",
            "keyExpression": "key3 like 'nur*'"
        }
    ]
}
```

### Properties for type 'disk'

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "disk",
      "entity": "nurswgvml007"
     }
   ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-0"
       },
       "tags": {
           "disk_%busy": "1.3",
           "disk_block_size": "4.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "59.1",
           "disk_write_kb/s": "236.3"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-1"
       },
       "tags": {
           "disk_%busy": "0.0",
           "disk_block_size": "0.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "0.0",
           "disk_write_kb/s": "0.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Properties for type 'disk' with key

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "disk",
      "entity": "nurswgvml007",
      "key": {"id": "dm-0"}
     }
   ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-0"
       },
       "tags": {
           "disk_%busy": "1.6",
           "disk_block_size": "4.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "59.5",
           "disk_write_kb/s": "238.2"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Properties for type 'process' with multiple keys

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "process",
      "entity": "nurswgvml007",
      "key": {"command": "java", "pid": "27297"} 
     }
   ]
}
```

> Response

```json
[
   {
       "type": "process",
       "entity": "nurswgvml007",
       "key": {
               "command": "java",
               "fullcommand": "java -server -xmx512m -xloggc:/home/axibase/atsd/logs/gc.log -verbose:gc -xx:+printgcdetails -xx:+printgcdatestamps -xx:+printgctimestamps -xx:+printgc -xx:+heapdumponoutofmemoryerror -xx:heapdumppath=/home/axibase/atsd/logs -classpath /home/axibase/atsd/conf:/home/axibase/atsd/bin/atsd-executable.jar com.axibase.tsd.server",
               "pid": "27297"
       },
       "tags": {
           "%cpu": "5.88",
           "%sys": "0.62",
           "%usr": "5.27",
           "majorfault": "0",
           "minorfault": "0",
           "resdata": "1575544",
           "resset": "456888",
           "restext": "36",
           "shdlib": "13964",
           "size": "1733508"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Key Expression

**key expression: Filter out all disks except those starting with `sd*`. Disks dm1, dm2 are excluded**

> Request

```json
{
    "queries": [
        {
            "timeFormat": "iso",
            "type": "disk",
            "entity": "nurswgvml007" ,
            "keyExpression": "id like 'sd*'"
        }
    ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda"
       },
       "tags": {
           "disk_%busy": "1.9",
           "disk_block_size": "6.1",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "43.1",
           "disk_write_kb/s": "262.8"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda1"
       },
       "tags": {
           "disk_%busy": "0.0",
           "disk_block_size": "0.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "0.0",
           "disk_write_kb/s": "0.0"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda2"
       },
       "tags": {
           "disk_%busy": "1.9",
           "disk_block_size": "6.1",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "43.1",
           "disk_write_kb/s": "262.8"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```
