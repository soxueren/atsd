# Alerts: Query

## Description

Retrieve a list of **open** alerts matching specified fields.

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

## Fields

An array of query objects containing the following filtering fields:

### Alert Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| rules       | list | Array of rules which produced the alerts.        |
| metrics     | list | Array of metric names for which the alerts were created. |
| severities  | list | Array of [severity code or names](/api/data/severity.md)   |
| minSeverity |  string   | Minimal [code or name](/api/data/severity.md) severity filter.  |

### Entity Filter Fields

* One of the entity fields is **required**.
* Entity name pattern may include `?` and `*` wildcards.
* `entity`, `entities`, `entityGroup` fields are mutually exclusive, only one of them can be specified in the query object. 
* `entityExpression` is applied as an additional filter to `entity`, `entities`, and `entityGroup` fields.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| entity   | string | Entity name or entity name pattern. |
| entities | list | Array of entity names or entity name patterns. |
| entityGroup | string | Entity group name. Return records for entites in the specified group.<br>Empty result is returned if the group doesn't exist or contains no entities. |
| entityExpression | string | Filter entities by name, entity tag, and properties using [syntax](/rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

### Date Filter Fields

* Date filter is **required**. 
* If `startDate` or `endDate` is not defined, the omitted field is calculated from `interval`/`endDate` and `startDate`/`interval` fields.

| **Name** | **Type** | **Description** |
|:---|:---|:---|
|startDate|	string | **[Required]** Start of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated at or after `startDate` are returned.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	string | **[Required]** End of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated before `endDate` are returned.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	string | Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|

## Response 

An array of matching alert objects containing the following fields:

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| id    | integer | Alert id.|
| acknowledged | boolean | Acknowledgement status.|
| entity | string | Entity name. |
| metric | string | Metric name.  |
| rule | string | Rule name. |
| severity  | string | [Severity](/api/data/severity.md) code.  |
| tags | object | Object containing `name=value` pairs, for example `tags: {"path": "/", "name": "sda"}` |
| repeatCount | integer | Number of times when the expression evaluated to true sequentially.  |
| textValue | string | Text value.  |
| value | double | Last numeric value received. |
| openValue | double | First numeric value received.  |
| openDate | string | ISO 8601 date when the alert was open.  |
| lastEventDate | string | ISO 8601 date when the last record was received.  |

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/alerts/query
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

#### Payload

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




