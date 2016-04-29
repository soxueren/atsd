## Entity: Get
### Method
```
GET /api/v1/entities/{entity}
```
### Basic Example
> Request

```
http://atsd_hostname:8088/api/v1/entities/nurswgvml006?timeFormat=iso
```

> Response

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

Displays entity properties and all tags.

###Response Fields

See: [Entities: List](#entities:-list)

