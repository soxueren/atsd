## Entity: Entity Groups
### Method
```
GET /api/v1/entities/{entity}/groups
```
### Simple Example
> Request

```
http://atsd_hostname:8088/api/v1/entities/nurswgvml007/groups
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
