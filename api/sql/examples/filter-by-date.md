# Filter by Date

## Query with Milliseconds

```sql
SELECT time, value 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
  AND time >= 1466100000000 AND time < 1466200000000
```

## Results

```ls
| time          | value | 
|---------------|------:| 
| 1466100003000 | 37.2  | 
| 1466100019000 | 3.1   | 
| 1466100035000 | 4.0   | 
```

## Query with ISO time

```sql
SELECT datetime, value 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
  AND datetime >= "2016-06-18T20:00:00.000Z" AND datetime < "2016-06-18T21:00:00.000Z"
```

## Results

```ls
| datetime                 | value | 
|--------------------------|------:| 
| 2016-06-18T20:00:11.000Z | 28.0  | 
| 2016-06-18T20:00:27.000Z | 6.1   | 
| 2016-06-18T20:00:43.000Z | 6.1   | 
```

## Query with Endtime

Both `time` and `datetime` columns support [endtime](/end-time-syntax.md) syntax.

```sql
SELECT datetime, value 
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007' 
  AND datetime >= previous_hour AND time < current_hour
```

## Results

```ls
| datetime                 | value | 
|--------------------------|------:| 
| 2016-06-18T20:00:11.000Z | 28.0  | 
| 2016-06-18T20:00:27.000Z | 6.1   | 
| 2016-06-18T20:00:43.000Z | 6.1   | 
```

## Query using BETWEEN

Notice that the BETWEEN condition is inclusive so subtract 1 millisecond from an AND value for a `[)` half-open range.

```sql
SELECT datetime, value FROM mpstat.cpu_busy 
  WHERE entity = 'nurswgvml007' 
AND datetime BETWEEN "2016-06-18T20:00:00.000Z" AND "2016-06-18T20:59:59.999Z"
```

The above condition is equivalent to:

```sql
WHERE datetime >= "2016-06-18T20:00:00.000Z" AND datetime < "2016-06-18T21:00:00.000Z"
```

## Results

```ls
| datetime                 | value | 
|--------------------------|------:| 
| 2016-06-18T20:00:11.000Z | 28.0  | 
| 2016-06-18T20:00:27.000Z | 6.1   | 
| 2016-06-18T20:00:43.000Z | 6.1   | 
```
