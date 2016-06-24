# Multiple Rules for Specified Metric

## Description

## Request

### URI
```elm
POST https://atsd_host:8443/api/v1/alerts/query
```
### Payload

```json
[
  {
    "metrics": [
      "message"
    ],
    "entity": "*",
    "startDate": "2016-06-20T04:00:00Z",
    "endDate": "2016-06-25T05:00:00Z"
  }
]
```

## Response

### Payload
```json
[
  {
    "entity": "694cc7f50ea5ba42795d1a33f4e1686feeb00fff2be45d2413c7abeafca0e818",
    "tags": {},
    "repeatCount": 1,
    "textValue": " ",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:04:38.835Z",
    "lastEventDate": "2016-06-24T15:04:42.466Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": " ",
    "id": 160
  },
  {
    "entity": "8ecf39adc76130c8a1d37ab4c72952097b20385501e0ea21facd95455bd92bb3",
    "tags": {},
    "repeatCount": 0,
    "textValue": " ",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:04:42.460Z",
    "lastEventDate": "2016-06-24T15:04:42.460Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": " ",
    "id": 161
  },
  {
    "entity": "8ecf39adc76130c8a1d37ab4c72952097b20385501e0ea21facd95455bd92bb3",
    "tags": {},
    "repeatCount": 1,
    "textValue": " ",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-docker",
    "openDate": "2016-06-24T14:57:25.061Z",
    "lastEventDate": "2016-06-24T15:04:42.463Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": " ",
    "id": 147
  },
  {
    "entity": "nurswgvml007",
    "tags": {},
    "repeatCount": 0,
    "textValue": "netstat",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:00:30.970Z",
    "lastEventDate": "2016-06-24T15:00:30.970Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": "netstat",
    "id": 155
  },
  {
    "entity": "nurswgvml010",
    "tags": {},
    "repeatCount": 3,
    "textValue": "build_display_name=#475, build_id=475, build_number=475, build_tag=jenkins-Chartlab-Deploy-475, build_url=https://nur.axibase.com:41791/jenkins/job/Chartlab-Deploy/475/",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:00:55.185Z",
    "lastEventDate": "2016-06-24T15:07:16.233Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": "build_display_name=#475, build_id=475, build_number=475, build_tag=jenkins-Chartlab-Deploy-475, build_url=https://nur.axibase.com:41791/jenkins/job/Chartlab-Deploy/475/",
    "id": 159
  },
  {
    "entity": "nurswgvml501",
    "tags": {},
    "repeatCount": 10,
    "textValue": " ",
    "metric": "message",
    "severity": "NORMAL",
    "rule": "alert-app",
    "openDate": "2016-06-24T15:00:22.425Z",
    "lastEventDate": "2016-06-24T15:07:34.064Z",
    "acknowledged": false,
    "openValue": 0,
    "value": 0,
    "message": " ",
    "id": 153
  }
]
```

