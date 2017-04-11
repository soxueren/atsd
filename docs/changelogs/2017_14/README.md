Weekly Change Log: April 3 - April 9, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| 4000 | sql | Bug | Fix behavior of [`min_value_time`](/docs/api/sql#aggregation-functions) and [`max_value_time`](/docs/api/sql#aggregation-functions) functions applied to NaN or NULL values |
| [4057](#issue-4057) | sql | Feature | Support multiple conditions in `CASE` expressions |
| [4083](#issue-4083) | sql | Feature | Allow `metric LIKE` condition in queries to [`atsd_series`](/docs/api/sql#atsd_series-table) table |
| 4082 | sql | Bug | Improve query cancellation responsiveness |
| 4081 | api-network | Bug | Fix processing of commands with multiline text values with `time` token |
| 4075 | sql | Bug | Optimize queries with [`metrics()`](/docs/api/sql#metrics) function and name condition |
| 4079 | sql | Bug | Metric/entity timeZone field should return timezone text value |
| 4067 | sql | Bug | [`SELECT 1`](/docs/api/sql#validation-query) query should return both column metadata and a row with `1` value |
| 4074 | sql | Bug | Fix the NullPointerException when ordering by null tag with [`ROW_MEMORY_THRESHOLD 0`](/docs/api/sql#row_memory_threshold-option) option |
| 4066 | log_aggregator | Bug | Reduce number of filter instances in  |
| 4070 | sql | Bug | Optimize processing of `OR datetime` conditions |
| [4060](#issue-4060) | sql | Feature | Support using [`LAG`](/docs/api/sql#lag)/[`LEAD`](/docs/api/sql#lead) functions with `BETWEEN` operator |
| 4058 | sql | Bug | Fix the NullPointerException in queries with external sorting and [`OUTER JOIN`](/docs/api/sql#outer-join) with partitioning |
| 4047 | sql | Bug | Fix the NullPointerException thrown when using `IN` operator in `WHERE` condition |
| 4062 | api-rest | Bug | Fix table formatting in API documentation |

### Collector

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------|
| 4090 | jdbc | Bug | Don't log error if InterpolateType value is NULL |
| 4078 | core | Bug | Fix parsing series commands with '=' symbol in text field  |
| 4061 | file | Bug | Save error message if FTP connection fails |

## ATSD

### Issue 4057
--------------

```sql
SELECT entity, datetime, value,
CASE entity
  WHEN 'nurswgvml006' OR 'nurswgvml007' THEN '00'
  ELSE 'Unknown'
END AS 'ent'
FROM cpu_busy
WHERE datetime >= previous_minute
```

### Issue 4083
--------------

```sql
SELECT entity, metric, datetime, value
  FROM atsd_series
WHERE metric LIKE 'cpu_*'
  AND datetime >= CURRENT_HOUR
ORDER BY datetime
```

### Issue 4060
--------------

```sql
SELECT datetime, LAG(value), value, LEAD(value)
  FROM cpu_busy 
WHERE entity = 'nurswgvml007'
  AND datetime BETWEEN '2017-04-02T14:19:15Z' AND '2017-04-02T14:21:15Z'
  AND value BETWEEN LAG(value) AND LEAD(value)
```