# Functions: coalesce

## Overview

Returns the first element of the provided collection, specified as an array of string, that is not null or an empty string.
The function returns an empty string if all elements of the collection are null or empty.

## Syntax

```java
coalesce([string str])
```

## Examples

* Returns 'string-3'.

```java
coalesce(['', null, 'string-3'])
```

* Returns `tags.location` if it's not empty and not null, 'SVL' otherwise.

```java
coalesce([tags.location, 'SVL'])
```

* Returns non-empty tag

```java
coalesce([entity.label, entity.tags.name])
```

Returns the value of the `entity.label` placeholder if it's not an empty string, otherwise returns value of the `entity.tags.name` placeholder.
If both placeholders are empty, then an empty string is returned.
