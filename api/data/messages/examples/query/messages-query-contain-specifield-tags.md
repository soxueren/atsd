# Query Messages that Contain the Specified Tag

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
    "entity": "nurswgvml007",
    "type": "logger",
    "limit": 5,
    "tags": {
      "level": "INFO",
      "thread": "*",
      "command": "com.axibase.tsd.Server"
    },
    "startDate": "2016-06-15T14:13:57.383Z",
    "endDate": "2016-06-21T14:13:57.383Z"
  }
]
```

## Response

### Payload
```json
[
  {
    "entity": "nurswgvml007",
    "type": "logger",
    "source": "default",
    "severity": "NORMAL",
    "tags": {
      "level": "INFO",
      "thread": "qtp1011680252-098",
      "command": "com.axibase.tsd.Server"
    },
    "message": "Start processing csv, config: nginx-status",
    "date": "2016-06-20T14:13:58.383Z"
  },
  {
    "entity": "nurswgvml007",
    "type": "logger",
    "source": "default",
    "severity": "NORMAL",
    "tags": {
      "level": "INFO",
      "thread": "qtp1011680252-242",
      "command": "com.axibase.tsd.Server"
    },
    "message": "Expression entity group 'scollector-linux' updated",
    "date": "2016-06-20T14:13:57.383Z"
  },
  {
    "entity": "nurswgvml007",
    "type": "logger",
    "source": "com.axibase.tsd.service.csv.parsingstatistics",
    "severity": "NORMAL",
    "tags": {
      "level": "INFO",
      "thread": "applicationExecutor-6",
      "command": "com.axibase.tsd.Server"
    },
    "message": "Parser 'nginx-status'. read: 3, filtered 0, skipped 0, cells skipped 0",
    "date": "2016-06-20T09:43:52.709Z"
  }
]
```
