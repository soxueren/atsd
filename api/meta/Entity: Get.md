## Entity: Get

```
GET /api/v1/entities/{entity}
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml006?timeFormat=iso
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

**Response Fields:**

See: [Entities: List](#entities:-list)

## Entity: Entity Groups

```
GET /api/v1/entities/{entity}/groups
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/groups
```

> Resonse

```json
[
    {
        "name": "VMware VMs",
        "tags": {}
    },
    {
        "name": "environment-prod",
        "tags": {
            "tag1": "v1"
        }
    },
    {
        "name": "nmon-linux",
        "tags": {}
    },
    {
        "name": "nur-entities-name",
        "tags": {}
    }
    {
        "name": "nurswg-dc1",
        "tags": {}
    },
    {
        "name": "scollector-linux",
        "tags": {}
    },
    {
        "name": "scollector-nur",
        "tags": {}
    },
    {
        "name": "scollector-uptime",
        "tags": {}
    },
    {
        "name": "solarwind-vmware-vm",
        "tags": {}
    },
    {
        "name": "tcollector - linux",
        "tags": {}
    }
]
```

Returns an array of Entity Groups to which the entity belongs. Entity-group tags are included in the reponse.
