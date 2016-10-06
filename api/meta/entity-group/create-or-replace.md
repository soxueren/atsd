# Entity Group: Create or Replace

## Description

Create an entity group with specified fields and tags or replace the fields and tags of an existing entity group.

In case of an existing entity group, all the current tags will be replaced with tags specified in the request.

If the replace request for an existing entity group doesn't contain any tags, the current tags will be deleted.

Fields that are set to `null` are ignored by the server and are set to their default value.

The replace request for an existing entity group doesn't affect the list of its member entities, since the internal identifier of the entity group remains the same.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| PUT | `/api/v1/entity-groups/{group}` | `application/json` |

### Path Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| group |string|Entity group name.|

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| expression | string| Group membership expression. The expression is applied to entities to automatically add/remove members of this group.|
| tags | object| Object containing entity group tags, where field name represents tag name and field value is tag value.<br>`{"tag-1":string,"tag-2":string}`.  |

## Response

### Fields

None. 

## Example

### Request

#### URI

```elm
PUT https://atsd_host:8443/api/v1/entity-groups/nmon-collectors
```

#### Payload

```json
{
    "tags": {
        "collector": "nmon"
    }
}
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nmon-collectors \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PUT \
  --data '{"tags": {"collector": "nmon"}}
 ```
 
### Response

None.

## Additional examples







