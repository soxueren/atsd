# Functions: coalesce

## Overview

Returns the first element of the provided collection, specified as an array of strings, which are not null or empty strings.
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

* Returns `tags.location` if it's not empty and not null, otherwise 'SVL' will be returned.

```java
coalesce([tags.location, 'SVL'])
```

* Returns a non-empty tag.

```java
coalesce([entity.label, entity.tags.name])
```

Returns the value of the `entity.label` placeholder if it's not an empty string, otherwise the value of the `entity.tags.name` placeholder will be returned.
If both placeholders are empty, then an empty string is returned.
