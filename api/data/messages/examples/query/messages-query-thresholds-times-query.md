# Thresholds times for query

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/messages/query
```
### Payload
```json
[
  {
    "entity": "d46",
    "startDate": "0001-01-01T00:00:00.000-23:59",
    "endDate": "9999-12-31T23:59:59.999+23:59"
  }
]
```

