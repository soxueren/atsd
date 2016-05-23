# Entity: Get

## Description 

Retrieve properties and tags for the specified entity.

## Request

### Path 

```elm
/api/v1/entities/{entity}
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

None.

## Response

### Fields

Refer to Response Fields in [Entities: List](list.md)

### Errors

None.

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entities/nurswgvml006
```
#### Payload

None.

#### curl 

```elm
curl https://atsd_host:8443/api/v1/entities/nurswgvml006 \
 --insecure --verbose --user {username}:{password} \
 --header "Content-Type: application/json" \
 --request GET
```

### Response

```json
{
    "name": "nurswgvml006",
    "enabled": true,
    "lastInsertDate": "2015-09-04T15:39:40.000Z",
    "tags": {
        "app": "Hadoop/HBASE",
        "ip": "10.102.0.5",
        "os": "Linux"
    }
}
```

## Additional Examples
