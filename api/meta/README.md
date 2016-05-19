## Overview

The Meta API lets you query and update meta-data about metrics, entities, entity groups, rules, and tags from Axibase Time-Series Database (ATSD) server. 

The API uses standard HTTP requests, such as: `GET`, `POST`, and `PATCH`. 

All requests must be authorized using BASIC AUTHENTICATION. 

In response, the ATSD server sends an HTTP status code (such as a 200-type status for success or 400-type status for failure) that reflects the result of each request. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses.

## Categories

* [Metric](metric)
    - [list](metric/list.md)
    - [delete](metric/delete.md)
    - [get](metric/get.md)
    - [update](metric/update.md)
    - [create or replace](metric/create-or-replace.md)
    - [entities and tags](metric/entities-and-tags.md)
* [Entity](entity)
    - [list](entity/list.md)
    - [delete](entity/delete.md)
    - [get](entity/get.md)
    - [update](entity/update.md)
    - [create or replace](entity/create-or-replace.md)
    - [entity groups](entity/entity-groups.md)
    - [metrics](entity/metrics.md)
    - [property types](entity/property-types.md)
* [Entity group](entity-group)
    - [list](entity-group/list.md)
    - [delete](entity-group/delete.md)
    - [get](entity-group/get.md)
    - [update](entity-group/update.md)
    - [create or replace](entity-group/create-or-replace.md)
    - [add entities](entity-group/add-entities.md)
    - [delete entities](entity-group/delete-entities.md)
    - [get entities](entity-group/get-entities)
    - [set (replace) entities](entity-group/set-replace-entities)




### Authentication

* User authentication is required.
* Authentication method: `HTTP BASIC`.
* Client may use session cookies to execute multiple requests without repeating authentication.
* Cross-domain requests are allowed. The server includes the following headers in each response:

`Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization`

`Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE`
    
`Access-Control-Allow-Origin: *`

### Compression

* Clients may send compressed data by specifying Content-Encoding: gzip

### Expression Syntax

**Syntax enhancements**

> Examples

```
name like 'nur*' and tags.app != ''
```

```
name = 'nurswgvml003'
```

```
name like 'nur*' and tags.os = 'Linux'
```

```
tags.ip like '10*01'
```

You can use both `=` and `==` as equality operator

You can use `and`, `or`, `not` logical operators as well as `&&` , `||`, `!`

You can use `in` operator for string collections, for example name in `('test1', 'test2')`

You can use `like` operator instead of regular expression: `name like nur*`. Placeholders `*` and `%` mean zero or more characters. Placeholder `.` means any character.

**Additional Functions**

* Collection list(String value);
* Collection list(String value, String delimiter);
* boolean likeAll(Object message, Collection values);
* boolean likeAny(Object message, Collection values);
* String upper(Object value);
* String lower(Object value);
* Collection collection(String name);

| Function   | Description                                                                         |
|------------|-------------------------------------------------------------------------------------|
| list       | Splits a string by delimiter. Default delimiter is comma                            |
| likeAll    | returns true, if every element in the collection of patterns matches message        |
| likeAny    | returns true, if at least one element in the collection of patterns matches message |
| upper      | converts the argument to upper case                                                 |
| lower      | converts the argument to lower case                                                 |
| collection | returns ATSD named collection                                                       |

**Variables**

You can access Entity/Metric tags by name, and access Entity/Metic name using special variable `name`

All the variables are string variables.

if entity or metric does not have some tag, expression engine treats this tag variable as an empty string.

For example expression `tags.app != ''` will find all entities that have app tag

