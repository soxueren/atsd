# Aggregate Group Order Example

## Request

```json
[{
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
}]
```
