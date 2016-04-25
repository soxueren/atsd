## Entity: Get

```
GET /api/v1/entities/{entity}
```

> Request

```
http://atsd_server.com:8088/api/v1/entities/nurswgvml006
```

> Response

```json
[{
    "name": "nurswgvml006",
    "enabled": true,
    "lastInsertTime": 1423144296000,
    "tags": {
        "app": "Hadoop/HBASE" 
    }
}]
```

Displays entity properties and all tags.

**Response Fields:**

See: [Get Entities](#entities:-list)
