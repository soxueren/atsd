# Query with Join Using Entity

`USING entity` modifies the default JOIN condition, it uses entity to join rows from merged tables instead of the default entity and tags.

## Join without `USING entity`

```sql
SELECT entity, datetime, AVG(mpstat.cpu_busy.value), AVG(df.disk_used.value), tags.*
  FROM mpstat.cpu_busy
JOIN df.disk_used
  WHERE datetime > current_hour
  AND entity = 'nurswgvml007'
GROUP BY entity, tags, period(15 minute)
  ORDER BY datetime
```

## Results

No records.

## Join with `USING entity`

```sql
SELECT entity, datetime, AVG(mpstat.cpu_busy.value), AVG(df.disk_used.value), tags.*
  FROM mpstat.cpu_busy
JOIN USING entity df.disk_used
  WHERE datetime > current_hour
  AND entity = 'nurswgvml007'
GROUP BY entity, tags, period(15 minute)
  ORDER BY datetime
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
SELECT entity, datetime, AVG(mpstat.cpu_busy.value), AVG(df.disk_used.value), tags.*
  FROM mpstat.cpu_busy
OUTER JOIN df.disk_used
  WHERE datetime > current_hour
  AND entity = 'nurswgvml007'
GROUP BY entity, tags, period(15 minute)
  ORDER BY datetime
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
SELECT entity, datetime, AVG(mpstat.cpu_busy.value), AVG(df.disk_used.value), tags.*
  FROM mpstat.cpu_busy
OUTER JOIN USING entity df.disk_used
  WHERE datetime > previous_hour
  AND entity = 'nurswgvml007'
GROUP BY entity, tags, period(15 minute)
  ORDER BY datetime
```

## Results

```ls
| entity       | datetime                 | AVG(cpu_busy.value) | AVG(disk_used.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------------:|---------------------:|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 10.0                | 1744011571.0         | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007 | 2016-06-18T10:00:00.000Z | 10.0                | 8700117.1            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
```


