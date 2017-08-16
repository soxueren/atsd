# Entity Search

## Overview

The entity search interface can find entities by name or entity tag values.

## Syntax

A keyword without a colon is considered an entity name filter, a keyword containing a colon is treated as a tag name.

```ls
name-filter [tag-name-1:tag-value-2] [tag-name-2:tag-value-2]
```

The `*` wildcard is automatically appended to the `name-filter`, thereby including entities with a name **starting** with the specified text.

If the search expression contains a tag name, such tags are displayed in the results table.

## Wildcards

* `*` matches any number of characters.
* `?` matches any one characher.

## Examples

* Find entities starting with 'nur'

```ls
nur
```

* Find entities starting with 'nur' (wildcard alternative)

```ls
nur*
```

* Find entities containing 'nur'

```ls
*nur*
```

* Find entities with the tag 'location' set to 'SVL'


```ls
location:SVL
```

* Find entities with any value for the tag 'location'


```ls
location:*
```

* Find entities starting with 'nur' **and** the tag 'location' set to 'SVL'


```ls
nur location:SVL
```
