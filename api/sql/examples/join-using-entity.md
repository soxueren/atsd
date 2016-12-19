# Join Using Entity

`USING entity` modifies the default `JOIN` condition. It uses the entity to join rows from merged tables instead of the default entity and tags.

## Join without `USING entity`

```sql
SELECT t1.entity, t1.datetime, AVG(t1.value), AVG(t2.value), t2.tags.*
  FROM mpstat.cpu_busy t1
JOIN df.disk_used t2
  WHERE t1.datetime > current_hour
  AND t1.entity = 'nurswgvml007'
GROUP BY t1.entity, t2.tags, t1.period(15 minute)
  ORDER BY t1.datetime
```

## Results

No records.

## Join with `USING entity`

```sql
SELECT t1.entity, t1.datetime, AVG(t1.value), AVG(t2.value), t2.tags.*
  FROM mpstat.cpu_busy t1
JOIN USING entity df.disk_used t2
  WHERE t1.datetime > current_hour
  AND t1.entity = 'nurswgvml007'
GROUP BY t1.entity, t2.tags, t1.period(15 minute)
  ORDER BY t1.datetime
```

## Results

```ls
| entity       | datetime                 | AVG(cpu_busy.value) | AVG(disk_used.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------------:|---------------------:|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 39.7                | 1744011571.0         | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 39.7                | 8699302.7            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
```

## Outer Join Query without `USING entity`

```sql
SELECT t1.entity, t1.datetime, AVG(t1.value), AVG(t2.value), t2.tags.*
  FROM mpstat.cpu_busy t1
OUTER JOIN df.disk_used t2
  WHERE t1.datetime > current_hour
  AND t1.entity = 'nurswgvml007'
GROUP BY t1.entity, t2.tags, t1.period(15 minute)
  ORDER BY t1.datetime
```

## Results

```ls
| entity       | datetime                 | AVG(cpu_busy.value) | AVG(disk_used.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------------:|---------------------:|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | null                | 1744011571.0         | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | null                | 8700117.1            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 10.0                | null                 | null                       | null                                | 
```

## OUTER JOIN with `USING entity`

```sql
SELECT t1.entity, t1.datetime, AVG(t1.value), AVG(t2.value), t2.tags.*
  FROM mpstat.cpu_busy t1
OUTER JOIN USING entity df.disk_used t2
  WHERE t1.datetime > previous_hour
  AND t1.entity = 'nurswgvml007'
GROUP BY t1.entity, t2.tags, t1.period(15 minute)
  ORDER BY t1.datetime
```

## Results

```ls
| entity       | datetime                 | AVG(cpu_busy.value) | AVG(disk_used.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------------:|---------------------:|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 10.0                | 1744011571.0         | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 10.0                | 8700117.1            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
```
