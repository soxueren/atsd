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
| z         | no           | timezone, e.g. GMT                         |
| t         | no           | idle timeout in seconds                    |

`f` nmon file name is used to re-read file header from its copy on the server in case of disconnect.

### ABNF Syntax

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
  ; MSP - one or multiple spaces
  ; entity or at least one tag is required
command = "nmon" MSP parser MSP entity MSP filename [MSP timezone] [MSP idle]
  ; NAME consists of visible characters. 
  ; double-quote must be escaped with backslash.
entity = "e:" NAME
  ; TEXTVALUE consists of visible characters and space. 
  ; double-quote must be escaped with backslash. 
  ; tag values containing space must me quoted with double-quote.  
parser = "p:" (ALPHA / "_" )
filename = "f:" NAME ".nmon"
timezone = "z:" 3ALPHA
idle = "t:" 1*DIGIT
```

## Examples

```ls
nmon e:server-001 p:default f:server-001.nmon
```

```ls
nmon e:server-001 p:default f:server-001.nmon d:UTC
```
