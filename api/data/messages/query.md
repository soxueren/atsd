# Messages: Query

## Description

Retrieve message records for the specified filters.

## Path

```elm
/api/v1/messages/query
```

## Method

```
POST 
```

## Request 

An array of query objects containing filtering fields. 

### Fields

| **Field** | **Required** | **Description** |
|---|---|---|
| entity    | yes (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | yes (1) | Array of entity names or entity name patterns |
| entityGroup | yes (1) | If `entityGroup` field is specified in the query, messages for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | yes (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
|type       |  no   | Message type. |
|source       |  no   | Message source. |
|tags	      | no  | An object containing `name=values` for matching message records with the same tags.         |
|startDate	  | no  | Start of the selection interval. Specified in ISO format or using endtime syntax.<br>Default value: endTime - 1 hour    |
|endDate	  | no  | End of the selection interval. Specified in ISO format or using endtime syntax. <br>Default value: current server time     |
|interval | no | Duration of the selection interval, specified as `count` and `unit`. For example: `"interval": {"count": 5, "unit": "MINUTE"}` |
|severity       |  no   | Severity [code or name](#severity).  |
|minSeverity       |  no   | Minimal [code or name](#severity) filter.  |
|timeFormat   | no  | Response time format: `iso` or `milliseconds`. Default value: `iso`|
|limit        |	no  | Maximum number of messages returned. Default value: 1000  |

* One of the following fields is required: **entity, entities, entityGroup, entityExpression**. 
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.

* One of the following combinations is required: interval, startDate, interval + startDate, interval + endDate, startDate + endDate

## Response 

### Fields

| **Field** | **Description** |
|:---|:---|
|entity | Entity name. |
|type | Message type. |
|source | Message source. |
|severity | Message [severity](#severity) code. |
|tags | An object containing name=value tags, for example `tags: {"path": "/", "name": "sda"}`. |
|message | Message text. |
|date | Message time in ISO format |

### Errors

None.

## Severity

| **Code** | **Description** |
|:---|:---|
| 0 | UNDEFINED |
| 1 | UNKNOWN |
| 2 | NORMAL |
| 3 | WARNING |
| 4 | MINOR |
| 5 | MAJOR |
| 6 | CRITICAL |
| 7 | FATAL |

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/messages/query
```
#### Payload

```json
[
    {
        "entity": "nurswgvml007",
        "type": "logger",
        "limit": 5,
        "endDate": "now",
        "interval": {
            "count": 30,
            "unit": "MINUTE"
        }
    }
]
```

#### curl

```elm
curl  https://atsd_host:8443/api/v1/messages/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
  ```
  
### Response

#### Payload

```json
[
   {
        "entity": "nurswgvml007",
        "type": "logger",
        "source": "com.axibase.tsd.service.entity.findentitybyexpressionserviceimpl",
        "severity": "NORMAL",
        "tags":
        {
            "level": "INFO",
            "thread": "applicationScheduler-5",
            "command": "com.axibase.tsd.Server"
        },
        "message": "Expression entity group 'scollector-linux' updated",
        "date": "2016-05-25T17:05:00Z"
    },
    {
        "entity": "nurswgvml007",
        "type": "logger",
        "source": "com.axibase.tsd.web.csv.csvcontroller",
        "severity": "NORMAL",
        "tags":
        {
            "level": "INFO",
            "thread": "qtp490763067-195",
            "command": "com.axibase.tsd.Server"
        },
        "message": "Start processing csv, config: nginx-status",
        "date": "2016-05-25T17:04:01Z"
    }
]
```

## Additional Examples
