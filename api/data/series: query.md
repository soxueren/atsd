## Series: Query

### Request Parameters

```
POST /api/v1/series
```

> Request

```json
    {
        "queries": [
            {
                "startTime": 1424612226000,
                "endTime": 1424612453000,
                "entity": "nurswgvml007",
                "metric": "cpu_busy"
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
          "metric": "cpu_busy",
          "data": [
            { "t": 1424612229000, "v": 14.0},
            { "t": 1424612245000, "v": 8.0}
          ]
        }
      ]
    }
```

|**Name**|**Required**|**Description**|
|---|---|---|---|---|
| startTime | no|  Start of the selection interval in Unix milliseconds. Default value: `endTime - 1 hour` | 
| endTime | no |  End of the selection interval in  Unix milliseconds. Default value: `current server time` |
| limit | no | maximum number of data samples returned. Only the most recent data samples will be returned if endtime/startime are set. Default value: 0 | 
| last | no |  Performs GET instead of scan. Retrieves only 1 most recent value. Boolean. Default value: false|
| entity | yes |  an entity name, such as server name, or a entity name pattern with `?` and `*` wildcards |
| metric | yes |  a metric name of the requested time series |
| tags | no |  An object. key is a tag name and value is an array of possible tag values with `?` and `*` wildcards |
| type | no | specifies source for underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default value: HISTORY |
| join | no | An object. Merges multiple time series into one serie. |
| rate| no | An object. Computes difference between consecutive samples per unit of time (rate interval). |
| aggregate | no | An object. Computes statistics for the specified time intervals. Default value: DETAIL |
| requestId | no | Optional identifier used to associate `series` object in request with `series` objects in response. |


#### Data Processing Sequence

join, rate, aggregate

#### join

> Join Example

```json
{   
     "join": {
        "type": "AVG",
        "interpolate": "STEP",
        "truncate": false,
        "interval": {"count": 5, "unit": "MINUTE"}
    }
}
```

join operator merges multiple series into one serie before rate and aggregator are applied.

#### Join Parameters

| **Parameter** | **Required** | **Description**                                                                                                     |
|---------------|--------------|---------------------------------------------------------------------------------------------------------------------|
| type          | yes          | Statistical function applied to value array `[v-n, w-n]`. Possible values: `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50`, `STANDARD_DEVIATION` |
| interpolate   | no           | Interpolation function used to compute missing values for a given input series at t-n. Possible values: `NONE`, `STEP`, `LINEAR`. Default value: STEP                                                                                                                                                 |
| truncate      | no           | Discards samples at the beginning and at the of the joined series until values for all input series are established. Possible values: true, false. Default value: false                                                                                                                                                       |
| interval      | no           | Replaces input series timestamps with regular timestamps based on count=unit frequency. Possible values: count, unit                                                                                                                                                      |

#### rate

Computes difference between consecutive samples per unit of time (rate interval). Used to compute rate of change when the underlying metric measures a continuously incrementing counter.

> Request

```json
{
    "queries": [
        {
            "startTime": 1425549600000,
            "endTime": 1425556800000,
            "entity": "nurswgvml007",
            "metric": "net_tx_bytes",
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
| interval | rateInterval |
| counter | if true, then negative differences between consecutive samples are ignored. Boolean. Default value: true |


    `rateInterval = rate.count * rate.unit (in milliseconds)`

    `if (value > previousValue) {`

        `resultValue = (value - previousValue) / (time - previousTime) * rateInterval;`

        `aggregator.addValue(timestamp, resultValue);`

    `}`

<aside class="notice">
If rate interval is specified, the function computes rate of change for the specified time interval: (value - previousValue) * rateInterval / (timestamp - previousTimestamp)
</aside>

#### aggregate

Computes statistics for the specified time intervals. The intervals start with the beginning of an hour.

> Request

```json
{
    "aggregate": {
        "types": [
            "AVG",
            "MAX"
        ],
        "interval": {
            "count": 1,
            "unit": "HOUR"
        },
        "interpolate": "NONE"
    }
}
```

#### aggregate properties

| Name           | Description                                                                                                                                                                                                                                                                                           |
|---|---|
| types          | An array of statistical functions `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT` |
| interval       | interval for computing statistics.                                                                                                                                                                                                                                                                    |
| interpolate    | Generates missing aggregation intervals using interpolation if enabled: `NONE`, `LINEAR`, `STEP`                                                                                                                                                                                                            |
| threshold      | min and max boundaries for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                   |
| calendar       | calendar settings for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                        |
| workingMinutes | working minutes settings for `THRESHOLD_X` aggregators                                                                                                                                                                                                                                                 |
| counter | Applies to DELTA aggregator. Boolean. Default value: false. If counter = true, the DELTA aggregator assumes that metric's values never decrease, except when a counter is reset or overflows. The DELTA aggregator takes such reset into account when computing differences. |

#### interval

| Name  | Description                                                                      |
|---|---|
| unit  | Aggregation interval unit: `MILLISECOND`, `SECOND`, `MINUTE`, `HOUR`, `DAY`, `WEEK`, `MONTH`, `QUARTER`, `YEAR` |
| count | Number of aggregation intervals                                                  |

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

### Basic Example with Tags

> Request

```json
    {
        "queries": [
            {
                "startTime": 1424612226000,
                "endTime": 1424698626000,
                "entity": "nurswgvml007",
                "metric": "Busy_CPU_Detail",
                "tags": {
                    "CPU_ID": [
                        "-1"
                    ]
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
            { "t": 1424613653007, "v": 8.62},
            { "t": 1424615453007, "v": 8.69}
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
        "startTime": 1423130001000,
        "endTime": 1423130008000,
        "requestId": "r-1",
        "entity": "Entity1",
        "metric": "Metric1",
        "tags": {"tag1":["value1"], "tag2":["value2","Value3"]},
        "type": "history",
        "join": {
            "type": "AVG",
            "interpolate": "STEP"
        },
        "rate" : {
              "interval": { "count" : 1, "unit": "HOUR" }
         },
         "aggregate": {
              "types": ["AVG", "MAX"],
              "interval": { "count" : 1, "unit": "HOUR" },
              "interpolate": "NONE"
         }
       },{
        "startTime": 1423130000000,
        "endTime": 1423130009000,
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
                "startTime": 1424612226000,
                "endTime": 1424698626000,
                "entity": "nurswgvml007",
                "metric": "cpu_busy",
                "type": "history",
                "aggregate": {
                    "type": [
                        "THRESHOLD_COUNT",
                        "THRESHOLD_DURATION",
                        "THRESHOLD_PERCENT"
                    ],
                    "interval": {
                        "count": 1,
                        "unit": "HOUR"
                    },
                    "workingMinutes": {
                        "start": 540,
                        "end": 1080
                    },
                    "threshold": {
                        "min": null,
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
                "metric": "cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_COUNT",
                    "interval": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "t": 1424613600000,
                        "v": 0
                    },
                    {
                        "t": 1424617200000,
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_DURATION",
                    "interval": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "t": 1424613600000,
                        "v": 0
                    },
                    {
                        "t": 1424617200000,
                        "v": 0
                    }
                ]
            },
            {
                "entity": "nurswgvml007",
                "metric": "cpu_busy",
                "tags": {},
                "type": "HISTORY",
                "aggregate": {
                    "type": "THRESHOLD_PERCENT",
                    "interval": {
                        "count": 1,
                        "unit": "HOUR"
                    }
                },
                "data": [
                    {
                        "t": 1424613600000,
                        "v": 100
                    },
                    {
                        "t": 1424617200000,
                        "v": 100
                    }
                ]
            }
        ]
    }
```
