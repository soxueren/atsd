# Join

## Joining Metrics Collected at the Same Time

The metrics have to be collected at the same time for the timestamps to be equal.

```sql
SELECT t1.entity, t1.datetime, t1.value, t2.value
  FROM mpstat.cpu_busy t1
JOIN mpstat.cpu_idle t2
  WHERE t1.datetime > now - 1 * hour
  AND t1.entity = 'nurswgvml006'
```

### Results

```ls
| entity       | datetime             | t1.value | t2.value |
|--------------|----------------------|----------|----------|
| nurswgvml006 | 2016-06-18T21:49:53Z | 14.1     | 85.9     |
| nurswgvml006 | 2016-06-18T21:50:09Z | 88.0     | 12.0     |
| nurswgvml006 | 2016-06-18T21:50:25Z | 8.0      | 92.0     |
| nurswgvml006 | 2016-06-18T21:50:41Z | 6.1      | 93.9     |
| nurswgvml006 | 2016-06-18T21:50:57Z | 2.0      | 98.0     |
| nurswgvml006 | 2016-06-18T21:51:13Z | 4.0      | 96.0     |
```

## Joining Metrics Collected at the Different Timestamps

The `JOIN` operation merges rows with the same timestamp, entity, and tags. Rows that have different timestamps are there will be excluded from the resultset.

```sql
SELECT t1.entity, t1.datetime, t1.value,
       t2.entity, t2.datetime, t2.value
FROM mpstat.cpu_busy t1
  JOIN meminfo.memfree t2
WHERE t1.datetime BETWEEN '2017-04-08T07:01:00Z' AND '2017-04-08T07:02:00Z'
  AND t1.entity = 'nurswgvml006'
```

```ls
| t1.entity    | t1.datetime          | t1.value | t2.entity    | t2.datetime          | t2.value |
|--------------|----------------------|----------|--------------|----------------------|----------|
| nurswgvml006 | 2017-04-08T07:01:27Z | 3.0      | nurswgvml006 | 2017-04-08T07:01:27Z | 70820.0  |
```

Rows with different timestamps can be included with `OUTER JOIN` with `NULL` used to fill missing values.

```sql
SELECT t1.entity, t1.datetime, t1.value,
       t2.entity, t2.datetime, t2.value
FROM mpstat.cpu_busy t1
  OUTER JOIN meminfo.memfree t2
WHERE t1.datetime BETWEEN '2017-04-08T07:01:00Z' AND '2017-04-08T07:02:00Z'
  AND t1.entity = 'nurswgvml006'
```

```ls
| t1.entity    | t1.datetime          | t1.value | t2.entity    | t2.datetime          | t2.value |
|--------------|----------------------|----------|--------------|----------------------|----------|
| nurswgvml006 | 2017-04-08T07:01:11Z | 6.1      | null         | null                 | null     |
| null         | null                 | null     | nurswgvml006 | 2017-04-08T07:01:12Z | 71276.0  |
| nurswgvml006 | 2017-04-08T07:01:27Z | 3.0      | nurswgvml006 | 2017-04-08T07:01:27Z | 70820.0  | <-- these rows have the same timestamp
| null         | null                 | null     | nurswgvml006 | 2017-04-08T07:01:42Z | 69944.0  |
| nurswgvml006 | 2017-04-08T07:01:43Z | 2.0      | null         | null                 | null     |
| null         | null                 | null     | nurswgvml006 | 2017-04-08T07:01:57Z | 75928.0  |
| nurswgvml006 | 2017-04-08T07:01:59Z | 10.9     | null         | null                 | null     |
```

Alternatively, the merged series can be regularized using `WITH INTERPOLATE` option.

```sql
SELECT t1.entity, t1.datetime, t1.value,
       t2.entity, t2.datetime, t2.value
FROM mpstat.cpu_busy t1
  JOIN meminfo.memfree t2
WHERE t1.datetime BETWEEN '2017-04-08T07:01:00Z' AND '2017-04-08T07:02:00Z'
  AND t1.entity = 'nurswgvml006'
  WITH INTERPOLATE(15 SECOND, PREVIOUS, OUTER)
```

```ls
| t1.entity    | t1.datetime          | t1.value | t2.entity    | t2.datetime          | t2.value |
|--------------|----------------------|----------|--------------|----------------------|----------|
| nurswgvml006 | 2017-04-08T07:01:00Z | 5.0      | nurswgvml006 | 2017-04-08T07:01:00Z | 74804.0  |
| nurswgvml006 | 2017-04-08T07:01:15Z | 6.1      | nurswgvml006 | 2017-04-08T07:01:15Z | 71276.0  |
| nurswgvml006 | 2017-04-08T07:01:30Z | 3.0      | nurswgvml006 | 2017-04-08T07:01:30Z | 70820.0  |
| nurswgvml006 | 2017-04-08T07:01:45Z | 2.0      | nurswgvml006 | 2017-04-08T07:01:45Z | 69944.0  |
| nurswgvml006 | 2017-04-08T07:02:00Z | 10.9     | nurswgvml006 | 2017-04-08T07:02:00Z | 75928.0  |
```

## `atsd_series` Alternative Syntax

* Regular syntax:

```sql
SELECT t1.datetime, t1.entity, t1.value, t2.value, t3.value
  FROM "mpstat.cpu_system" t1
  JOIN "mpstat.cpu_user" t2
  JOIN "mpstat.cpu_iowait" t3
WHERE t1.datetime >= '2016-06-16T13:00:00Z' AND t1.datetime < '2016-06-16T13:10:00Z'
  AND t1.entity = 'nurswgvml006'
LIMIT 3
```

* `atsd_series` syntax:

```sql
SELECT t1.datetime, t1.entity, t1.value, t2.value, t3.value
  FROM atsd_series t1
  JOIN "mpstat.cpu_user" t2
  JOIN "mpstat.cpu_iowait" t3
WHERE t1.metric = 'mpstat.cpu_system'
  AND t1.datetime >= '2016-06-16T13:00:00Z' AND t1.datetime < '2016-06-16T13:10:00Z'
  AND t1.entity = 'nurswgvml006'
LIMIT 3
```
