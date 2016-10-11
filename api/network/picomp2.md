# picomp2 Command

## Description

Upload archived data retrieved from the `picomp2` table using CSV format with a pre-defined column order.

The uploaded data is stored as numeric series or text message based on the PI Point's data type. 

PI tags with `string`, `digital`, and `timestamp` types are stored as messages, all other types are stored as series.

## Syntax

```css
picomp2 z:{timezone} e:{entity} i:{ignore-defaults} t:{tag-name}={tag-value}
... pointtypex, picomp2 column values ...
```

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| e         | string       | [**Required**] Default entity name. |
| i         | boolean      | Ignore default values: `status` = `0`, `status_test` = `GOOD`, flags = `false`, `annotations` = `null` or empty. <br>Default value = `true` (ignore defaults).|
| z         | string       | [Time Zone ID](timezone-abnf.md) applied when parsing dates, for example EST.<br>Default time zone = `UTC`. Default date format is `yyyy-MM-dd HH:mm:ss`.|
| t         | string       | One or multiple series or message tag key=value pairs, for example: `t:location=SVL` |

### Columns

1. pointtypex
2. tag
3. time
4. _index
5. value
6. status
7. status_text
8. questionable
9. substituted
10. annotated
11. annotations

### Data Types

The following data types are supported for the value column.

* String
* Digital
* Int16
* Int32
* Float16
* Float32
* Float64
* Timestamp

### PI Query

The order of [columns](#columns) in the uploaded content corresponds to the result set produced by the following query.

```
SELECT TOP 10 pointtypex,
        picomp2.tag,
        time,
        _index,
        CASE pointtypex 
		  WHEN 'digital' THEN CAST(DIGSTRING(CAST(value as Int32)) as VARIANT) 
		  ELSE value 
		END AS value,
		status,
        DIGSTRING(status) AS status_text,
        questionable, substituted, annotated, annotations  
FROM piarchive..picomp2 picomp2 
  JOIN pipoint..pipoint pipoint on picomp2.tag = pipoint.tag
WHERE picomp2.tag = 'sinusoid'
  ORDER BY time
```

```ls
| pointtypex | tag      | time                | _index | value | status | status_text | questionable | substituted | annotated | annotations | 
|------------|----------|---------------------|--------|-------|--------|-------------|--------------|-------------|-----------|-------------| 
| float64    | sinusoid | 2016-08-24 17:43:44 | 1      | 0.92  | 0      | Good        | false        | false       | false     | null        | 
```

* `_index` column which represents a sample order for a given timestamp is ignored if `<= 1`. If `_index` exceeds `1` it is added to the timestamp as `(_index - 1)` milliseconds.
* `tag`, `value`, and `annotations` column value must be properly escaped if the value contains comma, double quote or line break.
* Numbers must be formatted without the grouping separator using dot as the decimal separator.

## PI Tag to ATSD Command Mappings

### `series` Command - all tags except `string`, `digital`, `timestamp`

| **Command Field** | **Input Field** |
|:---|:---|
| Metric Name | `tag` column. Whitespace characters contained in the tag name will be replaced with underscore. |
| Entity Name | `e` entity field.  |
| Time        | `time` column.     |
| Value       | `value` column. Value is set to `NaN` (Not a Number) if cell text is empty or `null`. |
| Series Tags | `t` fields.<br>`status` and `status_text` columns if not default.<br>`questionable`, `substituted`, `annotated` fields if not `false`.<br>`annotations` column if not null/empty. |

### `message` Command - `string`, `digital`, `timestamp` tags

| **Command Field** | **Input Field** |
|:---|:---|
| Message Type   | `tag` column. Whitespace characters contained in the tag name will be replaced with underscore. |
| Entity Name    | `e` entity field.  |
| Time           | `time` column.        |
| MEssage        | `value` column. Value is set to `NaN` (Not a Number) if cell text is empty or `null`. |
| Message Source | `t:source` field, if specified. |
| Message Tags   | `t` fields.<br>`status` and `status_text` columns if not default.<br>`questionable`, `substituted`, `annotated` fields if not `false`.<br>`annotations` column if not null/empty. |

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
Float64,sinusoid,2016-10-09 17:43:44,1,0.92,0,false,false,false,
Float64,sinusoid,2016-10-09 17:43:58,1,0.97,0,false,false,false,
```

## Mapping Example

```ls
| pointtypex | tag                          | time                | _index | value    | raw_value | status_string | status | questionable | substituted | annotated | annotations | 
|------------|------------------------------|---------------------|--------|----------|-----------|---------------|--------|--------------|-------------|-----------|-------------| 
| Float32    | Memory_Avail MBytes          | 2016-09-20 12:57:49 | 1      | null     | null      | Pt Created    | -253   | false        | false       | false     | null        | 
|   -> series e:default m:Memory_Avail_MBytes=NaN status_text="Pt Created" status=-253 d:2016-09-20T12:57:49Z                                                                     |
| Float32    | Memory_Avail MBytes          | 2016-10-11 15:38:00 | 1      | 6139.0   | 6139.0    | Good          | 0      | false        | false       | false     | null        | 
|   -> series e:default m:Memory_Avail_MBytes=6139.0 d:2016-10-11T15:38:00Z                                                                                                       |
| Float32    | Memory_Avail MBytes          | 2016-10-11 15:38:00 | 2      | 6139.1   | 6139.1    | Good          | 0      | false        | false       | false     | null        | 
|   -> series e:default m:Memory_Avail_MBytes=6139.1 d:2016-10-11T15:38:00.001Z                                                                                                   |
| Float32    | Memory_Avail MBytes          | 2016-10-11 15:38:00 | 3      | 6139.2   | 6139.2    | Good          | 0      | false        | false       | false     | null        | 
|   -> series e:default m:Memory_Avail_MBytes=6139.2 d:2016-10-11T15:38:00.002Z                                                                                                   |
| Float32    | Memory_Avail MBytes          | 2016-10-11 15:38:01 | 1      | 6141.0   | 6141.0    | Good          | 0      | false        | false       | false     | null        | 
|   -> series e:default m:Memory_Avail_MBytes=6141.0 d:2016-10-11T15:38:01Z                                                                                                       |
| Digital    | BA:ACTIVE.1                  | 2016-08-24 15:02:55 | 1      | null     | null      | Pt Created    | -253   | false        | false       | false     | null        | 
|   -> message e:default t:type="BA:ACTIVE.1" d:2016-08-24T15:02:55Z m:"" status_text="Pt Created" status=-253                                                                    |
| Digital    | BA:ACTIVE.1                  | 2016-08-24 15:03:17 | 1      | Inactive | -65536    | Good          | 0      | false        | false       | false     | null        | 
|   -> message e:default t:type="BA:ACTIVE.1" d:2016-08-24T15:03:17Z m:"Inactive"                                                                                                 |
| Digital    | BA:ACTIVE.1                  | 2016-08-24 15:04:17 | 1      | Active   | -65537    | Good          | 0      | false        | false       | false     | null        | 
|   -> message e:default t:type="BA:ACTIVE.1" d:2016-08-24T15:04:17Z m:"Active"                                                                                                   |
| Digital    | BA:ACTIVE.1                  | 2016-08-24 16:15:17 | 1      | Inactive | -65536    | Good          | 0      | false        | false       | false     | null        | 
|   -> message e:default t:type="BA:ACTIVE.1" d:2016-08-24T16:15:17Z m:"Inactive"                                                                                                 |
| Int32      | CDEP158                      | 2016-08-24 15:01:09 | 1      | null     | null      | Shutdown      | -254   | false        | false       | false     | null        | 
|   -> series m:CDEP158=NaN status_text="Shutdown" status=-254 d:2016-08-24T15:01:09Z                                                                                             |
| Int32      | CDEP158                      | 2016-08-24 15:03:17 | 1      | 0        | 0         | Good          | 0      | false        | false       | false     | null        | 
|   -> series m:CDEP158=0 d:2016-08-24T15:03:17Z                                                                                                                                  |
| Int32      | CDEP158                      | 2016-08-24 15:43:17 | 1      | 12       | 12        | Good          | 0      | false        | false       | false     | null        | 
|   -> series m:CDEP158=12 d:2016-08-24T15:43:17Z                                                                                                                                 |
| Int32      | CDEP158                      | 2016-08-24 16:21:20 | 1      | null     | null      | Shutdown      | -254   | false        | false       | false     | null        | 
|   -> series m:CDEP158=NaN status_text="Shutdown" status=-254 d:2016-08-24T16:21:20Z                                                                                             |
| Int32      | CDEP158                      | 2016-08-24 16:23:30 | 1      | 1        | 1         | Good          | 0      | false        | false       | false     | null        | 
|   -> series m:CDEP158=1 d:2016-08-24T16:23:30Z                                                                                                                                  |
| Int32      | CDEP158                      | 2016-08-24 22:28:30 | 1      | 43       | 43        | Good          | 0      | false        | false       | false     | null        | 
|   -> series m:CDEP158=43 d:2016-08-24T22:28:30Z                                                                                                                                 |
|------------|------------------------------|---------------------|--------|----------|-----------|---------------|--------|--------------|-------------|-----------|-------------| 
```

