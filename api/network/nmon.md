## Command: `nmon`

Initiate line-by-line streaming of an nmon file. The transmission can be resumed without full header in case of disconnect.

```
nmon p:{parser} e:{entity} f:{file_name} z:{timezone} t:{timeout}
... nmon file header ...
... nmon snapshot ...
```

> Example

```
nmon p:default e:nurswgvml007 f:nurswgvml007_141014_2000.nmon z:PST
```

| **Field** | **Required** | **Description**                            |
|---|---|---|
| p         | yes          | parser name from Admin : nmon Parsers page |
| e         | yes          | entity                                     |
| f         | yes          | name of the nmon file                      |
| z         | no           | Timezone applied to snapshots, e.g. GMT<br>Java [Time Zone ID](timezone-abnf.md)  |
| o         | no           | Read timeout in seconds. <br>Set to 2x snapshot interval to prevent the server from terminating an idle connection.|

`f` nmon file name is used to re-read file header from its copy on the server in case of disconnect.

### ABNF Syntax

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
  ; MSP - one or multiple spaces
  ; entity or at least one tag is required
command = "nmon" MSP parser MSP entity MSP filename [MSP timezone] [MSP timeout]
  ; NAME consists of visible characters. 
  ; double-quote must be escaped with backslash.
entity = "e:" entity-name
  ; entity name, typically hostname where nmon is running
entity-name = NAME
parser = "p:" (ALPHA / "_" )
  ; nmon file name should start with entity name, typically hostname where nmon is running
filename = "f:" entity-name NAME ".nmon"
  ; defined in timezone-abnf.md
timezone = "z:" TIMEZONE
timeout = "o:" POSITIVE_INTEGER
```

## Examples

```ls
nmon e:server-001 p:default f:server-001.nmon
```

```ls
nmon e:server-001 p:default f:server-001.nmon z:UTC
```
