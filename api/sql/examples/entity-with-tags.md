# Entity with Series Tags

## Query

```sql
SELECT count(*), entity, tags.*, period (30 minute) FROM df.disk_used 
 WHERE entity = 'nurswgvml102' AND tags.mount_point = '/' 
 AND tags.file_system = '/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a' 
 AND datetime BETWEEN '2015-07-08T16:00:00Z' AND '2015-07-08T16:30:00Z' 
 GROUP BY entity, tags, period (30 minute)
```

## Results

| count(*) | entity       | tags.mount_point | tags.file_system                                       | period(30 MINUTE) | 
|----------|--------------|------------------|--------------------------------------------------------|-------------------| 
| 120.0    | nurswgvml102 | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 1436371200000     | 

## GROUP BY Query with HAVING

> Request

```sql
SELECT entity, avg(value), count(*) 
  FROM mpstat.cpu_busy 
WHERE time > now - 1* hour 
  GROUP BY entity 
  HAVING avg(value) > 10 AND count(*) > 200
```

## Results

| entity       | avg(value)         | count(*) | 
|--------------|--------------------|----------| 
| awsswgvml001 | 13.290133333333332 | 225.0    | 
| nurswgvml006 | 21.918035714285715 | 224.0    | 
| nurswgvml007 | 47.27337777777781  | 225.0    | 
