# Select Entity Tags as Columns

## Query

```sql
SELECT entity, entity.tags.os AS os, entity.tags.ip AS ip, avg(value), tags.* 
  FROM df.disk_used 
WHERE time > now - 1*HOUR 
  GROUP BY entity, tags
  ORDER BY time
```

## Results

```ls
| entity       | os    | ip          | avg(value)   | tags.mount_point          | tags.file_system                                       | 
|--------------|-------|-------------|--------------|---------------------------|--------------------------------------------------------| 
| nurswgvml006 | Linux | 10.102.0.5  | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml502 | null  | null        | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml006 | Linux | 10.102.0.5  | 5413676.4    | /                         | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml502 | null  | null        | 30142875.9   | /                         | /dev/sda1                                              | 
| nurswgvml006 | Linux | 10.102.0.5  | 50502897.1   | /media/datadrive          | /dev/sdc1                                              | 
| nurswgvml007 | Linux | 10.102.0.6  | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml007 | Linux | 10.102.0.6  | 8722716.1    | /                         | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml011 | Linux | 10.102.0.10 | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml011 | Linux | 10.102.0.10 | 6860249.7    | /                         | /dev/sda1                                              | 
| nurswgvml010 | Linux | 10.102.0.9  | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml102 | Linux | 10.102.0.1  | 1744011571.0 | /mnt/u113452              | //u113452.nurstr003/backup                             | 
| nurswgvml010 | Linux | 10.102.0.9  | 1744011571.0 | /mnt/u113452/jenkins_data | //u113452.nurstr003/backup/jenkins_data                | 
| nurswgvml102 | Linux | 10.102.0.1  | 1528738.8    | /                         | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml010 | Linux | 10.102.0.9  | 6480247.9    | /                         | /dev/sda1                                              | 
| nurswgvml010 | Linux | 10.102.0.9  | 30440670.4   | /app                      | /dev/sdb1                                              | 
| nurswgvml102 | Linux | 10.102.0.1  | 1528738.8    | /                         | rootfs                                                 | 
```

