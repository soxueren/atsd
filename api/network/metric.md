# metric Command

## Description

Creates or updates the specified metric including its data type, versioning, and tags.

## Syntax

```css
metric m:{metric} p:{data-type} l:{label} i:{interpolate} d:{description} f:{filter} v:{versioning} t:{tag-1}={text} t:{tag-2}={text}
```

* Metric name and tag names are case-insensitive and are converted to lower case when stored. 
* Other field values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.
* At least one the following fields is required in addition to metric name: `p:`, `l:`, `d:`, `f:`, `v:`, or `t:`.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| m         | string           | **[Required]** Metric name. |
| l         | string           | Label. |
| d         | string           | Description. |
| p         | string           | Data type: short, integer, long, float, double, decimal. Default: float. |
| i         | string           | Interpolation mode: linear, previous. |
| f         | string           | Filter expression. |
| v         | boolean          | Versioning enabled/disabled. |
| t         | string           | [Multiple] Metric tag name and value.  |

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
command = "metric" MSP metric [MSP label] [MSP description] [MSP data-type] [MSP interpolate] [MSP filter] [MSP versioning] *(MSP tag)
metric = "m:" NAME
data-type = "p:" ("short" / "integer" / "long" / "float" / "double" / "decimal")
interpolate = "i:" ("linear" / "previous")
label = "l:" VALUE
description = "d:" VALUE
filter = "f:" VALUE
versioning = "v:" ("true" / "false")
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
metric m:temperature p:long v:false t:unit=Celsius
```

```ls
metric m:temperature p:long v:false l:"Temperature in Celsius" t:unit=Celsius
```
