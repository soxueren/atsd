# Alerts: Delete

## Description

Delete specified alerts by id from the database.

## Request

### Path

```elm
/api/v1/alerts/delete
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

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/alerts/delete
```

#### Payload

```json
[
  {"id": 10},
  {"id": 14}
]
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/alerts/delete \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"id":10},{"id":14}]'
```

### Response

None.

## Additional Examples
