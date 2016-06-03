# series Command

## Description

Insert timestamped metric samples for an entity and series tags into the database. 

New entities and metrics will be automatically created provided they meet naming requirements.

New metrics will be created with `float` data type by default.<br>To insert metric samples with another datatype, create or update metric properties using [Meta API](/api/meta/metric/create-or-replace.md) or the web interface.

## Syntax

```css
series e:{entity} m:{metric-1}={number} m:{metric-2}={number} t:{tag-1}={text} t:{tag-2}={text} s:{seconds} 
```

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| e         | yes          | Entity name. |
| m         | yes          | Metric name and numeric value. Multiple. |
| t         | no           | Tag name and text value. Multiple. |
| s         | no           | Time in UNIX seconds. | 
| ms        | no           | Time in UNIX milliseconds. | 
| d         | no           | Time in ISO format. | 

> If time fields are omitted, the values are inserted with the current server time.

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
  ; entity and at least one metric is required
command = "series" MSP entity 1*(MSP metric) *(MSP tag) [MSP time]
entity = "e:" NAME
metric = "m:" NAME "=" NUMBER
tag = "t:" NAME "=" VALUE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE_INTEGER
time-second = "s:" POSITIVE_INTEGER
time-iso = "d:" ISO_DATE
```

## Examples

```ls
series e:server001 m:cpu_used=72.0 m:memory_used=94.5 s:1425482080
```

```ls
series e:server001 m:cpu_used=72.0 m:memory_used=94.5 ms:1425482080000
```

```ls
series e:server001 m:cpu_used=72.0 m:memory_used=94.5 d:2015-03-04T12:43:20Z
```

```ls
series e:server001 m:disk_used_percent=20.5 m:disk_size_mb=10240 t:mount_point=/ t:disk_name=/sda1
```

## Versioning


[Versioning](http://axibase.com/products/axibase-time-series-database/data-model/versioning/) enables tracking of time-series value changes for the purpose of audit trail and traceable data reconciliation.

Versioning is disabled by default. It can be enabled for particular metrics by setting Versioning checkbox to selected on Metric Editor page.

To insert versioning fields use reserved tags:

* `$version_source`
* `$version_status`

```ls
series s:1425482080 e:e-vers m:m-vers=13 t:$version_status=OK t:$version_source=collector-1
```

If the metrics is versioned,  `$version_source` and `$version_status` tags will be ignored as regular series tags and instead will be converted to corresponding versioning tags.

