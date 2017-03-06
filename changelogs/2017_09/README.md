Weekly Change Log: February 27 - March 5, 2017
==============================================

### ATSD

| Issue| Category        | Type    | Subject                                                                              |
|------|-----------------|---------|--------------------------------------------------------------------------------------|
| 3940 | client          | Feature | `sendCommands` method                                                                |
| 3918 | api-rest        | Bug     | URL query returns no data with last=true                                             |

### Collector

| Issue| Category        | Type    | Subject                                                                              |
|------|-----------------|---------|--------------------------------------------------------------------------------------|  
| 3977 | UI              | Feature | Show referencing configurations for HTTP Pools, Database Configurations, Replacement Tables |
| 3976 | collection      | Feature | Text area for script text                                                            |
| 3974 | http-pool       | Bug     | Can't delete pool                                                                    |
| 3969 | json            | Bug     | Can't include field if it is already included                                        |
| 3967 | collection      | Bug     | Items are not substituted instead of `${ITEM}`                                       |
| 3949 | json            | Bug     | Entity field included as metric                                                      |
| 3933 | json            | Bug     | Field specification inconsistency                                                    |
| 3932 | json            | Feature | Fetching tag name/value with JSON Path                                               |
| 3829 | scheduler       | Bug     | Disable Run if the job is already running                                            |
| 3817 | socrata         | Feature | Skip re-sending of old data                                                          |

### Charts

| Issue| Category        | Type    | Subject                                                                              |
|------|-----------------|---------|--------------------------------------------------------------------------------------| 
| 3970 | treemap         | Bug     | Display not hiding series                                                            |
| 3964 | table           | Bug     | Process alert-expression before hide-columns                                         |
| 3961 | treemap         | Feature | Layout mode                                                                          |
| 3960 | time-chart      | Bug     | Legend is hidden even if multiple series requested                                   | 
| 3959 | api             | Feature | For last=true option remove api requests with last=true                              |
| 3941 | widget-settings | Feature | Meta function to retrieve entity and metric tags in threshold and other calculations |
| 3927 | core            | Bug     | Widget content geometry should be calculated earlier                                 |

## ATSD

## Collector

## Charts

### Issue 3961
--------------

https://apps.axibase.com/chartlab/fc68bae4/7/

![](Images/Figure1.png)

### Issue 3959
--------------

https://apps.axibase.com/chartlab/25551747

### Issue 3941
--------------

https://apps.axibase.com/chartlab/2b15e6f9

https://apps.axibase.com/chartlab/c4c1f7b8

https://apps.axibase.com/chartlab/c9bd5eb5
