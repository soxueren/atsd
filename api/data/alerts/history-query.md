# Alerts: History Query

## Description

Retrieve a list of **closed** alerts matching specified fields.

## Request

### Path

```elm
/api/v1/alerts/history/query
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
| rule       | string | Rule name.        |
| metric     | string | Metric name. |

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

### Result Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default: 1000. | 

## Response 

An array of matching alert objects containing the following fields:

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
| entity | string | Entity name. |
| metric | string | Metric name.  |
| rule | string | Rule name. |
| ruleExpression | string | Rule expression. |
| ruleFilter | string | Rule filter. |
| severity  | string | [Severity](/api/data/severity.md) code.  |
| tags | object | Object containing `name=value` pairs, for example `tags: {"path": "/", "name": "sda"}` |
| repeatCount | integer | Number of times when the expression evaluated to true sequentially.  |
| alert | string | Alert message.  |
| value | double | Last numeric value received. |
| type | string | Alert state when closed: `OPEN`, `CANCEL`, `REPEAT`  |
| date | string | ISO 8601 date.  |
| alertOpenDate | string | ISO 8601 date when the alert was open.  |
| alertDuration | number | Time in milliseconds when alert was in `OPEN` or `REPEAT` state.  |
| receivedDate | string | ISO 8601 date when the last value was received.  |

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/alerts/history/query
```

#### Payload

```json
[{
    "metric":"nmon.cpu_total.busy%",
    "startDate": "2016-05-27T18:00:00Z",
    "endDate": "2016-05-27T18:15:00Z",
    "limit": 5
}]
```

#### curl 

```elm
curl  https://atsd_host:8443/api/v1/alerts/history \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
  ```
### Response

```json
[{
  "date": "2016-05-27T18:08:29Z",
  "entity": "nurswgvml201",
  "metric": "nmon.cpu_total.busy%",
  "type": "CANCEL",
  "value": 12.2,
  "alert": "Cancel alert for nurswgvml201, nmon.cpu_total.busy%, {}",
  "severity": 0,
  "rule": "nmon_cpu",
  "ruleExpression": "avg() > 85 OR avg() > 30 AND entity != 'nurswgvml006'",
  "repeatCount": 6,
  "alertDuration": 420096,
  "alertOpenDate": "2016-05-27T18:01:29Z",
  "receivedDate": "2016-05-27T18:08:29Z"
}]
```

## Additional Examples
