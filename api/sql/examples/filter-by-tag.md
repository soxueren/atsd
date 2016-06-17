# Filter by Tag

One metric, one entity, filter by tag, detailed values for time range

## Query

```sql
SELECT time, value, tags.file_system 
  FROM df.df.disk_used_percent 
WHERE entity = 'nurswgvml007'
  AND tags.file_system LIKE '/d%' 
  AND time between now - 1 * hour AND now
```

## Results

| time          | value   | tags.file_system                    | 
|---------------|---------|-------------------------------------| 
| 1446033205000 | 72.2738 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033220000 | 72.275  | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033236000 | 72.2697 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033282000 | 72.2749 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033297000 | 72.2763 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033312000 | 72.2706 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033327000 | 72.2722 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033342000 | 72.2736 | /dev/mapper/vg_nurswgvml007-lv_root | 
| 1446033357000 | 72.2747 | /dev/mapper/vg_nurswgvml007-lv_root | 
