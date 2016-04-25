## Command: `csv`

Initiate line-by-line streaming of a csv file.

```
csv p:{parser} e:{entity} r:{metric_prefix} z:{timezone} t:{timeout}
... csv file header ...
... csv file data rows  ...
```

> Example

```
csv p:was-csv e:nurswgvml007 z:PST
time,jvm_memory_used_pct
1423139581216,46.6
1423139581216,46.4
```


| **Field** | **Required** | **Description**                           |
|-----------|--------------|-------------------------------------------|
| p         | yes          | parser name from Admin : csv Parsers page |
| e         | no           | default entity name                       |
| r         | no           | metric prefix                             |
| z         | no           | timezone, e.g. GMT                        |
| t         | no           | one or multiple tag key=value pairs. For example: t:location=SPB   |
| o | no | server read timeout in seconds. For example: 60 |

<aside class="notice">
Tags specified with t: field are merged with default tags specified in csv parser configuration. 
Matching default tags are replaced with values specified in the command.
</aside>
