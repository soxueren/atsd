# Command `series`

Insert one or multiple metric samples for an entity and series tags into the database. 

```css
series e:{entity} s:{unix_seconds} m:{metric}={value} m:{metric}={value} t:{key}={value} t:{key}={value}
```

## Fields

| **Field** | **Required** |
|-----------|--------------|
| e         | yes          |
| s         | no           |
| ms        | no           |
| d         | no           |
| t         | no           |
| m         | yes          |

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

```
series d:2015-08-13T10:00:00Z e:e-vers m:m-vers=13 t:$version_status=OK t:$version_source=collector:10.102.0.44
```

Versioning enables tracking of time-series value changes for the purpose of audit trail and traceable data reconciliation.

Versioning is disabled by default. It can be enabled for particular metrics by setting Versioning checkbox to selected on Metric Editor page.

To insert versioning fields use reserved series tags:

* `$version_source`
* `$version_status`

These tags will be removed by the server to populate corresponding versioning fields.

> Note that if metric is unversioned, `$version_source` and `$version_status` tags will be processed as regular tags.

[Learn more about Versioning](http://axibase.com/products/axibase-time-series-database/data-model/versioning/)
