## Entity: Update

## Description

Update specified properties and tags for the given entity.

## Request

### Path

```elm
/api/v1/entities/{entity}
```

### Method

```
PATCH 
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

#### Payload

#### curl

### Response

## Additional examples



### Request Fields



 > Request

```
{
    "tags": {
        "alias": "cadvisor"
    }
}
```

See: [Entity: Create or Replace](#entity:-create-or-replace)

<aside class="notice">
PATCH method updates specified properties and tags for an existing entity. Properties and tags that are not specified are left unchanged.
</aside>
