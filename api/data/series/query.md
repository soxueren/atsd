# Series: Query
## Path 
```
/api/v1/series
```
## Method
```
POST 
```
### Basic Example
> Request

```json
    {
        "queries": [
            {
                "startDate": "2015-02-22T13:37:00Z",
                "endDate": "2015-02-22T13:40:00Z",
                "timeFormat": "iso",
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy"
            }
        ]
    }
```

> Response

```json
    {
      "series": [
        {
          "entity": "NURSWGVML007",
          "metric": "mpstat.cpu_busy",
          "data": [
            { "d": "2015-02-22T13:37:09Z", "v": 14.0},
            { "d": "2015-02-22T13:37:25Z", "v": 8.0}
          ]
        }
      ]
    }
```

<aside class="notice">
If endTime is not specified, endDate is used. If endDate is not specified an error is raised.
If startTime is not specified, startDate is used. If startDate is not specified, endDate is used minus interval. If no start can be established, an error is raised.
</aside>

### Request Fields

|Field|Required|Description|
|---|---|---|
| metric | yes |  Metric name |
| entity    | yes (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | yes (1) | Array of entity names or entity name patterns |
| entityGroup | yes (1) | If `entityGroup` field is specified in the query, series for the specified metric anf tags for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | yes (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
|startTime|no*|start of the selection interval. Specified in UNIX milliseconds.|
|endTime|no*|end of the selection interval. Specified in UNIX milliseconds.|
|startDate|no*|start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|no*|end of the selection interval. Specified in ISO format or using endtime syntax.|
|interval|no*|Duration of the selection interval, specified as `count` and `unit`|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
| limit | no | maximum number of data samples returned. Only the most recent data samples will be returned if endtime/startime are set. Default value: 0 | 
| last | no |  Performs GET instead of scan. Retrieves only 1 most recent value. Boolean. Default value: false|

| tags | no |  An object. key is a tag name and value is a single tag value or an array of possible tag values with `?` and `*` wildcards. |
| type | no | specifies source for underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default value: `HISTORY` |
|forecastName| no | Unique forecast name. You can store an unlimited number of named forecasts for any series using `forecastName`. If `forecastName` is not set, then the default ATSD forecast will be returned. `forecastName` is applicable only when `type` is set to `FORECAST` or `FORECAST_DEVIATION` |
| group | no | An object. Merges multiple time series into one serie. |
| rate| no | An object. Computes difference between consecutive samples per unit of time (rate period). |
| aggregate | no | An object. Computes statistics for the specified time periods. Default value: DETAIL |
| requestId | no | Optional identifier used to associate `query` object in request with `series` objects in response. |
| cache | no | `cache = true` redirects the query to Last Insert Cache table which results in faster response time for last-value queries at the cost of slight latency, with up to 1 minute delay in value update time. |
| versioned | no | Boolean. Returns version status, source, time/date if metric is versioned. |
|versionFilter| no | Expression to filter value history (versions) by version status, source or time, for example: `version_status = 'Deleted'` or `version_source LIKE '*user*'`. To filter by version `time`, use `date()` function, for example, `version_time > date('2015-08-11T16:00:00Z')` or `version_time > date('current_day')`. The `date()` function accepts End Time syntax.|

<aside class="notice">
* Interdependent fields. Interval start and end should be set using a combination of startTime, endTime, startDate, endDate and interval.
</aside>

<aside class="notice">
* One of the following fields is required: **entity, entities, entityGroup, entityExpression**. 
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.
</aside>

<h4 id="period">period</h4>

| Name  | Description                                                                      |
|---|---|
| count | Number of aggregation periods                                                  |
| unit  | Aggregation period unit: `MILLISECOND`, `SECOND`, `MINUTE`, `HOUR`, `DAY`, `WEEK`, `MONTH`, `QUARTER`, `YEAR` |

#### Data Processing Sequence

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

group operator merges multiple series into one serie before rate and aggregator are applied.

#### Group Parameters

| **Parameter** | **Required** | **Description**                                                                                                     |
|---------------|--------------|---------------------------------------------------------------------------------------------------------------------|
| type          | yes          | Statistical function applied to value array `[v-n, w-n]`. Possible values: `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| interpolate   | no           | Interpolation function used to compute missing values for a given input series at t-n. Possible values: `NONE`, `STEP`, `LINEAR`. Default value: STEP                                                                                                                                                 |
| truncate      | no           | Discards samples at the beginning and at the of the grouped series until values for all input series are established. Possible values: true, false. Default value: false                                                                                                                                                       |
| period      | no           | Replaces input series timestamps with regular timestamps based on count=unit frequency. Possible values: count, unit                                                                                                                                                      |
| order         | no           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.             |


<h4 id="rate">rate</h4>

Computes difference between consecutive samples per unit of time (rate period). Used to compute rate of change when the underlying metric measures a continuously incrementing counter.

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

#### Rate Properties

| Name     | Description  |
|---|---|
| period | ratePeriod |
| counter | if true, then negative differences between consecutive samples are ignored. Boolean. Default value: true |

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

<h4 id="aggregate">aggregate</h4>

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

<h4 id="aggregateproperties">aggregate properties</h4>

| Name | Required           | Description                                                                                                                                                                                                                                                                                           |
|---|---|---|
| types | yes          | An array of statistical functions `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| type  | no        | An statistical function, specify only one (mutually exclusive with `types` parameter): `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| period  | yes     | period for computing statistics.                                                                                                                                                                                                                                                                    |
| interpolate  | no  | Generates missing aggregation periods using interpolation if enabled: `NONE`, `LINEAR`, `STEP`                                                                                                                                                                                                            |
| threshold    | no  | min and max boundaries for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                   |
| calendar     | no  | calendar settings for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                        |
| workingMinutes | no | working minutes settings for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                 |
| counter | no | Applies to DELTA aggregator. Boolean. Default value: false. If counter = true, the DELTA aggregator assumes that metric's values never decrease, except when a counter is reset or overflows. The DELTA aggregator takes such reset into account when computing differences. |
| order         | no           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.             |

#### period

| Name  | Description                                                                      |
|---|---|
| count | Number of aggregation periods                                                  |
| unit  | Aggregation period unit: `MILLISECOND`, `SECOND`, `MINUTE`, `HOUR`, `DAY`, `WEEK`, `MONTH`, `QUARTER`, `YEAR` |

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







