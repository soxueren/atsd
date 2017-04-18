Weekly Change Log: April 10 - April 16, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| 4096 | UI | Bug | Fix HTML escaping on the `CSV Parsers` page. |
| 4064 | core | Support | Deprecate the `logging.properties` configuration file. |
| 3952 | test | Bug | Support ISO8601 datetime format without milliseconds in Network API tests. |
| [3739](#issue-3739) | sql | Feature | Add options for sending empty or erroneous SQL query results. |
| 2880 | UI | Bug | Perform HTML-escaping for all form data in UI. |


### Collector

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| 4095 | socrata | Bug | Fix Collector freeze on processing corrupted JSON files.  |
| 4092 | docker | Bug | Do not collect FS metrics for stopped containers. |
| [4063](#issue-4063) | core | Feature | Implement a configuration editor with syntax highlighting. |
| 4036 | jdbc | Bug | Fix column order in Test SQL result table to match the order in SQL query. |


### Charts

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| [4043](#issue-4043) | widget-settings | Bug | Fix ignoring of colors array by wildcard series. |


## ATSD

### Issue 3739
--------------
![](Images/Figure1.png)

## Collector

### Issue 4063
--------------
![](Images/Figure2.png)

## Charts

### Issue 4043
--------------

http://apps.axibase.com/chartlab/bbc5e671/5/