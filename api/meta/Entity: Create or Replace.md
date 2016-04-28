## Entity: Create or Replace

```
PUT /api/v1/entities/{entity}
```

```
{
    "tags": {
        "alias": "vmware_host"
    }
}
```
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
