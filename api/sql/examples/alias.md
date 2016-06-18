# Alias

Table and column aliases can be unquoted added or enclosed in quotes or double-quotes.

Unquoted `alias` should start with letter [a-zA-Z], followed by letter, digit or underscore.

## Query

```sql
SELECT datetime, value, entity, metric AS "measurement" 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml006' 
  AND time BETWEEN now - 5 * minute AND now
```

## Results

| datetime                 | value | entity       | measurement | 
|--------------------------|------:|--------------|-------------| 
| 2016-06-17T19:16:00.000Z | 3.0   | nurswgvml006 | cpu_busy    | 
| 2016-06-17T19:16:16.000Z | 3.0   | nurswgvml006 | cpu_busy    | 
| 2016-06-17T19:16:32.000Z | 4.0   | nurswgvml006 | cpu_busy    | 
| 2016-06-17T19:16:48.000Z | 3.0   | nurswgvml006 | cpu_busy    | 