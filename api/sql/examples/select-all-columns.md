# Select All Columns

`SELECT *` can be used to include all available columns in the resultset.

The list of available columns includes datetime, entity, value, and optional series tags, each in its own column.

> Note that the `SELECT *` expression does not include `time`, `entity.tags`, `metric.tags` columns.

## Query Without Series Tags

```sql
SELECT * 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml006' 
  AND datetime > now - 5 * minute
```

## Results

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|-------| 
| nurswgvml006 | 2016-07-15T09:25:39.000Z | 3.0   | 
| nurswgvml006 | 2016-07-15T09:25:55.000Z | 7.1   | 
| nurswgvml006 | 2016-07-15T09:26:11.000Z | 16.3  | 
```

## Query With Series Tags

```sql
SELECT * 
  FROM df.disk_used
WHERE entity = 'nurswgvml006' 
  AND datetime > now - 5 * minute
ORDER BY datetime
```

## Results

```ls
| entity       | datetime                 | value        | tags.mount_point | tags.file_system                    | 
|--------------|--------------------------|--------------|------------------|-------------------------------------| 
| nurswgvml006 | 2016-07-15T09:26:21.000Z | 5613504.0    | /                | /dev/mapper/vg_nurswgvml006-lv_root | 
| nurswgvml006 | 2016-07-15T09:26:21.000Z | 53449656.0   | /media/datadrive | /dev/sdc1                           | 
| nurswgvml006 | 2016-07-15T09:26:21.000Z | 1753141830.0 | /mnt/u113452     | //u113452.nurstr003/backup          | 
```

## Query - JOIN

If the query is joining multiple tables, the list of columns in the result set includes columns from each table.

In case of `JOIN`, column names are fully qualified and include both table name (alias) and column name.

```sql
SELECT * 
  FROM df.disk_used
  JOIN USING entity mpstat.cpu_busy
WHERE entity = 'nurswgvml006' 
  AND datetime > now - 5 * minute
```

## Results

```ls
| disk_used.entity | disk_used.datetime       | disk_used.value | disk_used.tags.mount_point | disk_used.tags.file_system          | cpu_busy.entity | cpu_busy.datetime        | cpu_busy.value | 
|------------------|--------------------------|-----------------|----------------------------|-------------------------------------|-----------------|--------------------------|----------------| 
| nurswgvml006     | 2016-07-15T09:36:51.000Z | 1753141830.0    | /mnt/u113452               | //u113452.nurstr003/backup          | nurswgvml006    | 2016-07-15T09:36:51.000Z | 6.1            | 
| nurswgvml006     | 2016-07-15T09:36:51.000Z | 5613572.0       | /                          | /dev/mapper/vg_nurswgvml006-lv_root | nurswgvml006    | 2016-07-15T09:36:51.000Z | 6.1            | 
| nurswgvml006     | 2016-07-15T09:36:51.000Z | 53436128.0      | /media/datadrive           | /dev/sdc1                           | nurswgvml006    | 2016-07-15T09:36:51.000Z | 6.1            | 
```



