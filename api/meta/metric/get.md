# Metric: Get
## Description 

Retrieve properties and tags for the specified metric.

## Path 

```elm
/api/v1/metrics/{metric}
```

## Method 

```
GET
```

## Request

## Response

### Fields

Refer to Response Fields in [Metrics: List](list.md#fields)

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/metrics/md-2
```

#### curl 

```css
curl https://atsd_host:8443/api/v1/metrics/md-2 \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
{
    "name": "md-2",
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



