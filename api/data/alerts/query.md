# Alerts: Query
## Description
## Path
```
POST
```
## Method

```
/api/v1/alerts
```
## Request 
### Fields
| Field       | Required | Description              |
|-------------|----|----------------------|
| entity    | no (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | no (1) | Array of entity names or entity name patterns |
| entityGroup | no (1) | If `entityGroup` field is specified in the query, alerts for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | no (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
| rules       | no | an array of rules which produced the alerts        |
| metrics     | no | an array of metric names for which the alerts were created |
| severities  | no | an array of severities   |
| minSeverity | no | Minimal severity filter  |

<aside class="notice">
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.
</aside>

<aside class="notice">
If queries[] array is empty, then all alerts are returned.
</aside>

**Severity codes**

| **Code** | **Description** |
|---:|---|
| 0 | undefined |
| 1 | unknown |
| 2 | normal |
| 3 | warning |
| 4 | minor |
| 5 | major |
| 6 | critical |
| 7 | fatal |

## Example
### Request
#### URI
```elm
POST https://atsd_host:8443/api/v1/alerts
```
#### Payload
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
#### curl 
```css
curl --insecure https://atsd_host:8443/api/v1/alerts \
  --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @json.file
 ```
### Response

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
