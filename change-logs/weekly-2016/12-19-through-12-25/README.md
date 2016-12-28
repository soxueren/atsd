Weekly Change Log: December 19-25, 2016
=======================================

### ATSD

| Issue| Category    | Type    | Subject                                                                                               |
|------|-------------|---------|-------------------------------------------------------------------------------------------------------|
| [3737](#issue-3737) | sql         | Bug     | Fixed issue with long scan and timeout for an entity that does not collect the specified metric. | 
| [3735](#issue-3735) | sql         | Bug     | Math functions do not accept arithmetic expressions in the `WHERE` clause. Updated error message to read 'IllegalArgumentException: Aggregate functions are not supported in the WHERE clause'. | 
| 3731 | api-rest    | Bug     | Fixed an issue with property queries where metadata was not present in the response if the property type was set to `$entity_tags`. | 
| 3729 | api-rest    | Bug     | For non-existent URLs in ATSD, updated error URL and message. | 
| [3727](#issue-3727) | api-network | Feature | Optimized TCP handler, running on the default port 8081, for faster processing of `series` commands streamed by a single TCP client. | 
| [3725](#issue-3725) | sql         | Bug     | Optimized order by `ASC` and `DESC` queries by appending the `LIMIT` clause. | 
| [3719](#issue-3719) | sql         | Feature | Narrowed/Optimized the start date in [windowing](https://github.com/axibase/atsd-docs/tree/master/api/sql#last_time-syntax) queries, which is now determined as the minimum (last insert date) for all series. | 
| 3718 | UI          | Bug     | Changed metric form to prevent users from saving metric names without metric name validation. | 
| 3715 | UI          | Feature | Updated styling characteristics to account create page for ATSD installation. | 
| 3714 | UI          | Bug     | In the SQL console removed '0' from decimal precision and set non-valid numbers to -1. | 
| [3713](#issue-3713) | sql         | Bug     | Fixed number format error due to the `WHERE` condition. |
| [3703](#issue-3703) | sql         | Feature | Added capability to display first and/or last sample time in the `WITH` clause. | 
| [3697](#issue-3697) | sql         | Feature | Updated SQL sequence so that the `HAVING` filter is applied to both existing and interpolated periods, and will be applied after period interpolation. | 
| [3696](#issue-3696) | sql         | Bug     | Refactored the `ORDER BY 1` clause into `ORDER BY` to fix column index errors. | 
| [3694](#issue-3694) | sql         | Bug     | To optimize query execution time, applied tag filters to all metrics in the `JOIN` clause. | 
| 3693 | UI          | Bug     | Updated SQL query plan. Now, after executing the query, and clicking on the query plan and then back in the browser, you do not need to resubmit the query, as the query text will be retained. | 
| [3689](#issue-3689) | sql         | Feature | Added support for the `SELECT 1` query without the `FROM` clause, which can be used for testing connectivity. | 
| 3687 | UI          | Bug     | Corrected Admin menu for User Groups (active link was Entity Groups). | 
| [3672](#issue-3672) | sql         | Feature | Extended query plan (used for diagnosing slow query response times) to display start and end dates for each HBase scan and scans to atsd_lies. | 
| [3555](#issue-3555) | sql         | Feature | Implemented [LOOKUP](https://github.com/axibase/atsd-docs/tree/master/api/sql#lookup) function to translate the key into a value using the specified replacement table. | 
| [3421](#issue-3421) | sql         | Feature | Implemented the `searched case` variant of the [CASE](https://github.com/axibase/atsd-docs/tree/master/api/sql#case) expression to evaluate a sequence of boolean expressions and return a matching result expression. | 

### Collector

| Issue| Category    | Type    | Subject                                                                                               |
|------|-------------|---------|-------------------------------------------------------------------------------------------------------|
| 3745 | docker      | Bug     | Disabled old Docker checker program to test the built-in checker in Collector. | 
| 3732 | core        | Feature | Updated Collector start to wait until ATSD is ready when running the [docker-compose](https://github.com/axibase/atsd-use-cases/blob/master/USMortality/resources/docker-compose.yml) file. | 
| 3724 | core        | Bug     | Created a `docker-compose` file to launch socrata-cdc and the ATSD/Collecot container bundle, used for computing [mortality statistics](https://github.com/axibase/atsd-use-cases/blob/master/USMortality/README.md). | 
| 3723 | data-source | Bug     | Added missing avatica package dependencies to the ATSD jdbc driver. | 
| 3722 | core        | Feature | Upon Collector start up, added that a property command be sent to the primary storage driver. | 
| 3686 | core        | Feature | Added a list of pre-configured jobs and their xml files [here](https://github.com/axibase/axibase-collector-docs/blob/updating-collector-docs/docker-job-autostart.md#autostart-job-from-file). Examples are included for starting multiple jobs at once. | 
| 3571 | admin       | Bug     | Created faster Collector application startup from the docker container. |

### Issue 3727
-------------

The TCP handler, running on the default port 8081, was optimized for faster processing of `series` commands streamed by a single TCP client. The new implementation provides a pool of 
threads instead of a single one to offload parsing and processing from the TCP handler. The size of the pool is controlled with the `series.processing.pool.size` parameter on the
Admin > Server Properties -> Network page. The default value is 2 and is recommended to be set to the number of cores on the server.

As a result, the TCP processing and parsing throughput (measured in commands per second) has increased by 40% on average.

### Issue 3725
--------------

Previously, execution of queries with the `LIMIT` clause involved copying selected rows into a temporary table, even if only a small subset of the rows, restricted with `LIMIT`, was required.
Both `ASC` and `DESC` ordered results were optimized by reducing the number of rows copied into a temporary table. The following queries should see a 90% speedup in execution time.

```sql
SELECT *
  FROM mpstat.cpu_busy tot
  ORDER BY datetime DESC
LIMIT 10
```

```sql
SELECT *
  FROM mpstat.cpu_busy tot
  ORDER BY datetime
LIMIT 10
```

### Issue 3703
--------------

Now aggregate functions such as `MAX`, `MIN`, and `DELTA` can be applied to the `time` column, which returns the sampling time in Unix milliseconds.
One of the use cases is to display the most recent time in windowing queries where the [last_time](https://github.com/axibase/atsd-docs/tree/master/api/sql#last_time-syntax) function can be utilized to select data for a sliding interval, such as the most recent 4 weeks for each series in the example below.

```sql
SELECT tags.city, tags.state, sum(value), date_format(max(time)) as Last_Date
  FROM dmv.incidents
GROUP BY entity, tags
  WITH time > last_time - 2*week
ORDER BY max(time)
```

```ls
| tags.city    | tags.state | sum(value) | Last_Date                | 
|--------------|------------|------------|--------------------------| 
| Fort Worth   | TX         | 411        | 2009-01-31T00:00:00.000Z | 
| Philadelphia | PA         | 53882      | 2012-11-24T00:00:00.000Z | 
| Pittsburgh   | PA         | 38926      | 2015-06-27T00:00:00.000Z | 
| New Haven    | CT         | 13311      | 2016-07-16T00:00:00.000Z | 
| Washington   | DC         | 41937      | 2016-08-06T00:00:00.000Z | 
```

### Issue 3719
--------------

We added an optimization to narrow the start date in [windowing](https://github.com/axibase/atsd-docs/tree/master/api/sql#last_time-syntax) queries which is now determined as the minimum (last insert date) for all series.
Prior to this change, the start date was set to 0 (not applied) if it was not specified explicitly in the query.

```sql
SELECT date_format(time, 'yyyy-MM-dd') as 'date', 
  tags.city, tags.state, sum(value)
FROM dmv.incidents
  GROUP BY entity, tags, datetime
WITH time = last_time
  ORDER BY sum(value) desc
```

The above query will now scan data with a start date determined as the earliest of all the matching series.
For example, if we have 3 series with the following last insert dates:

* A - 2016-12-21
* B - 2016-10-05
* C - 2016-12-20

The SQL optimizer will add a condition `AND datetime >= '2016-20-05T00:00:00Z'`, even if it is not set in the query.

### Issue 3697
--------------

The sequence of period interpolation and period filtering with the [HAVING](https://github.com/axibase/atsd-docs/tree/master/api/sql#having-filter) clause was modified.
Now the `HAVING` filter is applied after [PERIOD interpolation](https://github.com/axibase/atsd-docs/tree/master/api/sql#interpolation) whereas before it was the opposite.

```sql
SELECT date_format(period(1 MONTH)), count(value)
  FROM dmv.incidents
WHERE tags.city = 'Boston'
  AND datetime >= '2016-09-01T00:00:00Z' AND datetime < '2016-12-01T00:00:00Z'
GROUP BY period(1 MONTH, VALUE 0)
  HAVING count(value) > 0
ORDER BY datetime
```

Assuming there were no detailed records for this series in October 2016, the current result looks as follows:

```ls
| date_format(period(1 MONTH)) | sum(value) | count(value) | 
|------------------------------|------------|--------------|  
| 2016-09-01T00:00:00.000Z     | 537.0      | 4.0          | 
| 2016-11-01T00:00:00.000Z     | 234.0      | 4.0          |
```

Previous result:

```ls
| date_format(period(1 MONTH)) | sum(value) | count(value) | 
|------------------------------|------------|--------------|  
| 2016-09-01T00:00:00.000Z     | 537.0      | 4.0          | 
| 2016-10-01T00:00:00.000Z     | 0.0        | 0.0          | <- this period was added by interpolation set in period(1 MONTH, VALUE 0), after HAVING.
| 2016-11-01T00:00:00.000Z     | 234.0      | 4.0          |
```

### Issue 3694
--------------

The query optimizer was modified to apply tag filter specified in `JOIN` queries on one of the tables to the remaining tables, since 
[JOINs](https://github.com/axibase/atsd-docs/tree/master/api/sql#joins) in ATSD perform merging of rows on time, entity, and series tags anyway. Prior to this change, the tag filter 
was applied only to those tables where the filter was set explicitly.

![Figure 2](Figure2.png)

### Issue 3689
--------------

Implemented the special `SELECT 1` query, which is typically used to [test connectivity](https://github.com/axibase/atsd-docs/tree/master/api/sql#validation-query) and validate open 
connections in the shared connection pool in active state.

### Issue 3672
--------------

SQL Query Plan is used for diagnosing slow query response times. The plan was extended to:

1) Display start and end dates for each HBase scan.
2) Display scans to the atsd_li (last insert) table, which are used to add additional filters and to determine optimal query plan

![Figure 1](Figure1.png)

### Issue 3555
--------------

Implemented the [LOOKUP](https://github.com/axibase/atsd-docs/tree/master/api/sql#lookup) function, which translates the key into a value using the specified replacement table. 

The primary purpose of a replacement table is to act as a dictionary for decoding series tags/values.

```sql
SELECT datetime, entity, value, LOOKUP('tcp-status-codes', value)
  FROM 'docker.tcp-connect-status'
WHERE datetime > now - 5 * MINUTE
  AND LOOKUP('tcp-status-codes', value) NOT LIKE '*success*'
LIMIT 10
```

```ls
| datetime                 | entity   | value | LOOKUP('tcp-status-codes',value) | 
|--------------------------|----------|-------|----------------------------------| 
| 2016-12-28T13:06:11.085Z | 1f4faa42 | 1     | Connection Error                 | 
| 2016-12-28T13:06:11.085Z | 131b6339 | 1     | Connection Error                 | 
| 2016-12-28T13:06:11.085Z | 37dc00da | 2     | No Route To Host                 | 
```

### Issue 3421
--------------

Implemented the `searched case` variant of the [CASE](https://github.com/axibase/atsd-docs/tree/master/api/sql#case) expression.

The `CASE` expression evaluates a sequence of boolean expressions and returns a matching result expression.

```sql
CASE  
     WHEN search_expression THEN result_expression
     [ WHEN search_expression THEN result_expression ]
     [ ELSE result_expression ]
END 
```

Refer to [examples](https://github.com/axibase/atsd-docs/blob/master/api/sql/examples/case.md) for additional information.

```sql
SELECT entity, avg(value),
    CASE
      WHEN avg(value) < 20 THEN 'under-utilized'
      WHEN avg(value) > 80 THEN 'over-utilized'
      ELSE 'right-sized'
    END AS 'Utilization'
  FROM cpu_busy
WHERE datetime > current_hour
  GROUP BY entity
``` 


```ls
| entity       | avg(value) | Utilization    | 
|--------------|------------|----------------| 
| nurswgvml006 | 6.2        | under-utilized | 
| nurswgvml007 | 80.8       | over-utilized  | 
| nurswgvml010 | 3.8        | under-utilized | 
```

### Issue 3737
--------------

```sql
SELECT entity, tags.*, value, datetime
  FROM disk_used
WHERE datetime > now - 1 * day
  AND entity = 'nurswgvml501'
  WITH ROW_NUMBER(entity, tags ORDER BY time DESC) <= 1
```

### Issue 3735
--------------

```sql
SELECT entity, avg(value), ABS((last(value) / avg(value) - 1)*100)
  FROM cpu_busy
WHERE datetime > previous_minute
  AND ABS(value) > 0
GROUP BY entity
  HAVING ABS((last(value) / avg(value) - 1)*100) > 0
```

### Issue 3713
--------------

```sql
SELECT tot.datetime, tot.tags.city as 'city', tot.tags.state as 'state', 
 tot.value - t1.value - t24.value - t44.value - t64.value - t64o.value as 'other_deaths',
 t1.value as 'infant_deaths',
 t24.value as '1-24_deaths',
 t44.value as '25-44_deaths',
 t64.value as '45-64_deaths',
 t64o.value as '64+_deaths',
 tot.value as 'all_deaths'
FROM cdc.all_deaths tot
 JOIN cdc._1_year t1
 JOIN cdc._1_24_years t24
 JOIN cdc._25_44_years t44
 JOIN cdc._54_64_years t64
 JOIN cdc._65_years t64o
WHERE tot.entity = 'mr8w-325u'
 AND tot.tags.city = 'New York'
 AND tot.datetime > '2016-08-27T00:00:00Z'
 AND (tot.value - t1.value - t24.value - t44.value - t64.value - t64o.value) != 0 
OPTION (ROW_MEMORY_THRESHOLD 500000)
```

### Issue 3696
--------------

```sql
SELECT date_format(period(1 MONTH)), sum(value), count(value)
  FROM cdc.all_deaths tot
WHERE tags.city = 'Boston'
  AND datetime >= '2016-01-01T00:00:00Z'
GROUP BY period(1 MONTH)
  HAVING count(value) >= 4
ORDER BY 1
```

