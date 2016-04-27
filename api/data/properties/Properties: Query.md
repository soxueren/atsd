## Properties: Query

### Request Parameters

```
POST /api/v1/properties
```

> Request

```json
{
  "queries": [
    {
      "type": "system",
      "entity": "nurswgvml007",
      "key": {}
     }
   ]
}
```

| **Name**  | **Required** | **Description**  |
|---|---|---|---|---|
| entity    | yes          | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards            |
| startTime | no           |   start of the selection interval. Default value: `endTime - 1 hour`                                                                                                                        |
| endTime   | no           | end of the selection interval. Default value: `current server time`                                                                                                                             | 
| limit     | no           | maximum number of data samples returned. Default value: 0                                                                                                                 | 
| type      | yes          | type of data properties                                                                                                                                   |
| key      | no           | JSON object containing `name=values` that uniquely identify the property record                                                                                   |
| keyExpression | no | expression for matching properties with specified keys |

### Response Fields

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
       "timestamp": 1423155302000
   }
]
```

| **Name**  | **Description**  |
|---|---|
| type | property type name |
| entity | entity name |
| key | JSON object containing `name=value` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |

### Sample Request

**properties for type using key expression**

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

### Sample Request

**properties for type disk, multiple rows for `key='id'`**

> Request

```json
{
  "queries": [
    {
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
       "timestamp": 1423156623000
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
       "timestamp": 1423156623000
   }
]
```

### Sample Request

**properties for type `disk`, specific key**

> Request

```json
{
  "queries": [
    {
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
       "timestamp": 1423156803000
   }
]
```

### Sample Request

**properties for type `process`, multiple keys**

> Request

```json
{
  "queries": [
    {
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
       "timestamp": 1423222143000
   }
]
```

### Sample Request

**key expression: Filter out all disks except those starting with `sd*`. Disks dm1, dm2 are excluded**

> Request

```json
{
    "queries": [
        {
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
       "timestamp": 1425405363000
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
       "timestamp": 1425405363000
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
       "timestamp": 1425405363000
   }
]
```
