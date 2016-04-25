## Entity: Create or Replace

```
PUT /api/v1/entities/{entity}
```

Create an entity with specified properties and tags or replace an existing entity.
This method creates a new entity or replaces an existing entity. 

<aside class="notice">
If only a subset of fields is provided for an existing entity, the remaining properties will be set to default values and tags will be deleted.
</aside>

### Request Parameters

See: [Get Entities](#entities:-list)
