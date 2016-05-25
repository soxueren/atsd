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

| **Field**  |**Required**  | **Description**  |
|:---|:---|
| type | yes | Property type name. |
| entity | yes | Entity name. |
| key | no | An object containing `name=value` fields that uniquely identify the property record. |
| tags | no | An object containing `name=value` tags, for example tags: `{"path": "/", "name": "sda"}`. |
| date | no | Time in ISO format. Set to current server time if omitted. |

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
   "type":"type-1",
   "entity":"entity-1",
   "key":{"server_name":"server","user_name":"system"},
   "tags":{"name.1": "value.1"},
   "date":"2015-02-05T16:55:02Z"
},{
   "type":"type-2",
   "entity":"entity-1",
   "tags":{"name.2": "value.2"}
}]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/properties/insert  \
  --insecure  --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request  POST \
  --data @file.json
```

## Response 

