# Messages: Statistics Query

## Description

Retrieve message counters for the specified filters as series.

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
|groupKeys|array | Array of field names to be used for grouping: `type`, `source`, `entity`, etc. |
|aggregate|object | Period [aggregator](/api/data/series/aggregate.md), typically used with `COUNT` function to calculate the number of messages received per period.<br>`"aggregate" : { "type" : "COUNT", "period": { "count" : 1, "unit" : "HOUR" } }` |

### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

### Control Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default: 1000. | 
| requestId | string | Optional identifier used to associate `query` object in request with `series` objects in response. |
| timeFormat |string| Time format for data array. `iso` or `milliseconds`. Default: `iso`. |

## Response 

An array of series objects containing message filter fields, aggregator, and type field.

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
