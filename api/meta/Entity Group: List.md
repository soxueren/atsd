## Entity Group: List

```
GET /api/v1/entity-groups/{group}
```

> Request

```
http://atsd_server.com:8088/api/v1/entity-groups/nmon-aix
```

> Response

```json
{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3" 
    }
}
```

Displays entity group properties and all tags.

**Response Fields:**

See: [Entity Groups](#entity-groups:-get)
