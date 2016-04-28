## Alerts: Query

### Method

```
POST /api/v1/alerts
```
### Basic Example
> Request

```json
{
   "queries": [
   {
      "metrics": ["loadavg.5m", "message"],
      "entities" : ["awsswgvml001"],
      "timeFormat": "iso",
      "minSeverity" : 2,
      "severities": [2, 6, 7]
   }
   ]
}
```

> Response

```json
[
    {
        "value": 0,
        "message": "",
        "id": 6,
        "textValue": "",
        "tags":
        {
        },
        "metric": "message",
        "entity": "awsswgvml001",
        "severity": 2,
        "rule": "alert-app",
        "repeatCount": 15,
        "acknowledged": false,
        "openValue": 0,
        "lastEventDate": "2015-05-12T14:57:42Z",
        "openDate": "2015-05-12T13:39:37Z"
    }
]
```
### Request Fields
| Field       | Required | Description              |
|-------------|----|----------------------|
| metrics     | no | an array of metric names |
| entity      | no* | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| entities    | no | an array of entity names |
| entityGroup | no | If `entityGroup` field is specified in the query, alerts for all entities in this group are returned. entityGroup is used only if `entity` or `entities` fields are missing or if entity field is an empty string. If entityGroup is not found or contains no entities an empty resultset will be returned. |
| rules       | no | an array of rules        |
| severities  | no | an array of severities   |
| minSeverity | no | Minimal severity filter  |

<aside class="notice">
If the entity field only contains a wildcard (*), then alerts for all entities are returned.
</aside>

<aside class="notice">
* If entity, entities, entityGroup is not defined, then data is returned for all entities (subject to remaining conditions).
</aside>

<aside class="notice">
If queries[] array is empty, then all alerts are returned.
</aside>

**Severity codes**

| **Code** | **Description** |
|---|---|
| 0 | undefined |
| 1 | unknown |
| 2 | normal |
| 3 | warning |
| 4 | minor |
| 5 | major |
| 6 | critical |
| 7 | fatal |
