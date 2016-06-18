# Row Number

## Overview

ROW_NUMBER returns sequential number of a row within a partition of result set, starting at 1 for the first row in each partition.

Partition is a subset of all rows in the resultset grouped by equal values of partitioning columns.

Assuming that the below resultset was partitioned by entity and then ordered by time within each partition, the row numbers would be as follows: 

```ls
|--------------|--------------------------|-------| ROW_NUMBER
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  |     1
| nurswgvml006 | 2016-06-18T12:00:21.000Z | 8.1   |     2
| nurswgvml007 | 2016-06-18T12:00:03.000Z | 18.2  |     1
| nurswgvml007 | 2016-06-18T12:00:19.000Z | 67.7  |     2
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   |     1
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 |     1
| nurswgvml011 | 2016-06-18T12:00:26.000Z | 4.0   |     2
| nurswgvml011 | 2016-06-18T12:00:29.000Z | 0.0   |     3
```

## Syntax

```sql
ROW_NUMBER({partitioning columns} ORDER BY {ordering columns [direction]})
```

* {partitioning columns} can be `entity`, `tags`, or `entity, tags`
* {ordering columns [direction]} can be any in the `FROM` clause with optional ASC|DESC direction.

Examples:

* `ROW_NUMBER(entity ORDER BY time)`
* `ROW_NUMBER(entity, tags ORDER BY time DESC)`
* `ROW_NUMBER(entity, tags ORDER BY time DESC, avg(value))`
 
 The returned number can be used to filter rows within each partition.
 
 * `WITH ROW_NUMBER(entity ORDER BY time) <= 1`

## Data

The input data for the specified interval contains 11 rows, 2 rows for 5 entities, and 1 entity with 1 row.

### Query

```sql
SELECT entity, datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= "2016-06-18T12:00:00.000Z" AND datetime < "2016-06-18T12:00:30.000Z"
  ORDER BY entity, time
```

### Results

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|------:| 
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  | +
| nurswgvml006 | 2016-06-18T12:00:21.000Z | 8.1   | 
| nurswgvml007 | 2016-06-18T12:00:03.000Z | 18.2  | +
| nurswgvml007 | 2016-06-18T12:00:19.000Z | 67.7  | 
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   | +
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 | +
| nurswgvml011 | 2016-06-18T12:00:26.000Z | 4.0   | 
| nurswgvml102 | 2016-06-18T12:00:02.000Z | 0.0   | +
| nurswgvml102 | 2016-06-18T12:00:18.000Z | 0.0   | 
| nurswgvml502 | 2016-06-18T12:00:01.000Z | 13.7  | +
| nurswgvml502 | 2016-06-18T12:00:17.000Z | 0.5   | 
```

## First record in each partition

### Query

```sql
SELECT entity, datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= "2016-06-18T12:00:00.000Z" AND datetime < "2016-06-18T12:00:30.000Z"
  WITH ROW_NUMBER(entity ORDER BY time) <= 1
  ORDER BY entity, time
```

### Results

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|------:| 
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  | 
| nurswgvml007 | 2016-06-18T12:00:03.000Z | 18.2  | 
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   | 
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 | 
| nurswgvml102 | 2016-06-18T12:00:02.000Z | 0.0   | 
| nurswgvml502 | 2016-06-18T12:00:01.000Z | 13.7  | 
```

## Last record in each partition

Reverse ordering is accomplished with `ORDER BY time DESC` condition in ROW_NUMBER function.

### Query

```sql
SELECT entity, datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= "2016-06-18T12:00:00.000Z" AND datetime < "2016-06-18T12:00:30.000Z"
  WITH ROW_NUMBER(entity ORDER BY time DESC) <= 1
  ORDER BY entity, time
```

### Results

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|-----:| 
| nurswgvml006 | 2016-06-18T12:00:21.000Z | 8.1  | 
| nurswgvml007 | 2016-06-18T12:00:19.000Z | 67.7 | 
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5  | 
| nurswgvml011 | 2016-06-18T12:00:26.000Z | 4.0  | 
| nurswgvml102 | 2016-06-18T12:00:18.000Z | 0.0  | 
| nurswgvml502 | 2016-06-18T12:00:17.000Z | 0.5  | 
```

## Max value in each partition

Maximum value for each partition can be queried with `ORDER BY value desc` condition in ROW_NUMBER function.

### Query

```sql
SELECT entity, datetime, value
  FROM mpstat.cpu_busy
WHERE datetime >= "2016-06-18T12:00:00.000Z" AND datetime < "2016-06-18T12:00:30.000Z"
  WITH ROW_NUMBER(entity ORDER BY value DESC) <= 1
  ORDER BY entity, time
```

### Results

```ls
| entity       | datetime                 | value | 
|--------------|--------------------------|-------| 
| nurswgvml006 | 2016-06-18T12:00:05.000Z | 66.0  | 
| nurswgvml007 | 2016-06-18T12:00:19.000Z | 67.7  | 
| nurswgvml010 | 2016-06-18T12:00:14.000Z | 0.5   | 
| nurswgvml011 | 2016-06-18T12:00:10.000Z | 100.0 | 
| nurswgvml102 | 2016-06-18T12:00:02.000Z | 0.0   | 
| nurswgvml502 | 2016-06-18T12:00:01.000Z | 13.7  | 
```









