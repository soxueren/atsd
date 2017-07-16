# Metric Search

## Overview

The metric search interface allows finding metrics by name as well as by specific metric tag values.

## Wildcards

`*` matches any number of characters.
`.` matches one characher.

`*` wildcard is automatically appended to search queries for convenience of the most common use case.

## Syntax

Keyword without colon is considered metric name expression, whereas keywords containing colon are treated as tag conditions.

```ls
name-filter [tag-name-1:tag-value-2] [tag-name-2:tag-value-2]
```

If the search expression contains tag conditions such tags are displayed in the results table.

## Examples

* Find metrics starting with text 'cpu'

```ls
cpu
```

* Find metrics starting with text 'cpu' (wildcard alternative)

```ls
cpu*
```

* Find metrics containing text 'cpu'

```ls
*cpu*
```

* Find metrics with tag 'hello' set to 'World'


```ls
hello:World
```

* Find metrics with any value for tag 'hello'


```ls
hello:*
```

* Find metrics starting with text 'cpu' and tag 'unit' set to 'percent'


```ls
cpu hello:* unit:percent
```