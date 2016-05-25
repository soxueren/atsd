# Properties: URL Query

## Description

Retrieve property records for the specified entity and type.

## Request 

### Path

```elm
/api/v1/properties/{entity}/types/{type}
```

### Method 

```
GET
```

### Headers

None.

## Response 

### Fields

| **Field**  | **Description**  |
|:---|:---|
| type | Property type name. |
| entity | Entity name. |
| key | An object containing `name=value` fields that uniquely identify the property record. |
| tags | An object containing `name=value` tags, for example tags: `{"path": "/", "name": "sda"}`. |
| date | Time when the record was last updated, in ISO format. |

## Example

### Request

#### URI

```elm 
GET https://atsd_server:8443/api/v1/properties/nurswgvml007/types/system
```

#### curl

```elm
curl https://atsd_server:8443/api/v1/properties/nurswgvml007/types/system?timeFormat=iso \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
[
   {
       "type": "system",
       "entity": "nurswgvml007",
       "key": {},
       "tags": {
           "cpu_total.busy": "1",
           "cpu_total.idle%": "93.6",
           "cpu_total.sys%": "1.1"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```
