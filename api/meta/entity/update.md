## Entity: Update

Update specified properties and tags for the given entity.

### Request Fields

```
PATCH /api/v1/entities/{entity}
```

 > Request

```
{
    "tags": {
        "alias": "cadvisor"
    }
}
```

See: [Entity: Create or Replace](#entity:-create-or-replace)

<aside class="notice">
PATCH method updates specified properties and tags for an existing entity. Properties and tags that are not specified are left unchanged.
</aside>
