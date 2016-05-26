# List entities by minInsertDate

List entities with `lastInsertDate` equal or greater than 2016-05-18T22:13:40.000Z

## Request

### URI

```elm
GET https://atsd_host:8443/api/v1/entities?minInsertDate=2016-05-18T22:13:40.000Z
```

## Response

```json
[
   {
      "name":"weather",
      "enabled":true,
      "lastInsertTime":1464267381000
   },
   {
      "name":"animal",
      "enabled":true,
      "lastInsertTime":1463654306731
   },
   {
      "name":"plant",
      "enabled":true,
      "lastInsertTime":1464267373920
   }
]
```
