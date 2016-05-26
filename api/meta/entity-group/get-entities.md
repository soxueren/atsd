# Entity Group: Get Entities

## Description

## Request

### Path

```elm
/api/v1/entity-groups/{group}/entities
```
### Method

```
GET
```

### Headers

None.

### Parameters

None.

### Fields

|**Field**|**Required**|**Description**|
|---|---|---|
|active|no| Filter entities by `last_insert_time`. If `active = true`, only entities with positive `last_insert_time` are included in the response|
|expression|no|Use `name` variable for entity name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity tags to be included in the response|
|limit|no|Limit response to first N entities, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

## Response

### Fields

| **Field**                                 | **Description**                                                                             |
|------------------------------------------|---------------------------------------------------------------------------------------------|
| name                                     | Entity name (unique)                                                                        |
| enabled                                  | Enabled status. Incoming data is discarded for disabled entities                            |
| lastInsertTime                           | Last time value was received by ATSD for this entity. Time specified in epoch milliseconds. |
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
|tags as requested by tags parameter|User-defined tags|

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities?timeFormat=iso&tags=*&limit=3
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nur-entities-name/entities?timeFormat=iso&tags=*&limit=3 \
  --insecure --verbose --user {username}:{password} \
  --request GET 
  ```
  
### Response

```json
[
    {
        "name": "atsd_server",
        "enabled": true,
        "tags": {}
    },
    {
        "name": "nurswgvml003",
        "enabled": true,
        "lastInsertDate": "2015-09-04T15:43:36.000Z",
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "ip": "10.102.0.2",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml004",
        "enabled": true,
        "tags": {}
    }
]
```

## Additional examples

