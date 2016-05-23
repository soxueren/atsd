# Entity: Entity Groups

## Description

Returns an array of Entity Groups to which the entity belongs. Entity-group tags are included in the reponse.

## Request

### Path

```elm
/api/v1/entities/{entity}/groups
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

### Errors

None.

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entities/nurswgvml007/groups
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities/nurswgvml007/groups \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

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

## Additional examples



