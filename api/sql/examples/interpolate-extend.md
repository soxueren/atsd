# Interpolate with Extend

The `EXTEND` option adds missing periods at the beginning and end of the selection interval using `NEXT` and `PREVIOUS` interpolation functions.

## Data

```sql
SELECT datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:25:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
```

The 

```ls
| datetime                 | value | 
|--------------------------|-------| 
| 2016-06-03T09:25:04.000Z | 17.0  | 
| 2016-06-03T09:25:20.000Z | 2.0   | 
| 2016-06-03T09:25:36.000Z | 6.9   | 
| 2016-06-03T09:25:52.000Z | 1.0   | 
| 2016-06-03T09:26:08.000Z | 6.9   | 
| 2016-06-03T09:26:24.000Z | 0.0   | 
| 2016-06-03T09:26:40.000Z | 2.0   | 
| 2016-06-03T09:26:56.000Z | 5.0   | 
-- no data betweeen 26:56 and 38.24 --
| 2016-06-03T09:38:24.000Z | 0.0   | 
| 2016-06-03T09:38:40.000Z | 4.0   | 
| 2016-06-03T09:38:56.000Z | 4.0   | 
| 2016-06-03T09:39:12.000Z | 8.1   | 
| 2016-06-03T09:39:28.000Z | 7.0   | 
| 2016-06-03T09:39:44.000Z | 18.8  | 
```

## Query without `EXTEND`

If the query selects 2-minute periods for the 09:30 - 09:40 interval, the first period will be dated 09:38

```sql
SELECT datetime, count(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:30:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(1 MINUTE)
```

```ls
| datetime                 | avg(value) | 
|--------------------------|------------| 
| 2016-06-03T09:38:00.000Z | 2.7        | 
| 2016-06-03T09:39:00.000Z | 11.3       | 
```

## Query with `EXTEND`

Query with `EXTEND` adds missing periods at the beginning of the interval by applying `NEXT` interpolation function.

```sql
SELECT datetime, avg(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:30:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(1 MINUTE, EXTEND)
```

```ls
| datetime                 | avg(value) | 
|--------------------------|------------| 
| 2016-06-03T09:30:00.000Z | 2.7        | 
| 2016-06-03T09:31:00.000Z | 2.7        | 
| 2016-06-03T09:32:00.000Z | 2.7        | 
| 2016-06-03T09:33:00.000Z | 2.7        | 
| 2016-06-03T09:34:00.000Z | 2.7        | 
| 2016-06-03T09:35:00.000Z | 2.7        | 
| 2016-06-03T09:36:00.000Z | 2.7        | 
| 2016-06-03T09:37:00.000Z | 2.7        | 
| 2016-06-03T09:38:00.000Z | 2.7        | 
| 2016-06-03T09:39:00.000Z | 11.3       | 
```

## Query with `EXTEND` and inner interpolation

To apply interpolation both to inner periods as well as to leading/trailing periods include both the `EXTEND` option and an interpolation function in the `PERIOD` clause.

```sql
SELECT datetime, count(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:37:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(15 second, VALUE 0, EXTEND)
```

```ls
| datetime                 | count(value) | 
|--------------------------|--------------| 
| 2016-06-03T09:37:00.000Z | 0            | 
| 2016-06-03T09:37:15.000Z | 0            | 
| 2016-06-03T09:37:30.000Z | 0            | 
| 2016-06-03T09:37:45.000Z | 0            | 
| 2016-06-03T09:38:00.000Z | 0            | 
| 2016-06-03T09:38:15.000Z | 1            | 
| 2016-06-03T09:38:30.000Z | 1            | 
| 2016-06-03T09:38:45.000Z | 1            | 
| 2016-06-03T09:39:00.000Z | 1            | 
| 2016-06-03T09:39:15.000Z | 1            | 
| 2016-06-03T09:39:30.000Z | 1            | 
| 2016-06-03T09:39:45.000Z | 0            | 
```
