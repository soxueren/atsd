# ROW_NUMBER with ORDER BY avg

Retrieve top-3 15-minute periods with maximum average disk usage, for each disk matching '/dev*' pattern.

## Query

```sql
SELECT entity, tags.*, datetime, avg(value)
  FROM disk_used 
WHERE datetime > now - 1 * day
  AND tags.file_system LIKE '/dev/*'
GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY avg(value) DESC) <= 3
```

## Results

| entity       | tags.mount_point | tags.file_system                                       | datetime                 | avg(value) | 
|--------------|------------------|--------------------------------------------------------|--------------------------|-----------:| 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 2016-06-18T20:30:00.000Z | 5413803.1  | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 2016-06-18T20:15:00.000Z | 5413709.1  | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 2016-06-18T20:00:00.000Z | 5413353.0  | 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 2016-06-18T02:45:00.000Z | 53977000.1 | 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 2016-06-18T01:30:00.000Z | 53704456.3 | 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 2016-06-18T02:00:00.000Z | 52554126.5 | 
| nurswgvml007 | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 2016-06-18T20:30:00.000Z | 8726755.3  | 
| nurswgvml007 | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 2016-06-18T20:15:00.000Z | 8722492.9  | 
| nurswgvml007 | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 2016-06-18T20:00:00.000Z | 8719981.4  | 
| nurswgvml010 | /                | /dev/sda1                                              | 2016-06-18T06:30:00.000Z | 6565711.1  | 
| nurswgvml010 | /                | /dev/sda1                                              | 2016-06-18T06:15:00.000Z | 6504368.9  | 
| nurswgvml010 | /                | /dev/sda1                                              | 2016-06-18T03:45:00.000Z | 6482958.6  | 
| nurswgvml010 | /app             | /dev/sdb1                                              | 2016-06-18T02:00:00.000Z | 30443142.1 | 
| nurswgvml010 | /app             | /dev/sdb1                                              | 2016-06-18T19:30:00.000Z | 30440675.5 | 
| nurswgvml010 | /app             | /dev/sdb1                                              | 2016-06-18T13:30:00.000Z | 30440674.3 | 
| nurswgvml011 | /                | /dev/sda1                                              | 2016-06-18T20:30:00.000Z | 6860848.4  | 
| nurswgvml011 | /                | /dev/sda1                                              | 2016-06-18T20:15:00.000Z | 6860539.4  | 
| nurswgvml011 | /                | /dev/sda1                                              | 2016-06-18T19:45:00.000Z | 6859553.2  | 
| nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 2016-06-18T20:30:00.000Z | 1528761.0  | 
| nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 2016-06-18T20:15:00.000Z | 1528724.0  | 
| nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 2016-06-18T20:00:00.000Z | 1528686.6  | 
| nurswgvml502 | /                | /dev/sda1                                              | 2016-06-18T20:30:00.000Z | 30142965.6 | 
| nurswgvml502 | /                | /dev/sda1                                              | 2016-06-18T19:45:00.000Z | 30142827.9 | 
| nurswgvml502 | /                | /dev/sda1                                              | 2016-06-18T20:15:00.000Z | 30142776.7 | 

