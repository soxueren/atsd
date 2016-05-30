# Series: Query

## Description 

Retrieve series objects containing time:value arrays for specified filters.

## Request

### Path

```elm
/api/v1/series/query
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

### Series Filter Fields

| **Field** | **Type** | **Description** |
|---|---|---|
| metric | string | [**Required**] Metric name |
| tags | object  | Object with `name=value` fields. <br>Matches series with tags that contain the same fields but may also include other fields. <br>Tag field values support `?` and `*` wildcards. |
| type | string | Type of underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default: `HISTORY` |

### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

### Forecast Filters

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
|forecastName| string | Unique forecast name. You can store an unlimited number of named forecasts for any series using `forecastName`. If `forecastName` is not set, then the default ATSD forecast will be returned. `forecastName` is applicable only when `type` is set to `FORECAST` or `FORECAST_DEVIATION` |

### Versioning Filters
| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| versioned | boolean |Returns version status, source, and change date if metric is versioned. Default: `false`. |
|versionFilter| string | Expression to filter value history (versions) by version status, source or time, for example: `version_status = 'Deleted'` or `version_source LIKE '*user*'`. To filter by version `time`, use `date()` function, for example, `version_time > date('2015-08-11T16:00:00Z')` or `version_time > date('current_day')`. The `date()` function accepts [endtime](/end-time-syntax.md) syntax.|

### Control Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of time:value samples for all matching series to be returned. Default: 0. | 
| last | boolean | Retrieves only 1 most recent value for each series. Default: `false`.<br>Start time and end time are ignored when `last=true`. |
| cache | boolean | If true, execute the query against Last Insert table which results in faster response time for last value queries. Default: `false`<br>Values in Last Insert table maybe delayed of up to 1 minute (cache to disk interval). |
| requestId | string | Optional identifier used to associate `query` object in request with `series` objects in response. |
| timeFormat |string| Time format for data array. `iso` or `milliseconds`. Default: `iso`. |

### Processor Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| [aggregate](#aggregate-processor) | object | Group detailed values into [periods](#period) and calculate statistics for each period. Default: `DETAIL` |
| [group](#group-processor) | object | Merge multiple series into one series. |
| [rate](#rate-processor) | object | Compute difference between consecutive samples per unit of time (rate period). |

## Period

| **Name**  | **Description** |
|:---|:---|
| count | Number of units. |
| unit  | Time unit: `MILLISECOND`, `SECOND`, `MINUTE`, `HOUR`, `DAY`, `WEEK`, `MONTH`, `QUARTER`, `YEAR` |

## Data Processing Sequence

The default processor sequence is follows:

1. group
2. rate
3. aggregate

The sequence can be modified by specifying an `order` field in each processor, in which case processors steps are executed in the ascending order as specified in `order` field. 

The [following example](/api/data/examples/aggregate-group-order-query.md) outlines how to aggregate series first, and group it second.

## Rate Processor


                                                             |

#### requestId

To associate `series` object (one) in request with `series` objects (many) in response, the client can optionally specify a unique `requestId` property in each series object in request.
For example, the client can set requestId to series object's index in the request.
The server echos requestId for each series in the response.

#### last

`last` can return most recent value faster than scan. When last is specified and there is no aggregator or aggregator is `DETAIL`, ATSD executes GET request for the last hour. If the first `GET` returns no data, a second `GET` is executed for the previous hour.
Entity and tag wildcards are not supported if `last = true`.


## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

#### Payload

```json
[
    {
        "startDate": "2016-02-22T13:37:00Z",
        "endDate": "2016-02-22T13:40:00Z",
        "entity": "nurswgvml007",
        "metric": "mpstat.cpu_busy"
    }
]
```

### Response

```json
[
    {
      "entity": "NURSWGVML007",
      "metric": "mpstat.cpu_busy",
      "data": [
        { "d": "2015-02-22T13:37:09Z", "v": 14.0},
        { "d": "2015-02-22T13:37:25Z", "v": 8.0}
      ]
    }
]
```

## Additional Examples 
* [Named Forecast](/api/data/examples/named-forecast-query.md)




