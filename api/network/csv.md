# csv Command

## Description

Insert plain text data in CSV/TSV format into the database.

Lines are parsed and converted into commands as they're received, without buffering.

The client may keep a persistent connection. To prevent the server from timing out an idle connection, set read timeout to a desired threshold with `o` field.

## Syntax

```css
csv p:{parser} e:{entity} r:{metric_prefix} z:{timezone} t:{timeout}
... Header Row ...
... Data Rows  ...
```

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| p         | yes          | CSV Parser name from **Admin: CSV Parsers** page |
| e         | no           | Default entity name                       |
| r         | no           | Metric prefix applied to all metrics in the file                           |
| z         | no           | Timezone applied when parsing timestamps in local time, e.g. GMT<br>Java [Time Zone ID](timezone-abnf.md)  |
| o | no | Server read timeout in seconds. For example: 60 |
| t         | no           | One or multiple series tag key=value pairs. For example: t:location=SPB   |

> Tags specified in `t:` fields override `Default Tags` with the same name specified in CSV parser configuration.

### ABNF Syntax

Rules inherited from [base ABNF](base-abnf.md).

```properties
command = "csv" MSP parser [MSP entity] [MSP metric-prefix] [MSP timezone] [MSP timeout] *(MSP tag)
parser = "p:" NAME
entity = "e:" NAME
metric-prefix = "r:" NAME
timezone = "z:" TIMEZONE
timeout = "o:" POSITIVE_INTEGER
tag = "t:" NAME "=" VALUE
```

## Examples

```ls
csv p:was-csv-parser e:nurswgvml007 z:PST t:environment=prod
date,jvm.memory_used_pct,jvm.system_load
2016-05-22T00:00:00Z,46.6,1.2
2016-05-22T00:00:30Z,46.4,0.8
```
