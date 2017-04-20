# Series Query: Regularize

## Description

Applying periodic aggregation to a time series with varying intervals between observations using the `FIRST` and `LAST` statistic function provides a technique for regularizing the series, including fill gaps with interpolated values.

* `FIRST` function returns the first observed value in each period.
* `LAST` function returns the last observed value in each period.

Unlike average and rank functions, such as percentiles, `FIRST` and `LAST` functions do not average out the values of the underlying series.

The process is sometimes called downsampling.

## Request

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
  {
    "startDate": "2016-06-27T14:10:00Z",
    "endDate":   "2016-06-27T14:15:00Z",
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
	   "period": {"count": 1, "unit": "MINUTE"},
       "type": "FIRST",
       "interpolate": {"type": "LINEAR"}
    }
  }
]
```

## Response

### Payload

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "tags": {},
    "type": "HISTORY",
    "aggregate": {
      "type": "FIRST",
      "period": {
        "count": 1,
        "unit": "MINUTE"
      }
    },
    "data": [
      {
        "d": "2016-06-27T14:10:00.000Z",
        "v": 11.83
      },
      {
        "d": "2016-06-27T14:11:00.000Z",
        "v": 6.12
      },
      {
        "d": "2016-06-27T14:12:00.000Z",
        "v": 12
      },
      {
        "d": "2016-06-27T14:13:00.000Z",
        "v": 3
      },
      {
        "d": "2016-06-27T14:14:00.000Z",
        "v": 5.05
      }
    ]
  }
]
```
