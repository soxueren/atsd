# Series: Query

## Description 

Retrieve series objects containing time:value arrays for specified filters.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| POST | `/api/v1/series/query` | `application/json` |

### Parameters

None.

### Fields

An array of query objects containing the following filtering fields:

#### Series Filter Fields

| **Field** | **Type** | **Description** |
|---|---|---|
| metric | string | [**Required**] Metric name |
| tags | object  | Object with `name=value` fields. <br>Matches series with tags that contain the same fields but may also include other fields. <br>Tag field values support `?` and `*` wildcards. |
| type | string | Type of underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default: `HISTORY` |

#### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

#### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

#### Forecast Filters

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
|forecastName| string | Unique forecast name. Identifies a custom forecast by name. If `forecastName` is not set, then the default forecast computed by the database will be returned. `forecastName` is applicable only when `type` is set to `FORECAST` or `FORECAST_DEVIATION` |

#### Versioning Filters

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| versioned | boolean |Returns version status, source, and change date if metric is versioned. Default: false. |
| versionFilter | string | Expression to filter value history (versions) by version status, source or time, for example: `version_status = 'Deleted'` or `version_source LIKE '*user*'`. To filter by version `time`, use `date()` function, for example, `version_time > date('2015-08-11T16:00:00Z')` or `version_time > date('current_day')`. The `date()` function accepts [endtime](/end-time-syntax.md) syntax.|

#### Control Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of time:value samples returned for each series. Default: 0.<br>Note that limit is applied from the end (default direction=DESC), for example limit=1 means last value.<br>Limit is not applied if the parameter value <= 0.  | 
| direction| string | Scan order for applying the `limit`: `DESC` - descending, `ASC` - ascending. Default: `DESC`. <br>The returned data values will still be sorted in ascending order.<br>`ASC` direction is supported @since HBase 1.1|
| last | boolean | Retrieves only 1 most recent value for each series. Default: false.<br>Start time and end time are ignored when `last=true`. <br>`last` can return most recent value faster than scan. <br>When last is specified and there is no aggregator or aggregator is `DETAIL`, ATSD executes GET request for the last hour. <br>If the first `GET` returns no data, a second `GET` is executed for the previous hour.|
| cache | boolean | If true, execute the query against Last Insert table which results in faster response time for last value queries. Default: `false`<br>Values in Last Insert table maybe delayed of up to 1 minute (cache to disk interval). |
| requestId | string | Optional identifier used to associate `query` object in request with `series` objects in response. |
| timeFormat |string| Time format for data array. `iso` or `milliseconds`. Default: `iso`. |

#### Processor Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| [aggregate](aggregate.md) | object | Group detailed values into periods and calculate statistics for each period. Default: `DETAIL` |
| [group](group.md) | object | Merge multiple series into one series. |
| [rate](rate.md) | object | Compute difference between consecutive samples per unit of time (rate period). |

## Data Processing Sequence

The default processor sequence is follows:

1. group
2. rate
3. aggregate

The sequence can be modified by specifying an `order` field in each processor, in which case processors steps are executed in the ascending order as specified in `order` field. 

The [following example](/api/data/examples/aggregate-group-order-query.md) outlines how to aggregate series first, and group it second.

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

* [Named Forecast](examples/query-named-forecast.md)
* [Max Value Time Aggregator](examples/query-max-value-time.md)



