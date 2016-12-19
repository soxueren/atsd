# Outer Join with Aggregation


## Query

```sql
SELECT t1.entity, t1.datetime, AVG(mpstat.cpu_busy.value), AVG(df.disk_used.value)
 FROM mpstat.cpu_busy t1
OUTER JOIN df.disk_used t2
 WHERE t1.datetime > current_hour
GROUP BY t1.entity, t1.period(15 minute)
```

## Results

```ls
| entity       | datetime                 | AVG(cpu_busy.value) | AVG(disk_used.value) | 
|--------------|--------------------------|---------------------|----------------------| 
| nurswgvml006 | 2016-06-18T22:00:00.000Z | 98.2                | 600074918.8          | 
| nurswgvml006 | 2016-06-18T22:15:00.000Z | 10.1                | 600173726.6          | 
| nurswgvml006 | 2016-06-18T22:30:00.000Z | 5.9                 | 600179612.4          | 
| nurswgvml007 | 2016-06-18T22:00:00.000Z | 11.7                | 876369576.8          | 
| nurswgvml007 | 2016-06-18T22:15:00.000Z | 15.1                | 876369861.9          | 
| nurswgvml007 | 2016-06-18T22:30:00.000Z | 5.0                 | 876368398.7          | 
```
