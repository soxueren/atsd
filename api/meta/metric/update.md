## Metric: Update

## Description

Update specified properties and tags for an existing metric.

Properties and tags that are not specified in the request are left unchanged.

## Path

```
/api/v1/metrics/{metric}
```

## Method

```
PATCH
```

## Request

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Fields

Refer to [Metric: List](list.md#fields)

## Example

### Request

#### URI

```elm
PATCH https://atsd_host:8443/api/v1/metrics/collectd.cpu.aggregation.busy.average
```

#### Payload

```json
{
    "label": "CPU Busy Average",
    "tags": {
        "table": "CollectD CPU Total"
    }
}
```





