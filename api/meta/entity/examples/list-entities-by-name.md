# List entities by name

## Description

List all entities whose name includes car

## Request

### URI

```elm
http://127.0.0.1:8088/api/v1/entities?limit=10&expression=name%20like%20%27*car*%27
```

### Payload

```json
[
   {
      "name":"car",
      "enabled":true,
      "lastInsertTime":1462427358127
   },
   {
      "name":"sport-car",
      "enabled":true,
      "lastInsertTime":1456044144000
   },
   {
      "name":"card",
      "enabled":true,
      "lastInsertTime":1456012800000
   }
]
```
