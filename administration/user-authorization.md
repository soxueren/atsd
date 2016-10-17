# User Authorization


Axibase Time Series Database implements Role Based Access Control (RBAC)
and Entity Permissions (EP) to restrict user access to protected
information.

## Role Based Access Control

Authenticated users are allowed to access protected resources based on
their role. The role specifies which URLs and HTTP methods the user can access. Each user can be assigned multiple roles.

### Available API Roles
| Role | Description |
| --- | --- |
|API_DATA_READ | Query Data API to read series, properties, messages, and alerts from the database.|
|API_DATA_WRITE | Submit Data API requests to insert series, properties, and messages into the database.|
|API_META_READ | Query Meta Data API to read metric, entity, and entity group settings.|
|API_META_WRITE | Submit Meta Data API requests to change metric and entity settings. API requests to change entity groups or add/remove their members require a ENTITY_GROUP_ADMIN role.|

### Available User Interface Roles

| Role | Description |
| --- | --- |
| USER | View information on all pages except Configuration, Entity Group, and Admin pages. Includes API_DATA_READ and API_META_READ. |
| EDITOR | View and edit information on all pages except Admin and Entity Group pages. Includes USER. |
| ENTITY_GROUP_ADMIN | Edit entity groups. Includes USER. |
| ADMIN | View and edit information on all pages. Includes all roles. |

## Entity Permissions

Permissions to read and write data are granted to User Groups at the Entity Group level.

In order to read data for an entity, the user must have a `API_DATA_READ` role. In addition, one of the user’s User
Groups must be granted a Read permission to an Entity Group containing the
entity.

In order to write data for an entity the user must have a `API_DATA_WRITE` role. In addition, one of the user’s User Groups must be granted a Write permission to an Entity Group containing the entity. Effective user permissions are calculated as a union of all User Groups permissions to which the user belongs.

![entity_group_permission](images/entity_group_permission.png)

*In the following diagram, to read data for entity-30, the user must be either added to user-group-C as a member, or
entity-group-3 must be assigned to user-group-B or user-group-A.*

![atsd_role_hierarchy](images/atsd_role_hierarchy-2.png)

### All Entities Permissions

In addition to specific Entity Group permissions, user groups can be granted a special `All Entities: Read` or `All Entities: Write` permission which allows reading or writing data to any entity, including entities that do not belong to any Entity Group. Users inherit `All Entities` permissions from the
User Groups to which they belong.

### Inserting Data for New Entities

Since non-existent entities cannot be assigned to a group, the `All Entities: Write` permission is required to create
entities either in the user interface or by inserting data via API. User with a `API_DATA_WRITE` role but without the
`All Entities: Write` permission will be able to insert data only for existing entities.

### Wildcard Requests

Users without the `All Entities: Read` permission are allowed to query Data API using wildcards as part of entity name as well as execute SQL queries without entity name conditions. In both cases, the results will be filtered based on users effective permissions, therefore different users may see different results for the same API request or SQL query depending on their entity permissions.

## Implementation Notes

-   The Users role, group membership, and entity permissions are cached while the session is active or until it times out. In order to apply changes, the user must re-login into the application.
