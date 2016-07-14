# Filter by Entity

## Filter results by entity name.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE entity = 'nurswgvml007'
  AND datetime > current_hour
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T14:53:10.000Z | nurswgvml007 | 7.1   | 
| 2016-07-14T14:53:26.000Z | nurswgvml007 | 6.0   | 
| 2016-07-14T14:53:42.000Z | nurswgvml007 | 9.0   | 
```

## Filter results excluding a particular entity.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE entity != 'nurswgvml007'
  AND datetime > current_hour
ORDER BY datetime
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T14:00:04.000Z | nurswgvml009 | 100.0 | 
| 2016-07-14T14:00:06.000Z | nurswgvml006 | 21.2  | 
| 2016-07-14T14:00:08.000Z | nurswgvml010 | 2.0   | 
| 2016-07-14T14:00:08.000Z | nurswgvml011 | 39.2  | 
| 2016-07-14T14:00:12.000Z | nurswgvml102 | 2.0   | 
| 2016-07-14T14:00:16.000Z | nurswgvml502 | 3.5   | 
```

## Filter results for multiple entities.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE entity IN ('nurswgvml006', 'nurswgvml007')
  AND datetime > current_hour
ORDER BY datetime  
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T14:00:02.000Z | nurswgvml007 | 100.0 | 
| 2016-07-14T14:00:06.000Z | nurswgvml006 | 21.2  | 
| 2016-07-14T14:00:18.000Z | nurswgvml007 | 100.0 | 
| 2016-07-14T14:00:22.000Z | nurswgvml006 | 22.0  | 
| 2016-07-14T14:00:34.000Z | nurswgvml007 | 19.0  | 
| 2016-07-14T14:00:38.000Z | nurswgvml006 | 21.4  | 
```

## Filter results excluding particular entities.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE entity NOT IN ('nurswgvml006', 'nurswgvml007')
  AND datetime > current_hour
ORDER BY datetime  
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T15:00:04.000Z | nurswgvml502 | 51.1  | 
| 2016-07-14T15:00:06.000Z | nurswgvml009 | 3.0   | 
| 2016-07-14T15:00:10.000Z | nurswgvml011 | 100.0 | 
| 2016-07-14T15:00:13.000Z | nurswgvml102 | 2.0   | 
| 2016-07-14T15:00:14.000Z | nurswgvml010 | 27.7  | 
| 2016-07-14T15:00:26.000Z | nurswgvml011 | 42.7  | 
```

## Filter results for entity name pattern.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE entity LIKE 'nurswgvml00*'
  AND datetime > current_hour
ORDER BY datetime  
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T14:00:02.000Z | nurswgvml007 | 100.0 | 
| 2016-07-14T14:00:04.000Z | nurswgvml009 | 100.0 | 
| 2016-07-14T14:00:06.000Z | nurswgvml006 | 21.2  | 
| 2016-07-14T14:00:18.000Z | nurswgvml007 | 100.0 | 
| 2016-07-14T14:00:20.000Z | nurswgvml009 | 100.0 | 
| 2016-07-14T14:00:22.000Z | nurswgvml006 | 22.0  | 
| 2016-07-14T14:00:34.000Z | nurswgvml007 | 19.0  | 
| 2016-07-14T14:00:36.000Z | nurswgvml009 | 100.0 | 
| 2016-07-14T14:00:38.000Z | nurswgvml006 | 21.4  | 
```

## Filter results for entities with the specified entity tag.

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

## Filter results for entities with entity tag matching an expression.

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

## Filter results for entities belonging to the specified entity group.

### Query

```sql
SELECT datetime, entity, value
  FROM cpu_busy
WHERE 'nur-collectors' IN entity.groups
  AND datetime > current_hour
ORDER BY datetime 
```

### Results

```ls
| datetime                 | entity       | value | 
|--------------------------|--------------|-------| 
| 2016-07-14T15:00:04.000Z | nurswgvml502 | 51.1  | 
| 2016-07-14T15:00:06.000Z | nurswgvml009 | 3.0   | 
| 2016-07-14T15:00:07.000Z | nurswgvml007 | 44.7  | 
| 2016-07-14T15:00:10.000Z | nurswgvml011 | 100.0 | 
| 2016-07-14T15:00:13.000Z | nurswgvml102 | 2.0   | 
```

### Query with Entity Groups column

```sql
SELECT datetime, entity, value, entity.groups
  FROM cpu_busy
WHERE 'nur-collectors' IN entity.groups
  AND datetime > current_hour
ORDER BY datetime 
```

### Results with Entity Groups column

```ls
| datetime                 | entity       | value | entity.groups                                                                                                                                                                | 
|--------------------------|--------------|-------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 
| 2016-07-14T15:00:04.000Z | nurswgvml502 | 51.1  | nmon-linux;nmon-linux-beta;nur-collectors;scollector-nur;tcollector - linux                                                                                                  | 
| 2016-07-14T15:00:06.000Z | nurswgvml009 | 3.0   | VMware VMs;nmon-linux;nmon-linux-beta;nur-collectors;oracle-hosts;scollector-nur;solarwind-vmware-vm;tcollector - linux                                                      | 
| 2016-07-14T15:00:07.000Z | nurswgvml007 | 44.7  | VMware VMs;java-loggers;java-virtual-machine;jetty-web-server;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-nur;solarwind-vmware-vm;tcollector - linux | 
| 2016-07-14T15:00:10.000Z | nurswgvml011 | 100.0 | VMware VMs;nmon-linux;nmon-linux-beta;nur-collectors;scollector-nur;solarwind-vmware-vm;solarwinds-base;tcollector - linux                                                   | 
| 2016-07-14T15:00:13.000Z | nurswgvml102 | 2.0   | VMware VMs;nmon-linux;nmon-linux-beta;nur-collectors;solarwind-vmware-vm;tcollector - linux                                                                                  | 
```


