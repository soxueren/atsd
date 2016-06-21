# Messages: Statistics Query

## Description

Retrieve message counters for the specified filters as series.

If period aggregation is enabled, the series contains the number of messages in each period. Otherwise, the series contains the total number of messages within the specified timespan.

## Request

### Path

```elm
/api/v1/messages/stats/query
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

## Fields

An array of query objects containing the following filtering fields:

### Message Filter Fields

Refer to message [query](query.md#message-filter-fields) fields.

### Message Counter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
|metric|string | [**Required**] Must be set to `message-count`. |
|groupKeys|array | Array of message tags used for grouping: `type`, `source`, `entity`, etc, for example `"groupKeys": ["entity", "type"]` |
|aggregate|object | Period [aggregator](/api/data/series/aggregate.md). Only `COUNT` type is supported. <br>`"aggregate":{"type":"COUNT", "period":{"count":1,"unit":"HOUR"}}` |

### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

### Control Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| requestId | string | Optional identifier used to associate `query` object in request with `series` objects in response. |
| timeFormat |string| Time format for data array. `iso` or `milliseconds`. Default: `iso`. |

## Response 

An array of `series` objects containing message filter fields and message counter fields.

Tags used for grouping are returned in the `tags` object.

### Fields

Refer to [series fields](/api/data/series/insert.md#fields).

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/messages/stats/query
```
#### Payload

```json
[{
        "entity" : "nurswgvml007",
        "metric" : "message-count",
        "groupKeys" : ["type"],
        "aggregate" : {
            "type" : "COUNT",
            "period" : {
                "count" : 1,
                "unit" : "HOUR"
            }
        },
        "startDate" : "current_day",
        "endDate" : "now"
    }
]
```

#### curl

```elm
curl  https://atsd_host:8443/api/v1/messages/stats/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
  ```
  
### Response

#### Payload

```json
[
{
    "entity": "nurswgvml007",
    "metric": "message-count",
    "tags":
    {
        "type": "application"
    },
    "type": "HISTORY",
    "aggregate":
    {
        "type": "COUNT",
        "period":
        {
            "count": 1,
            "unit": "HOUR"
        }
    },
    "data":
    [
        {
            "d": "2016-05-31T06:00:00.000Z",
            "v": 1
        }
    ]
},
{
    "entity": "nurswgvml007",
    "metric": "message-count",
    "tags":
    {
        "type": "backup"
    },
    "type": "HISTORY",
    "aggregate":
    {
        "type": "COUNT",
        "period":
        {
            "count": 1,
            "unit": "HOUR"
        }
    },
    "data":
    [
        {
            "d": "2016-05-31T03:00:00.000Z",
            "v": 4
        }
    ]
},
{
    "entity": "nurswgvml007",
    "metric": "message-count",
    "tags":
    {
        "type": "security"
    },
    "type": "HISTORY",
    "aggregate":
    {
        "type": "COUNT",
        "period":
        {
            "count": 1,
            "unit": "HOUR"
        }
    },
    "data":
    [
        {
            "d": "2016-05-31T00:00:00.000Z",
            "v": 32
        },
        {
            "d": "2016-05-31T07:00:00.000Z",
            "v": 30
        },
        {
            "d": "2016-05-31T09:00:00.000Z",
            "v": 2
        },
        {
            "d": "2016-05-31T10:00:00.000Z",
            "v": 30
        },
        {
            "d": "2016-05-31T12:00:00.000Z",
            "v": 16
        }
    ]
}
]
```

## Additional Examples
