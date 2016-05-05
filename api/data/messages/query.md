## Messages: Query

### Method

```
POST /api/v1/messages
```
### Basic Request Example
> Request

```json
{
    "queries": [
        {
            "entity": "nurswgvml007",
            "timeFormat": "iso",
            "type": "security",
            "limit": 5,
            "severity": "UNDEFINED",
            "endDate": "2015-09-17T10:00:00Z",
            "interval": {
                "count": 15,
                "unit": "MINUTE"
            },
            "tags": {
                "path": "/var/log/secure"
            }
        }
    ]
}
```

### Request Fields

| **Field** | **Required** | **Description** |
|---|---|---|
| entity    | yes (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | yes (1) | Array of entity names or entity name patterns |
| entityGroup | yes (1) | If `entityGroup` field is specified in the query, messages for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | yes (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
|startTime	  | no*  | start of the selection interval. Default value: endTime - 1 hour                     |
|endTime	  | no*  | end of the selection interval. Default value: current server time                    |
|startDate	  | no*  | start of the selection interval. Specified in ISO format or using endtime syntax.    |
|endDate	  | no*  | end of the selection interval. Specified in ISO format or using endtime syntax.      |
|interval | no* | duration of the selection interval, specified as `count` and `unit`. For example: `"interval": {"count": 5, "unit": "MINUTE"}` |
|timeFormat   | no  | response time format. Possible values: iso, milliseconds. Default value: milliseconds|
|limit        |	no  | maximum number of data samples returned. Default value: 1000                            |
|severity       |  no   | severity, must be upper-case. Only one severity level can be queried. If severity is not sent in the request, all severity levels will be returned satisfying the request. Severity Codes:  UNDEFINED, UNKNOWN, NORMAL, WARNING, MINOR, MAJOR, CRITICAL, FATAL |
|type       |  no   | type                                                                       |
|source       |  no   | source                                                                       |
|tags	      | no  | JSON object containing name=values that uniquely identify the message record         |

<aside class="notice">
* Interdependent fields. Interval start and end should be set using a combination of startTime, endTime, startDate, endDate and interval.
</aside>

<aside class="notice">
* One of the following fields is required: **entity, entities, entityGroup, entityExpression**. 
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.
</aside>

### Basic Response Example

> Response

```json
[
    {
        "entity": "nurswgvml007",
        "type": "security",
        "source": "default",
        "severity": "UNDEFINED",
        "tags": {
            "path": "/var/log/secure"
        },
        "message": "Sep 17 09:13:20 NURSWGVML007 sshd[1930]: pam_unix(sshd:session): session closed for user nmonuser",
        "date": "2015-09-17T09:13:20Z"
    },
    {
        "entity": "nurswgvml007",
        "type": "security",
        "source": "default",
        "severity": "UNDEFINED",
        "tags": {
            "path": "/var/log/secure"
        },
        "message": "Sep 17 09:13:18 NURSWGVML007 sshd[23006]: error: connect_to localhost port 8081: failed.",
        "date": "2015-09-17T09:13:18Z"
    }
]
```
### Response Fields
| Field | Description |
|---|---|
|entity | entity name |
|type | type |
|source | source |
|severity | severity code |
|tags | JSON object containing name=value that uniquely identify the message record |
|message | message text |
|date | date and time in ISO format |
|time | date and time in milliseconds |
