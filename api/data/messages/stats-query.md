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
|groupKeys|array | Array of field names to be used for grouping: `type`, `source`, `entity`, etc. |
|aggregate|object | Periodic [aggregator](/api/data/series/aggregate.md), typically with `COUNT` functions.<br>`"aggregate" : { "type" : "COUNT", "period": { "count" : 1, "unit" : "HOUR" } }` |

### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

### Result Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default: 1000. | 

## Response 

An array of matching message objects containing the following fields:

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
|entity | string | Entity name. |
|type | string | Message type. |
|source | string | Message source. |
|severity | string | Message [severity](/api/data/severity.md) code or name. |
|tags | object |  Object containing `name=value` fields, for example `tags: {"path": "/", "name": "sda"}`. |
|message | string | Message text. |
|date | string | ISO 8601 date when the message record was created. |

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
        "startDate" : "2016-05-29T14:00:00.000Z",
        "endDate" : "2016-05-30T17:00:00.000Z"
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

```

## Additional Examples
