# Aggregate Processor

## Overview

Computes statistics for the specified time periods. The periods start with the beginning of an hour.

## Fields

| **Name** | **Type**  | **Description**   |
|:---|:---|:---|
| type  | string        | [**Required**] A statistical function, specify only one (mutually exclusive with `types` parameter): `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| types | array          | An array of statistical functions `DETAIL`, `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `FIRST`, `LAST`, `DELTA`, `WAVG`, `WTAVG`, `THRESHOLD_COUNT`, `THRESHOLD_DURATION`, `THRESHOLD_PERCENT`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| period  | object     | [**Required**] [Period](#period) for computing statistics.  |
| interpolate  | string  | Generates missing aggregation periods using interpolation if enabled: `NONE`, `LINEAR`, `STEP`   |
| threshold    | object  | min and max boundaries for `THRESHOLD_X` aggregators  |
| calendar     | object  | calendar settings for `THRESHOLD_X` aggregators  |
| workingMinutes | object | working minutes settings for `THRESHOLD_X` aggregators  |
| counter | boolean | Applies to DELTA aggregator. Boolean. Default value: false. If counter = true, the DELTA aggregator assumes that metric's values never decrease, except when a counter is reset or overflows. The DELTA aggregator takes such reset into account when computing differences. |
| order         | number           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.             |


### calendar

| **Name** | **Type**| **Description* |
|:---|:---|:---|
| name | string | Custom calendar name |

### threshold

| **Name** | **Type**| **Description* |
|:---|:---|:---|
| min  | number | min threshold |
| max  | number | max threshold |

### workingMinutes

| **Name** | **Type**| **Description* |
|:---|:---|:---|
| start | number | Working date start time, in minutes. If working day starts at 9:30 then `start = 570 = 9 * 60 + 30`. |
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



