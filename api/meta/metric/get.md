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

Refer to Response Fields in [Metrics: List](https://github.com/axibase/atsd-docs/blob/master/api/meta/metric/list.md)

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/metrics/md-2
```

#### curl 

```css
curl https://atsd_host:8443/api/v1/metrics/md-2 \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X GET
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



