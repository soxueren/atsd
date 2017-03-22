Weekly Change Log: March 13 - March 19, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------|
| 4021 | sql | Bug | Allowed using [CASE](/api/sql#case) expression without table specified. |
| 3981 | sql | Bug | Allowed using [CASE](/api/sql#case) expression in aggregation functions. |
| 3837 | sql | Bug | Added support for constant strings and numbers in the [SELECT](/api/sql#select-expression) expressions. |
| 4007 | sql | Bug | Fixed addressing to non-existing tag. |
| [3658](#issue-3658) | sql | Feature | Provided an ability to address metric parameters in SQL |
| 4017 | sql | Bug | Fixed [CONCAT](/api/sql#string-functions) function with empty-string arguments |
| [3907](#issue-3907) | applications | Feature | Implemented ATSD query_runner in Redash |
| 4011 | csv | Bug | Fixed the ConcurrentModificationException in API test |
| 4010 | core | Bug | Fixed HBase filters to prevent getting into infinite loop |
| 3950 | sql | Bug | Added support for the SUM function in [SELECT](/api/sql#select-expression) expressions |
| 3913 | sql | Bug | Added support for [CASE](/api/sql#case) as a part of the [SELECT](/api/sql#select-expression) expression |
| 3888 | sql | Bug | Added support for entity attributes in WHERE clause |
| 3881 | sql | Bug | Fixed NullPointerException in queries with aggregation of metrics with decimal datatype |
| 3842 | sql | Bug | Added support for column alias in [CAST](/api/sql#cast) and [ROW_NUMBER](/api/sql#row_number-syntax) functions |
| 3838 | sql | Bug | Added support for column alias in ORDER BY clause |
| 3979 | applications | Bug | Fixed widget height in the [data slider](https://apps.axibase.com/slider/energinet-2017) |
| 3963 | client | Bug | Replaced `last` with `cache` parameter in Java API client |

### Collector

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------|
| [4001](#issue-4001) | jdbc | Feature | Substituted Replacement tables with LOOKUP function |
| 3992 | socrata | Bug | Applied encoding to JSON output |
| 4004 | socrata | Bug | Added support for request parameters in URL wizard |

### Charts

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------|
| [4006](#issue-4006) | widget-settings | Feature | Added fixed-fraction formatter |
| [3997](#issue-3997) | time-chart | Bug | Changed legend style for forecast series |


### ATSD

### Issue 3658
--------------

```sql
SELECT metric,
    metric.dataType,
    metric.description,
    metric.enabled,
    metric.filter,
    metric.id,
    metric.interpolate,
    metric.invalidValueAction,
    metric.label,
    metric.lastInsertTime
FROM df.disk_used
LIMIT 1
```

| metric | metric.dataType | metric.description | metric.enabled | metric.filter | metric.id | metric.interpolate | metric.invalidValueAction | metric.label | metric.lastInsertTime |
|---|---|---|---|---|---|---|---|---|---|
| df.disk_used | FLOAT | null | true | tags.file_system != 'none' | 35 | LINEAR | NONE | null | null |

```sql
SELECT metric,
    metric.maxValue,
    metric.minValue,
    metric.name,
    metric.persistent,
    metric.retentionIntervalDays,
    metric.tags,
    metric.timePrecision,
    metric.timeZone,
    metric.units,
    metric.versioning
FROM df.disk_used
LIMIT 1
```

| metric | metric.maxValue | metric.minValue | metric.name | metric.persistent | metric.retentionIntervalDays | metric.tags | metric.timePrecision | metric.timeZone | metric.units | metric.versioning |
|---|---|---|---|---|---|---|---|---|---|---|
| df.disk_used | null | null | df.disk_used | true | 0 | null | MILLISECONDS | null | null | false |

### Issue 3907
--------------

See the [walkthrough](https://github.com/axibase/website/blob/master/user-guide/data-sources/axibase_tsd.md) on how to configure Redash to work with ATSD.


### Collector

### Issue 4001
--------------

Replacement tables are deprecated. Similar functionality is provided by the `LOOKUP` function.

![](Images/Figure1.png)

### Charts

### Issue 4006
--------------

| Setting | Description |
|---------|-------------|
| formatter = fixed(n) | Format numbers with the specified number of digits in the fraction portion of a number.<br>Default: 0 fractional digits. |

https://apps.axibase.com/chartlab/b510b820

### Issue 3997
--------------

![](Images/Figure2.png)

https://apps.axibase.com/chartlab/075941a0/2/