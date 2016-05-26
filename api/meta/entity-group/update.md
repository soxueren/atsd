# Entity Group: Update

## Description

Update specified properties and tags for the given entity group.
This method updates specified properties and tags for an existing entity group. 

## Request

### Path

```elm
/api/v1/entity-groups/{entity-group}
```

### Method

```
PATCH
```

### Headers

None.

### Parameters

None.

### Fields

Refer to Request Fields in  [Entity Group: Create or Replace](./create-or-replace.md)

<aside class="notice">
Properties and tags that are not specified are left unchanged.
</aside>

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
PATCH https://atsd_host:8443/api/v1/entity-groups/{entity-group}
```

#### Payload

#### curl

### Response

## Additional examples


