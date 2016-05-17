# Entity Filter

## Entity Fields

One of the entity fields is required.

| **Field** | **Description** |
|---|---|---|
| entity    | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | Array of entity names or entity name patterns |
| entityGroup | If `entityGroup` field is specified in the query, series for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | Select entities matching the specified `entityExpression`. The filter is applied to entities returned other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group.   |

## entity-expression Syntax

`entityExpression` returns boolean result based on evaluating an expression.

Supported fields:

* id (entity id)
* name (entity id)
* tags.tag-name or tags['tag-name']

Supported functions:

* [functions](/rule-engine/functions.md)

## Examples:

```css
tags.environment = 'production'
```

```css
tags.location LIKE 'SVL*'
```

```css
id LIKE 'nurswgvml*'
```

```css
tags.container_label.com.axibase.code == 'collector'
```

```css
id LIKE 'nurswgvml*' && property_values('docker.info::version').contains('1.9.1')
```

```css
matches('*ubuntu*', property_values('docker.info::version'))
```

