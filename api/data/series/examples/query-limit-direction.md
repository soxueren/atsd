# Series Query: Apply Limit to Ordered Results

## Description

`direction` parameter orders samples by time for the purpose of applying `limit`.

Samples for each series are always sorted by time in ascending order.

With `limit` specified, `ASC` returns first N values, ordered by time. `DESC` returns last N values.

`ASC` order is implemented only when the query returns 1 series.

## Request

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
    {
        "startDate": "2016-02-22T13:30:00Z",
        "endDate":   "2016-02-22T13:31:00Z",
        "entity": "nurswgvml007",
        "metric": "cpu_busy",
		"limit": 1,
		"direction": "ASC"
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
      "type": "DETAIL"
    },
    "data": [
      {
        "d": "2016-02-22T13:30:08.000Z",
        "v": 4
      }
    ]
  }
]
```


## Request with DESC order

DESC order is default.

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
    {
        "startDate": "2016-02-22T13:30:00Z",
        "endDate":   "2016-02-22T13:31:00Z",
        "entity": "nurswgvml007",
        "metric": "cpu_busy",
		"limit": 1,
		"direction": "DESC"
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
      "type": "DETAIL"
    },
    "data": [
      {
        "d": "2016-02-22T13:30:56.000Z",
        "v": 4
      }
    ]
  }
]
```

