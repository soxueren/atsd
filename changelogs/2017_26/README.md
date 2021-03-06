Weekly Change Log: June 26, 2017 - July 02, 2017
==================================================

### ATSD 


| Issue| Category    | Type    | Subject              |
|------|-------------|---------|----------------------|
| 4335 | sql | Feature | Optimize metadata queries with a `FALSE` condition, e.g. `WHERE 1=0`. The queries are used by some BI tools (Tableau) to retrieve column information. |
| [4331](#Issue-4331) | UI | Feature | Implement right-to-left Text Direction layout for Arabic and Hebrew languages. |
| 4327 | search | Feature | Implement [Synonym Search](https://github.com/axibase/atsd/blob/master/search/synonyms.md). |
| [4327a](#Issue-4327a) | search | Feature | Implement [Series Search](https://github.com/axibase/atsd/blob/master/search/README.md). |
| 4313 | export | Bug | Modify Excel files produced by ATSD to increase compatability with analytics tools such as IBM SPSS. |
| 4312 | sql | Bug | Fix a NullPointerException in SQL console when results contain [`NULL`](https://github.com/axibase/atsd/tree/master/api/sql#null) values. |

### ATSD

##### Issue 4331

![](Images/4331.jpg)

##### Issue 4327a

![](Images/4327a.jpg)