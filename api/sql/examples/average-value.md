# Average Value Query

Average value for one metric, one entity.

## Query

```sql
SELECT avg(value) AS CPU_Avg 
 FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
 AND time >= previous_day AND time < now
```

## Results

```ls
CPU_Avg
10.2
```
