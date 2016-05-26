# List entities by maxInsertDate

## Description

List entities with `lastInsertDate` less than 2016-05-19T08:13:40.000Z

## Request

### URI

```elm
GET https://atsd_server:8443/api/v1/entities?maxInsertDate=2016-05-19T08:13:40.000Z
```

## Response
```json
[
   {
      "name":"car",
      "enabled":true,
      "lastInsertTime":1462427358127
   },
   {
      "name":"nurswgvml017",
      "enabled":true,
      "lastInsertTime":1462427358127
   },
   {
      "name":"number",
      "enabled":true,
      "lastInsertTime":1462427358127
   }
]
```
