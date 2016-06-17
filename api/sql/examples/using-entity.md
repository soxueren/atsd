# Using Entity

Join tables using entity instead of entity, time, and tags (default), fill the rows with metric values based on entity and time.

## Query

```sql
SELECT entity, disk_used.datetime, cpu_busy.datetime, AVG(cpu_busy.value), AVG(disk_used.value), tags.*
  FROM cpu_busy
JOIN USING entity disk_used
  WHERE time > current_hour
  AND entity = 'nurswgvml007'
GROUP BY entity, tags, period(15 minute)
```

## Results

| entity       | disk_used.datetime       | cpu_busy.datetime        | AVG(cpu_busy.value) | AVG(disk_used.value) | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|--------------|--------------------------|--------------------------|---------------------|----------------------|----------------------------|-------------------------------------| 
| nurswgvml007 | 2016-06-17T19:00:00.000Z | 2016-06-17T19:00:00.000Z | 4.0                 | 1743850988.0         | /mnt/u113452               | //u113452.nurstr003/backup     | 
| nurswgvml007 | 2016-06-17T19:15:00.000Z | 2016-06-17T19:15:00.000Z | 11.2                | 1743850988.0         | /mnt/u113452               | //u113452.nurstr003/backup     | 
| nurswgvml007 | 2016-06-17T19:30:00.000Z | 2016-06-17T19:30:00.000Z | 5.4                 | 1743850988.0         | /mnt/u113452               | //u113452.nurstr003/backup     | 
| nurswgvml007 | 2016-06-17T19:00:00.000Z | 2016-06-17T19:00:00.000Z | 4.0                 | 8687733.0            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | 2016-06-17T19:15:00.000Z | 2016-06-17T19:15:00.000Z | 11.2                | 8679954.0            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | 2016-06-17T19:30:00.000Z | 2016-06-17T19:30:00.000Z | 5.4                 | 8684064.0            | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 

