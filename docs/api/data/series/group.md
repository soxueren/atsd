# Group Processor

Groups multiple input series into one series and applies a grouping function to grouped values. 

If the `period` is not specified, values are grouped at all unique timestamps in the input series, otherwise values are grouped by period.

| **Parameter** | **Type** | **Description**  |
|:---|:---|:---|
| type          | string          | **[Required]** Grouping [function](#grouping-functions) applied to values of the input series. |
| period      | object           | [Period](period.md). Splits the merged series into periods and applies the grouping function to values in each period separately. <br>Default: undefined. If period is undefined, and the query includes both `group` and `aggregate` objects, period is inherited from `aggregate` object.|
| interpolate   | object           | [Interpolation](#interpolation) function to fill gaps in input series (no period) or in grouped series (if period is specified). Default value: `NONE` |
| truncate      | boolean           | Discards samples at the beginning of the interval until values for all input series are established. Default: false.  |
| order         | number           | Controls the processing order. If `group` order exceeds `aggregation` order, `group` is executed first. Default: 0.  |

## Grouping Functions

* COUNT
* MIN
* MAX
* AVG
* SUM
* PERCENTILE_999
* PERCENTILE_995
* PERCENTILE_99
* PERCENTILE_95
* PERCENTILE_90
* PERCENTILE_75
* PERCENTILE_50
* MEDIAN
* STANDARD_DEVIATION 

## Interpolation

### Interpolation Fields

| **Name** | **Type**  | **Description**   |
|:---|:---|:---|
| type  | string | [**Required**] Interpolation [function](#interpolation-functions). |
| value | number | [**Required by `VALUE` function**] Constant number used to set value for the missing periods. |
| extend  | boolean | Add missing periods at the beginning and the end of the selection interval. Default: `false`. |

Values added by `extend` setting are determined as follows:

* If the `VALUE {n}` interpolation function is specified, the `extend` option sets empty leading/trailing period values to equal `{n}`.
* Without the `VALUE {n}` function, the `extend` option adds missing periods at the beginning and end of the selection interval using the `NEXT` and `PREVIOUS` interpolation functions.

### Interpolation Functions

| **Type** | **Description** |
|:---|:---|
| NONE | No interpolation. Periods without any raw values are excluded from results. |
| PREVIOUS | Set value for the period based on the previous period's value. |
| NEXT | Set value for the period based on the next period's value. |
| LINEAR | Calculate period value using linear interpolation between previous and next period values. |
| VALUE| Set value for the period to a specific number. |

## Examples

### Data

#### Detailed Data by Series

```ls
| entity | datetime                 | value | 
|--------|--------------------------|-------| 
| e-1    | 2016-06-25T08:00:00.000Z | 1     | 
| e-2    | 2016-06-25T08:00:00.000Z | 11    | 
| e-1    | 2016-06-25T08:00:05.000Z | 3     | e-1 only
| e-1    | 2016-06-25T08:00:10.000Z | 5     | e-1 only
| e-1    | 2016-06-25T08:00:15.000Z | 8     | 
| e-2    | 2016-06-25T08:00:15.000Z | 8     | 
| e-1    | 2016-06-25T08:00:30.000Z | 3     | 
| e-2    | 2016-06-25T08:00:30.000Z | 13    | 
| e-1    | 2016-06-25T08:00:45.000Z | 5     | 
| e-2    | 2016-06-25T08:00:45.000Z | 15    | 
| e-2    | 2016-06-25T08:00:59.000Z | 19    | e-2 only
```

#### Detailed Data Grouped by Timestamp

```ls
| datetime                 | e1.value | e2.value | 
|--------------------------|----------|----------| 
| 2016-06-25T08:00:00.000Z | 1        | 11       | 
| 2016-06-25T08:00:05.000Z | 3        | -        | 
| 2016-06-25T08:00:10.000Z | 5        | -        | 
| 2016-06-25T08:00:15.000Z | 8        | 8        | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 
| 2016-06-25T08:00:59.000Z | -        | 19       | 
```

### No Aggregation

When aggregation is disabled, the `group` function is applied to values for all unique timestamps in the merged series.

In the example below, the `SUM` function returns 12 (1+11) at 2016-06-25T08:00:00Z as a total of e-1 and e-2 series values, both of which have samples this timestamp.

On the other hand, the `SUM` returns 3 (3 + null->0) at 2016-06-25T08:00:05Z because only e-1 series has a value at that timestamp.

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "group": {
      "type": "SUM"
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"group":{"type":"SUM","order":0},
"data":[
	{"d":"2016-06-25T08:00:00.000Z","v":12.0},
	{"d":"2016-06-25T08:00:05.000Z","v":3.0},
	{"d":"2016-06-25T08:00:10.000Z","v":5.0},
	{"d":"2016-06-25T08:00:15.000Z","v":16.0},
	{"d":"2016-06-25T08:00:30.000Z","v":16.0},
	{"d":"2016-06-25T08:00:45.000Z","v":20.0},
	{"d":"2016-06-25T08:00:59.000Z","v":19.0}
]}]
```

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:00.000Z | 1        | 11       | 12  | 
| 2016-06-25T08:00:05.000Z | 3        | -        | 3   | 
| 2016-06-25T08:00:10.000Z | 5        | -        | 5   | 
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | -        | 19       | 19  |
```

### Truncation

Truncation discards timestamps at the beginning of the interval until all of the merged values have a value.

The example below uses `startDate` of **2016-06-25T08:00:01Z**

The first time is MAX(MIN(series_sample_time)), the last time is MIN(MAX(series_sample_time)).

MAX(MIN(series_sample_time)) = 2016-06-25T08:00:15.000Z.

MIN(MAX(series_sample_time)) = 2016-06-25T08:00:45.000Z.

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:05.000Z | 3        | -        | 3   | discarded because time < MAX(MIN(series_sample_time))
| 2016-06-25T08:00:10.000Z | 5        | -        | 5   | discarded because time < MAX(MIN(series_sample_time))
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | -        | 19       | 19  | discarded because time > MIN(MAX(series_sample_time))
```

Samples for series e-1 at 2016-06-25T08:00:05.000Z and at 2016-06-25T08:00:10.000Z are discarded because there is no value for series e-2 until 2016-06-25T08:00:15.000Z.

Sample for series e-2 at 2016-06-25T08:00:59.000Z is discarded because there is no value for series e-1 after 2016-06-25T08:00:45.000Z.

```json
[
  {
    "startDate": "2016-06-25T08:00:01Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "group": {
      "type": "SUM",
	  "truncate": true
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"group":{"type":"SUM","truncate":true,"order":0},
"data":[
	{"d":"2016-06-25T08:00:15.000Z","v":16.0},
	{"d":"2016-06-25T08:00:30.000Z","v":16.0},
	{"d":"2016-06-25T08:00:45.000Z","v":20.0}
]}]
```

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
```

### Extend

An opposite operation to truncation, extend adds missing values at the beginning and end of the interval so that all merged series have values when the `group` function is applied.

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:05.000Z | 3        | 8 +      | 11  | e2.value extended to start at the beginning of the interval
| 2016-06-25T08:00:10.000Z | 5        | 8 +      | 13  | e2.value extended to start at the beginning of the interval
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | 5 +      | 19       | 24  | e1.value extended until the end of the interval
```

```json
[
  {
    "startDate": "2016-06-25T08:00:01Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "group": {
      "type": "SUM",
	  "interpolate": { "extend": true }
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"group":{"type":"SUM","interpolate":{"type":"NONE","value":0.0,"extend":true},"order":0},
"data":[
	{"d":"2016-06-25T08:00:05.000Z","v":11.0},
	{"d":"2016-06-25T08:00:10.000Z","v":13.0},
	{"d":"2016-06-25T08:00:15.000Z","v":16.0},
	{"d":"2016-06-25T08:00:30.000Z","v":16.0},
	{"d":"2016-06-25T08:00:45.000Z","v":20.0},
	{"d":"2016-06-25T08:00:59.000Z","v":24.0}
]}]
```

Extend is similar to interpolation where missing values at the beginning of in interval are interpolated with `NEXT` type, and missing values at the end of the interval are interpolated with `PREVIOUS` type.

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:05.000Z | 3        | 8 +(NEXT)| 11  |
| 2016-06-25T08:00:10.000Z | 5        | 8 +(NEXT)| 13  |
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | 5 +(PREV)| 19       | 24  |
```

Since `extend` is performed prior to truncation, `truncate` setting has no effect on extended results.

### Interpolation

Interpolation can fill the gaps in merged series. The `interpolate` function is applied to two consecutive samples to calculate an interim value for a known timestamp.

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "group": {
      "type": "SUM",
	  "interpolate": { "type": "PREVIOUS" }
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"group":{"type":"SUM","interpolate":{"type":"PREVIOUS","value":0.0,"extend":false},"order":0},
"data":[
	{"d":"2016-06-25T08:00:00.000Z","v":12.0},
	{"d":"2016-06-25T08:00:05.000Z","v":14.0},
	{"d":"2016-06-25T08:00:10.000Z","v":16.0},
	{"d":"2016-06-25T08:00:15.000Z","v":16.0},
	{"d":"2016-06-25T08:00:30.000Z","v":16.0},
	{"d":"2016-06-25T08:00:45.000Z","v":20.0},
	{"d":"2016-06-25T08:00:59.000Z","v":19.0}
]}]
```

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:00.000Z | 1        | 11       | 12  | 
| 2016-06-25T08:00:05.000Z | 3        | 11 (PREV)| 14  | 
| 2016-06-25T08:00:10.000Z | 5        | 11 (PREV)| 16  | 
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | -        | 19       | 19  | 
```

### Group Aggregation

By default, the `group` function is applied at all unique sample times from the merged series.
To split values into periods, specify period.

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "group": {
      "type": "SUM",
	  "period": {"count": 10, "unit": "SECOND"}
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"group":{"type":"SUM","period":{"count":10,"unit":"SECOND","align":"CALENDAR"},"order":0},
"data":[
	{"d":"2016-06-25T08:00:00.000Z","v":15.0},
	{"d":"2016-06-25T08:00:10.000Z","v":21.0},
	{"d":"2016-06-25T08:00:30.000Z","v":16.0},
	{"d":"2016-06-25T08:00:40.000Z","v":20.0},
	{"d":"2016-06-25T08:00:50.000Z","v":19.0}
]}]
```

This is equivalent to `Group <-> Aggregation` processing in case of `SUM`+`SUM` functions.

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "aggregate": {
      "type": "SUM",
      "period": {"count": 10, "unit": "SECOND"}
    },    
    "group": {
      "type": "SUM",
      "period": {"count": 10, "unit": "SECOND"}
    }
  }
]
```


### Aggregation -> Group

The `Aggregation -> Group` order creates aggregate series for each of the merged series and then performs grouping of the aggregated series.

The timestamps used for grouping combine period start times from the underlying aggregated series.

```ls
| 10-second period start   | e1.COUNT | e2.COUNT | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:00.000Z | 2        | 1        | 3   |
| 2016-06-25T08:00:10.000Z | 2        | 1        | 3   |
| 2016-06-25T08:00:20.000Z | -        | -        | -   | Period not created because there are no detailed values in the [00:20-00:30) period for any series.
| 2016-06-25T08:00:30.000Z | 1        | 1        | 2   | 
| 2016-06-25T08:00:40.000Z | 1        | 1        | 2   | 
| 2016-06-25T08:00:50.000Z | 0        | 1        | 1   |
```

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "aggregate": {
      "type": "COUNT",
      "period": {"count": 10, "unit": "SECOND"},
	  "order": 0
    },    
    "group": {
      "type": "SUM",
	  "order": 1
    }
  }
]
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
"aggregate":{"type":"COUNT","period":{"count":10,"unit":"SECOND","align":"CALENDAR"}},
"group":{"type":"SUM","order":1},
"data":[
	{"d":"2016-06-25T08:00:00.000Z","v":3.0},
	{"d":"2016-06-25T08:00:10.000Z","v":3.0},
	{"d":"2016-06-25T08:00:30.000Z","v":2.0},
	{"d":"2016-06-25T08:00:40.000Z","v":2.0},
	{"d":"2016-06-25T08:00:50.000Z","v":1.0}
]}]
```

### Group -> Aggregation 

The `Group -> Aggregation` merges series first, and then splits the merged series into periods.

At the first stage, grouping produces the following `SUM` series:

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",  
    "group": {
      "type": "SUM"
    }
  }
]
```

```ls
| datetime                 | e1.value | e2.value | SUM | 
|--------------------------|----------|----------|-----| 
| 2016-06-25T08:00:00.000Z | 1        | 11       | 12  | 
| 2016-06-25T08:00:05.000Z | 3        | -        | 3   | 
| 2016-06-25T08:00:10.000Z | 5        | -        | 5   | 
| 2016-06-25T08:00:15.000Z | 8        | 8        | 16  | 
| 2016-06-25T08:00:30.000Z | 3        | 13       | 16  | 
| 2016-06-25T08:00:45.000Z | 5        | 15       | 20  | 
| 2016-06-25T08:00:59.000Z | -        | 19       | 19  |
```

The grouped `SUM` series is then aggregated into periods.

> Note that if period is not specified, the `group` function automatically applies aggregation for the same period as the `aggregate` function.<br>To avoid this, specify `"period": {"count": 1, "unit": "MILLISECOND"}` in `group`.

```json
[
  {
    "startDate": "2016-06-25T08:00:00Z",
    "endDate":   "2016-06-25T08:01:00Z",
    "entities": ["e-1", "e-2"],
    "metric": "m-1",
    "aggregate": {
      "type": "COUNT",
      "period": {"count": 10, "unit": "SECOND"},
	  "order": 1
    },    
    "group": {
      "type": "SUM",
      "period": {"count": 1, "unit": "MILLISECOND"},
	  "order": 0
    }
  }
]
```

```ls
| datetime                 | COUNT(SUM(value)) | 
|--------------------------|-------------------| 
| 2016-06-25T08:00:00.000Z | 2                 | 
| 2016-06-25T08:00:10.000Z | 2                 | 
| 2016-06-25T08:00:30.000Z | 1                 | 
| 2016-06-25T08:00:40.000Z | 1                 | 
| 2016-06-25T08:00:50.000Z | 1                 |
```

```json
[{"entity":"*","metric":"m-1","tags":{},"entities":["e-1","e-2"],"type":"HISTORY",
	"aggregate":{"type":"COUNT","period":{"count":10,"unit":"SECOND","align":"CALENDAR"}},
	"group":{"type":"SUM","period":{"count":1,"unit":"MILLISECOND","align":"CALENDAR"},"order":0},
"data":[
	{"d":"2016-06-25T08:00:00.000Z","v":2.0},
	{"d":"2016-06-25T08:00:10.000Z","v":2.0},
	{"d":"2016-06-25T08:00:30.000Z","v":1.0},
	{"d":"2016-06-25T08:00:40.000Z","v":1.0},
	{"d":"2016-06-25T08:00:50.000Z","v":1.0}
]}]
```
