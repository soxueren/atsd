# Alias

A column or table `alias` can be specified in single (`'alias'`) or double-quotes (`"alias"`).

Unquoted `alias` must begin with a letter followed by letters, underscores, digits (0-9).

## Query

```sql
SELECT datetime, value, entity, metric AS "measurement" 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml006' 
  AND time BETWEEN now - 5 * minute AND now
```

## Results

```
datetime	value	entity	measurement
2016-06-17T19:16:00.000Z	3.0	nurswgvml006	cpu_busy
2016-06-17T19:16:16.000Z	3.0	nurswgvml006	cpu_busy
2016-06-17T19:16:32.000Z	4.0	nurswgvml006	cpu_busy
2016-06-17T19:16:48.000Z	3.0	nurswgvml006	cpu_busy
```