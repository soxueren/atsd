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
