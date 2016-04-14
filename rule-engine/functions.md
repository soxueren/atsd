# Functions

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

> Example:

```java
NOT property_compare_except (['name', '*time']).isEmpty()
```

Returns true if property tags have changed except for `name` tag and any tags that end with `time`.

### property_compare_except(Collection\<String> keys, Collection\<String> previousValues)

Same as `property_compare_except(keys)` with a list of previous values that are excluded from difference map.

> Example:

```java
NOT property_compare_except(['name', '*time'], ['*Xloggc*']).isEmpty()
```

Returns true if property tags have changed except for `name` tag, any tags that end with `time`, and any previous tags with value containing `Xloggc`. Pattern `*Xloggc*` would ignore changes such as:

```
{inputarguments_19='-Xloggc:/home/axibase/axibase-collector/logs/gc_29286.log' 
                -> '-Xloggc:/home/axibase/axibase-collector/logs/gc_13091.log'}
```

### entity_tags

Returns a map containing entity tags.

> Example:

```java
entity_tags.location = 'NUR'
```

### property_values(String propertySearch) 

Returns a list of property tag values given the property search string.
Property search syntax is as follows:

`<property_type>:[<property_key>=<property_value>[,]]:<tag_name>`.

> Examples:

```java
property_values('docker.container::image').contains('atsd/latest')
```

```java
property_values('linux.disk:fstype=ext4:mount_point').contains('/')
```

### matches(String pattern, Collection\<String> values)

Returns true if one of collection items matches the specified pattern.

> Example:

```java
matches('*atsd*', property_values('docker.container::image'))
```









