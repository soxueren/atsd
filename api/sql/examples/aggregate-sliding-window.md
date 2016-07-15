# Aggregate - Sliding Window

## Window without series tag grouping

### Query

```sql
SELECT entity, avg(value), max(value), last(value), count(*)
 FROM mpstat.cpu_busy
WHERE datetime >= now - 1 * HOUR
 GROUP BY entity
```

### Results

```ls
| entity       | avg(value) | max(value) | last(value) | count(*) | 
|--------------|------------|------------|-------------|----------| 
| nurswgvml006 | 20         | 100        | 50          | 223      | 
| nurswgvml007 | 16         | 100        | 49          | 223      | 
| nurswgvml009 | 16         | 100        | 10          | 225      | 
| nurswgvml010 | 14         | 100        | 68          | 223      | 
| nurswgvml011 | 6          | 100        | 0           | 226      | 
| nurswgvml102 | 1          | 9          | 1           | 224      | 
| nurswgvml502 | 5          | 75         | 28          | 223      | 
```

## Window with Tags

### Query

```sql
SELECT entity, datetime, tags, avg(value), max(value), last(value), count(*)
 FROM df.disk_used
WHERE datetime >= now - 1 * HOUR
 GROUP BY entity, tags
```

### Results

```ls
| entity       | datetime                 | tags                                                          | avg(value) | max(value) | last(value) | count(*) | 
|--------------|--------------------------|---------------------------------------------------------------|------------|------------|-------------|----------| 
| nurswgvml102 | 2016-07-15T14:06:16.000Z | file_system=/dev/disk/by-uuid/8a5a178f;mount_point=/          | 1553218    | 1553300    | 1553300     | 238      | 
| nurswgvml006 | 2016-07-15T14:06:09.000Z | file_system=/dev/mapper/vg_nurswgvml006-lv_root;mount_point=/ | 5617505    | 5617872    | 5617872     | 238      | 
| nurswgvml007 | 2016-07-15T14:06:10.000Z | file_system=/dev/mapper/vg_nurswgvml007-lv_root;mount_point=/ | 9082739    | 9169668    | 9169668     | 239      | 
| nurswgvml009 | 2016-07-15T14:06:07.000Z | file_system=/dev/mapper/vg_nurswgvml009-lv_root;mount_point=/ | 12444758   | 12444812   | 12444804    | 240      | 
| nurswgvml010 | 2016-07-15T14:06:18.000Z | file_system=/dev/sda1;mount_point=/                           | 6682074    | 6682524    | 6682524     | 238      | 
| nurswgvml011 | 2016-07-15T14:06:15.000Z | file_system=/dev/sda1;mount_point=/                           | 6787113    | 6795684    | 6794820     | 243      | 
| nurswgvml502 | 2016-07-15T14:06:11.000Z | file_system=/dev/sda1;mount_point=/                           | 30500874   | 30501064   | 30501064    | 239      | 
| nurswgvml010 | 2016-07-15T14:06:18.000Z | file_system=/dev/sdb1;mount_point=/app                        | 30123650   | 30293432   | 30090640    | 238      | 
| nurswgvml009 | 2016-07-15T14:06:07.000Z | file_system=/dev/sdb1;mount_point=/opt                        | 30713820   | 30717788   | 30709056    | 240      | 
| nurswgvml006 | 2016-07-15T14:06:09.000Z | file_system=/dev/sdc1;mount_point=/media/datadrive            | 53902399   | 54246992   | 53426820    | 238      | 
| nurswgvml102 | 2016-07-15T14:06:16.000Z | file_system=rootfs;mount_point=/                              | 1553218    | 1553300    | 1553300     | 238      | 
```
