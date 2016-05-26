# Entity Groups: List

## Description

## Request

### Path

```elm
/api/v1/entity-groups
```

### Method

```
GET
```

### Headers

None.

### Parameters

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for entity group name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity group tags to be included in the response|
|limit|no|Limit response to first N entity groups, ordered by name.|

### Fields

None.

## Response

### Fields

| **Name**   | **Description**                                   |
|------------|---------------------------------------------------|
| name       | Entity group name (unique)                        |
| expression | Entity group expression                           |
| tags       | Entity group tags, as requested by tags parameter |

### Errors

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

