# pi Command

## Description

Stores PI tag values as a [series](series.md) or [message](message.md) command.

## Syntax

```css
pi p:{pitag-name} {time} nv:{numeric-value} sv:{string-value} i:{index} a:{annotation} st:{status} fa:{annotated} fs:{substituted} fq:{questionable} t:{tag-1}={text} t:{tag-2}={text}
```

## Command Type

The data is stored as series unless the `sv` fields contains a non-empty string in which case the data is stored as message.

## Field Mappings

### `series` Command

| **Command Field** | **Input Field** |
|:---|:---|
| Metric Name | `p` (PI Tag Name). |
| Entity Name | `e` (Entity Name) if specified, otherwise set to literal `pi`. |
| Value | `nv` (Numeric value). |
| Series Tags | `t`, as well as `i`, `st`, `fa`, `fs`, `fq` if specified and non-default. |

### `message` Command

| **Command Field** | **Input Field** |
|:---|:---|
| Message Type | `p` (PI Tag Name). |
| Entity Name | `e` (Entity Name) if specified, otherwise set to literal `pi`. |
| Value | `sv` (String value). |
| Message Source | `t:source`, if specified. |
| Series Tags | `t`, as well as `i`, `st`, `fa`, `fs`, `fq` if non-default. |

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| p         | string          | **[Required]** PI tag name. |
| d         | string          | **[Required]** Time in ISO format. One of `d`, `s`, `ms` fields is required. | 
| s         | integer         | **[Required]** Time in UNIX seconds. | 
| ms        | integer         | **[Required]** Time in UNIX milliseconds. | 
| nv        | string          | **[Required]** Numeric value. Either `nv` or `sv` field is required. |
| sv        | string          | **[Required]** String value. |
| i         | integer         | Index. Ignored if less than 1 or empty. |
| ns        | integer         | Numeric status. Ignored if 0. |
| ss        | string          | String status. Ignored if `GOOD` or empty.|
| fa        | boolean         | Annotated flag. Ignored if `false`. |
| fs        | boolean         | Substituted flag. Ignored if `false`. |
| fq        | boolean         | Questionable flag. Ignored if `false`. |
| a         | string          | Annotation. |
| e         | string          | Entity name. |
| t         | string          | Named tag (series tag or metric tag). Multiple. |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "pi" MSP pitag MSP time MSP value [MSP index] [MSP status] [MSP annotated] [MSP substituted] [MSP questionable] [MSP annotation] [MSP entity] *(MSP tag) 
pitag = "p:" NAME
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE_INTEGER
time-second = "s:" POSITIVE_INTEGER
time-iso = "d:" ISO_DATE
value = nvalue / svalue
nvalue = "nv:" NUMBER
svalue = "sv:" VALUE
index  =  "i:" ("0" / POSITIVE_INTEGER)
status = nstatus / sstatus
nstatus = "ns:"  ("0" / POSITIVE_INTEGER)
sstatus = "ss:" VALUE
annotated    = "fa:" ("true" / "false")
substituted  = "fs:" ("true" / "false")
questionable = "fq:" ("true" / "false")
annotation =  "a:" VALUE
entity = "e:" NAME
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
pi d:2016-10-07T12:15:00Z p:SINUSOID nv:0.934 i:0 ss:GOOD fa:false fs:false fq:true

-> series  d:2016-10-07T12:15:00Z e:pi m:sinusoid=0.934 t:questionable=true
```

```ls
pi d:2016-10-07T12:15:00Z p:WIN-ERROR sv:"Error 6 raised" i:0 ss:GOOD fa:false fs:false fq:false t:location=SVL e:nurswgvml007

-> message d:2016-10-07T12:15:00Z e:nurswgvml007 t:type=WIN-ERROR t:location=SVL m:"Error 6 raised"
```



