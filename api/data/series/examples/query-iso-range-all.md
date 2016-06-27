# Series Query: All Data Range

## Description

Query all detailed data for the series using ISO date format.

The database accepts data timestamped at or after Epoch time (1970-01-01T00:00:00.000 UTC).

ISO parser is currently limited to 4 digits in the year part, hence maximum date is limited to year 9999.

## Request

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
    {
        "startDate": "1970-01-01T00:00:00.000Z",
        "endDate":   "9999-12-31T23:59:59.999Z",
        "entity": "nurswgvml007",
        "metric": "mpstat.cpu_busy",
		"limit": 1
    }
]
```

## Response

### Payload

```json
[
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
        "d": "2016-06-09T07:04:56.434Z",
        "v": 224
      }
    ]
  }
]
```
