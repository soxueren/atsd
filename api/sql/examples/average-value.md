# Average Value Query

Average value for one metric, one entity.

## Query

```sql
SELECT entity, avg(value)
 FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
 AND datetime >= previous_day AND datetime < now
```

## Results

```ls
| entity       | avg(value) | 
|--------------|------------| 
| nurswgvml007 | 11.3       | 
```
