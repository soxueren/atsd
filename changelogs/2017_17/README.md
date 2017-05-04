Weekly Change Log: April 25 - April 30, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| [4085a](#issue-4085a) | UI | Feature | Add time formatting options in SQL Console |
| 4085b | UI | Feature | Perform client-side formatting on options change in SQL Console |
| 4148 | UI | Bug | Fix recognizing datetime columns as numeric ones in queries with [JOIN](../..//api/sql#joins) clause in SQL Console |
| 4137 | UI | Bug | Fix parsing of non-numeric client-formatted values in SQL Console |
| 4114 | UI | Bug | Prepend a prefix to keys persisted in localStorage by API Client  |
| 4119 | api-rest | Bug | Optimize [series query](../../api/data/series/query.md) with `limit = 1` and `entity = "*"` |
| [4134](#issue-4134) | portal | Feature | Add a default template for new portal configurations |
| 4120 | sql | Bug | Speed up queries with interpolation mode = PREVIOUS |
| 4069 | sql | Bug | Fix interpolation issues in queries with overlapping periods |

### Charts

| Issue| Category    | Type    | Subject                                                             |
|------|-------------|---------|---------------------------------------------------------------------|
| 4126 | box | Bug | Add 'Last' to the tooltip in box chart |
| 4122 | widget-settings | Bug | Remove colon suffix in label when `add-meta` is used and `legend-last-value = false` |

## ATSD

### Issue 4085a
--------------
![](Images/Figure1.png)

### Issue 4134
--------------
![](Images/Figure2.png)




