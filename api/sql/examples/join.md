# Join

## Query

```sql
SELECT t1.entity, t1.datetime, t1.value, t2.value
  FROM mpstat.cpu_busy t1
JOIN mpstat.cpu_idle t2
  WHERE t1.datetime > now - 1 * hour
```

## Results

```ls
| entity       | datetime                 | t1.value | t2.value |
|--------------|--------------------------|----------|----------|
| nurswgvml006 | 2016-06-18T21:49:53.000Z | 14.1     | 85.9     |
| nurswgvml006 | 2016-06-18T21:50:09.000Z | 88.0     | 12.0     |
| nurswgvml006 | 2016-06-18T21:50:25.000Z | 8.0      | 92.0     |
| nurswgvml006 | 2016-06-18T21:50:41.000Z | 6.1      | 93.9     |
| nurswgvml006 | 2016-06-18T21:50:57.000Z | 2.0      | 98.0     |
| nurswgvml006 | 2016-06-18T21:51:13.000Z | 4.0      | 96.0     |
```
