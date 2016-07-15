# Filter by Entity Tag

## Select Entities with Specified Entity Tag

### Query

```sql
SELECT datetime, entity, value, entity.tags.app
  FROM cpu_busy
WHERE entity.tags.app IS NOT NULL
  AND datetime > current_hour
ORDER BY datetime 
```

### Results

```ls
| datetime                 | entity       | value | entity.tags.app       | 
|--------------------------|--------------|-------|-----------------------| 
| 2016-07-14T15:00:06.000Z | nurswgvml009 | 3.0   | Oracle EM             | 
| 2016-07-14T15:00:07.000Z | nurswgvml007 | 44.7  | ATSD                  | 
| 2016-07-14T15:00:10.000Z | nurswgvml011 | 100.0 | HMC Simulator, mysql  | 
| 2016-07-14T15:00:13.000Z | nurswgvml102 | 2.0   | Router                | 
| 2016-07-14T15:00:14.000Z | nurswgvml010 | 27.7  | SVN, Jenkins, Redmine | 
| 2016-07-14T15:00:16.000Z | nurswgvml006 | 4.0   | Hadoop/HBASE          | 
| 2016-07-14T15:00:22.000Z | nurswgvml009 | 58.0  | Oracle EM             | 
```

## Select entities with entity tag matching an expression.

### Query

```sql
SELECT datetime, entity, value, entity.tags.app
  FROM cpu_busy
WHERE entity.tags.app LIKE 'H*'
  AND datetime > current_hour
ORDER BY datetime 
```

### Results

```ls
| datetime                 | entity       | value | entity.tags.app      | 
|--------------------------|--------------|-------|----------------------| 
| 2016-07-14T15:00:10.000Z | nurswgvml011 | 100.0 | HMC Simulator, mysql | 
| 2016-07-14T15:00:16.000Z | nurswgvml006 | 4.0   | Hadoop/HBASE         | 
| 2016-07-14T15:00:26.000Z | nurswgvml011 | 42.7  | HMC Simulator, mysql | 
| 2016-07-14T15:00:33.000Z | nurswgvml006 | 15.8  | Hadoop/HBASE         | 
```
