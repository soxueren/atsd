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

#### Transformation Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| [aggregate](aggregate.md) | object | Group detailed values into periods and calculate statistics for each period. Default: `DETAIL` |
| [group](group.md) | object | Merge multiple series into one series. |
| [rate](rate.md) | object | Compute difference between consecutive samples per unit of time (rate period). |

## Transformation Sequence

The default processor sequence is follows:

1. group
2. rate
3. aggregate

The sequence can be modified by specifying an `order` field in each processor, in which case processors steps are executed in the ascending order as specified in `order` field. 

The [following example](examples/query-group-order.md) outlines how to aggregate series first, and group it second.

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

### Time Range

* [ISO Start/End Range](examples/query-iso-range.md)
* [ISO Millisecond Precision](examples/query-iso-range-millis.md)
* [ISO hh:mm Timezone](examples/query-iso-hhmm-timezone.md)
* [ISO: All Data Range](examples/query-iso-range-all.md)
* [End Date and Interval](examples/query-end-date-interval.md)
* [EndTime Syntax](examples/query-endtime-syntax.md)
* [EndTime Syntax with Expression](examples/query-endtime-syntax-expression.md)
* [EndTime: Current Day](examples/query-endtime-currentday.md)
* [EndTime: Previous Hour](examples/query-endtime-previousday.md)
* [EndTime: Hour Window](examples/query-endtime-hour-window.md)
* [Response Time Format](examples/query-response-time-format.md)
* [Cache Range](examples/query-cache-range.md)

### Series Tags

* [Defined Tags](examples/query-tags-defined.md)
* [Wildcard: All Values](examples/query-tags-wildcard.md)
* [Wildcard: Expression](examples/query-tags-wildcard-expression.md)
* [Unknown Tag](examples/query-tags-unknown.md)

### Entity Filter

* [Multiple Entities](examples/query-entity-array.md)
* [Multiple Entities Including Entities without Data](examples/query-entity-array.md)
* [Entity Wildcard](examples/query-entity-wildcard.md)
* [Entity Expression: Name](examples/query-entity-expr-name.md)
* [Entity Expression: Entity Tags](examples/query-entity-expr-entity-tags.md)
* [Entity Expression: No Entity Tag](examples/query-entity-expr-entity-tags-not.md)
* [Entity Expression: Entity Properties](examples/query-entity-expr-entity-properties.md)
* [Entity Group](examples/query-entity-group.md)

### Multiple Queries

* [Multiple Queries](examples/query-multiple.md)
* [Multiple Queries With Request Id](examples/query-multiple-queries-request-id.md)
* [Multiple Queries for Unknown Entity](examples/query-multiple-unknown-entity.md)
* [Multiple Queries with Limit](examples/query-multiple-queries-limit.md)

### Control Fields

* [Limit](examples/query-limit.md)
* [Limit with Direction](examples/query-limit-direction.md)

### Regularize / Downsample

* [EndTime: Hour to now](examples/query-regularize.md)

### Aggregation

* [Average](examples/query-aggr-avg.md)
* [Multiple Functions](examples/query-aggr-multiple.md)
* [All Functions](examples/query-aggr-all-functions.md)
* [Counter/Delta](examples/query-aggr-counter.md)
* [Maximum Value Times](examples/query-aggr-minmax-value.md)
* [Maximum Value Time (2)](examples/query-aggr-max-value-time.md)
* [Threshold](examples/query-aggr-threshold.md)
* [Threshold with Calendar](examples/query-aggr-threshold-calendar.md)
* [Interpolation](examples/query-aggr-interpolation.md)

<!--
### Period

* [Period EndTime Align](examples/query-period-endtime.md)
* [Period Misalignment](examples/query-period-misalignment.md)
* [Period/StartDate Mismatch](examples/query-aggr-period-extended.md)

### Group

* [Group Order](examples/query-group-order.md)

### Forecast

* [Named Forecast](examples/query-named-forecast.md)

### Versioning

* [Versioning](examples/query-versioning.md)
* [Versioning: Status Filter](examples/query-versioning-filter-status.md)
* [Versioning: Value Filter](examples/query-versioning-filter-value.md)
* [Versioning: Date Filter](examples/query-versioning-filter-date.md)
* [Versioning: Composite Filter](examples/query-versioning-filter-composite.md)
-->