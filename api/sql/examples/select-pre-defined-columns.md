# Select Pre-Defined Columns

## Query

```sql
SELECT entity, datetime, time, metric, value, tags, entity.groups
  FROM df.disk_used
WHERE entity = 'nurswgvml006' 
  AND datetime > now - 5 * minute
ORDER BY datetime
```

## Results

```ls
| entity       | datetime                 | time          | metric    | value        | tags                                                            | entity.groups                                                                                                                                           |  
|--------------|--------------------------|---------------|-----------|--------------|-----------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------| 
| nurswgvml006 | 2016-07-15T09:47:37.000Z | 1468576057000 | disk_used | 5613628.0    | file_system=/dev/mapper/vg_nurswgvml006-lv_root;mount_point=/   | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
| nurswgvml006 | 2016-07-15T09:47:37.000Z | 1468576057000 | disk_used | 53430536.0   | file_system=/dev/sdc1;mount_point=/media/datadrive              | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
| nurswgvml006 | 2016-07-15T09:47:37.000Z | 1468576057000 | disk_used | 1753141830.0 | file_system=//u113452.nurstr003/backup;mount_point=/mnt/u113452 | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
```
