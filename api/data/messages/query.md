## Messages: Query

### Request Fields

```
POST /api/v1/messages
```

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

|   Field          |  Required   | Description                                                                                     |
|-------------|-----|--------------------------------------------------------------------------------------|
|entity 	  | yes** | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
|entities | no** | an array of entities |
|entityGroups | no** | if entityGroups field is specified in the query, messages for entities in the listed entity groups are returned. entityGroups is used only if entity field is missing or if entity field is an empty string. If the entities listed in entityGroups are not found or contain no entities an empty resultset will be returned. |
|excludeGroups | no | entity groups that will be excluded from the response. |
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
** Mutually exclusive fields. Entities or an Entity should be specified in the request using ONE of the following fields: entity, entities, entityGroup.
</aside>

### Response Fields

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
