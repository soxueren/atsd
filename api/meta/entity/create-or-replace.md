# Entity: Create or Replace
## Description

## Request

### Path

```
/api/v1/entities/{entity}
```

### Method

```
PUT
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

```
PUT https://atsd_host:8443/api/v1/entities/{entity}
```

#### Payload

```
{
  "tags": {
     "alias": "vmware_host"
        }
}
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/metrics/cpu_busy \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request PATCH \
  --data '{"label":"CPU Busy Average","tags":{"table":"CollectD CPU Total"}}'
  ```
  

### Response 

## Additional Examples


### Basic Example

Create an entity with specified properties and tags or replace the properties and tags of an existing entity.
This method creates a new entity or replaces the properties and tags of an existing entity. 

<aside class="notice">
If only a subset of fields is provided for an existing entity, the remaining properties will be set to default values and tags will be deleted.
</aside>

### Request Fields

| **Field**                            | **Description**                                                                             |
|---|---|
| enabled                             | Enabled status. Incoming data is discarded for disabled entities.                           |
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"alias": "vmware_host"}`|
