# entity Command

## Description

Creates or updates entity fields and tags of the specified entity. If the entity doesn't exist, it will be created automatically.

## Syntax

```css
entity e:{entity} z:{timezone} t:{tag-1}={text} t:{tag-2}={text}
```

* Entity name and tag names are case-insensitive and are converted to lower case when stored. 
* Timezone ID value is case-insensitive.
* Tag values are case-sensitive and are stored as submitted.
* Tag values cannot be empty.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| e         | string           | **[Required]** Entity name. |
| z         | string           | Time Zone ID, for example `America/New_York` or `EST`.<br>Refer to [Java Time Zone](timezone-list.md) table for a list of supported Time Zone IDs.<br>The timezone is applied by date-formatting functions to return local time in entity-specific timezone.|
| t         | string           | Entity tag name and text value. Multiple. |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "entity" MSP entity [MSP timezone] 1*(MSP tag)
entity = "e:" NAME
timezone = "z:" TIMEZONE
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
entity e:nurswgvml007 z:PST t:location=SVL t:environment=production
```
