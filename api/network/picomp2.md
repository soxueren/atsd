# pi-csv Command

## Description

Upload data retrieved from the `picomp2` table in PI server using the pre-defined column order in CSV format.

Data is stored as numeric series or text messages based on the `value` column data type.

If `value` can be parsed into a number, data is stored as series.

## Syntax

```css
picomp2 z:{timezone} e:{entity} i:{ignore-defaults}
... Data Rows  ...
```

### Column Order

1. tag
2. time
3. _index
4. value
5. status
6. questionable
7. substituted
8. annotated
9. annotations

The above header corresponds to the order of columns in the `SELECT * FROM piarchive.picomp2` query.

```sql
SELECT TOP 5 * FROM piarchive.picomp2
```

```ls
| tag      | time                | _index | value | status | questionable | substituted | annotated | annotations | 
|----------|---------------------|--------|-------|--------|--------------|-------------|-----------|-------------| 
| SINUSOID | 2016-08-24 17:43:44 | 1      | 0.92  | 0      | false        | false       | false     | null        | 
```

Cell values containing comma (`tag`, `value`, `annotations`), must be properly escaped.

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| e         | string       | [**Required**] Default entity name. |
| i         | boolean      | Ignore default values: index `1`, status `0`, `false` flags, `null` or empty string for other fields. Default value: `true`. |
| z         | string       | Timezone applied when parsing dates specified in local time, for example GMT.<br>Java [Time Zone ID](timezone-abnf.md).<br>Default timezone is UTC. Date format is `yyyy-MM-dd HH:mm:ss`.|
| t         | string       | One or multiple series or message tag key=value pairs, for example: `t:location=SVL` |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "picomp2" MSP entity [MSP timezone] [MSP ignore-defaults] *(MSP tag)
entity = "e:" NAME
ignore-defaults = "i:" ("true" / "false")
timezone = "z:" TIMEZONE
tag = "t:" NAME "=" VALUE
```

## Example

```ls
picomp2 e:nurswgvml007 t:environment=prod
SINUSOID,2016-10-09 17:43:44,1,0.92,0,false,false,false,
SINUSOID,2016-10-09 17:43:58,1,0.97,0,false,false,false,
```
