# Entity Filter

## Entity Fields

One of the entity fields is required.

| **Field** | **Description** |
|---|---|---|
| entity    | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | Array of entity names or entity name patterns |
| entityGroup | If `entityGroup` field is specified in the query, series for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group.   |

## entity-expression Syntax

`entityExpression` should return boolean result based on evaluating an expression.

Supported fields and [functions](/rule-engine/functions.md):

* id (entity id)
* name (entity id)
* tags.{tag-name}
* entity_tags
* property_values

## Examples:

`tags.location='SVL'

