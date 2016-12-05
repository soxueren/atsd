# Functions

### tags.(string `name`)

Returns tag value for the current series, property, or message. tag `name` can be specified after `.` or inside square brackets.

Examples:

```sh
tags.location = 'NUR'
```

```sh
tags.image-name = 'collector'
```

```
tags.io.docker.environment != 'test'
```

```sh
tags['image-name'] like '*collector*'
```

### entity_tags / entity.tags

Returns a map containing entity tags for the current entity.

_Example_

```sh
entity_tags.location = 'NUR'
```

### entity.tags.(string `name`)

Returns entity tag value for current entity.

```sh
entity.tags.location = 'docker'
```

```sh
entity.tags.io.docker.environment != 'test'
```

### entity_tags(string `entityName`)

Returns a map containing entity tags for the specified entity.
The map is empty if the entity is not found.

_Example_

```java
entity_tags(tags.hardware_node).location = 'NUR'
```

### property_values(string `propertySearch`)

Returns a list of property tag values for the current entity given the [property search string](../property-search-syntax.md).

The list is empty if the property or tag is not found.

_Example_

```java
property_values('docker.container::image').contains('atsd/latest')
```

_Example_

```java
property_values('linux.disk:fstype=ext4:mount_point').contains('/')
```

### property_values(string `entity`, string `propertySearch`)

Same as `property_values`(String propertySearch) but for an explicitly specified entity.

_Example_

```java
property_values('nurswgvml007', 'docker.container::image').contains('atsd/latest')
```

_Example_

```java
property_values(entity_tags.image, 'docker.image.config::name').contains('atsd/latest')
```

### property(String `pattern`)

Returns the first value in the collection of strings returned by the `property_values()` function. The function returns an empty string if no property records are found.

_Example_

```java
property(docker.container::image')
```

### matches(String `pattern`, collection\<string> `values`)

Returns true if one of collection items matches the specified pattern.

_Example_

```java
matches('*atsd*', property_values('docker.container::image'))
```



### property_compare_except(collection\<string> `keys`)

Compares previous and current property tags and returns a difference map containing a list of changed tag values.

Sample difference map:

```java
{inputarguments_19='-Xloggc:/home/axibase/axibase-collector/logs/gc_29286.log' -> '-Xloggc:/home/axibase/axibase-collector/logs/gc_13091.log'}
```

The map includes tags that are not present in new property tags and tags that were deleted.
If the difference map is empty, no changes were identified.
This comparison is case-insensitive.

_Example_

```java
NOT property_compare_except (['name', '*time']).isEmpty()
```

Returns true if property tags have changed except for the `name` tag and any tags that end with `time`.

### property_compare_except(collection\<string> `keys`, collection\<string> `previousValues`)

Same as `property_compare_except(keys)` with a list of previous values that are excluded from difference map.

_Example_

```java
NOT property_compare_except(['name', '*time'], ['*Xloggc*']).isEmpty()
```

Returns true if property tags have changed, except for the `name` tag, any tags that end with `time`, and any previous tags with value containing `Xloggc`. The pattern `*Xloggc*` would ignore changes such as:

``` java
{inputarguments_19='-Xloggc:/home/axibase/axibase-collector/logs/gc_29286.log'-> '-Xloggc:/home/axibase/axibase-collector/logs/gc_13091.log'}
```

### coalesce(collection\<string> `names`)

Returns the first element of the provided collection, specified as an array of string, that is not null or an empty string.
The function returns an empty string if all elements of the collection are null or empty.

_Example_

```java
coalesce(['', null, 'string-3'])
```
Returns 'string-3'.

_Example_

```java
coalesce([tags.location, 'SVL'])
```
Returns `tags.location` if it's not empty and not null, 'SVL' otherwise.

_Example_

```java
coalesce([entity.label, entity.tags.name])
```
Returns the value of the `entity.label` placeholder if it's not an empty string, otherwise returns value of the `entity.tags.name` placeholder.
If both placeholders are empty, then return an empty string.
