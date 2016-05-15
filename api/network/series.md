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

```properties
command = "series= " entity 1*(" " metric) *(" " tag) [" " time] LF
entity = "e:" *VCHAR
         ; any visible character except double quote and whitespace
metric = "m:" *VCHAR "=" number
tag = "t:" VCHAR "=" [DQUOTE] *VCHAR / %x20 [DQUOTE]
time = timemillisecond / timesecond / timeiso
timemillisecond = "ms:" *DIGIT
timesecond = "s:" *DIGIT
timeiso = "d:" isodate
isodate = yyyy-MM-dd'T'HH:mm:ss.SSSZ -> RFC-3339-Appendix-A-ABNF
number = *DIGIT ["." *DIGIT] / "NaN"
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

