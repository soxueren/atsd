# Entity: Delete

## Description

Delete the entity. Delete the entity from any Entity Groups that it belongs to.
Data collected by the entity will be removed asynchronously in the background.

## Request

### Path

```elm
/api/v1/entities/{entity}
```

### Method

```
DELETE 
```

### Headers 

None.

### Parameters

None.

### Fields

None.

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
DELETE https://atsd_host:8443/api/v1/entities/hostmain
```
#### Payload

None.

#### curl 

```elm
curl https://atsd_host:8443/api/v1/entities/hostmain \
  --insecure --verbose --user {username}:{password} \
  --request DELETE
```

### Response

None.

## Additional Examples

