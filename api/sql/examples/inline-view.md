# Inline View

Inline view is a subquery used in the `FROM` clause of a containing query. It allows the containing query to operate on the results of the subquery instead of the actual table.

## Query

Calculate hourly maximums for each hour and then calculate average hourly maximum for the days in the week.

```sql
SELECT datetime, AVG(value) AS "daily_average" 
  FROM -- actual table replaced with subquery
  (
    SELECT datetime, MAX(value) AS "value"
      FROM "mpstat.cpu_busy" WHERE datetime >= CURRENT_WEEK
    GROUP BY PERIOD(1 HOUR)
  )
GROUP BY PERIOD(1 DAY)
```

### Results

```ls
| datetime            | daily_average | 
|---------------------|---------------| 
| 2017-08-14 00:00:00 | 96.1          | 
| 2017-08-15 00:00:00 | 96.6          | 
| 2017-08-16 00:00:00 | 98.8          | 
| 2017-08-17 00:00:00 | 95.4          | 
| 2017-08-18 00:00:00 | 98.3          | 
| 2017-08-19 00:00:00 | 96.1          | 
| 2017-08-20 00:00:00 | 93.8          | 
```

## Query

Calculate maximum average hourly maximum.

```sql
SELECT MAX(value) FROM (
  SELECT datetime, AVG(value) AS "value" FROM (
    SELECT datetime, MAX(value) AS "value"
      FROM "mpstat.cpu_busy" WHERE datetime >= CURRENT_WEEK
    GROUP BY PERIOD(1 HOUR)
  )
  GROUP BY PERIOD(1 DAY)
)
```

### Results

The number of nested subqueries in the inline view is not limited. 

```ls
| max(value) |
|------------|
| 98.8       |
```

## Query

Group results by a subset of series tags and regularize series in the subquery, and apply aggregation functions to subquery results in the containing query.

```sql
SELECT datetime, tags.application, tags.transaction, 
  sum(value)/count(value) as daily_good_pct
FROM (
    SELECT datetime, tags.application, tags.transaction,
      CASE WHEN sum(value) >= 0.3 THEN 1 ELSE 0 END AS "value"
    FROM "good_requests" 
    WHERE tags.application = 'SSO'
      AND tags.transaction = 'authenticate'
      AND datetime >= '2017-03-15T00:00:00Z' AND datetime < '2017-03-15T03:00:00Z'
      WITH INTERPOLATE (5 MINUTE)
      GROUP BY datetime, tags.application, tags.transaction
) 
GROUP BY tags.application, tags.transaction, PERIOD(1 hour)
```

### Results

```ls
| datetime            | tags.application | tags.transaction | hourly_good_pct | 
|---------------------|------------------|------------------|-----------------| 
| 2017-03-15 00:00:00 | SSO              | authenticate     | 1.00            | 
| 2017-03-15 01:00:00 | SSO              | authenticate     | 0.75            | 
| 2017-03-15 02:00:00 | SSO              | authenticate     | 0.83            | 
```


