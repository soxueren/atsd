## Entity: Property Types

Returns an array of property types for the entity. 


### Method
```
GET /api/v1/entities/{entity}/property-types
```
### Basic Example
> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/property-types
```
> Response

```json
[
   "configuration", 
   "system",
   "process"
]
```
### Request Parameters
| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| startTime        | no        | Return only property types that have been collected after the specified time. |

### Response Fields



| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
