# Entity: Create or Replace
## Description

Create an entity with specified fields and tags or replaces the fields and tags of an existing entity.

In case of an existing entity, all the current entity tags will be replaced with entity tags specified in the request.

If the replace request for an existing entity doesn't contain any tags, current entity tags will be deleted.

The replace request for an existing entity doesn't delete any series, properties, or metrics.

## Request

### Path

```elm
/api/v1/entities/{entity}
```

### Method

```
PUT
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

###  Parameters

None.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| enabled | boolean | Data collection status. Default: true. For disabled entities data such as series, properties, and messages is discarded. |
| tags |object|Object containing entity tags, for example `"tags": {"location": "NUR-2", "env": "production"}` |

* If `enabled` field is not specified in the request, it is reset to its default value which is `true`.

## Response

### Fields 

None.

### Errors

None. 

## Example

### Request

#### URI

```elm
PUT https://atsd_host:8443/api/v1/entities/nurswgvml006
```

#### Payload

```css
{
  "enabled": true,
  "tags": {
    "location": "NUR-2",
    "env": "production"
  }
}
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities/nurswgvml006 \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PUT \
  --data '{"enabled":true,"tags":{"env":"production","location":"NUR-2"}}'
  ```
  

### Response 

None. 

## Additional Examples

