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
; https://tools.ietf.org/html/rfc5234
; 1*SP - one or multiple whitespaces
command = "series" 1*SP entity 1*(1*SP metric) *(1*SP tag) [1*SP time] LF
entity = "e:" 1*NBCHAR
metric = "m:" 1*NBCHAR "=" number
; tag values containing whitespace must be double quoted
tag = "t:" 1*NBCHAR "=" 1*NBCHAR / DQUOTE 1*(NBCHAR / SP) DQUOTE
time = time-millisecond / time-second / time-iso
; %x31-39 is non-zero-digit 1 to 9
time-millisecond = "ms:" positive-number
time-second = "s:" positive-number
time-iso = "d:" iso-date
; RFC-3339-Appendix-A-ABNF
iso-date = yyyy-MM-dd'T'HH:mm:ss.SSSZ
number = fractional-number / real-number / "NaN"
; any visible character except double quote %x21 and whitespace %x20
NBCHAR = %x21 / %x23-7E / UTF8-NON-ASCII 
; http://tools.ietf.org/html/rfc6531#section-3.3
UTF8-NON-ASCII  = %x80-FF / ; Latin-1 Supplement
                  %x100-17F / ; Latin Extended-A
                  %x370-3FF / ; Greek and Coptic
                  %x400-4FF / ; Cyrillic
                  %x500-52F / ; Cyrillic Supplement
                  %x4E00-9FFF ; CJK Unified Ideographs
fractional-number = positive-number ["." 1*decimal-digit]                  
positive-number = non-zero-digit *decimal-digit
decimal-digit   = %x30-39  ; "0" to "9"
non-zero-digit  = %x31-39  ; "1" to "9"  
real-number = mantissa exponent
mantissa   = (positive-number [ "." *decimal-digit ]) / ( "0." *("0") positive-number )
exponent   = "E" ( "0" / ([ "-" ] positive-number))
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

