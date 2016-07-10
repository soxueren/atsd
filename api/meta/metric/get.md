# Metric: Get

## Description 

Retrieve properties and tags for the specified metric.

## Request

| **Method** | **Path** | 
|:---|:---|---:|
| GET | `/api/v1/metrics/{metric}` |

### Path Parameters

| **Name** | **Description** |
|:---|:---|
| metric | **[Required]** Metric name. |

## Response

### Fields

Refer to Response Fields in [Metrics: List](list.md#fields)

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/metrics/cpu_busy
```

#### Payload

None.

#### curl 

```elm
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



