# Select All Columns

Computed columns can be created by applying functions and arithmetic expression to existing columns. 

## Query Without Series Tags

```sql
SELECT datetime, entity, t1.value, t2.value, t1.value + t2.value AS total_cpu
  FROM "mpstat.cpu_system" t1
  JOIN "mpstat.cpu_user" t2
WHERE datetime > now - 1*MINUTE
```

## Results

```ls
| datetime                 | entity       | t1.value | t2.value | total_cpu | 
|--------------------------|--------------|----------|----------|-----------| 
| 2016-07-15T14:40:16.000Z | nurswgvml006 | 3.0      | 5.9      | 8.9       | 
| 2016-07-15T14:40:32.000Z | nurswgvml006 | 1.0      | 1.0      | 2.0       | 
| 2016-07-15T14:40:48.000Z | nurswgvml006 | 3.2      | 64.9     | 68.1      | 
| 2016-07-15T14:40:06.000Z | nurswgvml007 | 7.4      | 34.0     | 41.5      | 
```

## Query With Functions

```sql
SELECT datetime, entity, max(t1.value), max(t2.value), max(t1.value) + max(t2.value), max(t1.value + t2.value) AS max_total_cpu
  FROM "mpstat.cpu_system" t1
  JOIN "mpstat.cpu_user" t2
WHERE datetime > now - 1*MINUTE
```

## Results

```ls
| datetime                 | entity       | max(t1.value) | max(t2.value) | max(t1.value)+max(t2.value) | max_total_cpu | 
|--------------------------|--------------|---------------|---------------|-----------------------------|---------------| 
| 2016-07-15T14:41:58.000Z | nurswgvml007 | 2.0           | 24.8          | 26.8                        | 25.1          | 
```