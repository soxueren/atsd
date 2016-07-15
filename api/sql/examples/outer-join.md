# Outer Join

Outer Join missing column values filled with NULL.

## Query

```sql
SELECT *
  FROM mpstat.cpu_busy
OUTER JOIN df.disk_used
  WHERE datetime > current_hour
  AND entity = 'nurswgvml007'
```

## Results

```ls
| cpu_busy.entity | disk_used.entity | cpu_busy.time | disk_used.time | cpu_busy.value | disk_used.value | disk_used.tags.mount_point | disk_used.tags.file_system          | 
|-----------------|------------------|---------------|----------------|----------------|-----------------|----------------------------|-------------------------------------| 
| nurswgvml007    | nurswgvml007     | 1466289428000 | 1466289428000  | null           | 1744011571.0    | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007    | nurswgvml007     | 1466289443000 | 1466289443000  | null           | 1744011571.0    | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007    | nurswgvml007     | 1466289458000 | 1466289458000  | null           | 1744011571.0    | /mnt/u113452               | //u113452.nurstr003/backup          | 
| nurswgvml007    | nurswgvml007     | 1466289428000 | 1466289428000  | null           | 8729956.0       | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007    | nurswgvml007     | 1466289443000 | 1466289443000  | null           | 8730752.0       | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007    | nurswgvml007     | 1466289458000 | 1466289458000  | null           | 8731676.0       | /                          | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007    | nurswgvml007     | 1466289426000 | 1466289426000  | 15.3           | null            | null                       | null                                | 
| nurswgvml007    | nurswgvml007     | 1466289442000 | 1466289442000  | 3.1            | null            | null                       | null                                | 
| nurswgvml007    | nurswgvml007     | 1466289458000 | 1466289458000  | 4.0            | null            | null                       | null                                | 
```



