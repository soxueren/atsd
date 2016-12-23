# CASE Expression

## Categorize Series By Value Range

```sql
SELECT entity, avg(value),
    CASE
      WHEN avg(value) < 20 THEN 'under-utilized'
      WHEN avg(value) > 80 THEN 'over-utilized'
      ELSE 'right-sized'
    END AS 'Utilization'
  FROM mpstat.cpu_busy
WHERE datetime > current_day
  GROUP BY entity
```

### Results

```ls
| entity       | avg(value) | Utilization    |
|--------------|------------|----------------|
| nurswgvml006 | 4.8        | under-utilized |
| nurswgvml007 | 7.9        | under-utilized |
| nurswgvml010 | 3.6        | under-utilized |
| nurswgvml301 | 0.6        | under-utilized |
| nurswgvml502 | 3.5        | under-utilized |
```

## Create Derived Columns - String

```sql
SELECT entity, tags.*, value,
  CASE
    WHEN LOCATE('//', tags.file_system) = 1 THEN 'nfs'
    ELSE 'local'
  END AS 'fs_type'
  FROM df.disk_used
WHERE datetime > current_hour
  WITH ROW_NUMBER(entity, tags ORDER BY time DESC) <= 1
```

### Results

```ls
| entity       | tags.file_system                    | tags.mount_point        | value        | fs_type |
|--------------|-------------------------------------|-------------------------|--------------|---------|
| nurswgvml006 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | nfs     |
| nurswgvml006 | /dev/mapper/vg_nurswgvml006-lv_root | /                       | 6065376.0    | local   |
| nurswgvml006 | /dev/sdc1                           | /media/datadrive        | 56229332.0   | local   |
| nurswgvml007 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | nfs     |
| nurswgvml007 | /dev/mapper/vg_nurswgvml007-lv_root | /                       | 9051904.0    | local   |
| nurswgvml010 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | nfs     |
| nurswgvml010 | //u1134.store02/backup/jenkins_data | /mnt/u1134/jenkins_data | 1791263474.0 | nfs     |
| nurswgvml010 | /dev/sda1                           | /                       | 7489100.0    | local   |
| nurswgvml010 | /dev/sdb1                           | /app                    | 30978888.0   | local   |
| nurswgvml301 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | nfs     |
| nurswgvml301 | /dev/sda1                           | /                       | 1428792.0    | local   |
| nurswgvml502 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | nfs     |
| nurswgvml502 | /dev/sda1                           | /                       | 31847840.0   | local   |
```

## Create Derived Columns - Numeric

```sql
SELECT entity, tags.*, value,
  CASE
    WHEN LOCATE('//', tags.file_system) = 1 THEN 1
    ELSE 0
  END AS 'fs_type'
  FROM df.disk_used
WHERE datetime > current_hour
  WITH ROW_NUMBER(entity, tags ORDER BY time DESC) <= 1
```

### Results

```ls
| entity       | tags.file_system                    | tags.mount_point        | value        | fs_type |
|--------------|-------------------------------------|-------------------------|--------------|---------|
| nurswgvml006 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | 1.0     |
| nurswgvml006 | /dev/mapper/vg_nurswgvml006-lv_root | /                       | 6065396.0    | 0.0     |
| nurswgvml006 | /dev/sdc1                           | /media/datadrive        | 56238020.0   | 0.0     |
| nurswgvml007 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | 1.0     |
| nurswgvml007 | /dev/mapper/vg_nurswgvml007-lv_root | /                       | 9060008.0    | 0.0     |
| nurswgvml010 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | 1.0     |
| nurswgvml010 | //u1134.store02/backup/jenkins_data | /mnt/u1134/jenkins_data | 1791263474.0 | 1.0     |
| nurswgvml010 | /dev/sda1                           | /                       | 7489108.0    | 0.0     |
| nurswgvml010 | /dev/sdb1                           | /app                    | 30978888.0   | 0.0     |
| nurswgvml301 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | 1.0     |
| nurswgvml301 | /dev/sda1                           | /                       | 1428796.0    | 0.0     |
| nurswgvml502 | //u1134.store02/backup              | /mnt/u1134              | 1791263474.0 | 1.0     |
| nurswgvml502 | /dev/sda1                           | /                       | 31847924.0   | 0.0     | 
```

## Handle NaN and NULL Values

```sql
SELECT entity, datetime, value, text, ISNULL(text, 'IN: text is null'), ISNULL(value, 'IN: value is null'),
  CASE
    WHEN value IS NULL THEN 'CASE: val is null'
    WHEN text  IS NULL THEN 'CASE: txt is null'
    ELSE 'ok'
  END AS null_check
  FROM atsd_series
WHERE metric IN ('temperature', 'status') AND datetime >= '2016-10-13T08:00:00Z'
```

### Results

```ls
| entity   | datetime                 | value | text                           | ISNULL(text,'IN: text is null') | ISNULL(value,'IN: value is null') | null_check        |
|----------|--------------------------|-------|--------------------------------|---------------------------------|-----------------------------------|-------------------|
| sensor-1 | 2016-10-13T10:30:00.000Z | NaN   | Shutdown by adm-user, RFC-5434 | Shutdown by adm-user, RFC-5434  | IN: value is null                 | CASE: val is null |
| sensor-1 | 2016-10-13T08:00:00.000Z | 20.3  | null                           | IN: text is null                | 20.3                              | CASE: txt is null |
| sensor-1 | 2016-10-13T08:15:00.000Z | 24.4  | Provisional                    | Provisional                     | 24.4                              | ok                |
```

## ISNULL Alternative

```sql
SELECT entity, datetime, value, text,
  CASE
    WHEN value IS NOT NULL THEN value
    ELSE -1
  END AS case_check_1,
  CASE
    WHEN text IS NULL THEN 'CASE: text column is null'
    ELSE text
  END AS case_check_2
  FROM atsd_series
WHERE metric IN ('temperature', 'status') AND datetime >= '2016-10-13T08:00:00Z'
```

### Results

```ls
| entity   | datetime                 | value | text                           | case_check_1 | case_check_2                   |
|----------|--------------------------|-------|--------------------------------|--------------|--------------------------------|
| sensor-1 | 2016-10-13T10:30:00.000Z | NaN   | Shutdown by adm-user, RFC-5434 | -1.0         | Shutdown by adm-user, RFC-5434 |
| sensor-1 | 2016-10-13T08:00:00.000Z | 20.3  | null                           | 20.3         | CASE: text column is null      |
| sensor-1 | 2016-10-13T08:15:00.000Z | 24.4  | Provisional                    | 24.4         | Provisional                    |
```
