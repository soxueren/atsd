# Transforming Unevenly Space Series to Regular Series

`WITH INTERPOLATE` clause provides a way to transform unevenly spaced time series into regular series.

The underlying transformation calculates values at regular intervals aligned to the calendar using linear interpolation. 

Unlike `GROUP BY PERIOD` clause with `LINEAR` option, which interpolates missing periods, `WITH INTERPOLATE` clause operates on raw values.

The regularized series can be used in JOIN queries, `WHERE` condition, `ORDER BY` and `GROUP BY` clauses just like the original series.

## Calculation

The interpolated values are calculated using linear regression between two neighboring values. 

Irregular series:

```ls
| time                 | value | 
|----------------------|-------| 
| 2016-09-17T08:00:00Z | 3.70  |
| 2016-09-17T08:00:26Z | 4.40  |
| 2016-09-17T08:01:14Z | 9.00  |
| 2016-09-17T08:01:30Z | 2.30  |
```

Regular `30 SECOND` series:

```ls
| time                 | value | 
|----------------------|-------| 
| 2016-09-17T08:00:00Z | 3.70  | returned "as is" since raw value is already aligned to calendar
| 2016-09-17T08:00:30Z | 4.78  | = 4.4 + (9.0-4.4) * ((00:30-00:26)/(01:14-00:26)) = 4.4 + 4.6*(4/48)  = 4.783
| 2016-09-17T08:01:00Z | 7.66  | = 4.4 + (9.0-4.4) * ((01:00-00:26)/(01:14-00:26)) = 4.4 + 4.6*(34/48) = 7.658
| 2016-09-17T08:01:30Z | 2.30  | returned "as is" since raw value is already aligned to calendar
```

## Examples

* [Chartlab examples](https://apps.axibase.com/chartlab/3203bddb)

![Regularization Modes](images/regularized_series.png)

### Raw Values

```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
  AND datetime >= '2016-09-16T00:00:00Z' AND datetime < '2016-09-18T00:00:00Z'
```

```ls
| datetime                 | value   | 
|--------------------------|---------| 
| 2016-09-17T00:00:00.000Z |   4.500 | 
| 2016-09-17T01:23:11.000Z |     NaN | 
| 2016-09-17T02:00:05.000Z | -70.000 | 
| 2016-09-17T08:00:18.000Z |  10.400 | 
| 2016-09-17T08:00:26.000Z |   4.400 | 
| 2016-09-17T08:01:14.000Z |   9.000 | 
| 2016-09-17T08:01:34.000Z |   2.100 | 
| 2016-09-17T08:01:52.000Z |  26.500 | 
| 2016-09-17T08:02:10.000Z |   0.000 | 
| 2016-09-17T08:03:00.000Z |   7.700 | 
| 2016-09-17T08:04:48.000Z |   6.600 | 
| 2016-09-17T23:04:00.000Z | -23.400 | 
```


### Default

The default mode (NONE) doesn't extend returned series to start and end dates of the selection interval in case of missing values.

```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T07:59:00Z' AND datetime < '2016-09-17T08:06:00Z'
  WITH INTERPOLATE(30 SECOND)
```

```ls
| datetime                 | value  | 
|--------------------------|--------| 
| 2016-09-17T08:00:00.000Z |  10.4  | 
| 2016-09-17T08:00:30.000Z |   4.8  | 
| 2016-09-17T08:01:00.000Z |   7.7  | 
| 2016-09-17T08:01:30.000Z |   3.5  | 
| 2016-09-17T08:02:00.000Z |  14.7  | 
| 2016-09-17T08:02:30.000Z |   3.1  | 
| 2016-09-17T08:03:00.000Z |   7.7  | 
| 2016-09-17T08:03:30.000Z |   7.4  | 
| 2016-09-17T08:04:00.000Z |   7.1  | 
| 2016-09-17T08:04:30.000Z |   6.8  | 
```

### LINEAR

Prior value outside of the interval is retrieved and is used to calculate an interpolated value between the outside value and the first raw value within the interval. 

In addition, next outside value outside the interval is retrieved and is used to interpolate last value within the interval.

```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T07:59:00Z' AND datetime < '2016-09-17T08:06:00Z'
  WITH INTERPOLATE(30 SECOND, LINEAR)
```

```ls
| datetime                 | value  | 
|--------------------------|--------| 
| 2016-09-17T07:59:00.000Z | 10.110 | 
| 2016-09-17T07:59:30.000Z | 10.221 | 
| 2016-09-17T08:00:00.000Z | 10.333 | 
| 2016-09-17T08:00:30.000Z |  4.783 | 
| 2016-09-17T08:01:00.000Z |  7.658 | 
| 2016-09-17T08:01:30.000Z |  3.480 | 
| 2016-09-17T08:02:00.000Z | 14.722 | 
| 2016-09-17T08:02:30.000Z |  3.080 | 
| 2016-09-17T08:03:00.000Z |  7.700 | 
| 2016-09-17T08:03:30.000Z |  7.394 | 
| 2016-09-17T08:04:00.000Z |  7.089 | 
| 2016-09-17T08:04:30.000Z |  6.783 | 
| 2016-09-17T08:05:00.000Z |  6.593 | 
| 2016-09-17T08:05:30.000Z |  6.577 | 
```

### EXTEND

Missing values at the beginning of the interval are set to first raw value within the interval. <br> Missing values at the end of the interval are set to last raw value within the interval.

```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T07:59:00Z' AND datetime < '2016-09-17T08:06:00Z'
  WITH INTERPOLATE(30 SECOND, EXTEND)
```

```ls
| datetime                 | value  | 
|--------------------------|--------| 
| 2016-09-17T07:59:00.000Z | 10.400 | 
| 2016-09-17T07:59:30.000Z | 10.400 | 
| 2016-09-17T08:00:00.000Z | 10.400 | 
| 2016-09-17T08:00:30.000Z |  4.783 | 
| 2016-09-17T08:01:00.000Z |  7.658 | 
| 2016-09-17T08:01:30.000Z |  3.480 | 
| 2016-09-17T08:02:00.000Z | 14.722 | 
| 2016-09-17T08:02:30.000Z |  3.080 | 
| 2016-09-17T08:03:00.000Z |  7.700 | 
| 2016-09-17T08:03:30.000Z |  7.394 | 
| 2016-09-17T08:04:00.000Z |  7.089 | 
| 2016-09-17T08:04:30.000Z |  6.783 | 
| 2016-09-17T08:05:00.000Z |  6.600 | 
| 2016-09-17T08:05:30.000Z |  6.600 | 
```

### PRIOR

Prior value outside of the interval is retrieved and is used to set first values within the interval to the prior value until first raw value within the interval.

```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T07:59:00Z' AND datetime < '2016-09-17T08:06:00Z'
  WITH INTERPOLATE(30 SECOND, PRIOR)
```

```ls
| datetime                 | value   | 
|--------------------------|---------| 
| 2016-09-17T07:59:00.000Z | -70.000 | 
| 2016-09-17T07:59:30.000Z | -70.000 | 
| 2016-09-17T08:00:00.000Z | -70.000 | 
| 2016-09-17T08:00:30.000Z |   4.783 | 
| 2016-09-17T08:01:00.000Z |   7.658 | 
| 2016-09-17T08:01:30.000Z |   3.480 | 
| 2016-09-17T08:02:00.000Z |  14.722 | 
| 2016-09-17T08:02:30.000Z |   3.080 | 
| 2016-09-17T08:03:00.000Z |   7.700 | 
| 2016-09-17T08:03:30.000Z |   7.394 | 
| 2016-09-17T08:04:00.000Z |   7.089 | 
| 2016-09-17T08:04:30.000Z |   6.783 | 
| 2016-09-17T08:05:00.000Z |   6.600 | 
| 2016-09-17T08:05:30.000Z |   6.600 | 
```

### `GROUP BY PERIOD` compared to `WITH INTERPOLATE`

The `GROUP BY PERIOD()` clause calculates value for all values in each period by applying an aggregation function such as average, maximum, first, last etc.

If the period doesn't have any values, the period is omitted from results.

An optional `LINEAR` directive for `GROUP BY PERIOD()` clause changes the default behavior and returns results missing periods by applying linear interpolation between values of the neighboring periods.

#### Data

```ls
| datetime                 | value   | 
|--------------------------|---------| 
| 2016-09-17T02:00:05.000Z | -70.000 | 
| 2016-09-17T08:00:18.000Z |  10.400 | 
| 2016-09-17T08:00:26.000Z |   4.400 | 
| 2016-09-17T08:01:14.000Z |   9.000 | 
| 2016-09-17T08:01:34.000Z |   2.100 | 
```

#### `GROUP BY PERIOD`

```sql
SELECT datetime, first(value), last(value), avg(value) FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T08:00:00Z' AND datetime < '2016-09-17T08:02:00Z'
  GROUP BY PERIOD(30 SECOND, LINEAR)
```

```ls
| datetime                 | first(value) | last(value) | avg(value) | 
|--------------------------|--------------|-------------|------------| 
| 2016-09-17T08:00:00.000Z | 10.40        |  4.40       |  7.40      | -- The period has 2 values. Set to first value at 08:00:18, last value at 08:00:26 or using an aggregation function such as average
| 2016-09-17T08:00:30.000Z |  9.70        |  6.70       |  8.20      | -- The period has no values. Linearly interpolated between 08:00:00 and 08:01:00; 10.40 + (9.00-10.40)* 30sec/60sec = 9.70
| 2016-09-17T08:01:00.000Z |  9.00        |  9.00       |  9.00      | -- The period has only 1 record. last(), first(), and avg() return the same result.
| 2016-09-17T08:01:30.000Z |  2.10        | 26.50       | 14.30      | -- The period has 2 values, calculated similar to period starting 08:00:00.
```

#### `WITH INTERPOLATE`

`WITH INTERPOLATE` clause, on the other hand, calculates values at calendar-aligned timestamps using neighboring raw values.


```sql
SELECT datetime, value FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T08:00:00Z' AND datetime < '2016-09-17T08:02:00Z'
  WITH INTERPOLATE(30 SECOND, LINEAR)
```

```ls
| datetime                 | value | 
|--------------------------|-------| 
| 2016-09-17T08:00:00.000Z | 10.33 | -- Linearly interpolated between prior value of -70 at 02:00:05 and first value of 10.40 at 08:00:18.
| 2016-09-17T08:00:30.000Z |  4.78 | -- Linearly interpolated between 4.40 at 08:00:26 and 9.00 at 08:01:14.
| 2016-09-17T08:01:00.000Z |  7.66 | -- Linearly interpolated between 4.40 at 08:00:26 and 9.00 at 08:01:14.
| 2016-09-17T08:01:30.000Z |  3.48 | -- Linearly interpolated between 9.00 at 08:01:14 and 2.10 at 08:01:34.
```

If raw values had extra samples recorded within values in each period, such values would be ignored.

```ls
| time     | value   | 
|----------|---------| 
| 08:00:18 |   10.40 | -- used to interpolate value at 08:00:00 between 02:00:05 and 08:00:18
| 08:00:19 |  100.50 | -- ignored
| 08:00:20 |  200.40 | -- ignored
| 08:00:21 |  400.10 | -- ignored
| 08:00:22 |  600.20 | -- ignored
| 08:00:23 | -100.30 | -- ignored
| 08:00:24 |     0.0 | -- ignored
| 08:00:25 |    10.3 | -- ignored
| 08:00:26 |    4.40 | -- used to interpolate value at 08:00:30 between 08:00:26 and 08:01:14
```

### Combination

#### `GROUP BY` Example

The interpolation and `GROUP BY` clauses can be combined. 

`WITH INTERPOLATE` transformation is performed first, with regular series subsequently processed by aggregation functions. 

```sql
SELECT datetime, count(value), avg(value) FROM metric1
  WHERE entity = 'e1'
AND datetime >= '2016-09-17T08:00:00Z' AND datetime < '2016-09-17T08:02:00Z'
  GROUP BY PERIOD (60 SECOND)
  WITH INTERPOLATE(30 SECOND, LINEAR)
```

```ls
| datetime                 | count(value) | avg(value) | 
|--------------------------|--------------|------------| 
| 2016-09-17T08:00:00.000Z | 2.00         | 7.56       | 
| 2016-09-17T08:01:00.000Z | 2.00         | 5.57       | 
```

#### `JOIN` Example 

`WITH INTERPOLATE` transformation regularizes all series returned by the query to the same timestamps so that their values can be joined.

Series t1:

```sql
SELECT t1.datetime, t1.value
  FROM meminfo.memfree t1
WHERE t1.datetime >= '2016-09-18T14:00:00.000Z' AND t1.datetime < '2016-09-18T14:01:00.000Z'
```

```ls
| t1.datetime              | t1.value  | 
|--------------------------|-----------| 
| 2016-09-18T14:00:07.000Z | 4883376.0 | 
| 2016-09-18T14:00:22.000Z | 2868332.0 | 
| 2016-09-18T14:00:37.000Z | 1683240.0 | 
| 2016-09-18T14:00:52.000Z | 1279048.0 | 
```

Series t2:

```sql
SELECT t2.datetime, t2.value
  FROM mpstat.cpu_busy t2
WHERE t2.datetime >= '2016-09-18T14:00:00.000Z' AND t2.datetime < '2016-09-18T14:01:00.000Z'
```

```ls
| t2.datetime              | t2.value | 
|--------------------------|----------| 
| 2016-09-18T14:00:13.000Z | 52.7     | 
| 2016-09-18T14:00:29.000Z | 62.6     | 
| 2016-09-18T14:00:45.000Z | 56.2     | 
```

JOINed multi-variate series:

```sql
SELECT t1.datetime, t1.value, t2.value
  FROM meminfo.memfree t1
  JOIN mpstat.cpu_busy t2
WHERE t1.datetime >= '2016-09-18T14:00:00.000Z' AND t1.datetime < '2016-09-18T14:01:00.000Z'
  WITH INTERPOLATE(15 SECOND, LINEAR)
```

```ls
| t1.datetime              | t1.value  | t2.value | 
|--------------------------|-----------|----------| 
| 2016-09-18T14:00:00.000Z | 2866265.3 | 20.2     | 
| 2016-09-18T14:00:15.000Z | 3808685.9 | 53.9     | 
| 2016-09-18T14:00:30.000Z | 2236282.9 | 62.2     | 
| 2016-09-18T14:00:45.000Z | 1467670.9 | 56.2     | 
```

Without interpolation, a join of Series 1 and Series 2 would have produced an empty result because their sample times are different.

### `value` Filter

value condition in `WHERE` clause is applied to interpolated values.

```sql
SELECT datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-09-18T14:03:30.000Z' AND datetime <= '2016-09-18T14:04:30.000Z'
```

```ls
| datetime                 | value | 
|--------------------------|-------| 
| 2016-09-18T14:03:41.000Z | 21.4  | 
| 2016-09-18T14:03:57.000Z | 19.4  | 
| 2016-09-18T14:04:13.000Z | 33.4  | 
| 2016-09-18T14:04:29.000Z | 11.3  | 
```

Without `INTERPOLATE` raw values can be filtered out with `value` condition as usual.

```sql
SELECT datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-09-18T14:03:30.000Z' AND datetime <= '2016-09-18T14:04:30.000Z'
  AND value < 32
```

```ls
| datetime                 | value | 
|--------------------------|-------| 
| 2016-09-18T14:03:41.000Z | 21.4  | 
| 2016-09-18T14:03:57.000Z | 19.4  | 
| 2016-09-18T14:04:29.000Z | 11.3  | 
```

Once `INTERPOLATE` clause is added, the value filter is applied to interpolated values instead of raw values.

The following queries produce the same result because `value < 32` is no longer applied to raw values and as such sample at 14:04:13 remains in the series for the purpose of interpolation.

```sql
SELECT datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-09-18T14:03:30.000Z' AND datetime <= '2016-09-18T14:04:30.000Z'
  WITH INTERPOLATE(15 SECOND, LINEAR)
```

```sql
SELECT datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= '2016-09-18T14:03:30.000Z' AND datetime <= '2016-09-18T14:04:30.000Z'
  AND value < 32
  WITH INTERPOLATE(15 SECOND, LINEAR)
```

The above queries return the same result:

```ls
| datetime                 | value | 
|--------------------------|-------| 
| 2016-09-18T14:03:30.000Z | 17.7  | 
| 2016-09-18T14:03:45.000Z | 20.9  | 
| 2016-09-18T14:04:00.000Z | 22.0  | 
| 2016-09-18T14:04:15.000Z | 30.7  | 
| 2016-09-18T14:04:30.000Z | 12.0  | 
```

### Input Commands

```ls
series e:e1   m:metric1=4.5 d:2016-09-17T00:00:00Z
series e:e1   m:metric1=NaN d:2016-09-17T01:23:11Z
series e:e1 m:metric1=-70.0 d:2016-09-17T02:00:05Z
series e:e1  m:metric1=10.4 d:2016-09-17T08:00:18Z
series e:e1   m:metric1=4.4 d:2016-09-17T08:00:26Z
series e:e1   m:metric1=9.0 d:2016-09-17T08:01:14Z
series e:e1   m:metric1=2.1 d:2016-09-17T08:01:34Z
series e:e1  m:metric1=26.5 d:2016-09-17T08:01:52Z
series e:e1   m:metric1=0.0 d:2016-09-17T08:02:10Z
series e:e1   m:metric1=7.7 d:2016-09-17T08:03:00Z
series e:e1   m:metric1=6.6 d:2016-09-17T08:04:48Z
series e:e1 m:metric1=-23.4 d:2016-09-17T23:04:00Z

series e:e2  m:metric1=10.4 d:2016-09-17T01:23:11Z

series e:e3   m:metric1=1.0 d:2016-09-17T01:01:00Z
series e:e3   m:metric1=NaN d:2016-09-17T01:03:00Z
series e:e3   m:metric1=4.0 d:2016-09-17T01:04:00Z
```



