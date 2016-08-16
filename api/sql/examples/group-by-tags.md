# GROUP BY tags

## Query with GROOUP BY particular tag columns

```sql
SELECT entity, datetime, tags.file_system, tags.mount_point, AVG(value) 
  FROM df.disk_used
WHERE datetime > now-2*hour
  AND entity LIKE 'nurswgvml00*' AND tags.mount_point != '/'
  GROUP BY entity, tags.file_system, tags.mount_point, PERIOD(1 HOUR)
```

## Query with GROOUP BY all tag columns

The query produces the same result as above assuming the list of series tags is the same, namely there are two series tags: `mount_point` and `file_system`.

Using columns `tags` and `tags.*` (in SELECT expression) provides an option of grouping by tags without knowing available series tags ahead of time.

```sql
SELECT entity, datetime, tags.*, AVG(value) 
  FROM df.disk_used
WHERE datetime > now-2*hour
  AND entity LIKE 'nurswgvml00*' AND tags.mount_point != '/'
  GROUP BY entity, tags, PERIOD(1 HOUR)
```

## Results

```ls
| entity       | datetime                 | tags.file_system           | tags.mount_point | AVG(value)   | 
|--------------|--------------------------|----------------------------|------------------|--------------| 
| nurswgvml006 | 2016-08-16T10:00:00.000Z | //u113452.nurstr003/backup | /mnt/u113452     | 1698750587.0 | 
| nurswgvml006 | 2016-08-16T11:00:00.000Z | //u113452.nurstr003/backup | /mnt/u113452     | 1698750587.0 | 
| nurswgvml007 | 2016-08-16T10:00:00.000Z | //u113452.nurstr003/backup | /mnt/u113452     | 1698750587.0 | 
| nurswgvml007 | 2016-08-16T11:00:00.000Z | //u113452.nurstr003/backup | /mnt/u113452     | 1698750587.0 | 
| nurswgvml009 | 2016-08-16T10:00:00.000Z | /dev/sdb1                  | /opt             | 31156676.3   | 
| nurswgvml009 | 2016-08-16T11:00:00.000Z | /dev/sdb1                  | /opt             | 31156757.7   | 
| nurswgvml006 | 2016-08-16T10:00:00.000Z | /dev/sdc1                  | /media/datadrive | 50273598.6   | 
| nurswgvml006 | 2016-08-16T11:00:00.000Z | /dev/sdc1                  | /media/datadrive | 50287589.8   | 
```
