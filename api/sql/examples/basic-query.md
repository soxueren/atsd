# Basic Query

Query for one metric, one entity and detailed values for a time range.

## Query

```sql
SELECT datetime, value, entity 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
  AND time >= current_day AND time < now
```

## Results

| datetime                 | value | entity       | 
|--------------------------|-------|--------------| 
| 2016-06-16T00:00:11.000Z | 9.1   | nurswgvml007 | 
| 2016-06-16T00:00:27.000Z | 3.0   | nurswgvml007 | 
| 2016-06-16T00:00:43.000Z | 3.0   | nurswgvml007 | 
| 2016-06-16T00:00:59.000Z | 2.0   | nurswgvml007 | 
| 2016-06-16T00:01:15.000Z | 3.0   | nurswgvml007 | 
| 2016-06-16T00:01:31.000Z | 6.0   | nurswgvml007 | 
