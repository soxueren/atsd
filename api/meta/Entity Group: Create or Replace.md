## Entity Group: Create or Replace

```
PUT /api/v1/entity-groups/{entity-group}
```

Create an entity group with specified properties and tags or replace properties and tags for an existing entity group.
This method creates a new entity group or replaces the properties and tags of an existing entity group. 

<aside class="notice">
If only a subset of fields is provided for an existing entity group, the remaining properties and tags will be deleted.
</aside>

### Request Fields

 | **Field**   | **Description**                                   |
|------------|---------------------------------------------------|
| expression | Entity group expression                           |
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"os_level": "aix 6.3"}`|
