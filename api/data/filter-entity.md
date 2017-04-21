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
| entityExpression | string | Filter entities by name, entity tag, and properties using [syntax](../../rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

## `entityExpression` Syntax

`entityExpression` returns boolean result based on evaluating an expression.

Supported fields:

* id (entity id)
* name (entity id)
* tags.tag-name or tags['tag-name']

Supported functions:

* [functions](../../rule-engine/functions.md)


## Entity Name Match Examples

> Match entities with name starting with `nurswgvml`, for example `nurswgvml001`, `nurswgvml772`.

```javascript
id LIKE 'nurswgvml*'
```

## Entity Tag Match Examples

> Match entities with entity tag `environment` equal to `production`.

```javascript
tags.environment = 'production'
```

> Match entities with entity tag `location` starting with `SVL`, for example `SVL`, `SVL02`.

```javascript
tags.location LIKE 'SVL*'
```

> Match entities with entity tag `container_label.com.axibase.code` equal to `collector`.

```javascript
tags.container_label.com.axibase.code = 'collector'
```

## Property Match Functions

#### Function `property_values(<path>)`

The function returns a collection of tag values for the specified path, whereas such `<path>` consists of property type, key (optional), and tag name. Since the results represent a collection, it can be evaluated with such methods as `size()`, `isEmpty()`, `contains()`. The function returns an empty collection if no property records are found.

#### Function `property(<path>)`

The function return the first value in the collection of strings returned by the `property_values(<path>)` function. The function returns an empty string if no property records are found.

#### Function `matches(<pattern>, <path>)`

The function returns `true` if one the values in the returned collection matches the specified pattern.

## Property Match Examples

> Match entities with a `java_home` stored in `docker.container.config.env` equal to '/usr/lib/jvm/java-8-openjdk-amd64/jre'.

```javascript
property('docker.container.config.env::java_home') = '/usr/lib/jvm/java-8-openjdk-amd64/jre'
```

> Match entities which have a `/opt` file_system stored in `nmon.jfs` property type.

```javascript
property_values('nmon.jfs::file_system').contains('/opt')
```

> Match entities with a `file_system` which name includes `ora`, stored in `nmon.jfs` property type.

```javascript
matches('*ora*', property_values('nmon.jfs::file_system'))
```

> Match entities with non-empty `java_home` in `docker.container.config.env` property type.

```javascript
!property_values('docker.container.config.env::java_home').isEmpty()
```

> Match entities without `java_home` in `docker.container.config.env` property type.

```javascript
property_values('docker.container.config.env::java_home').size() == 0
```
