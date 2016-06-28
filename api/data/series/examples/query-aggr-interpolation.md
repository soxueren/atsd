# Series Query: Interpolate

## Description

Interpolation allows filling gaps in irregular/incomplete series.

* Detailed data is missing between 2016-02-19T13:31:31.000 and 2016-02-19T13:59:00.000Z.
* 10-minute Period `[13:40-13:50)` has no values and will be interpolated

https://apps.axibase.com/chartlab/d8c03f11/3/

## Request : Detailed Data

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T14:00:00.000Z"
  }
]
```

## Response

### Payload

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY","aggregate":{"type":"DETAIL"},
"data":[
	{"d":"2016-02-19T13:30:11.000Z","v":4.0},
	{"d":"2016-02-19T13:30:27.000Z","v":3.03},
	{"d":"2016-02-19T13:30:43.000Z","v":4.04},
	{"d":"2016-02-19T13:30:59.000Z","v":9.09},
	{"d":"2016-02-19T13:31:15.000Z","v":3.06},
	{"d":"2016-02-19T13:31:31.000Z","v":6.0},
	{"d":"2016-02-19T13:59:00.000Z","v":100.0},
	{"d":"2016-02-19T13:59:16.000Z","v":100.0},
	{"d":"2016-02-19T13:59:32.000Z","v":100.0},
	{"d":"2016-02-19T13:59:48.000Z","v":100.0}
]}]
```

## Request : Interpolate Linear

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
      "type": "AVG",
      "period": {"count": 10, "unit": "MINUTE"},    
      "interpolate": { "type": "LINEAR" }
    },
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T14:00:00.000Z"
  }
]
```

### Response

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY","aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":52.435000002384186},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```

## Request : Interpolate Previous

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
      "type": "AVG",
      "period": {"count": 10, "unit": "MINUTE"},    
      "interpolate": { "type": "PREVIOUS" }
    },
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T14:00:00.000Z"
  }
]
```

### Response

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY","aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```

## Request : Interpolate Value

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
      "type": "AVG",
      "period": {"count": 10, "unit": "MINUTE"},    
      "interpolate": {"type":"VALUE","value":0}
    },
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T14:00:00.000Z"
  }
]
```

### Response

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY","aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":0},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```



