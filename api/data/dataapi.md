# Data API

## Overview

The Data API lets you insert and retrieve time-series, properties, messages, and alerts from Axibase Time-Series Database (ATSD) server. 

The API uses standard HTTP requests, such as: `GET`, `POST`, and `PATCH`. 

All requests must be authorized using BASIC AUTHENTICATION. 

In response, the ATSD server sends an HTTP status code (such as a 200-type status for success or 400-type status for failure) that reflects the result of each request. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses. 

### Authentication

* User authentication is required.
* Authentication method: HTTP BASIC.
* Client may use session cookies to execute multiple requests without repeating authentication.
* Cross-domain requests are allowed. The server includes the following headers in each response:

`Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization`

`Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE`

`Access-Control-Allow-Origin: *`

### Compression

* Clients may send compressed data by specifying Content-Encoding: gzip

## Series: Query

### Request Fields

```
POST /api/v1/series
```

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

|Field|Required|Description|
|---|---|---|
|startTime|no*|start of the selection interval. Specified in UNIX milliseconds.|
|endTime|no*|end of the selection interval. Specified in UNIX milliseconds.|
|startDate|no*|start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|no*|end of the selection interval. Specified in ISO format or using endtime syntax.|
|interval|no*|Duration of the selection interval, specified as `count` and `unit`|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
| limit | no | maximum number of data samples returned. Only the most recent data samples will be returned if endtime/startime are set. Default value: 0 | 
| last | no |  Performs GET instead of scan. Retrieves only 1 most recent value. Boolean. Default value: false|
| entity | yes** |  an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
| entities | no** | an array of entities |
| entityGroup | no** | If `entityGroup` field is specified in the query, series for all entities in this group are returned. entityGroup is used only if `entity` or `entities` fields are missing or if entity field is an empty string. If entityGroup is not found or contains no entities an empty resultset is returned. |
| metric | yes |  a metric name of the requested time series |
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
** Mutually exclusive fields. Entities or an Entity should be specified in the request using ONE of the following fields: entity, entities, entityGroup.
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

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


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

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

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

### Basic endDate Example

> Request

```json
{
    "queries": [
        {
            "endDate": "now",
            "interval": {
                "count": 5,
                "unit": "MINUTE"
            },
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
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
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-07T12:16:05.000Z",
                    "v": 73.68
                },
                {
                    "d": "2015-09-07T12:16:21.000Z",
                    "v": 52.58
                },
                {
                    "d": "2015-09-07T12:16:37.000Z",
                    "v": 44.33
                },
                {
                    "d": "2015-09-07T12:16:53.000Z",
                    "v": 40.43
                }
            ]
        }
    ]
}
```

### Last Value with Cache Example

> Request

```json
{
    "queries": [
        {
            "startDate": "now - 5 * minute",
            "endDate": "now",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "last" : true,
            "cache": true,
            "metric": "df.disk_used"
        }
    ]
}
```

> Response

```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "df.disk_used",
            "tags": {
                "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
                "mount_point": "/"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "last": true,
            "cache": true,
            "data": [
                {
                    "d": "2015-09-22T10:56:38.000Z",
                    "v": 8450888
                }
            ]
        },
        {
            "entity": "nurswgvml007",
            "metric": "df.disk_used",
            "tags": {
                "file_system": "10.102.0.2:/home/store/share",
                "mount_point": "/mnt/share"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "last": true,
            "cache": true,
            "data": [
                {
                    "d": "2015-09-22T10:56:38.000Z",
                    "v": 132548224
                }
            ]
        }
    ]
}
```

### Entities Array Example

> Request

```json
{
    "queries": [
        {
            "entities": [
                "nurswgvml006",
                "nurswgvml007"
            ],
            "metric": "mpstat.cpu_busy",
            "interval": {
                "count": 5,
                "unit": "MINUTE"
            },
            "endDate": "now",
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
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:34:20.000Z",
                    "v": 4.04
                },
                {
                    "d": "2015-09-22T11:34:36.000Z",
                    "v": 6.06
                },
                {
                    "d": "2015-09-22T11:34:52.000Z",
                    "v": 6
                }
            ]
        },
        {
            "entity": "nurswgvml006",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:34:16.000Z",
                    "v": 5.05
                },
                {
                    "d": "2015-09-22T11:34:32.000Z",
                    "v": 3
                },
                {
                    "d": "2015-09-22T11:34:48.000Z",
                    "v": 1.01
                }
            ]
        }
    ]
}
```

### EntityGroup Example

> Request

```json
{
    "queries": [
        {
            "entityGroup": "nur-entities-name",
            "metric": "mpstat.cpu_busy",
            "interval": {
                "count": 5,
                "unit": "MINUTE"
            },
            "endDate": "now",
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
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:36:28.000Z",
                    "v": 7
                },
                {
                    "d": "2015-09-22T11:36:44.000Z",
                    "v": 5.1
                },
                {
                    "d": "2015-09-22T11:37:00.000Z",
                    "v": 56.52
                }
            ]
        },
        {
            "entity": "nurswgvml006",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:36:24.000Z",
                    "v": 7.07
                },
                {
                    "d": "2015-09-22T11:36:40.000Z",
                    "v": 6.86
                },
                {
                    "d": "2015-09-22T11:36:56.000Z",
                    "v": 3
                }
            ]
        },
        {
            "entity": "nurswgvml102",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-09-22T11:36:34.000Z",
                    "v": 1.01
                },
                {
                    "d": "2015-09-22T11:36:50.000Z",
                    "v": 0
                },
                {
                    "d": "2015-09-22T11:37:06.000Z",
                    "v": 1
                }
            ]
        }
    ]
}
```

### Basic Example with Tags

> Request

```json
{
    "queries": [
        {
            "startDate": "2015-02-22T13:37:00Z",
            "endDate": "2015-02-23T13:37:00Z",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "metric": "Busy_CPU_Detail",
            "tags": {
                "CPU_ID": "-1"
            },
            "type": "HISTORY"
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
          "metric": "Busy_CPU_Detail",
          "tags": { "CPU_ID": "-1"},
          "type": "HISTORY",
          "data": [
            { "d": "2015-02-22T14:00:53Z", "v": 8.62},
            { "d": "2015-02-22T14:30:53Z", "v": 8.69}
          ]
        }
      ]
    }
```

### Tags as an Array Example

> Request

```json
{
    "queries": [
        {
            "startDate": "2015-02-22T13:37:00Z",
            "endDate": "2015-02-23T13:37:00Z",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "metric": "df.disk_used_percent",
            "tags": {
                "mount_point": [
                    "/",
                    "/mnt/share"
                ]
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
            "entity": "nurswgvml007",
            "metric": "df.disk_used_percent",
            "tags": {
                "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
                "mount_point": "/"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-02-22T13:37:14.000Z",
                    "v": 56.0642
                },
                {
                    "d": "2015-02-22T13:37:29.000Z",
                    "v": 56.0667
                },
                {
                    "d": "2015-02-22T13:37:44.000Z",
                    "v": 56.0703
                },
                {
                    "d": "2015-02-22T13:37:59.000Z",
                    "v": 56.079
                }
            ]
        }
    ]
}
```


### Aggregated Example

> Request

```json
    {
       "queries": [
       {
        "startDate": "2015-02-05T09:53:00Z",
        "endDate": "2015-02-05T09:54:00Z",
        "timeFormat": "iso",
        "requestId": "r-1",
        "entity": "Entity1",
        "metric": "Metric1",
        "tags": {"tag1":["value1"], "tag2":["value2","Value3"]},
        "type": "history",
        "group": {
            "type": "AVG",
            "interpolate": "STEP"
        },
        "rate" : {
              "period": { "count" : 1, "unit": "HOUR" }
         },
         "aggregate": {
              "types": ["AVG", "MAX"],
              "period": { "count" : 1, "unit": "HOUR" },
              "interpolate": "NONE"
         }
       },{
        "startDate": "2015-02-05T09:53:20Z",
        "endDate": "2015-02-05T09:53:29Z",
        "timeFormat": "iso",
        "requestId": "r-2",
        "entity": "Entity2",
        "metric": "Metric2"
        }
      ]
    }
```

### SLA Example

> Request

```json
    {
        "queries": [
            {
                "startDate": "2015-02-22T13:37:00Z",
                "endDate": "2015-02-23T13:37:00Z",
                "timeFormat": "iso",
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "type": "history",
                "aggregate": {
                    "types": [
                        "THRESHOLD_COUNT",
                        "THRESHOLD_DURATION",
                        "THRESHOLD_PERCENT"
                    ],
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    },
                    "workingMinutes": {
                        "start": 540,
                        "end": 1080
                    },
                    "threshold": {
                        "max": 80
                    },
                    "calendar": {
                        "name": "my_calendar"
                    }
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
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_COUNT",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 0
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_DURATION",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 0
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "mpstat.cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_PERCENT",
                    "period": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "d": "2015-02-22T14:00:00Z",
                        "v": 100
                    },
                    {
                        "d": "2015-02-22T15:00:00Z",
                        "v": 100
                    }
                ]
            }
        ]
    }
```

### Named Forecast Example

> Request

```json
{
    "queries": [
        {
            "entity": "duckduckgo",
            "metric": "direct.queries",
            "tags": {},
            "forecastName": "DuckDuckGo1",
            "type": "FORECAST",
            "requestId": 0,
            "timeFormat": "iso",
            "startDate": "2015-05-01T00:00:00Z",
            "endDate": "2015-07-30T00:00:00Z"
        }
    ]
}
```

> Reponse

```json
{
    "series": [
        {
            "requestId": "0",
            "entity": "duckduckgo",
            "metric": "direct.queries",
            "tags": {},
            "type": "FORECAST",
            "aggregate": {
                "type": "DETAIL"
            },
            "forecastName": "DuckDuckGo1",
            "meta": {
                "timestamp": "2015-06-26T00:00:00Z",
                "averagingInterval": 86400000,
                "alpha": 0.1,
                "beta": 0.2,
                "gamma": 0,
                "period": "WEEKLY",
                "stdDev": 874884.3451501856
            },
            "data": [
                {
                    "d": "2015-06-17T00:00:00.000Z",
                    "v": 9497228.587367011
                },
                {
                    "d": "2015-06-18T00:00:00.000Z",
                    "v": 9517253.496233052
                },
                {
                    "d": "2015-06-19T00:00:00.000Z",
                    "v": 9227410.099153783
                },
                {
                    "d": "2015-06-20T00:00:00.000Z",
                    "v": 8481158.872775367
                },
                {
                    "d": "2015-06-21T00:00:00.000Z",
                    "v": 8921320.873833349
                },
                {
                    "d": "2015-06-22T00:00:00.000Z",
                    "v": 10065887.391646788
                },
                {
                    "d": "2015-06-23T00:00:00.000Z",
                    "v": 9989231.479620669
                },
                {
                    "d": "2015-06-24T00:00:00.000Z",
                    "v": 0
                }
            ]
        }
    ]
}
```

## Series: Insert

Payload - an array of series with `data` arrays.

### Request Fields

```
POST /api/v1/series/insert
```

> Request

```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "d": "2015-02-05T12:33:00Z", "v": 22.0},
      { "d": "2015-02-05T12:34:00Z", "v": 24.0}
    ]
},{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "d": "2015-02-05T12:33:00Z", "v": 42.0},
      { "d": "2015-02-05T12:34:00Z", "v": 44.0}
    ]
}]
```

> Request (named forecast)

```json
[
    {
        "entity": "duckduckgo",
        "metric": "direct.queries",
        "tags": {},
        "forecastName": "DuckDuckGo2",
        "type": "FORECAST",
        "data": [
            {
                "d": "2015-06-17T00:00:00.000Z",
                "v": 9497228.587367011
            },
            {
                "d": "2015-06-18T00:00:00.000Z",
                "v": 9517253.496233052
            },
            {
                "d": "2015-06-19T00:00:00.000Z",
                "v": 9227410.099153783
            }
        ]
    }
]
```

|**Field**|**Required**|**Description**|
|---|---|---|---|
| entity | yes | entity name |
| metric | yes | metric name |
| tags | no | an object with named keys, where a key is a tag name and a value is tag value |
| type | no | specifies source for underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default value: HISTORY |
|forecastName| no | Unique forecast name. You can store an unlimited number of named forecasts for any series using `forecastName`. If `forecastName` is not set, then the default ATSD forecast will be overwritten. `forecastName` is applicable only when `type` is set to `FORECAST` or `FORECAST_DEVIATION` |
| data | yes | an array of key-value objects, where key 't' is unix milliseconds anf 'v' is the metrics value at time 't' |
|version |no| An object. Contains source, status and change time fields for versioned metrics. |

#### version

> Request (verioned metric)

```json
[
    {
        "entity": "e-vers",
        "metric": "m-vers",
        "data": [
            {
                "t": 1447834771665,
                "v": 513,
                "version": {
                    "status": "provisional",
                    "source": "t540p"
                }
            }
        ]
    }
]
```

|Name | Description|
|---|---|
|status | Status describing value change event.|
|source | Source that generated value change event.|


## Series URL: Query

```
GET /api/v1/series/{csv|json}/{entity}/{metric}?t:name=t:value
```

**Request Parameters**

> Request

```
/api/v1/series/json/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso
```

> Response

```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-04-27T11:00:09Z",
                    "v": 5.05
                },
                {
                    "d": "2015-04-27T11:00:25Z",
                    "v": 3.03
                },
                {
                    "d": "2015-04-27T11:00:41Z",
                    "v": 5
                }
            ]
        }
    ]
}
```

|Parameters|Required|Description|
|---|---|---|
|t:name|no|Tag name, prefixed by `t:`. Tag value specified as parameter value, for example, `&t:file_system=/tmp`. Multiple values for the same tag can be specified by repeating parameter, for example, `&t:file_system=/tmp&&t:file_system=/home/export`|
|startTime|no* |start of the selection interval. Specified in UNIX milliseconds.|
|endTime|no* |end of the selection interval. Specified in UNIX milliseconds.|
|startDate|no* |start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|no* |end of the selection interval. Specified in ISO format or using endtime syntax.|
|interval|no|Duration of the selection interval, specified as `count`-`timeunit`, for example, 1-hour|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
|period|no|Duration of regular time period for grouping raw values. Specified as `count`-`timeunit`, for example, 1-hour.|
|aggregate|no|Statistical function to compute aggregated values for values in each period|
|limit|no|maximum number of data samples returned. Default value: 0|
|last|no|Performs GET instead of scan. Retrieves only 1 most recent value. Boolean. Default value: false|
|columns|no|Specify which columns must be included. Possible values: time, date (time in ISO), entity, metric, t:{name}, value. Default: time, entity, metric, requested tag names, value

<aside class="notice">
* Interdependent fields. Interval start and end should be set using a combination of startTime, endTime, startDate, endDate and interval.
</aside>

> Request

```
/api/v1/series/csv/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&columns=date,entity,metric,value
```

> Response

> CSV file is exported, example content:

```
time,entity,metric,value
2015-05-12T14:00:00Z,nurswgvml007,mpstat.cpu_busy,7.0
2015-05-12T14:01:00Z,nurswgvml007,mpstat.cpu_busy,2.0
2015-05-12T14:02:00Z,nurswgvml007,mpstat.cpu_busy,5.0
2015-05-12T14:03:00Z,nurswgvml007,mpstat.cpu_busy,4.95
2015-05-12T14:04:00Z,nurswgvml007,mpstat.cpu_busy,0.0
```

<aside class="notice">
If endTime is not specified, endDate is used. If endDate is not specified an error is raised.
If startTime is not specified, startDate is used. If startDate is not specified, endDate is used minus interval. If no start can be established, an error is raised.
</aside>

## Series CSV: Insert

```
POST /api/v1/series/csv/{entity}?tag1=value1&tag2=value2
```

Payload - CSV containing time column and one or multiple metric columns.

* Separator must be comma.
* Time must be specified in Unix milliseconds.
* Time column must be first, name of the time column could be arbitrary.
* Content-type: text/plain or text/csv

### Request Parameters

> Example

```
/api/v1/series/csv/nurswgvml007?file_system=/sda&mount_point=/
```

> Payload

```
time,df.disk_used_percent,disk_size,df.disk_used
1423139581216,22.2,25165824,5586812
1423139581216,22.2,25165824,5586812
```

| **Name** | **Required** | **Description**                                   |
|---|---|---|---|
| entity   | yes          | entity name                                       |
| tag      | no           | one or multiple `tag=value` request parameter pairs |

## Properties: Query

### Request Fields

```
POST /api/v1/properties
```

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "system",
      "entity": "nurswgvml007",
      "key": {}
     }
   ]
}
```

| **Field**  | **Required** | **Description**  |
|---|---|---|---|---|
| entity    | no*          | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards            |
| entities | no* | an array of entities |
| entityGroup | no* | If `entityGroup` field is specified in the query, properties of the specified type for entities in this group are returned. `entityGroup` is used only if entity field is missing or if entity field is an empty string. `entityGroup` is supported both for regular types and reserved `$entity_tags` type. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| startTime | no           |   start of the selection interval. Default value: `endTime - 1 hour`                                                                                                                        |
| endTime   | no           | end of the selection interval. Default value: `current server time`                                                                                                                             | 
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
| limit     | no           | maximum number of data samples returned. Default value: 0                                                                                                                 | 
| type      | yes          | type of data properties. Supports reserved `$entity_tags` type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.                                                                                                                              |
| key      | no           | JSON object containing `name=values` that uniquely identify the property record                                                                                   |
| keyExpression | no | expression for matching properties with specified keys |

<aside class="notice">
'$entity_tags' is a reserved property type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.
</aside>

<aside class="notice">
* Mutually exclusive fields. Entities or an Entity should be specified in the request using ONE of the following fields: entity, entities, entityGroup.
</aside>

### Response Fields

> Response

```json
[
   {
       "type": "system",
       "entity": "nurswgvml007",
       "key": {},
       "tags": {
           "cpu_total.busy": "1",
           "cpu_total.idle%": "93.6",
           "cpu_total.sys%": "1.1",
           "cpu_total.user%": "4.7",
           "cpu_total.wait%": "0.6",
           "memory_mb.active": "946.2",
           "memory_mb.bigfree": "-1.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

| **Field**  | **Description**  |
|---|---|
| type | property type name |
| entity | entity name |
| key | JSON object containing `name=value` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |
| date | date and time in ISO format |

### Examples

### Retreive Entity Tags

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "$entity_tags",
      "entity": "nurswgvml007"
     }
   ]
}
```

>Response

```json


    [
        {
            "type": "$entity_tags",
            "entity": "nurswgvml007",
            "key":
            {
            },
            "tags":
            {
                "alias": "007",
                "app": "ATSD",
                "ip": "10.102.0.6",
                "loc_area": "dc2",
                "loc_code": "nur,nur",
                "os": "Linux"
            },
            "date": "2015-09-08T09:06:32Z"
        }
    ]
```

### Entity Tags for entityGroup

> Request

```json
{
    "queries": [
        {
            "entityGroup": "nur-entities-name",
            "type": "$entity_tags",
            "timeFormat": "iso"
        }
    ]
}
```

> Response

```json
[
    {
        "type": "$entity_tags",
        "entity": "nurswgvml003",
        "key": {},
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "app-test": "1",
            "ip": "10.102.0.2",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    },
    {
        "type": "$entity_tags",
        "entity": "nurswgvml006",
        "key": {},
        "tags": {
            "app": "Hadoop/HBASE",
            "ip": "10.102.0.5",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    },
    {
        "type": "$entity_tags",
        "entity": "nurswgvml007",
        "key": {},
        "tags": {
            "alias": "007",
            "app": "ATSD",
            "ip": "10.102.0.6",
            "loc_area": "dc2",
            "loc_code": "nur,nur",
            "os": "Linux"
        },
        "date": "2015-09-08T09:37:13Z"
    }
]
```

### Properties for type using expression Example

> Request

```json
{
    "queries": [
        {
            "type": "manager2",
            "entity": "host2",
            "keyExpression": "key3 like 'nur*'"
        }
    ]
}
```

### Properties for type 'disk'

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "disk",
      "entity": "nurswgvml007"
     }
   ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-0"
       },
       "tags": {
           "disk_%busy": "1.3",
           "disk_block_size": "4.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "59.1",
           "disk_write_kb/s": "236.3"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-1"
       },
       "tags": {
           "disk_%busy": "0.0",
           "disk_block_size": "0.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "0.0",
           "disk_write_kb/s": "0.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Properties for type 'disk' with key

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "disk",
      "entity": "nurswgvml007",
      "key": {"id": "dm-0"}
     }
   ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "dm-0"
       },
       "tags": {
           "disk_%busy": "1.6",
           "disk_block_size": "4.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "59.5",
           "disk_write_kb/s": "238.2"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Properties for type 'process' with multiple keys

> Request

```json
{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "process",
      "entity": "nurswgvml007",
      "key": {"command": "java", "pid": "27297"} 
     }
   ]
}
```

> Response

```json
[
   {
       "type": "process",
       "entity": "nurswgvml007",
       "key": {
               "command": "java",
               "fullcommand": "java -server -xmx512m -xloggc:/home/axibase/atsd/logs/gc.log -verbose:gc -xx:+printgcdetails -xx:+printgcdatestamps -xx:+printgctimestamps -xx:+printgc -xx:+heapdumponoutofmemoryerror -xx:heapdumppath=/home/axibase/atsd/logs -classpath /home/axibase/atsd/conf:/home/axibase/atsd/bin/atsd-executable.jar com.axibase.tsd.server",
               "pid": "27297"
       },
       "tags": {
           "%cpu": "5.88",
           "%sys": "0.62",
           "%usr": "5.27",
           "majorfault": "0",
           "minorfault": "0",
           "resdata": "1575544",
           "resset": "456888",
           "restext": "36",
           "shdlib": "13964",
           "size": "1733508"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Key Expression

**key expression: Filter out all disks except those starting with `sd*`. Disks dm1, dm2 are excluded**

> Request

```json
{
    "queries": [
        {
            "timeFormat": "iso",
            "type": "disk",
            "entity": "nurswgvml007" ,
            "keyExpression": "id like 'sd*'"
        }
    ]
}
```

> Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda"
       },
       "tags": {
           "disk_%busy": "1.9",
           "disk_block_size": "6.1",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "43.1",
           "disk_write_kb/s": "262.8"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda1"
       },
       "tags": {
           "disk_%busy": "0.0",
           "disk_block_size": "0.0",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "0.0",
           "disk_write_kb/s": "0.0"
       },
       "date": "2015-02-05T16:55:02Z"
   },
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "id": "sda2"
       },
       "tags": {
           "disk_%busy": "1.9",
           "disk_block_size": "6.1",
           "disk_read_kb/s": "0.0",
           "disk_transfers_per_second": "43.1",
           "disk_write_kb/s": "262.8"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

## Properties: Query for Entity and Type

Returns properties for entity and type. 

### Request Parameters

```
GET /api/v1/properties/{entity}/types/{type}
```

```
GET /api/v1/entities/{entity}/property-types/{property-type}
```

> Request

```
http://atsd_server:8088/api/v1/properties/nurswgvml007/types/system?timeFormat=iso
```

> Response

```json
[
   {
       "type": "system",
       "entity": "nurswgvml007",
       "key": {},
       "tags": {
           "cpu_total.busy": "1",
           "cpu_total.idle%": "93.6",
           "cpu_total.sys%": "1.1",
           "cpu_total.user%": "4.7",
           "cpu_total.wait%": "0.6",
           "memory_mb.active": "946.2",
           "memory_mb.bigfree": "-1.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

> Request

```
http://atsd_server:8088/api/v1/entities/i-943a8878/property-types/aws_ec2.instance?timeFormat=iso
```

> Response

```json
[
    {
        "type": "aws_ec2.instance",
        "entity": "i-943a8878",
        "key": {},
        "tags": {
            "amilaunchindex": "0",
            "architecture": "x86_64",
            "clienttoken": "TqxBb1417594114891",
            "dnsname": "ec2-75-101-140-203.compute-1.amazonaws.com",
            "ebsoptimized": "false",
            "hypervisor": "xen",
            "imageid": "ami-08389d60",
            "instancestate.code": "16",
            "instancestate.name": "running",
            "instancetype": "m1.small",
            "ipaddress": "75.101.140.203",
            "keyname": "basepair",
            "launchtime": "2014-12-03T08:08:35.000Z",
            "monitoring.state": "disabled",
            "placement.availabilityzone": "us-east-1d",
            "placement.tenancy": "default",
            "privatednsname": "ip-10-111-164-53.ec2.internal",
            "privateipaddress": "10.111.164.53",
            "rootdevicename": "/dev/sda1",
            "rootdevicetype": "ebs",
            "virtualizationtype": "paravirtual"
        },
        "date": "2015-09-04T14:30:30Z"
    }
]
```

| **Parameter**  | **Required** | **Description**  |
|---|---|---|---|---|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

#### RESPONSE FIELDS

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |
| entity | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
| key | JSON object containing `name=values` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |
| date | date and time in ISO format |

## Properties: Property Types

Returns an array of properties for entity and type. 

```
GET /api/v1/entities/{entity}/property-types
```

```
GET /api/v1/properties/{entity}/types
```

> Request

```
http://atsd_server:8088/api/v1/entities/i-943a8878/property-types
```

> Response

```json
[
    "groupset",
    "aws_ec2.instance",
    "tagset",
    "aws_ec2.blockdevicemapping",
    "blockdevicemapping",
    "aws_ec2.tagset",
    "instance",
    "aws_ec2.groupset"
]
```

> Request

```
http://atsd_server:8088/api/v1/properties/nurswgvml007/types
```

> Response

```json
[
    "tcollector.proc.net.tcp",
    "disk",
    "cpu",
    "sw.vmw.vm",
    "network",
    "process",
    "jfs",
    "system",
    "network_status",
    "vmware.vm",
    "ws-test-1",
    "ping_status",
    "tcollector",
    "configuration"
]
```

**RESPONSE FIELDS:**

| **Name**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |

## Properties: Insert

```
POST /api/v1/properties/insert
```

> Request

```json
[{
   "type":"type-1",
   "entity":"entity-1",
   "key":{"server_name":"server","user_name":"system"},
   "tags":{"name.1": "value.1"},
   "timestamp":1000
},{
   "type":"type-2",
   "entity":"entity-1",
   "tags":{"name.2": "value.2"}
}]
```

Insert an array of properties.

## Properties: Batch

```
PATCH /api/v1/properties
```

Insert keys and delete keys by id or by partial key match in one request.

### Actions

> Request

```json
[{
    "action": "insert",
    "properties": [{
            "type":"type-1",
            "entity":"entity-1",
            "key":{"server_name":"server","user_name":"system"},
            "tags":{"name.1": "value.1"},
            "timestamp":1000
        },{
            "type":"type-2",
            "entity":"entity-2",
            "tags":{"name.2": "value.2"}
        }
    ]
},{
    "action": "delete",
    "properties": [{
            "type":"type-1",
            "entity":"entity-1",
            "key":{"server_name":"server","user_name":"system"}
        },{
            "type":"type-1",
            "entity":"entity-2",
            "key":{"server_name":"server","user_name":"system"}
        }
    ]
},{
    "action": "delete-match",
    "matchers": [{
            "type":"type-1",
            "createdBeforeTime":1000
        },{
            "type":"type-2","entity":"entity-2"
        },{
            "type":"type-3",
            "key":{"server_name":"server"}
        }
    ]
}]
```

| **Name**     | **Description**                                                                   |
|---|---|
| insert       | Insert an array of properties for a given entity, type                            |
| delete       | Delete an array of properties for entity, type, and optionally for specified keys |
| delete-match | Delete rows that partially match the specified key                                |

<aside class="success">
For 'delete-match' action, 'createdBeforeTime' specifies an optional time condition. The server should delete all keys that have been created before the specified time.
'createdBeforeTime' is specified in unix milliseconds.
</aside>

## Messages: Query

### Request Fields

```
POST /api/v1/messages
```

> Request

```json
{
    "queries": [
        {
            "entity": "nurswgvml007",
            "timeFormat": "iso",
            "type": "security",
            "limit": 5,
            "severity": "UNDEFINED",
            "endDate": "2015-09-17T10:00:00Z",
            "interval": {
                "count": 15,
                "unit": "MINUTE"
            },
            "tags": {
                "path": "/var/log/secure"
            }
        }
    ]
}
```

|   Field          |  Required   | Description                                                                                     |
|-------------|-----|--------------------------------------------------------------------------------------|
|entity 	  | yes** | an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
|entities | no** | an array of entities |
|entityGroups | no** | if entityGroups field is specified in the query, messages for entities in the listed entity groups are returned. entityGroups is used only if entity field is missing or if entity field is an empty string. If the entities listed in entityGroups are not found or contain no entities an empty resultset will be returned. |
|excludeGroups | no | entity groups that will be excluded from the response. |
|startTime	  | no*  | start of the selection interval. Default value: endTime - 1 hour                     |
|endTime	  | no*  | end of the selection interval. Default value: current server time                    |
|startDate	  | no*  | start of the selection interval. Specified in ISO format or using endtime syntax.    |
|endDate	  | no*  | end of the selection interval. Specified in ISO format or using endtime syntax.      |
|interval | no* | duration of the selection interval, specified as `count` and `unit`. For example: `"interval": {"count": 5, "unit": "MINUTE"}` |
|timeFormat   | no  | response time format. Possible values: iso, milliseconds. Default value: milliseconds|
|limit        |	no  | maximum number of data samples returned. Default value: 1000                            |
|severity       |  no   | severity, must be upper-case. Only one severity level can be queried. If severity is not sent in the request, all severity levels will be returned satisfying the request. Severity Codes:  UNDEFINED, UNKNOWN, NORMAL, WARNING, MINOR, MAJOR, CRITICAL, FATAL |
|type       |  no   | type                                                                       |
|source       |  no   | source                                                                       |
|tags	      | no  | JSON object containing name=values that uniquely identify the message record         |

<aside class="notice">
* Interdependent fields. Interval start and end should be set using a combination of startTime, endTime, startDate, endDate and interval.
</aside>

<aside class="notice">
** Mutually exclusive fields. Entities or an Entity should be specified in the request using ONE of the following fields: entity, entities, entityGroup.
</aside>

### Response Fields

> Response

```json
[
    {
        "entity": "nurswgvml007",
        "type": "security",
        "source": "default",
        "severity": "UNDEFINED",
        "tags": {
            "path": "/var/log/secure"
        },
        "message": "Sep 17 09:13:20 NURSWGVML007 sshd[1930]: pam_unix(sshd:session): session closed for user nmonuser",
        "date": "2015-09-17T09:13:20Z"
    },
    {
        "entity": "nurswgvml007",
        "type": "security",
        "source": "default",
        "severity": "UNDEFINED",
        "tags": {
            "path": "/var/log/secure"
        },
        "message": "Sep 17 09:13:18 NURSWGVML007 sshd[23006]: error: connect_to localhost port 8081: failed.",
        "date": "2015-09-17T09:13:18Z"
    }
]
```

| Field | Description |
|---|---|
|entity | entity name |
|type | type |
|source | source |
|severity | severity code |
|tags | JSON object containing name=value that uniquely identify the message record |
|message | message text |
|date | date and time in ISO format |
|time | date and time in milliseconds |

## Messages: Insert

### Request Fields

```
POST /api/v1/messages/insert
```

> Request

```json
[
    {
        "entity": "nurswgvml007",
        "type": "application",
        "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
        "severity": "undefined",
        "source": "atsd"
    }
]
```

| Field       | Required | Description              |
|---|---|---|
| entity | yes | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| date | no | date and time in ISO format |
| timestamp | no | time in Unix milliseconds |
| message | no | message text |
| severity | no | severity, must be upper-case. Severity Codes:  UNDEFINED, UNKNOWN, NORMAL, WARNING, MINOR, MAJOR, CRITICAL, FATAL |
| type | no | type |
| source | no | source |
| tags | no | JSON object containing name=values | 

<aside class="notice">
severity, message, type, source are reserved fields. In insert command, these reserved fields should be specified as fields, not as tags. In case of collision, tags with the same names as reserved keywords are discarded.
</aside>

## Alerts: Query

### Request Fields

```
POST /api/v1/alerts
```

> Request

```json
{
   "queries": [
   {
      "metrics": ["loadavg.5m", "message"],
      "entities" : ["awsswgvml001"],
      "timeFormat": "iso",
      "minSeverity" : 2,
      "severities": [2, 6, 7]
   }
   ]
}
```

> Response

```json
[
    {
        "value": 0,
        "message": "",
        "id": 6,
        "textValue": "",
        "tags":
        {
        },
        "metric": "message",
        "entity": "awsswgvml001",
        "severity": 2,
        "rule": "alert-app",
        "repeatCount": 15,
        "acknowledged": false,
        "openValue": 0,
        "lastEventDate": "2015-05-12T14:57:42Z",
        "openDate": "2015-05-12T13:39:37Z"
    }
]
```

| Field       | Required | Description              |
|-------------|----|----------------------|
| metrics     | no | an array of metric names |
| entity      | no* | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| entities    | no | an array of entity names |
| entityGroup | no | If `entityGroup` field is specified in the query, alerts for all entities in this group are returned. entityGroup is used only if `entity` or `entities` fields are missing or if entity field is an empty string. If entityGroup is not found or contains no entities an empty resultset will be returned. |
| rules       | no | an array of rules        |
| severities  | no | an array of severities   |
| minSeverity | no | Minimal severity filter  |

<aside class="notice">
If the entity field only contains a wildcard (*), then alerts for all entities are returned.
</aside>

<aside class="notice">
* If entity, entities, entityGroup is not defined, then data is returned for all entities (subject to remaining conditions).
</aside>

<aside class="notice">
If queries[] array is empty, then all alerts are returned.
</aside>

**Severity codes**

| **Code** | **Description** |
|---|---|
| 0 | undefined |
| 1 | unknown |
| 2 | normal |
| 3 | warning |
| 4 | minor |
| 5 | major |
| 6 | critical |
| 7 | fatal |

## Alerts: Update

```
PATCH /api/v1/alerts
```

**Supported update actions:**

* update - set fields of specified alerts to specified values.
* delete - delete specified alerts.

### Acknowledge Alerts

To acknowledge alerts, specify action `update`, field `'acknowledge':true` and array of alert identifiers to acknowledge.

> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": true
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### De-acknowledge Alerts

To de-acknowledge alerts, specify action `update`, field `'acknowledge':false` and array of alert identifiers to de-acknowledge.

> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": false
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### Delete Alerts

To delete alerts, specify action `delete` and array of alert identifiers to delete.

> Request

```json
[{
    "action": "delete",
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### Multiple Actions 

Multiple actions can be combined in one request.

> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": true
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
},{
    "action": "update",
    "fields": {
        "acknowledge": false
    },
    "alerts": [
        {"id": "evt-3"},
        {"id": "evt-4"}
    ]
}]
```
## Alerts: History Query

### Request Fields

```
POST /api/v1/alerts/history
```

> Request

```json
{
   "queries": [
   {
      "startDate": "2015-01-25T22:15:00Z",
      "endDate": "2015-01-25T22:30:00Z",
      "timeFormat": "iso",
      "metric": "mpstat.cpu_busy",
      "entity" : "host",
      "entityGroup":"group",
      "rule":"mpstat.cpu_busy_monitor",
      "limit" : 10
   }
   ]
}
```

> Response

```json
[
   {
        "alert": null,
        "alertDuration": 45000,
        "alertOpenTime": 1422224160000,
        "entity": "nurswgvml006",
        "metric": "df.disk_used_percent",
        "receivedTime": 1422224206474,
        "repeatCount": "2",
        "rule": "disk_threshold",
        "ruleExpression": "new_maximum() && threshold_linear_time(99) < 120",
        "ruleFilter": null,
        "schedule": null,
        "severity": "UNDEFINED",
        "tags": null,
        "date": "2015-01-25T22:16:46Z",
        "type": "CANCEL",
        "value": 57.3671,
        "window": null
   }
]
```

|**Field**| **Required** | **Description** |
|---|---|---|
| startTime| no |Unix timestamp, default `0`|
|endTime| no | Unix timestamp, default `Long.MAX_VALUE`|
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
|metric| yes |a metric name of the requested time series |
| entity      | no | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| entities    | no | an array of entity names |
| entityGroup | no | If `entityGroup` field is specified in the query, alerts for all entities in this group are returned. entityGroup is used only if `entity` or `entities` fields are missing or if entity field is an empty string. If entityGroup is not found or contains no entities an empty resultset will be returned. |
|rule| yes | alert rule |
|limit| no | default 1000|

## COMMAND

```
POST /api/v1/command
```

```
series e:DL1866 m:speed=650 m:altitude=12300
property e:abc001 t:disk k:name=sda v:size=203459 v:fs_type=nfs
series e:DL1867 m:speed=450 m:altitude=12100
message e:server001 d:2015-03-04T12:43:20+00:00 t:subject="my subject" m:"Hello, world"
```

Content-type: text/plain

Payload: text containing multiple insert commands: [Series](#command-'series'), [Property](#command-'property'), [Message](#command-'message')

Supported commands:

* series
* property
* message
* ping

<aside class="success">
The client may choose to maintain a persistent connection and continue writing commands into the outputstream for several minutes and hours.
The server should impose a read timeout of 10 minutes.
To prevent a timeout, the client may send a `ping` command at any time.
</aside>