# metric Command

## Description

Creates or updates the specified metric including its data type, versioning, and tags.

## Syntax

```css
metric m:{metric} p:{data-type} v:{versioned} t:{tag-1}={text} t:{tag-2}={text}
```

* Metric name and tag names are case-insensitive and are converted to lower case when stored. 
* Tag values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.
* At least one the following fields is required in addition to metric name: `p:`, `v:`, or `t:`.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| m         | string           | **[Required]** Metric name. |
| p         | string           | Data type. |
| v         | boolean          | Versioning. |
| t         | string           | Metric tag name and text value. Multiple. |

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
command = "metric" MSP metric [MSP data-type] [MSP versioned] 1*(MSP tag)
metric = "m:" NAME
data-type = "p:" ("short" / "integer" / "long" / "float" / "double" / "decimal")
versioned = "v:" ("true" / "false")
tag = "v:" NAME "=" VALUE
```

## Examples

```ls
metric m:temperature p:long v:versioning=false t:unit=Celsius
```
