# metric Command

## Description

Creates or updates the specified metric including its data type, versioning, and tags.

## Syntax

```css
metric m:{metric} p:{data-type} l:{label} d:{description} f:{filter} v:{versioning} t:{tag-1}={text} t:{tag-2}={text}
```

* Metric name and tag names are case-insensitive and are converted to lower case when stored. 
* Other field values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.
* At least one the following fields is required in addition to metric name: `p:`, `l:`, `d:`, `f:`, `v:`, or `t:`.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| m         | string           | **[Required]** Metric name. |
| p         | string           | Data type. |
| l         | string           | Label. |
| d         | string           | Description. |
| f         | string           | Filter expression. |
| v         | boolean          | Versioning enabled/disabled. |
| t         | string           | Metric tag name and text value. Multiple. |

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
command = "metric" MSP metric [MSP data-type] [MSP label] [MSP description] [MSP filter] [MSP versioning] *(MSP tag)
metric = "m:" NAME
data-type = "p:" ("short" / "integer" / "long" / "float" / "double" / "decimal")
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
