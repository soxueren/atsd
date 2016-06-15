# Aggregate Processor

## Overview

Computes statistics for the specified time periods. By default, the periods are aligned with the beginning of calendar units as specified in period.

## Fields

| **Name** | **Type**  | **Description**   |
|:---|:---|:---|
| type  | string        | [**Required**] A statistical function applied to detailed values in each period: `DETAIL`, `AVG`, `SUM`. <br>`DETAIL` function means no aggregation is performed.<br>Refer to [aggregation functions](/api/data/aggregation.md). |
| types | array          | An array of statistical functions `DETAIL`, `AVG`, `SUM`. Either type or types are required. <br>Refer to [aggregation functions](/api/data/aggregation.md). |
| period  | object     | [**Required**] [Period](#period) for computing statistics.  |
| interpolate  | string  | Generates aggregation periods in case of missing detailed samples using an [interpolation function](#interpolation), for example, `PREVIOUS` or `LINEAR`   |
| threshold    | object  | Object containing minimum and and maximum range for a `THRESHOLD_*` aggregator.  |
| calendar     | object  | calendar settings for a `THRESHOLD_*` aggregator. |
| workingMinutes | object | working minutes settings for a `THRESHOLD_*` aggregator.  |
| order         | number           | Change the order in which `aggregate` and `group` are executed, the higher the value of `order` the later in the sequence will it be executed.             |


## Period

Period is a repeating time interval used to group detailed values occurred in the period in order to apply an aggregation function

The period contains the following fields:

| **Name** | **Type**| **Description** |
|:---|:---|:---|
| count  | number | [**Required**] Number of time units contained in the period |
| unit  | string | [**Required**] Time unit such as `MINUTE`, `HOUR`, or `DAY`. |
| align| string | Alignment of the period's start/end. Default: `CALENDAR`. <br>Possible values: `START_TIME`, `END_TIME`, `FIRST_VALUE_TIME`, `CALENDAR`.|

### calendar

| **Name** | **Type**| **Description** |
|:---|:---|:---|
| name | string | Custom calendar name |

### threshold

| **Name** | **Type**| **Description** |
|:---|:---|:---|
| min  | number | min threshold |
| max  | number | max threshold |

### workingMinutes

| **Name** | **Type**| **Description** |
|:---|:---|:---|
| start | number | Working date start time, in minutes. If working day starts at 9:30 then `start` can be specified as `570` (9 * 60 + 30). |
| end   | number |  Working date end time, in minutes.  |

## Examples

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

```json
{
    "aggregate" : {
        "type": "AVG",
        "period": {"count": 1, "unit": "HOUR"}
    }
}
```

### Interpolation

By the default, if the period doesn't contain any detailed values, it will not be included in the results.

| **Name** | **Description** |
|:---|:---|
| NONE | No interpolation. Periods without any raw values are excluded from results |
| PREVIOUS | Set value for the period based on the previous period's value |
| NEXT | Set value for the period based on the period period's value |
| LINEAR | Calculate period value using linear interpolation between previous and next period values |
| VALUE| Set value for the period to a specific number |

* PREVIOUS

```json
            "aggregate" : {
               "type": "AVG",
               "period": {"count": 1, "unit": "HOUR"},
               "interpolate" : {
                  "type": "PREVIOUS"
               }
            }
```

* LINEAR

```json
            "aggregate" : {
               "type": "AVG",
               "period": {"count": 1, "unit": "HOUR"},
               "interpolate" : {
                  "type": "LINEAR"
               }
            }
```

* VALUE

```json
            "aggregate" : {
               "type": "AVG",
               "period": {"count": 1, "unit": "HOUR"},
               "interpolate" : {
                  "type": "VALUE",
                  "value": 0
               }
            }
```



