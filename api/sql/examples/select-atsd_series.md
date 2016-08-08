# Select FROM `atsd_series` table

Querying `atsd_series` table provides an alternative syntax to specify metric name in the `WHERE` clause instead of the `FROM` query.

At least one metric condition must be present and applicable in `atsd_series` query. Metric names are case-insensitive. 

The following queries are equivalent:

```sql
SELECT entity, metric, datetime, value 
  FROM atsd_series 
WHERE metric = 'mpstat.cpu_busy'
  AND datetime > current_hour
```

```sql
SELECT entity, metric, datetime, value 
  FROM 'mpstat.cpu_busy' 
WHERE datetime > current_hour
```

### Results

```ls
| entity       | metric   | datetime                 | value | 
|--------------|----------|--------------------------|-------| 
| nurswgvml007 | cpu_busy | 2016-08-08T15:26:14.000Z | 7.0   | 
| nurswgvml007 | cpu_busy | 2016-08-08T15:26:30.000Z | 4.0   | 
| nurswgvml007 | cpu_busy | 2016-08-08T15:26:46.000Z | 3.1   | 
```

## Query Multiple Metrics

Unlike `FROM {metric}` syntax,  `FROM atsd_series` allows querying multiple metrics similar to `UNION ALL` operator.

```sql
SELECT entity, metric, datetime, value, tags
  FROM atsd_series 
WHERE metric IN ('mpstat.cpu_busy', 'mpstat.cpu_user') 
  AND entity = 'nurswgvml007'
  AND datetime > now - 2 * MINUTE
```

### Results

```ls
| entity       | metric   | datetime                 | value | tags | 
|--------------|----------|--------------------------|-------|------| 
| nurswgvml007 | cpu_busy | 2016-08-08T15:54:47.000Z | 6.1   | null | 
| nurswgvml007 | cpu_busy | 2016-08-08T15:55:03.000Z | 11.9  | null | 
| nurswgvml007 | cpu_user | 2016-08-08T15:54:47.000Z | 5.0   | null | 
| nurswgvml007 | cpu_user | 2016-08-08T15:55:03.000Z | 5.9   | null | 
```


By default results are ordered by metric. The default sort can be modified with `ORDER BY` clause.

```sql
SELECT entity, metric, datetime, value, tags
  FROM atsd_series 
WHERE metric IN ('mpstat.cpu_busy', 'mpstat.cpu_user') 
  AND entity = 'nurswgvml007'
  AND datetime > now - 2 * MINUTE
  ORDER BY datetime
```

### Results

```ls
| entity       | metric   | datetime                 | value | tags | 
|--------------|----------|--------------------------|-------|------| 
| nurswgvml007 | cpu_busy | 2016-08-08T15:54:47.000Z | 6.1   | null | 
| nurswgvml007 | cpu_user | 2016-08-08T15:54:47.000Z | 5.0   | null | 
| nurswgvml007 | cpu_busy | 2016-08-08T15:55:03.000Z | 11.9  | null | 
| nurswgvml007 | cpu_user | 2016-08-08T15:55:03.000Z | 5.9   | null | 
```

## Metric Condition

Metrics can be selected in the `WHERE` clause using `=` operator. Both `AND` and `OR` boolean operators are supported when processing conditions in multiple-metric queries. 

```sql
SELECT entity, metric, datetime, value, tags
  FROM atsd_series 
WHERE (metric = 'df.disk_used' OR metric = 'df.disk_used_percent')
  AND entity = 'nurswgvml007'
  AND datetime > now - 2 * MINUTE
```

## Limitations

Queries with `atsd_series` table do not support the following capabilities:

* `JOIN` queries are not supported.
* All columns in the `SELECT` expression must be specified explicitly. Namely, `SELECT *`, `SELECT tags.*`, `SELECT metric.tags.*` are not allowed. 


