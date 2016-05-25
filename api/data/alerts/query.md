# Alerts: Query

## Description

Retrieve a list of alerts matching specified fields.

## Request

### Path

```elm
/api/v1/alerts/query
```

### Method

```
POST
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

None.

### Fields

An array of query objects containing query fields used for filtering.

| **Field** | **Description** |
|:---|:---|:---|
| entity    | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | Array of entity names or entity name patterns |
| entityGroup | If `entityGroup` field is specified in the query, alerts for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
| rules       | an array of rules which produced the alerts        |
| metrics     | an array of metric names for which the alerts were created |
| severities  | an array of [severities](#severity)   |
| minSeverity | Minimal severity filter  |

* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.
* If queries[] array is empty, then all alerts are returned.

## Response

### Fields

| **Field** | **Description** |
|:---|:---|:---|
| id    | Alert id.|
| entity | Entity name. |
| metric | Metric name.  |
| rule | Rule name. |
| severity  | [Severity](#severity) code.  |
| tags | An object containing name=value tags, for example `tags: {"path": "/", "name": "sda"}` |
| repeatCount | Number of times when the expression evaluated to true sequentially.  |
| textValue | Text value.  |
| value | Last numeric value received. |
| openValue | First numeric value received.  |
| openDate | Time when alerts was open, in ISO format.  |
| lastEventDate | Time when alerts was last repeated, in ISO format.  |
| acknowledged | Acknowledgement status.|

### Errors

None.

## Severity

| **Code** | **Description** |
|:---|:---|
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
POST https://atsd_host:8443/api/v1/alerts/query/query
```

#### Payload

```json
[{
	"metrics": ["loadavg.5m", "message"],
	"entity": "nurswgvml007",
	"minSeverity": 4
}]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/alerts/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"metrics":["loadavg.5m","message"],"entity":"nurswgvml007","minSeverity":4}]'
```

### Response

####

```json
[
    {
        "id": 13,
        "entity": "nurswgvml006",        
        "tags": {
            "file_system": "/dev/sdc1",
            "mount_point": "/media/datadrive"
        },
        "repeatCount": 106,
        "textValue": "61.365",
        "metric": "disk_used_percent",
        "severity": 6,
        "rule": "disk_low",
        "acknowledged": false,
        "openDate": "2015-05-12T13:39:37Z",
        "openValue": 61.3998,
        "lastEventDate": "2015-05-12T14:57:42Z",
        "value": 61.3651
    }
]
```

## Additional Examples




