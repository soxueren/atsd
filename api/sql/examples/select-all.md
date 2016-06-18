# SELECT All

## Query

```sql
SELECT * FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' 
AND time between now - 1 * hour AND now
 LIMIT 3
```

## Results 

| entity       | time          | value | 
|--------------|---------------|-------| 
| nurswgvml007 | 1446034244000 | 35.71 | 
| nurswgvml007 | 1446034260000 | 39.78 | 
| nurswgvml007 | 1446034276000 | 16.0  | 

## Query for Series with Tags

```sql
SELECT * FROM disk_used
 WHERE entity = 'nurswgvml007' 
 AND time > now - 1 * hour
 ORDER BY time
 LIMIT 5
```

## Results

| entity       | time          | value        | tags.mount_point | tags.file_system                    | 
|--------------|---------------|--------------|------------------|-------------------------------------| 
| nurswgvml007 | 1466279748000 | 8693952.0    | /                | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | 1466279748000 | 1744011571.0 | /mnt/u113452     | //u113452.nurstr003/backup          | 
| nurswgvml007 | 1466279763000 | 8694648.0    | /                | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | 1466279763000 | 1744011571.0 | /mnt/u113452     | //u113452.nurstr003/backup          | 
| nurswgvml007 | 1466279778000 | 8695440.0    | /                | /dev/mapper/vg_nurswgvml007-lv_root | 




