# Outer Join

Outer Join missing column values with NULL.

## Query

```sql
SELECT *
  FROM cpu_busy
OUTER JOIN disk_used
  WHERE time > current_hour
  AND entity = 'nurswgvml007'
```

## Results
| cpu_busy.entity | disk_used.entity | cpu_busy.time | disk_used.time | cpu_busy.value | disk_used.value | disk_used.tags.mount_point | disk_used.tags.file_system      | 
|--------------|--------------|---------------|---------------|------|-----------|------|-------------------------------------| 
| nurswgvml007 | nurswgvml007 | 1466192293000 | 1466192293000 | null | 8666448.0 | /    | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | nurswgvml007 | 1466192308000 | 1466192308000 | null | 8667224.0 | /    | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | nurswgvml007 | 1466192323000 | 1466192323000 | null | 8668068.0 | /    | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | nurswgvml007 | 1466192338000 | 1466192338000 | null | 8668920.0 | /    | /dev/mapper/vg_nurswgvml007-lv_root | 
| nurswgvml007 | nurswgvml007 | 1466190009000 | 1466190009000 | 3.0  | null      | null | null                                | 
| nurswgvml007 | nurswgvml007 | 1466190025000 | 1466190025000 | 4.0  | null      | null | null                                | 
| nurswgvml007 | nurswgvml007 | 1466190041000 | 1466190041000 | 5.0  | null      | null | null                                | 
| nurswgvml007 | nurswgvml007 | 1466190057000 | 1466190057000 | 5.0  | null      | null | null                                | 



