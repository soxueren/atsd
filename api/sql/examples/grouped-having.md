# Grouped and Having

In this example the "not equal" operator `!=`, is used to exclude grouped rows with aggregation function delta equal to zero from the results.

## Query

```sql
SELECT entity, tags.mount_point AS mp, tags.file_system AS FS,
  MIN(df.disk_used.value), MAX(df.disk_used.value),  FIRST(df.disk_used.value), LAST(df.disk_used.value),
  DELTA(df.disk_used.value), COUNT(df.disk_used.value),  AVG(mpstat.cpu_busy.value) 
FROM mpstat.cpu_busy
  JOIN USING entity df.disk_used
WHERE time > now - 60 * minute
  GROUP BY entity, tags.mount_point, tags.file_system
  HAVING DELTA(df.disk_used.value) != 0
  ORDER BY DELTA(df.disk_used.value)
  DESC
```

## Results

```ls
| entity       | mp               | FS                                                     | MIN(disk_used.value) | MAX(disk_used.value) | FIRST(disk_used.value) | LAST(disk_used.value) | DELTA(disk_used.value) | COUNT(disk_used.value) | AVG(cpu_busy.value) | 
|--------------|------------------|--------------------------------------------------------|----------------------|----------------------|------------------------|-----------------------|------------------------|------------------------|---------------------| 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 5.0512204E7          | 5.0780556E7          | 5.0675844E7            | 5.0760228E7           | 84384.0                | 16.0                   | 26.3225             | 
| nurswgvml007 | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 8853224.0            | 8856120.0            | 8853304.0              | 8856120.0             | 2816.0                 | 15.0                   | 21.787333333333333  | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 8088372.0            | 8089452.0            | 8088528.0              | 8089148.0             | 620.0                  | 16.0                   | 26.3225             | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2482628.0            | 2483504.0            | 2482884.0              | 2483336.0             | 452.0                  | 15.0                   | 2.260666666666667   | 
| nurswgvml003 | /                | rootfs                                                 | 2482628.0            | 2483504.0            | 2482884.0              | 2483336.0             | 452.0                  | 15.0                   | 2.260666666666667   | 
| nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 1821076.0            | 1821236.0            | 1821076.0              | 1821236.0             | 160.0                  | 15.0                   | 1.2666666666666666  | 
| nurswgvml102 | /                | rootfs                                                 | 1821076.0            | 1821236.0            | 1821076.0              | 1821236.0             | 160.0                  | 15.0                   | 1.2666666666666666  | 
| nurswgvml010 | /                | /dev/sda1                                              | 5834992.0            | 5835140.0            | 5834992.0              | 5835140.0             | 148.0                  | 30.0                   | 0.9929999999999998  | 
| nurswgvml011 | /                | /dev/sda1                                              | 7056300.0            | 7057324.0            | 7056748.0              | 7056676.0             | -72.0                  | 15.0                   | 1.8646666666666667  | 
| nurswgvml011 | /mnt/backup      | 10.102.0.2:/backup                                     | 2482496.0            | 2483392.0            | 2482944.0              | 2482688.0             | -256.0                 | 15.0                   | 1.8646666666666667  | 
```