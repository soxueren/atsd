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

`group -> rate -> aggregate`

<h4 id="group">group</h4>

> Group Example

```json
{   
     "group": {
        "type": "AVG",
        "interpolate": "STEP",
        "truncate": false,
        "period": {"count": 5, "unit": "MINUTE"},
        "order": 1
    }
}
```

> Order Example

```
{
    "queries": [
        {
            "startDate": "2015-08-01T00:00:00.000Z",
            "endDate": "2015-08-01T02:00:00.000Z",
            "timeFormat": "iso",
            "entity": "nurswgvml006",
            "metric": "df.disk_used",
            "aggregate": {
                "type": "DELTA",
                "period": {
                    "count": 1,
                    "unit": "HOUR"
                },
                "counter": false,
                "order": 0
            },
            "group": {
                "type": "SUM",
                "period": {
                    "count": 1,
                    "unit": "HOUR"
                },
                "order": 1
            }
        }
    ]
}
```

> In this case aggregate will be executed first.

## Group Processor

Group processing merges multiple series into one serie before rate and aggregator are applied.

| **Parameter** | **Type** | **Description**  |
|:---|:---|:---|
| type          | yes          | Statistical function applied to value array `[v-n, w-n]`. Possible values: `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| interpolate   | no           | Interpolation function used to compute missing values for a given input series at t-n. Possible values: `NONE`, `STEP`, `LINEAR`. Default value: STEP |
| truncate      | no           | Discards samples at the beginning and at the of the grouped series until values for all input series are established. Possible values: true, false. Default value: false  |
| period      | no           | Replaces input series timestamps with regular timestamps based on count=unit frequency. Possible values: count, unit  |
| order         | no           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.             |

## Rate Processor

Computes difference between consecutive samples per unit of time (rate period). Used to compute rate of change when the underlying metric measures a continuously incrementing counter.

| **Name**     | **Description**  |
|:---|:---|
| period | ratePeriod |
| counter | if true, then negative differences between consecutive samples are ignored. Boolean. Default value: true |

> Request

```json
{
    "queries": [
        {
            "startDate": "2015-03-05T10:00:00Z",
            "endDate": "2015-03-05T12:00:00Z",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "metric": "net.tx_bytes",
            "tags": {
                "name": [
                    "*"
                ]
            },
            "rate": {
                "counter": true
            }
        }
    ]
}
```



<aside class="notice">
Rate supports NANOSECOND period unit.
</aside>

    `ratePeriod = rate.count * rate.unit (in milliseconds)`

    `if (value > previousValue) {`

        `resultValue = (value - previousValue) / (time - previousTime) * ratePeriod;`

        `aggregator.addValue(timestamp, resultValue);`

    `}`

<aside class="notice">
If rate period is specified, the function computes rate of change for the specified time period: (value - previousValue) * ratePeriod / (timestamp - previousTimestamp)
</aside>

> Request (NANOSECOND Period Example)

```json
{
    "queries": [
        {
            "startDate": "2015-09-03T12:00:00Z",
            "endDate": "2015-09-03T12:05:00Z",
            "timeFormat": "iso",
            "entity": "e-nano",
            "metric": "m-nano"
            ,"rate" : {
               "period": {"count": 1, "unit": "NANOSECOND"}
            }
        }
    ]
}
```

> Response

```json
{
    "series": [
        {
            "entity": "e-nano",
            "metric": "m-nano",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "rate": {
                "period": {
                    "count": 1,
                    "unit": "NANOSECOND"
                },
                "counter": true
            },
            "data": [
                {
                    "d": "2015-09-03T12:00:00.002Z",
                    "v": 0.7
                },
                {
                    "d": "2015-09-03T12:00:00.003Z",
                    "v": 0.1
                },
                {
                    "d": "2015-09-03T12:00:00.004Z",
                    "v": 0.4
                }
            ]
        }
    ]
}
```

## Aggregate Processor

Computes statistics for the specified time periods. The periods start with the beginning of an hour.

> Request

```json
{
    "aggregate": {
        "types": [
            "AVG",
            "MAX"
        ],
        "period": {
            "count": 1,
            "unit": "HOUR"
        },
        "interpolate": "NONE"
    }
}
```
> Request

```json
{
    "aggregate" : {
        "type": "AVG",
        "period": {"count": 1, "unit": "HOUR"}
    }
}
```

| **Name** | **Required**  | **Description**   |
|:---|:---|:---|
| types | yes          | An array of statistical functions `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| type  | no        | An statistical function, specify only one (mutually exclusive with `types` parameter): `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| period  | yes     | period for computing statistics.  |
| interpolate  | no  | Generates missing aggregation periods using interpolation if enabled: `NONE`, `LINEAR`, `STEP`   |
| threshold    | no  | min and max boundaries for `THRESHOLD_X` aggregators  |
| calendar     | no  | calendar settings for `THRESHOLD_X` aggregators  |
| workingMinutes | no | working minutes settings for `THRESHOLD_X` aggregators  |
| counter | no | Applies to DELTA aggregator. Boolean. Default value: false. If counter = true, the DELTA aggregator assumes that metric's values never decrease, except when a counter is reset or overflows. The DELTA aggregator takes such reset into account when computing differences. |
| order         | no           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.             |


#### calendar

| Name | Description          |
|---|---|
| name | Custom calendar name |

#### threshold

| Name | Description   |
|---|---|
| min  | min threshold |
| max  | max threshold |

#### Working Minutes

| Name  | Description                                                                 |
|---|---|
| start | start time (if working day starts at 9:30 then `start = 570 = 9 * 60 + 30` ) |
| end   | end time                                                                    |

#### requestId

To associate `series` object (one) in request with `series` objects (many) in response, the client can optionally specify a unique `requestId` property in each series object in request.
For example, the client can set requestId to series object's index in the request.
The server echos requestId for each series in the response.

#### last

`last` can return most recent value faster than scan. When last is specified and there is no aggregator or aggregator is `DETAIL`, ATSD executes GET request for the last hour. If the first `GET` returns no data, a second `GET` is executed for the previous hour.
Entity and tag wildcards are not supported if `last = true`.

#### version

> Request

```json
{
    "queries": [
        {
            "entity": "e-vers",
            "metric": "m-vers",
            "versioned":true,
            "versionFilter":"version_status='provisional'",
            "startDate": "2015-11-10T13:00:00Z",
            "endDate": "2015-11-12T13:00:00Z",
            "type": "HISTORY",
            "timeFormat": "iso"
        }
    ]
}   
```

> Response

```json
{
    "series": [
        {
            "entity": "e-vers",
            "metric": "m-vers",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-11-10T13:00:00.000Z",
                    "v": 2,
                    "version": {
                        "d": "2015-11-10T14:20:00.678Z",
                        "status": "provisional"
                    }
                },
                {
                    "d": "2015-11-10T13:15:00.000Z",
                    "v": 3.42,
                    "version": {
                        "d": "2015-11-10T14:20:00.657Z",
                        "status": "provisional"
                    }
                },
                {
                    "d": "2015-11-10T13:30:00.000Z",
                    "v": 4.68,
                    "version": {
                        "d": "2015-11-10T14:20:00.638Z",
                        "status": "provisional"
                    }
                }
            ]
        }
    ]
}
```

`version` is an object. Contains source, status and change time fields for versioned metrics. When a metric is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.

| Name | Description   |
|---|---|
| versioned | Boolean. Returns version status, source, time/date if metric is versioned. |
|versionFilter| Expression to filter value history (versions) by version status, source or time, for example: `version_status = 'Deleted'` or `version_source LIKE '*user*'`. To filter by version time, use `date()` function, for example, `version_time > date('2015-08-11T16:00:00Z')` or `version_time > date('current_day')`. The `date()` function accepts End Time syntax.|

<aside class="notice">
Verioned values are always returned with version time/date (t or d). Verision time/date is the value change time (when this version was stored in ATSD).
</aside>

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




