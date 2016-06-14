# property Command

## Description

Insert property record with specified type, keys and tags for the given entity.

Entity, property type and property keys (`k:` fields) form a composite primary key of the property record in the database, whereas tags (`v:` fields) are stored as additional attributes.

When a property record is inserted into the database, it overwrites an existing record with the same composite primary key: **entity+type[+key]**.

Use reserved `$entity_tags` type to insert/update entity tags with this property command.

* Entity name, property type, key names, and tag names are case-insensitive and are converted to lower case when stored. 
* Key values and tag values are case-sensitive and are stored as submitted.
* Tag names may duplicate key names specified in the same command.
* At least one tag is required

## Syntax

```css
property e:{entity} t:{type} k:{key-1}={value} k:{key-2}={value} v:{tag-1}={text} v:{tag-2}={text} s:{seconds}
```

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| e         | yes          | Entity name. |
| t         | yes           | Property type. |
| k         | no           | Property key name and text value. Multiple. |
| v         | yes           | Property tag name and text value. At least one. |
| s         | no           | Time in UNIX seconds. | 
| ms        | no           | Time in UNIX milliseconds. | 
| d         | no           | Time in ISO format. | 

> If time fields are omitted, the record is inserted with the current server time.

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
  ; entity, type and at least one tag is required
command = "property" MSP entity type *(MSP key) 1*(MSP tag) [MSP time]
entity = "e:" NAME
type = "t:" NAME
key = "k:" NAME "=" VALUE  
tag = "v:" NAME "=" VALUE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE_INTEGER
time-second = "s:" POSITIVE_INTEGER
time-iso = "d:" ISO_DATE
```

## Examples

```ls
property e:server-001 t:disk-config k:mount_point=/ k:name=sda1 v:size_gb=192 v:fs_type=nfs
```

```ls
property e:server-001 t:operating_system v:type=Linux d:2015-03-04T12:43:20Z
```
