Weekly Change Log: April 17 - April 23, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| 4102 | jdbc | Feature | Add support for BOOLEAN data type in ATSD JDBC driver. |
| 4101 | sql | Bug | Change interval start in [PERIOD](../../api/sql#period) function to 1970-01-01. |
| [4100](#issue-4100) | sql | Feature | ADD time zone parameter in [PERIOD](../../api/sql#period) function.  |
| 4051 | rule engine | Feature | Add alias [date_parse](../../rule-engine/functions-time.md#the-date_parse-function) for `date` function in Rule Engine, deprecate `date` function. |

### Collector

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| [4094](#issue-4094) | docker | Feature | Upgrade supported API versions.  |
| 4044 | docker | Bug | Handle HTTP request interruption for unix socket. |


## ATSD

### Issue 4100
--------------

```sql
SELECT datetime, date_format(time, "yyyy-MM-dd'T'HH:mm:ssZZ") AS local_datetime, 
  MIN(value), MAX(value), COUNT(value), FIRST(value), LAST(value)
FROM m1
  GROUP BY PERIOD(1 DAY, "UTC")
```

## Collector

### Issue 4094
--------------
![](Images/Figure1.png)
