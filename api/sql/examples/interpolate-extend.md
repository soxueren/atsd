# Interpolate with Extend

* If `VALUE {n}` interpolation function is specified in the `PERIOD` clause, the `EXTEND` option sets empty leading/trailing period values to equal `{n}`.
* Without `VALUE {n}` function, the `EXTEND` option adds missing periods at the beginning and end of the selection interval using `NEXT` and `PREVIOUS` interpolation functions.

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

## Query with inner LINEAR interpolation and without `EXTEND` option

```sql
SELECT datetime, avg(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:37:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(10 second, LINEAR)
```

```ls
| datetime                 | avg(value) | 
|--------------------------|------------| 
| 2016-06-03T09:38:20.000Z | 0.0        | 
| 2016-06-03T09:38:30.000Z | 2.0        | 
| 2016-06-03T09:38:40.000Z | 4.0        | 
| 2016-06-03T09:38:50.000Z | 4.0        | 
| 2016-06-03T09:39:00.000Z | 6.1        | 
| 2016-06-03T09:39:10.000Z | 8.1        | 
| 2016-06-03T09:39:20.000Z | 7.0        | 
| 2016-06-03T09:39:30.000Z | 12.9       | 
| 2016-06-03T09:39:40.000Z | 18.8       | 
```

## Query with inner LINEAR interpolation and with `EXTEND` option

To apply interpolation both to inner periods as well as to leading/trailing periods include both the `EXTEND` option and an interpolation function in the `PERIOD` clause.

```sql
SELECT datetime, avg(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:37:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(10 second, LINEAR, EXTEND)
```

```ls
| datetime                 | avg(value) | 
|--------------------------|------------| 
| 2016-06-03T09:37:00.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:37:10.000Z | 0.0        | + EXTEND -> interpolated with NEXT 
| 2016-06-03T09:37:20.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:37:30.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:37:40.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:37:50.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:38:00.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:38:10.000Z | 0.0        | + EXTEND -> interpolated with NEXT
| 2016-06-03T09:38:20.000Z | 0.0        | 
| 2016-06-03T09:38:30.000Z | 2.0        | + inner -> interpolated with LINEAR
| 2016-06-03T09:38:40.000Z | 4.0        | 
| 2016-06-03T09:38:50.000Z | 4.0        | 
| 2016-06-03T09:39:00.000Z | 6.1        | + inner -> interpolated with LINEAR
| 2016-06-03T09:39:10.000Z | 8.1        | 
| 2016-06-03T09:39:20.000Z | 7.0        | 
| 2016-06-03T09:39:30.000Z | 12.9       | + inner -> interpolated with LINEAR
| 2016-06-03T09:39:40.000Z | 18.8       | 
| 2016-06-03T09:39:50.000Z | 18.8       | + EXTEND -> interpolated with PREVIOUS
```

## Query with inner VALUE interpolation and with `EXTEND` option

`VALUE {n}` interpolation function applies both to inner and leading/trailing periods.

```sql
SELECT datetime, avg(value)
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-06-03T09:37:00.000Z' AND datetime < '2016-06-03T09:40:00.000Z'
  AND entity = 'nurswgvml006'
GROUP BY PERIOD(10 second, VALUE -10, EXTEND)
```

```ls
| datetime                 | avg(value) | 
|--------------------------|------------| 
| 2016-06-03T09:37:00.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:37:10.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:37:20.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:37:30.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:37:40.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:37:50.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:38:00.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:38:10.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
| 2016-06-03T09:38:20.000Z | 0.0        | 
| 2016-06-03T09:38:30.000Z | -10.0      | + inner -> interpolated with VALUE -10
| 2016-06-03T09:38:40.000Z | 4.0        | 
| 2016-06-03T09:38:50.000Z | 4.0        | 
| 2016-06-03T09:39:00.000Z | -10.0      | + inner -> interpolated with VALUE -10
| 2016-06-03T09:39:10.000Z | 8.1        | 
| 2016-06-03T09:39:20.000Z | 7.0        | 
| 2016-06-03T09:39:30.000Z | -10.0      | + inner -> interpolated with VALUE -10
| 2016-06-03T09:39:40.000Z | 18.8       | 
| 2016-06-03T09:39:50.000Z | -10.0      | + EXTEND -> interpolated with VALUE -10
```