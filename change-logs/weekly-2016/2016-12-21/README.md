Weekly Change Log: December 12-18, 2016
=======================================

### ATSD

| Issue    | Category        | Type            | Subject                                                   | Date Released       |                    
|----------|-----------------|-----------------|-----------------------------------------------------------|---------------------|
| 3710     | api-rest        | Feature         | Embedded user groups                                      | 12/14/2016 05:04 PM |
| 3704     | sql             | Bug             | SQL: percentile/median error                              | 12/13/2016 04:39 PM | 
| 3702     | sql             | Bug             | SQL: change syntax error message                          | 12/13/2016 08:29 PM | 
| 3701     | sql             | Feature         | SQL: optimize partitioning queries                        | 12/15/2016 12:22 PM | 
| 3325     | sql             | Bug             | SQL: count(column) error                                  | 12/16/2016 10:34 AM | 

### Collector

| Issue    | Category        | Type            | Subject                                                   | Date Released       |       
|----------|-----------------|-----------------|-----------------------------------------------------------|---------------------| 
| 3717     | docker          | Feature         | Docker: container label from env                          | 12/14/2016 01:35 PM | 
| 3716     | docker          | Bug             | Docker: image label with empty parent                     | 12/13/2016 04:26 PM | 
| 3700     | docker          | Bug             | Docker: process - convert to item list                    | 12/13/2016 04:26 PM | 
| 3699     | docker          | Bug             | Docker: volume label duplicates                           | 12/12/2016 12:20 PM | 
| 3692     | UI              | Bug             | UI: manual job run to check storage status                | 12/15/2016 09:40 PM | 
| 3685     | docker          | Feature         | Docker: remove deleted records from ATSD                  | 12/13/2016 04:26 PM | 
| 3684     | UI              | Bug             | UI: Enable/Disable/Run buttons                            | 12/14/2016 03:39 PM | 


### Issue 3685
--------------

Recently added to the `docker` job in Collector is the ability to set a time limit for the retention of deleted container records (days), as shown in the image below.  

![Figure 1](Figure1.png)

A number input is required (0 - any), with the default being 0. To keep these records, you must select the `Retain deleted image/volume/network records` checkbox. Default is set to false.
All deletions are logged to file.  

Containers with a `deleted` status will initially be retained in ATSD for the specified time interval (for example 50 days in the above image). The status of these containers is marked as
`deleted`. After the interval has passed, the containers will be permanently removed from ATSD.  

### Issue 3701
--------------

In this issue, we took a look at optimizing partitioning queries. Let's take the below query as an example.

```json
SELECT tags.city, tags.state, value 
  FROM cdc.all_deaths
WHERE entity = 'mr8w-325u' AND tags.city IS NOT NULL
  AND tags.region = '2'
WITH ROW_NUMBER(tags ORDER BY datetime desc) <= 1
  ORDER BY value DESC
```

To optimise the query we found the very last insert time for the metric `cdc.all_deaths`, the entity `mr8w-325u`, and `tags.city` for the condition `IS NOT NULL`.
This minimum last insert time is 1972-05-27T00:00:00Z and was used as a scan start time to limit the result of the scan. Considering the fact that the query has no time conditions in 
the `WHERE` clause, we fetched all rows for `cdc.all_deaths` and  `mr8w-325u` before this optimization. A condition like `ROW_NUMBER(tags ORDER BY datetime desc) <= 1`
means we only need the last value for each combination of tags. We can therefore skip values with a timestamp less than the minimum last insert time.

Check out our article on [SQL queries for U.S. death statistics](https://github.com/axibase/atsd-use-cases/blob/master/USMortality/README.md). 
