# Metric: Get

## Description 

Retrieve properties and tags for the specified metric.

## Request

### Path 

```elm
/api/v1/metrics/{metric}
```

### Method 

```
GET
```

### Headers

None.

### Parameters

None.

### Fields

None.

## Response

### Fields

Refer to Fields in [Metrics: List](list.md#fields)

### Errors

None.

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/metrics/cpu_busy
```

#### Payload

None.

#### curl 

```bmx
curl https://atsd_host:8443/api/v1/metrics/cpu_busy \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
{
  "name": "cpu_busy",
  "enabled": true,
  "dataType": "FLOAT",
  "counter": false,
  "persistent": true,
  "tags": {},
  "timePrecision": "MILLISECONDS",
  "retentionInterval": 0,
  "invalidAction": "NONE",
  "lastInsertDate": "2015-10-20T12:13:26.000Z",
  "versioned": false
}
```

## Additional Examples



