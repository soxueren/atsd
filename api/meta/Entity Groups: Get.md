## Entity Groups: Get

### Request Parameters

```
GET /api/v1/entity-groups
```

> Request

```
http://atsd_server.com:8088/api/v1/entity-groups
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for entity group name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity group tags to be included in the response|
|limit|no|Limit response to first N entity groups, ordered by name.|

### Response Fields

> Response

```json
[
{"name":"HP Servers"},
{"name":"Linux"},
{"name":"MQ black list"},
{"name":"MQ white list"},
{"name":"environment-ProD","expression":"environment = 'ProD'"}
]
```

| **Name**   | **Description**                                   |
|------------|---------------------------------------------------|
| name       | Entity group name (unique)                        |
| expression | Entity group expression                           |
| tags       | Entity group tags, as requested by tags parameter |

### Sample Request

> Request

```
http://atsd_server.com:8088/api/v1/entity-groups?tags=os_level&limit=2&expression=name%20like%20%27nmon*%27
```

> Expression

```
    name like 'nmon*'
```

> Response

```json
[{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3"
    }
},
{
    "name": "nmon-linux"
}]
```
