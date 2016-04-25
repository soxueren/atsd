## Properties: Query for Entity and Type

Returns properties for entity and type. 

### Request Parameters

```
GET /api/v1/properties/{entity}/types/{type}
```

> Request

```
http://atsd_server.com:8088/api/v1/properties/nurswgvml007/types/system
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
       "timestamp": 1423155302000
   }
]
```

See: [Properties Query](#properties:-query)

**RESPONSE FIELDS:**

| **Name**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
| entity | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
| key | JSON object containing `name=values` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |

