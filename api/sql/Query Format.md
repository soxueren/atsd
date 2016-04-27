## Query Format

> Request

```
SELECT * FROM {metric} WHERE entity = {entity} AND tags.{tagkey} = '/' AND time > {startTime} and time < {endTime}
```
