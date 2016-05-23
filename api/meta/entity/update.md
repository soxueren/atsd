## Entity: Update

## Description

Update specified properties and tags for the given entity. PATCH method updates specified properties and tags for an existing entity. Properties and tags that are not specified are left unchanged.

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

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

None.

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
PATCH https://atsd_host:8443/api/v1/entities/{entity}
```

#### Payload

```json
{
    "tags": {
        "alias": "cadvisor"
    }
}
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities/nurswgvml006 \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PUT \
  --data '{"tags": {"alias": "vmware_host"}}'
  ```

### Response

None.

## Additional examples

