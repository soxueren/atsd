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
|groupKeys|array | Array of message tags used for grouping: `type`, `source`, `entity`, etc, for example `"groupKeys": ["entity", "type"]` |
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
| requestId | string | Optional identifier used to associate `query` object in request with `series` objects in response. |
| timeFormat |string| Time format for data array. `iso` or `milliseconds`. Default: `iso`. |

## Response 

An array of `series` objects containing message filter fields and message counter fields.

Tags used for grouping are returned in tags object.

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
* [Counting all Messages](examples/stats-query/messages-stats-query-counting-all-messages.md)
* [Stats-query with multiple entities](examples/stats-query/messages-stats-query-with-multiple-entities.md)
* [Stats-query with multiple entities and group](examples/stats-query/messages-stats-query-with-multiple-entities-group.md)
* [Stats-query with multiple groupKeys](examples/stats-query/messages-stats-query-with-multiple-groupKeys.md)
* [Stats-query with type groupKey](examples/stats-query/messages-stats-query-with-type-groupKey.md)
* [Stats-query with count type](examples/stats-query/messages-stats-query-with-count-type-aggregate-field.md)
* [Stats-query with interpolation](examples/stats-query/messages-stats-query-with-interpolation.md)
* [Milliseconds timeFormat](examples/stats-query/messages-stats-query-with-milliseconds-time-format.md)
* [Stats-query with control field](examples/stats-query/messages-stats-query-with-control-requestid-field.md)



