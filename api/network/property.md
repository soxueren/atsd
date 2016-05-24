# property Command

## Description

Insert property record with specified type, keys and tags for the given entity.

Entity, property type and property keys form a composite primary key of the property record in the database, whereas tags (`v:` fields) are stored as additional attributes.

When a new property is inserted into the database, it overwrites an existing property with the same composite primary key (entity+type+[keys]).

Tag names may duplicate key names specified in the same command.

## Syntax

```css
property e:{entity} t:{type} k:{key-1}={value} k:{key-2}={value} v:{tag-1}={text} v:{tag-2}={text} s:{seconds}
```

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| e         | yes          | Entity name. |
| t         | no           | Property type. |
| k         | no           | Property key name and text value. Multiple. |
| v         | yes           | Property tag name and text value. At least one. |
| s         | no           | Time in UNIX seconds. | 
| ms        | no           | Time in UNIX milliseconds. | 
| d         | no           | Time in ISO format. | 

> If time fields are omitted, the record is inserted with the current server time.

### ABNF Syntax

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
  ; MSP - one or multiple spaces
  ; entity and at least one tag is required
command = "property" MSP entity type *(MSP key) 1*(MSP tag) [MSP time]
  ; NAME consists of printable characters. 
  ; double-quote must be escaped with backslash.
entity = "e:" NAME
  type = "t:" NAME
  ; TEXTVALUE consists of printable characters and space. 
  ; double-quote must be escaped with backslash. 
  ; tag values containing space must me quoted with double-quote.  
   key = "k:" NAME "=" TEXTVALUE  
   tag = "v:" NAME "=" TEXTVALUE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE-INTEGER
time-second = "s:" POSITIVE-INTEGER
time-iso = "d:" ISO-DATE
```

## Examples

```ls
property e:server-001 t:disk-config k:mount_point=/ k:name=sda1 v:size_gb=192 v:fs_type=nfs
```

```ls
property e:server-001 t:operating_system v:type=Linux d:2015-03-04T12:43:20Z
```
