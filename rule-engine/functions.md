# Functions

### tags.(String name)

Returns tag value for the current series, property or message. tag name can be specified after `.` or inside square brackets.

_Example_

```sh
tags.location = 'NUR'
```

```sh
tags.image-name = 'collector'
```

```sh
tags['image-name'] like '*collector*'
```

### entity_tags (also entity.tags)

Returns a map containing entity tags for the current entity.

_Example_

```java
entity_tags.location = 'NUR'
```

### entity.tags.(String name)

Returns entity tag value for current entity.

```sh
entity.tags.location = 'docker'
```

### entity_tags(String entityName)

Returns a map containing entity tags for the specified entity.
The map is empty if entity is not found.

_Example_

```java
entity_tags(tags.hardware_node).location = 'NUR'
```

### property_values(String propertySearch) 

Returns a list of property tag values for the current entity given the [property search string](../property-search-syntax.md).

The list is empty if property or tag is not found.

_Example_

```java
property_values('docker.container::image').contains('atsd/latest')
```

_Example_

```java
property_values('linux.disk:fstype=ext4:mount_point').contains('/')
```

### property_values(String entity, String propertySearch) 

Same as property_values(String propertySearch) but for an explicitly specified entity.

_Example_

```java
property_values('nurswgvml007', 'docker.container::image').contains('atsd/latest')
```

_Example_

```java
property_values(entity_tags.image, 'docker.image.config::name').contains('atsd/latest')
```

### matches(String pattern, Collection\<String> values)

Returns true if one of collection items matches the specified pattern.

_Example_

```java
matches('*atsd*', property_values('docker.container::image'))
```



### property_compare_except(Collection\<String> keys)

Compares previous and current property tags and returns a difference map containing a list of changed tag values. 

Sample difference map:

```java
{inputarguments_19='-Xloggc:/home/axibase/axibase-collector/logs/gc_29286.log' 
                -> '-Xloggc:/home/axibase/axibase-collector/logs/gc_13091.log'}
```

The map includes tags that are not present in new property tags and tags that were deleted.
If the difference map is empty, no changes were identified.
Comparision is case-insensitive.

_Example_

```java
NOT property_compare_except (['name', '*time']).isEmpty()
```

Returns true if property tags have changed except for `name` tag and any tags that end with `time`.

### property_compare_except(Collection\<String> keys, Collection\<String> previousValues)

Same as `property_compare_except(keys)` with a list of previous values that are excluded from difference map.

_Example_

```java
NOT property_compare_except(['name', '*time'], ['*Xloggc*']).isEmpty()
```

Returns true if property tags have changed except for `name` tag, any tags that end with `time`, and any previous tags with value containing `Xloggc`. Pattern `*Xloggc*` would ignore changes such as:

```
{inputarguments_19='-Xloggc:/home/axibase/axibase-collector/logs/gc_29286.log' 
                -> '-Xloggc:/home/axibase/axibase-collector/logs/gc_13091.log'}
```









