# Alerts: Update

## Description

Update specified alerts by id.

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

An array of objects containing 'id' property identifying alert in the database and acknowledge property.

|**Field**|**Description**|
|:---|:---|
|id|Alert id.|
|acknowledged|Acknowledgement status. true or false|

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
