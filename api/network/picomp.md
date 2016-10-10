# pi-csv Command

## Description

Upload data retrieved from the `picomp` table in PI server in CSV format.

The order of columns is pre-defined and corresponds to `SELECT * FROM piarchive.picomp` results.

Data is stored as a text message if `svalue` column is not empty/null, otherwise it is stored as numeric series.

## Syntax

```css
picomp z:{timezone} e:{entity} i:{ignore-defaults}
... Data Rows  ...
```

### Column Order

1. tag
2. time
3. value
4. svalue
5. status
6. flags

The above order corresponds to the sequence of columns in the `SELECT * FROM piarchive.picomp` query.

```sql
SELECT TOP 5 * FROM piarchive.picomp
```

```ls
| tag      | time                | value | svalue | status | flags |
|----------|---------------------|-------|--------|--------|-------|
| SINUSOID | 2016-08-24 17:43:44 | 0.92  | null   | 0      | SA    |
```

Cell values containing comma (`tag`, `svalue`), must be properly escaped.

The `flags` column is parsed by ATSD into separate tags: `questionable` (Q), `substituted` (S), `annotated` (A) containing boolean values.

> Note that the `picomp` table doesn't provide access to annotation text compared to the `picomp2` table.

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| e         | string       | [**Required**] Default entity name. |
| i         | boolean      | Ignore default values: status `0`, `empty` flags. Default value: `true`. |
| z         | string       | Timezone applied when parsing dates specified in local time, for example GMT.<br>Java [Time Zone ID](timezone-abnf.md).<br>Default timezone is UTC. Date format is `yyyy-MM-dd HH:mm:ss`.|
| t         | string       | One or multiple series or message tag key=value pairs, for example: `t:location=SVL` |

### ABNF Syntax

Rules inherited from [Base ABNF](base-abnf.md).

```properties
command = "picomp" MSP entity [MSP timezone] [MSP ignore-defaults] *(MSP tag)
entity = "e:" NAME
ignore-defaults = "i:" ("true" / "false")
timezone = "z:" TIMEZONE
tag = "t:" NAME "=" VALUE
```

## Example

```ls
picomp e:nurswgvml007 t:environment=prod
SINUSOID,2016-10-09 17:43:44,1,0.92,0,false,false,false,
SINUSOID,2016-10-09 17:43:58,1,0.97,0,false,false,false,
```
