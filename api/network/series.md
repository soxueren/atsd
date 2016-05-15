# `series` Command

Insert timestamped metric samples for an entity and series tags into the database. 

## Syntax

```css
series e:{entity} s:{unix_seconds} m:{metric}={value} m:{metric}={value} t:{key}={value} t:{key}={value}
```

## Fields

| **Field** | **Required** | **Description** |
|---|---|---|
| e         | yes          | Entity name |
| m         | yes          | Metric name, at least one |
| t         | no           | Tag key/value |
| s         | no           | Time in UNIX seconds | 
| ms        | no           | Time in UNIX milliseconds | 
| d         | no           | Time in ISO format | 

## ABNF

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
; 1*SP - one or multiple whitespaces
command = "series" 1*SP entity 1*(1*SP metric) *(1*SP tag) [1*SP time]
entity = "e:" 1*UCHAR
metric = "m:" 1*UCHAR "=" NUMBER
; tag values containing whitespace must be double quoted
tag = "t:" 1*UCHAR "=" 1*UCHAR / DQUOTE 1*(UCHAR / SP) DQUOTE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE-INTEGER
time-second = "s:" POSITIVE-INTEGER
time-iso = "d:" ISO-DATE
```

## Examples

```css
series e:server001 s:1425482080 m:cpu_used=72.0 m:memory_used=94.5
```

```css
series e:server001 ms:1425482080000 m:cpu_used=72.0 m:memory_used=94.5
```

```css
series e:server001 d:2015-03-04T12:43:20Z m:cpu_used=72.0 m:memory_used=94.5
```

```css
series e:server001 m:disk_used_percent=20.5 m:disk_size_mb=10240 t:mount_point=/ t:disk_name=/sda1
```

## Versioning


[Versioning](http://axibase.com/products/axibase-time-series-database/data-model/versioning/) enables tracking of time-series value changes for the purpose of audit trail and traceable data reconciliation.

Versioning is disabled by default. It can be enabled for particular metrics by setting Versioning checkbox to selected on Metric Editor page.

To insert versioning fields use reserved series tags:

* `$version_source`
* `$version_status`

```css
series s:1425482080 e:e-vers m:m-vers=13 t:$version_status=OK t:$version_source=collector-1
```

These tags will be ignored as series tags and instead will be used to populate corresponding versioning fields.

If the metric is unversioned, `$version_source` and `$version_status` tags will be processed as regular series tags.

