Weekly Change Log: December 19-25, 2016
=======================================

### ATSD

| Issue| Category    | Type    | Subject                                                                                               | Updated             |
|------|-------------|---------|-------------------------------------------------------------------------------------------------------|---------------------| 
| 3737 | sql         | Bug     | SQL: long scan and timeout on last insert table for entity that does not collect the specified metric | 12/22/2016 01:52 PM | 
| 3735 | sql         | Bug     | SQL: Math functions do not accept arithmetic expressions in WHERE clause                              | 12/22/2016 01:51 PM | 
| 3731 | api-rest    | Bug     | Data API: meta section not available in property query for $entity_tags type                          | 12/21/2016 07:46 PM | 
| 3729 | api-rest    | Bug     | NPE in time series statistics calculation                                                             | 12/21/2016 07:49 PM | 
| 3727 | api-network | Feature | Telnet: process series commands using thread pool                                                     | 12/21/2016 07:52 PM | 
| 3725 | sql         | Bug     | SQL: optimize order by queries with limit                                                             | 12/20/2016 10:42 AM | 
| 3719 | sql         | Feature | SQL: Optimize last_time                                                                               | 12/20/2016 04:34 PM | 
| 3718 | UI          | Bug     | Metric Form : Illegal metric name                                                                     | 12/22/2016 03:16 PM | 
| 3715 | UI          | Feature | UI: apply style to account create page                                                                | 12/21/2016 07:49 PM | 
| 3714 | UI          | Bug     | UI: decimal format error                                                                              | 12/21/2016 07:49 PM | 
| 3713 | sql         | Bug     | SQL: number format error due to WHERE condition                                                       | 12/19/2016 01:30 PM | 
| 3703 | sql         | Feature | SQL: aggregation for time column                                                                      | 12/19/2016 02:55 PM | 
| 3697 | sql         | Feature | SQL: Option to apply HAVING filter to interpolated data                                               | 12/19/2016 01:30 PM | 
| 3696 | sql         | Bug     | SQL: order by column index error                                                                      | 12/19/2016 01:32 PM | 
| 3694 | sql         | Bug     | SQL: tag filter not applied to JOIN                                                                   | 12/19/2016 01:31 PM | 
| 3693 | UI          | Bug     | UI: SQL Query Plan - post on go back                                                                  | 12/21/2016 07:49 PM | 
| 3689 | sql         | Feature | SQL: SELECT 1 query                                                                                   | 12/19/2016 01:31 PM | 
| 3687 | UI          | Bug     | UI: Admin menu active link incorrect                                                                  | 12/21/2016 07:49 PM | 
| 3672 | sql         | Feature | SQL: query plan - include atsd_li scan and start/end times                                            | 12/19/2016 02:32 PM | 
| 3555 | sql         | Feature | SQL: LOOKUP function to perform a value replacement                                                   | 12/20/2016 06:24 PM | 
| 3421 | sql         | Feature | SQL: CASE expression                                                                                  | 12/23/2016 04:26 PM | 

### Charts

| Issue| Category    | Type    | Subject                                                                                               | Updated             |
|------|-------------|---------|-------------------------------------------------------------------------------------------------------|---------------------| 
| 2312 | test        | Support | Charts screenshots validation                                                                         | 12/20/2016 04:34 PM | 

### Collector

| Issue| Category    | Type    | Subject                                                                                               | Updated             |
|------|-------------|---------|-------------------------------------------------------------------------------------------------------|---------------------| 
| 3745 | docker      | Bug     | Docker: disable old checker                                                                           | 12/24/2016 08:24 PM | 
| 3732 | core        | Feature | Collector Start: Wait until ATSD is ready                                                             | 12/21/2016 07:47 PM | 
| 3724 | core        | Bug     | docker-compose file to launch socrata-cdc                                                             | 12/19/2016 02:57 PM | 
| 3723 | data-source | Bug     | ATSD jdbc driver: missing dependencies                                                                | 12/20/2016 04:29 PM | 
| 3722 | core        | Feature | Start-ip: property send                                                                               | 12/19/2016 02:31 PM | 
| 3686 | core        | Feature | Job Auto-start                                                                                        | 12/20/2016 04:37 PM | 
| 3571 | admin       | Bug     | Dockerfile: long container startup - explode war during image build                                   | 12/19/2016 02:31 PM | 

