# Alerts: Update

## Description

Update specified alerts by id in the database. The fields specified in the payload will overwrite the same fields in existing alerts.

This method can be used to acknowledge and de-acknowledge alerts by adding `"acknowledge": true|false` property in the request.

## Request

### Path

```elm
/api/v1/alerts/update
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

An array of objects containing 'id' field identifying alert in the database.

|**Field**|**Description**|
|:---|:---|
|id|Alert id.|
|entity|Entity name.|
|metric|Metric name. For message and property commands, the metric name is 'messages' and 'property'.|
|rule|Rule name.|
|entity|Entity name.|
|acknowledged|Entity name.|
|severity|Severity code, 0 to 7.|
|value|Numeric value, for metric commands.|
|message|Text message, for message commands.|

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/alerts/update
```

#### Payload

```json
[
  {"id": 10, "acknowledge": true},
  {"id": 14, "acknowledge": true}
]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/alerts/update \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"id":10, "acknowledge": true},{"id":14, "acknowledge": true}]'
```

## Additional Examples
