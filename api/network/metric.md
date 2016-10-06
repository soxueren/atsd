# metric Command

## Description

Creates or updates the specified metric including its data type, versioning, and tags.

## Syntax

```css
metric m:{metric} p:{data-type} l:{label} i:{interpolate} d:{description} f:{filter} v:{versioning} z:{timezone} t:{tag-1}={text} t:{tag-2}={text}
```

* Metric name and tag names are case-insensitive and are converted to lower case when stored. 
* Time Zone ID is case-insensitive.
* Other field values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.

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
| z         | string           | Time Zone ID, for example `America/New_York` or `EST`.<br>Refer to [Java Time Zone](timezone-list.md) table for a list of supported Time Zone IDs.<br>The timezone is applied by date-formatting functions to return local time in metric-specific timezone.<br>To reset the time zone to the default value, specify it as a double-quoted empty string `z:""`.|
| t         | string           | [Multiple] Metric tag name and value.  |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "metric" MSP metric [MSP label] [MSP description] [MSP data-type] [MSP interpolate] [MSP filter] [MSP timezone] [MSP versioning] *(MSP tag)
metric = "m:" NAME
data-type = "p:" ("short" / "integer" / "long" / "float" / "double" / "decimal")
interpolate = "i:" ("linear" / "previous")
label = "l:" VALUE
description = "d:" VALUE
filter = "f:" VALUE
timezone = "z:" (TIMEZONE / DQUOTE DQUOTE)
versioning = "v:" ("true" / "false")
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
metric m:temperature p:long v:false t:unit=Celsius
```

```ls
metric m:temperature p:long v:false l:"Temperature in Celsius" z:PST t:unit=Celsius
```
