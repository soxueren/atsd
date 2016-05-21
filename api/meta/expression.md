
# Expression Syntax

## Variables

`name` - name of the record, such as entity name or metric name
`tags.{tag-name}` - value of tag with name `tag-name`, for example, `tags.location` or `tags.table`

* All the variables are string variables.
* Tag names are case-insensitive, for example, `tags.location` and `tags.Location` are equal.
* If tag `tags.{tag-name}` is not defined, the variable is equal to an empty string.

For example expression `tags.app != ''` will find all entities that have app tag

## Operators

Comparision operators: `=`, `==`, `!=`, `LIKE`

Logical operators: `and`, `or`, `not` as well as `&&` , `||`, `!`

Collections operator: `in`, for example `tags.location in ('SVL', 'NUR')`

## Wildcards

Wildcard `*` means zero or more characters. 

Wildcard `.` means any character.

## Examples

* Return records with name that starts with `nur` and that have `app` tag defined

```ls
name like 'nur*' and tags.app != ''
```

* Return records with name equal to `nurswgvml003`

```ls
name = 'nurswgvml003'
```

* Return records with name that starts with `nur` and with tag `os` equal to 'Linux'

```ls
name like 'nur*' and tags.os = 'Linux'
```

* Return records with tag `ip` starting with `10.` and ending with `22`

```ls
tags.ip like '10.*22'
```

## Extended Functions

* Collection list(String value);
* Collection list(String value, String delimiter);
* boolean likeAll(Object message, Collection values);
* boolean likeAny(Object message, Collection values);
* String upper(Object value);
* String lower(Object value);
* Collection collection(String name);

| Function   | Description                                                                         |
|:------------|:-------------------------------------------------------------------------------------|
| list       | Splits a string by delimiter. Default delimiter is comma                            |
| likeAll    | returns true, if every element in the collection of patterns matches message        |
| likeAny    | returns true, if at least one element in the collection of patterns matches message |
| upper      | converts the argument to upper case                                                 |
| lower      | converts the argument to lower case                                                 |
| collection | returns ATSD named collection                                                       |


