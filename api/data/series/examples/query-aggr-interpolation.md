# Series Query: Interpolate

## Description

Interpolation allows filling gaps in irregular/incomplete series.

## Data

* Detailed data is missing between 2016-02-19T13:31:31.000 and 2016-02-19T13:59:00.000Z.
* 10-minute Period `[13:40-13:50)` has no values and will be interpolated

https://apps.axibase.com/chartlab/d8c03f11/3/

### Detailed Data

```ls
| datetime                 | value  | 
|--------------------------|--------| 
| 2016-02-19T13:30:11.000Z | 4.00   | 
| 2016-02-19T13:30:27.000Z | 3.03   | 
| 2016-02-19T13:30:43.000Z | 4.04   | 
| 2016-02-19T13:30:59.000Z | 9.09   | 
| 2016-02-19T13:31:15.000Z | 3.06   | 
| 2016-02-19T13:31:31.000Z | 6.00   | -
| 2016-02-19T13:59:00.000Z | 100.00 | -
| 2016-02-19T13:59:16.000Z | 100.00 | 
| 2016-02-19T13:59:32.000Z | 100.00 | 
| 2016-02-19T13:59:48.000Z | 100.00 | 
```

### Aggregated Data

```ls
| datetime                 | AVG(value)        | 
|--------------------------|-------------------| 
| 2016-02-19T13:30:00.000Z | 4.870000004768372 | 
| 2016-02-19T13:50:00.000Z | 100               | 
```

## Request : No Interpolation

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
      "type": "AVG",
      "period": {"count": 10, "unit": "MINUTE"},    
      "interpolate": { "type": "NONE" }
    },
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T14:00:00.000Z"
  }
]
```

### Response

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY",
	"aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```


## Request : Interpolate - Fill Gap using Linear Interpolation

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
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY",
	"aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":52.435000002384186},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```

## Request : Interpolate - Fill Gap with Previous Value

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
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY",
	"aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```

## Request : Interpolate - Fill Gap with Constant Value

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
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY",
	"aggregate":{"type":"AVG","period":{"count":10,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":4.870000004768372},
	{"d":"2016-02-19T13:40:00.000Z","v":0},
	{"d":"2016-02-19T13:50:00.000Z","v":100.0}
]}]
```

## Request : Interpolate with Extension

`extend` setting adds missing periods at the beginning and the end of the interval.

```json
[
  {
    "entity": "nurswgvml007",
    "metric": "cpu_busy",
    "aggregate": {
      "type": "AVG",
      "period": {"count": 1, "unit": "MINUTE"},    
      "interpolate": { "type": "VALUE", "value": 0, "extend": true }
    },
    "startDate": "2016-02-19T13:30:00.000Z",
    "endDate": "2016-02-19T13:40:00.000Z"
  }
]
```

### Response

```json
[{"entity":"nurswgvml007","metric":"cpu_busy","tags":{},"type":"HISTORY",
	"aggregate":{"type":"AVG","period":{"count":1,"unit":"MINUTE","align":"CALENDAR"}},
"data":[
	{"d":"2016-02-19T13:30:00.000Z","v":5.040000021457672},
	{"d":"2016-02-19T13:31:00.000Z","v":4.5299999713897705},
	{"d":"2016-02-19T13:32:00.000Z","v":0.0},
	{"d":"2016-02-19T13:33:00.000Z","v":0.0},
	{"d":"2016-02-19T13:34:00.000Z","v":0.0},
	{"d":"2016-02-19T13:35:00.000Z","v":0.0},
	{"d":"2016-02-19T13:36:00.000Z","v":0.0},
	{"d":"2016-02-19T13:37:00.000Z","v":0.0},
	{"d":"2016-02-19T13:38:00.000Z","v":0.0},
	{"d":"2016-02-19T13:39:00.000Z","v":0.0}
]}]
```



