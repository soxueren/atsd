# Alerts: Update

## Description

Change acknowledgement status of the specified alerts.

This method can be used to acknowledge and un-acknowledge alerts with `"acknowledged": true|false` property in the request.

If `acknowledged` property is not specified, the alert will be un-acknowledged. 

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

An array of objects containing 'id' property identifying alert in the database and `acknowledge` status.

|**Field**|**Type**|**Required**|**Description**|
|:---|:---|:---|:---|
|id|number|yes|Alert id.|
|acknowledged|boolean|no|Acknowledgement status. Default `false`.|

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
  {"id": 10, "acknowledged": true},
  {"id": 14, "acknowledged": true}
]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/alerts/update \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"id":10, "acknowledged": true},{"id":14, "acknowledged": true}]'
```

## Additional Examples
* [Multiple Id Update](examples/update/alerts-update-multiple-id.md)



