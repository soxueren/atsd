# Entity Group: Get

## Description


Displays entity group properties and all tags.

## Request

### Path

```elm
 /api/v1/entity-groups/{group}
 ```
 
### Method

```
GET
```

### Headers

### Parameters

### Fields

## Response

### Fields

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entity-groups/nmon-aix
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nmon-aix \
  --insecure --verbose --user {username}:{password} \
  --request GET
  ```
  
### Response

```json
{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3" 
    }
}
```

## Additional examples
