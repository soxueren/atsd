# Entity Groups: List

## Description

## Request

| **Method** | **Path** | 
|:---|:---|---:|
| GET | `/api/v1/entity-groups` |

### Query Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| expression |string|Expression to include entity groups by name or tags. Use `name` variable for group name. Wildcard `*` is supported.|
| limit |integer|Maximum number of entity groups to retrieve, ordered by name.|
| tags |string|Comma-separated list of entity group tags to be included in the response.<br>For example, `tags=table,unit`<br>Specify `tags=*` to include all entity group tags.|

## Response

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| name | string| Entity group name. |
| expression | string | Group membership expression. The expression is applied to entities to automatically add/remove members of this group.|
| tags | object | Entity group tags, as requested with `tags` parameter. |

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entity-groups?tags=os_level&limit=2&expression=name%20like%20%27nmon*%27
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups?tags=os_level&limit=2&expression=name%20like%20%27nmon*%27 \
 --insecure --verbose --user {username}:{password} \
 --request GET
 ```
 
### Response

```json
[{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3"
    }
},
{
    "name": "nmon-linux"
}]
```

## Additional examples

