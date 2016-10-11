# Entity Filter Fields

* One of the entity fields is **required**.
* Entity name pattern may include `?` and `*` wildcards.
* Field precedence, from high to low: `entity`, `entities`, `entityGroup`. Although multiple fields can be specified in the query object, only the field with higher precedence will be applied. 
* `entityExpression` is applied as an additional filter to the `entity`, `entities`, and `entityGroup` fields.<br>For example, if both the `entityGroup` and `entityExpression` fields are specified, `entityExpression` is applied to members of the specified entity group.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| entity   | string | Entity name or entity name pattern. |
| entities | array | Array of entity names or entity name patterns. |
| entityGroup | string | Entity group name. Return records for member entities of the specified group.<br>The result will be empty if the group doesn't exist or contains no entities. |
| entityExpression | string | Filter entities by name, entity tag, and properties using [syntax](/rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

## `entityExpression` Syntax

`entityExpression` returns boolean result based on evaluating an expression.

Supported fields:

* id (entity id)
* name (entity id)
* tags.tag-name or tags['tag-name']

Supported functions:

* [functions](/rule-engine/functions.md)

## `entityExpression` Examples

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
