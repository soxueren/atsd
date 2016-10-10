# `series` Command

## Description

Insert timestamped numeric values for the specified entity, series tags, and one or multiple metrics. 

New entities, metrics, and tags will be created automatically.

New metrics will be initialized with `float` data type by default.

To insert metric samples with another data type, create or update metric properties using [Meta API](/api/meta/metric/create-or-replace.md) or the web interface.

## Syntax

```css
series e:{entity} m:{metric-1}={number} m:{metric-2}={number} t:{tag-1}={text} t:{tag-2}={text} s:{seconds} 
```

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| e         | string          | **[Required]** Entity name. |
| m         | string          | **[Required]** Metric name and numeric value. Multiple. |
| t         | string          | Tag name and text value. Multiple. |
| s         | integer         | Time in UNIX seconds. | 
| ms        | integer         | Time in UNIX milliseconds. | 
| d         | string          | Time in ISO format. | 

> If time fields are omitted, the values are inserted with the current server time.

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

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

## Number Representation

* The string representation of an inserted number consists of an optional sign, '+' ('\u002B') or '-' ('\u002D'), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by a fraction, optionally followed by an exponent.
* The exponent consists of the character 'e' ('\u0065') or 'E' ('\u0045') followed by an optional sign, '+' ('\u002B') or '-' ('\u002D'), followed by one or more decimal digits.
* The fraction consists of a decimal point followed by zero or more decimal digits. The string must contain at least one digit in either the integer or the fraction. 
* The number formed by the sign, the integer, and the fraction is referred to as the [**significand**](https://en.wikipedia.org/wiki/Significand).
* The **significand** value stripped from trailing zeros should be within Long.MAX_VALUE `9223372036854775807` and Long.MIN_VALUE  `-9223372036854775808` (19 digits). Otherwise the database will throw an **llegalArgumentException: BigDecimal significand overflows the long type** for decimal metrics or round the value for non-decimal metrics. For example, significand for `1.1212121212121212121212121212121212121212121` contains 44 digits and will be rounded to `1.121212121212121212` if inserted for a non-decimal metric.

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


[Versioning](http://axibase.com/products/axibase-time-series-database/data-model/versioning/) enables tracking of time-series value changes for the purpose of establishing an audit trail and traceable data reconciliation.

Versioning is disabled by default. It can be enabled for particular metrics by setting on the Versioning check box on the Metric Editor page.

To insert versioning fields use reserved tags:

* `$version_source`
* `$version_status`

```ls
series s:1425482080 e:e-vers m:m-vers=13 t:$version_status=OK t:$version_source=collector-1
```

If the metrics are versioned,  `$version_source` and `$version_status` tags will be ignored as regular series tags and instead will be converted to corresponding versioning tags.
