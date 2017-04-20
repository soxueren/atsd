# Join - Derived Series

Derived series, computed from multiple underlying series, can be produced with `JOIN`.

If the samples have the same timestamps (made with the same collector), inner `JOIN` is sufficient.

## Query

```sql
SELECT tot.entity, tot.datetime, 
  tot.value/1024 "total", 
  fre.value/1024 "free", 
  (tot.value - fre.value)/1024 "used",
  (1-fre.value/tot.value)*100 "used_%"
FROM "meminfo.memtotal" AS tot
  JOIN "meminfo.memfree" AS fre
WHERE tot.datetime > now - 1 * MINUTE
  AND entity = 'nurswgvml007'
```

## Results

```ls
| entity       | datetime                 | total  | free  | used   | used_% | 
|--------------|--------------------------|--------|-------|--------|--------| 
| nurswgvml007 | 2016-07-12T14:20:54.000Z | 1877.6 | 150.8 | 1726.7 | 92.0   | 
| nurswgvml007 | 2016-07-12T14:21:09.000Z | 1877.6 | 150.2 | 1727.4 | 92.0   | 
| nurswgvml007 | 2016-07-12T14:21:24.000Z | 1877.6 | 149.3 | 1728.3 | 92.1   | 
| nurswgvml007 | 2016-07-12T14:21:39.000Z | 1877.6 | 148.3 | 1729.3 | 92.1   | 
```
