## Entity: Property Types

Returns an array of property types for the entity. 

### Request Parameters

```
GET /api/v1/entities/{entity}/property-types
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/property-types
```

| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| startTime        | no        | Return only property types that have been collected after the specified time. |

### Response Fields

> Response

```json
[
   "configuration", 
   "system",
   "process"
]
```

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
