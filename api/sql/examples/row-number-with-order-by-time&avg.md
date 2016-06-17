# ROW_NUMBER with ORDER BY time & avg

`tags` - concatenates all tags in one column.

## Query

```sql
SELECT entity, tags, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY time DESC, avg(value) ASC) <=5
  ORDER BY entity
```

## Results

| entity       | tags                                                                             | datetime             | Avg(value)           | 
|--------------|----------------------------------------------------------------------------------|----------------------|----------------------| 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:30:00Z | 2433912.1666666665   | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:15:00Z | 2433866.6            | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T13:00:00Z | 2433769.1333333333   | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T12:45:00Z | 2433680.6            | 
| awsswgvml001 | file_system=/dev/xvda1;mount_point=/                                             | 2015-10-28T12:30:00Z | 2433610.7333333334   | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:30:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:15:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T13:00:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T12:45:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdf;mount_point=/backup                                        | 2015-10-28T12:30:00Z | 6.5373272E7          | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:30:00Z | 2.4670714E7          | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:15:00Z | 2.4670594733333334E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T13:00:00Z | 2.4670553466666665E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T12:45:00Z | 2.4670378466666665E7 | 
| awsswgvml001 | file_system=/dev/xvdh1;mount_point=/data                                         | 2015-10-28T12:30:00Z | 2.4670224266666666E7 | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:30:00Z | 2428777.8775510206   | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | file_system=/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff;mount_point=/ | 2015-10-28T12:30:00Z | 2428582.466666667    | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:30:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:15:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T13:00:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T12:45:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=/dev/sdb1;mount_point=/home/store                                    | 2015-10-28T12:30:00Z | 1.39204048E8         | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:30:00Z | 2428777.8775510206   | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | file_system=rootfs;mount_point=/                                                 | 2015-10-28T12:30:00Z | 2428582.466666667    | 
