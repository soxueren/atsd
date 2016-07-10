## Entity: Update

## Description

Update fields and tags of the specified entity. 

Unlike the [replace method](create-or-replace.md), fields and tags that are not specified in the request are left unchanged.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| PATCH | `/api/v1/entities/{entity}` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| entity |string|Entity name.|

### Fields

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| enabled | boolean | Enabled status. Default: true. Incoming data is discarded for a disabled entity.|
| tags | object | Object containing entity tags, where field name represents tag name and field value is tag value.<br>Example : `{"tag-1":string,"tag-2":string}`|

## Response

### Fields

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

