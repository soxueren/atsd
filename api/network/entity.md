# entity Command

## Description

Creates or updates entity tags of the specified entity. If the entity doesn't exist, it will be created automatically.

## Syntax

```css
entity e:{metric} t:{tag-1}={text} t:{tag-2}={text}
```

* Entity name and tag names are case-insensitive and are converted to lower case when stored. 
* Tag values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| e         | string           | **[Required]** Entity name. |
| t         | string           | **[Required]** Entity tag name and text value. Multiple. |

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
command = "entity" MSP entity 1*(MSP tag)
entity = "e:" NAME
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
entity m:nurswgvml007 t:location=SVL t:environment=production
```
