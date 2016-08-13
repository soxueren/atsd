# Meta API: Entity groups Methods

| **Name** | **Method** | **Path** | **Content-Type** | **Description** |
|:---|:---|:---|:---|:---|
| [Get](get.md) | GET | `/api/v1/entity-groups/{group}` | `application/json` | Retrieve information about the specified entity group including its tags. |
| [List](list.md) | GET | `/api/v1/entity-groups` | `application/json` | Retrieve a list of entity groups. |
| [Update](update.md) | PATCH | `/api/v1/entity-groups/{group}` | `application/json` |  Update fields and tags of the specified entity group.  |
| [Create or Replace](create-or-replace.md) | PUT | `/api/v1/entity-groups/{group}` | `application/json` |  Create an entity group with specified fields and tags or replace the fields and tags of an existing entity group.  |
| [Delete](delete.md) | DELETE | `/api/v1/entity-groups/{group}` | `application/json` |  Delete the specified entity group.  |
| [Get Entities](get-entities.md) | GET | `/api/v1/entity-groups/{group}/entities` | `application/json` |  Retrieve a list of entities that are members of the specified entity group and are matching the specified filter conditions.  |
| [Add Entities](add-entities.md) | POST | `/api/v1/entity-groups/{group}/entities/add` | `application/json` |  Add entities as members to the specified entity group. |
| [Set Entities](set-entities.md) | POST | `/api/v1/entity-groups/{group}/entities/set` | `application/json` |  Set members of the entity group from the specified entity list. |
| [Delete Entities](delete-entities.md) | POST | `/api/v1/entity-groups/{group}/entities/delete` | `application/json` |  Remove specified entities from members of the specified entity group. |
