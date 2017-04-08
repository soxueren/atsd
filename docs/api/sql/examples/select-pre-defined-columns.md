# Select Pre-Defined Columns

## Query

```sql
SELECT entity, datetime, time, metric, value, tags, metric.tags, entity.tags, entity.groups
  FROM df.disk_used
WHERE entity = 'nurswgvml006' 
  AND datetime > now - 5 * minute
ORDER BY datetime
```

## Results

```ls
| entity       | datetime                 | time          | metric    | value        | tags                                                                 | metric.tags         | entity.tags                                          | entity.groups                                                                                                                                           | 
|--------------|--------------------------|---------------|-----------|--------------|----------------------------------------------------------------------|---------------------|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------| 
| nurswgvml006 | 2016-08-08T08:57:03.000Z | 1470646623000 | disk_used | 5688556.0    | file_system=/dev/mapper/vg_nurswgvml006-lv_root;mount_point=/        | table=Disk (script) | app=Hadoop/HBASE;ip=10.102.0.5;loc_area=dc1;os=Linux | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
| nurswgvml006 | 2016-08-08T08:57:03.000Z | 1470646623000 | disk_used | 59204844.0   | file_system=/dev/sdc1;mount_point=/media/datadrive                   | table=Disk (script) | app=Hadoop/HBASE;ip=10.102.0.5;loc_area=dc1;os=Linux | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
| nurswgvml006 | 2016-08-08T08:57:03.000Z | 1470646623000 | disk_used | 1693287354.0 | file_system=//u113452.nurstr003/backup;mount_point=/mnt/u113452      | table=Disk (script) | app=Hadoop/HBASE;ip=10.102.0.5;loc_area=dc1;os=Linux | java-loggers;nmon-linux;nmon-linux-beta;nmon-sub-group;nur-collectors;scollector-linux;scollector-nur;solarwind-vmware-vm;tcollector - linux;VMware VMs | 
```
