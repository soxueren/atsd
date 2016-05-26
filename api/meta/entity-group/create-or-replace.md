# Entity Group: Create or Replace

## Description

Create an entity group with specified properties and tags or replace properties and tags for an existing entity group.
This method creates a new entity group or replaces the properties and tags of an existing entity group. 

<aside class="notice">
If only a subset of fields is provided for an existing entity group, the remaining properties and tags will be deleted.
</aside>

## Request

### Path

```elm
/api/v1/entity-groups/{entity-group}
```

### Method

```
PUT 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

None.

### Fields

|Field | Description|
|---|---|
| expression | Entity group expression|
|tags | User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"os_level": "aix 6.3"}`|

## Response

### Fields

None. 

### Errors

None.

## Example

### Request

#### URI

```elm
PUT https://atsd_host:8443/api/v1/entity-groups/nmon-aix
```

#### Payload
???
#### curl

```elm
curl https://atsd_host:8443/api/v1/entity-groups/nmon-aix \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PUT \
  --data ???
 ```
 
### Response

None.

## Additional examples







