# entity Command

## Description

Creates or updates fields and tags of the specified entity. 

If the entity doesn't exist, it will be created automatically.

## Syntax

```css
entity e:{entity} i:{interpolate} z:{timezone} t:{tag-1}={text} t:{tag-2}={text}
```

* Entity name and tag names are case-insensitive and are converted to lower case when stored.
* Tag values are case-sensitive and are stored as submitted.
* Other fields are case-insensitive.
* Tag values cannot be empty.
* Entity tags and fields that are not specified are left unchanged. 
* To remove an entity tag or to reset a Time Zone ID or Interpolate fields, set the value to a double-quoted empty string.

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| e         | string           | **[Required]** Entity name. |
| i         | string           | Interpolation mode: linear, previous. Default is none. |
| z         | string           | Time Zone ID, for example `America/New_York` or `EST`.<br>Refer to [Java Time Zone](timezone-list.md) table for a list of supported Time Zone IDs.<br>The timezone is applied by date-formatting functions to return local time in entity-specific timezone.<br>To reset the time zone to the default value, specify it as a double-quoted empty string `z:""`. |
| t         | string           | Entity tag name and text value. Multiple. |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "entity" MSP entity [MSP interpolate] [MSP timezone] 1*(MSP tag)
entity = "e:" NAME
interpolate = "i:" ("linear" / "previous" / DQUOTE DQUOTE)
timezone = "z:" (TIMEZONE / DQUOTE DQUOTE)
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
entity e:nurswgvml007 z:PST i:previous t:location=SVL t:environment=production
```

```ls
entity e:nurswgvml007 t:location=SVL t:environment=""
```
