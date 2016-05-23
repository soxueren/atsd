# Alerts: History Query

## Description

## Request

### Path

```elm
/api/v1/alerts/history
```

### Method

```
POST 
```

### Headers 

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Fields

|**Field**| **Required** | **Description** |
|---|---|---|
| startTime| no |Unix timestamp, default `0`|
|endTime| no | Unix timestamp, default `Long.MAX_VALUE`|
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
|metric| yes |a metric name of the requested time series |
| entity      | no | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| entities    | no | an array of entity names |
| entityGroup | no | If `entityGroup` field is specified in the query, alerts for all entities in this group are returned. entityGroup is used only if `entity` or `entities` fields are missing or if entity field is an empty string. If entityGroup is not found or contains no entities an empty resultset will be returned. |
|rule| yes | alert rule |
|limit| no | default 1000|

## Response

### Fields

None.

### Errors

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/alerts/history
```

#### Payload

```json
{
   "queries": [
   {
      "startDate": "2015-01-25T22:15:00Z",
      "endDate": "2015-01-25T22:30:00Z",
      "timeFormat": "iso",
      "metric": "mpstat.cpu_busy",
      "entity" : "host",
      "entityGroup":"group",
      "rule":"mpstat.cpu_busy_monitor",
      "limit" : 10
   }
   ]
}
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
[
   {
        "alert": null,
        "alertDuration": 45000,
        "alertOpenTime": 1422224160000,
        "entity": "nurswgvml006",
        "metric": "df.disk_used_percent",
        "receivedTime": 1422224206474,
        "repeatCount": "2",
        "rule": "disk_threshold",
        "ruleExpression": "new_maximum() && threshold_linear_time(99) < 120",
        "ruleFilter": null,
        "schedule": null,
        "severity": "UNDEFINED",
        "tags": null,
        "date": "2015-01-25T22:16:46Z",
        "type": "CANCEL",
        "value": 57.3671,
        "window": null
   }
]
```

## Additional Examples
