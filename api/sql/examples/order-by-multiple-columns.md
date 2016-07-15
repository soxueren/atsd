# ORDER BY Multiple Columns

## Query

```sql
SELECT entity, tags.file_system, tags.mount_point, 
  min(value), 
  max(value), 
  max(value) - min(value) AS range, 
  delta(value)
 FROM df.disk_used
WHERE datetime >= now - 1 * HOUR
 GROUP BY entity, tags
ORDER BY tags.mount_point, delta(value) DESC
```

## Results

```ls
| entity       | tags.file_system                                       | tags.mount_point | min(value) | max(value) | range   | delta(value) | 
|--------------|--------------------------------------------------------|------------------|------------|------------|---------|--------------| 
| nurswgvml007 | /dev/mapper/vg_nurswgvml007-lv_root                    | /                | 9051408    | 9191316    | 139908  | 70444        | 
| nurswgvml011 | /dev/sda1                                              | /                | 6786872    | 6796956    | 10084   | 9868         | 
| nurswgvml010 | /dev/sda1                                              | /                | 6682096    | 6815304    | 133208  | 1060         | 
| nurswgvml502 | /dev/sda1                                              | /                | 30369620   | 30501872   | 132252  | 916          | 
| nurswgvml006 | /dev/mapper/vg_nurswgvml006-lv_root                    | /                | 5617520    | 5618388    | 868     | 852          | 
| nurswgvml102 | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | /                | 1553224    | 1553372    | 148     | 140          | 
| nurswgvml102 | rootfs                                                 | /                | 1553224    | 1553372    | 148     | 140          | 
| nurswgvml009 | /dev/mapper/vg_nurswgvml009-lv_root                    | /                | 12444764   | 12444864   | 100     | 96           | 
| nurswgvml010 | /dev/sdb1                                              | /app             | 29352556   | 30657024   | 1304468 | 309400       | 
| nurswgvml006 | /dev/sdc1                                              | /media/datadrive | 53406264   | 53983484   | 577220  | -429412      | 
| nurswgvml009 | /dev/sdb1                                              | /opt             | 30705640   | 30717788   | 12148   | -10988       | 
```

