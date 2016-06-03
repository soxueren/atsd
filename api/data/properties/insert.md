# Properties: Insert

## Description

Insert an array of properties.

## Request

### Path

```elm
/api/v1/properties/insert
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Fields

| **Field**  |**Type**  | **Description**  |
|:---|:---|:---|
| type | string | [**Required**] Property type name. <br>Use reserved `$entity_tags` type to insert entity tags.|
| entity | string | [**Required**] Entity name. |
| key | object | Object containing `name=value` fields that uniquely identify the property record. <br>Example: `{"file_system": "/","mount_point":"sda1"}`|
| tags | object | Object containing `name=value` fields that are not part of the key and contain descriptive information about the property record. <br>Example: `{"fs_type": "ext4"}`. |
| date | string | ISO 8601 date, for example `2016-05-25T00:15:00Z`. <br>Set to current server time if omitted. |

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/properties/insert
```

#### Payload

```json
[{
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
}]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/properties/insert  \
  --insecure  --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request  POST \
  --data '[{"type":"disk","entity":"nurswgvml007","key":{"file_system":"/","mount_point":"sda1"},"tags":{"fs_type":"ext4"},"date":"2016-05-25T04:15:00Z"}]'
```

## Response 

