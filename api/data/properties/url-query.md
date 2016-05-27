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

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | Property type name. |
| entity |string |  Entity name. |
| key | object | Object containing `name=value` fields that uniquely identify the property record. <br>Example: `{"file_system": "/","mount_point":"sda1"}`|
| tags | object | Object containing `name=value` fields that are not part of the key and contain descriptive information about the property record. <br>Example: `{"fs_type": "ext4"}`. |
| date | string | ISO 8601 date when the property record was last modified. |

## Example

### Request

#### URI

```elm 
GET https://atsd_server:8443/api/v1/properties/nurswgvml007/types/disk
```

#### curl

```elm
curl https://atsd_server:8443/api/v1/properties/nurswgvml007/types/disk \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "file_system": "/",
           "mount_point": "sda1"
       },
       "tags": {
           "fs_type": "ext4"
       },
       "date": "2016-05-25T04:15:00Z"
   }
]
```
