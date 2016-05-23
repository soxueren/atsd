# Entity Group: Delete

## Description

Delete the entity group. 

<aside class="notice">
Entities that are members of the group are retained.
</aside>

## Request

### Path

```elm
/api/v1/entity-groups/{entity-group}
```

### Method

```
DELETE
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
DELETE https://atsd_host:8443/api/v1/entity-groups/nmon-aix
```

#### Payload

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nmon-aix \
  --insecure --verbose --user {username}:{password} \
  --request DELETE
  ```
  
### Response

## Additional examples




