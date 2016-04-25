## Entity Group: Create or Replace

```
PUT /api/v1/entity-groups/{entity-group}
```

Create an entity group with specified properties and tags or replace an existing entity group.
This method creates a new entity group or replaces an existing entity group. 

<aside class="notice">
If only a subset of fields is provided for an existing entity group, the remaining properties and tags will be deleted.
</aside>

### Request Fields

See: [Entity Groups](#entity-groups:-get)
