# Entity: Create or Replace

## Description

Create an entity with specified fields and tags or replace the fields and tags of an existing entity.

In case of an existing entity, all the current entity tags will be replaced with entity tags specified in the request.

If the replace request for an existing entity doesn't contain any tags, the current tags will be deleted.

The replace request for an existing entity doesn't affect any series, properties, or metrics since the internal identifier of the entity remains the same.

## Request

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| PUT | `/api/v1/entities/{entity}` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| entity |string|Entity name.|

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| enabled | boolean | Data collection status. Default: true. Incoming data is discarded for a disabled entity.  |
| tags |object|Object containing entity tags, where field name represents tag name and field value is tag value.<br>`{"tag-1":string,"tag-2":string}` |

* If a field is not specified in the request, it will be reset to its default value.

## Response

### Fields 

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

