# String Functions

String functions are supported in the `SELECT` expression, the `WHERE`, `GROUP BY`, `HAVING`, and `ORDER BY` clauses.

## Concatenation and String Length

```sql
SELECT datetime, entity,
  concat(tags.disk, ':') AS drive, 
  value/POWER(2, 30) as used_gb
  FROM win.disk.fs.space_used 
WHERE datetime > current_minute
  AND LENGTH(tags.disk) = 1
```

### Results

```ls
| datetime                 | entity       | drive | used_gb | 
|--------------------------|--------------|-------|---------| 
| 2016-10-03T11:37:01.000Z | nurswgvmw015 | C:    | 10.9    | 
| 2016-10-03T11:37:09.000Z | nurswgvmw016 | C:    | 32.2    | 
| 2016-10-03T11:37:11.000Z | nurswgvmw014 | C:    | 42.2    | 
| 2016-10-03T11:37:11.000Z | nurswgvmw014 | E:    | 19.0    | 
```

## Concatenate Multiple Columns

```sql
SELECT datetime, entity, concat('nfs:', tags.file_system, '/') AS drive, value/POWER(2, 20) as used_gb
  FROM df.disk_used 
WHERE datetime > current_hour
  AND LOCATE('.com', tags.file_system) > 0 AND LOCATE('.com', tags.file_system) < LOCATE('/', tags.file_system, 3)
  ORDER BY datetime 
```

### Results

```ls
| datetime                 | entity       | drive                              | used_gb | 
|--------------------------|--------------|------------------------------------|---------| 
| 2016-10-03T11:44:01.000Z | nurswgvml502 | nfs://nurstr01.axibase.com/backup/ | 1650.8  | 
| 2016-10-03T11:44:03.000Z | nurswgvml006 | nfs://nurstr01.axibase.com/backup/ | 1650.8  | 
| 2016-10-03T11:44:11.000Z | nurswgvml007 | nfs://nurstr01.axibase.com/backup/ | 1650.8  | 
```

## Filter By Modified Entity Tags

```sql
SELECT entity, datetime, value, tags.*, concat(entity.tags.app, '@', entity.tags.environment) AS appl
  FROM disk_used
WHERE datetime > previous_minute
AND REPLACE(entity.tags.environment, 'production', 'prod') = 'prod'
  WITH ROW_NUMBER(entity ORDER BY time DESC) <= 1
```

### Results

```ls
| entity       | datetime                 | value   | tags.file_system                    | tags.mount_point | appl                       | 
|--------------|--------------------------|---------|-------------------------------------|------------------|----------------------------| 
| nurswgvml006 | 2016-10-03T13:41:26.000Z | 8346084 | /dev/mapper/vg_nurswgvml006-lv_root | /                | Hadoop/HBASE@prod          | 
| nurswgvml007 | 2016-10-03T13:41:32.000Z | 8220616 | /dev/mapper/vg_nurswgvml007-lv_root | /                | ATSD@prod                  | 
| nurswgvml010 | 2016-10-03T13:41:32.000Z | 7185520 | /dev/sda1                           | /                | SVN, Jenkins, Redmine@prod | 
```

