# Average Value Query

Average value for one metric, one entity.

## Query

```sql
SELECT entity, avg(value)
 FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
 AND time >= previous_day AND time < now
```

## Results

```ls
| entity       | avg(value) | 
|--------------|------------| 
| nurswgvml007 | 11.3       | 
```
