# Meta API: Entities Methods

| **Name** | **Method** | **Path** | **Content-Type** | **Description** |
|:---|:---|:---|:---|:---|
| [Get](get.md) | GET | `/api/v1/entities/{entity}` |  | Retrieve information about the specified entity including its tags. |
| [List](list.md) | GET | `/api/v1/entities` |  | Retrieve a list of entities matching the specified filter conditions. |
| [Update](update.md) | PATCH | `/api/v1/entities/{entity}` | `application/json` |  Update fields and tags of the specified entity.  |
| [Create or Replace](create-or-replace.md) | PUT | `/api/v1/entities/{entity}` | `application/json` |  Create an entity with specified fields and tags or replace the fields and tags of an existing entity.  |
| [Delete](delete.md) | DELETE | `/api/v1/entities/{entity}` | `application/json` |  Delete the specified entity and delete it as member from any entity groups that it belongs to.  |
| [Entity Groups](entity-groups.md) | GET | `/api/v1/entities/{entity}/groups` |  |  Retrieve a list of entity groups to which the specified entity belongs.  |
| [Metrics](metrics.md) | GET | `/api/v1/entities/{entity}/metrics` |  |  Retrieve a list of metrics collected by the entity. |
| [Property Types](property-types.md) | GET | `/api/v1/entities/{entity}/property-types` |  |  Retrieve a list property types for the entity. |
