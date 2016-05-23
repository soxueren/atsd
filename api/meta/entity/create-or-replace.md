# Entity: Create or Replace
## Description

Create an entity with specified properties and tags or replace the properties and tags of an existing entity.
This method creates a new entity or replaces the properties and tags of an existing entity. 

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


### Fields
| **Field**                            | **Description**                                                                             |
|---|---|
| enabled                             | Enabled status. Incoming data is discarded for disabled entities.                           |
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"alias": "vmware_host"}`|


<aside class="notice">
If only a subset of fields is provided for an existing entity, the remaining properties will be set to default values and tags will be deleted.
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
PUT https://atsd_host:8443/api/v1/entities/hostmain
```

#### Payload

```css
{
  "tags": {
     "alias": "vmware_host"
        }
}
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities/hostmain \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PATCH \
  --data '{"tags": {"alias": "vmware_host"}}'
  ```
  

### Response 

None. 

## Additional Examples

