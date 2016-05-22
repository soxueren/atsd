# Entity: Get

## Description 

Retrieve properties and tags for the specified entity.

## Path 

```elm
/api/v1/entities/{entity}
```

## Method 

```
GET
```

## Request

## Response

### Fields

Refer to Response Fields in [Entities: List](list.md)

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entities/nurswgvml006
```

#### curl 

```css
curl https://atsd_host:8443/api/v1/entities/nurswgvml006 \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X GET
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
