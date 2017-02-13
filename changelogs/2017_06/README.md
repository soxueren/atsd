Weekly Change Log: February 6 - February 12, 2017
=================================================

### ATSD

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3912 | sql            | Bug     | NPE in JOIN without a where condition                                                       |
| 3910 | rule engine    | Bug     | errors when any rule is saved                                                       |
| 3909 | rule engine    | Feature | add previous value filter                                                           |
| 3902 | api-network    | Bug     | series command delete text value when inserted in batch with append flag               |
| 3894 | rule engine    | Bug     | Couldn't enable rule that try to use array variable                                 |
| 3893 | sql            | Bug     | delta aggregator returns incorrect values when dates are filtered                           |
| 3892 | sql            | Bug     | Invalid grouping for column "time"                                                          |
| 3890 | sql            | Bug     | high-cardinility metric: tag encoding error                                                 |
| 3887 | UI             | Bug     | HTML string in query affects the page                                                        | 
| 3885 | api-network    | Bug     | series command fails to append message when inserted in batch with append flag         |
| 3883 | rule engine    | Bug     | allow variables to be referenced by other variables                                 |
| 3880 | sql            | Bug     | NPE in JOIN query when aggregating null values                                              |
| 3879 | rule engine    | Feature | time filter                                                                         |
| 3872 | sql            | Bug     | Incorrect merge with "JOIN USING entity"                                                    |
| 3863 | rule engine    | Support | oebb message processor                                                              |
| 3860 | api-rest       | Bug     | series query returns no text value with last=true                                      |
| 3844 | sql            | Bug     | Error when ISNULL is a part of expression                                                   |
| 3528 | data-in        | Support | Amgen Data Inserted                                                                              |
| 3508 | log_aggregator | Feature | message size                                                                     |

### Collector

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3903 | socrata        | Bug     | trim tag value prior to saving, discard empty                                           |
| 3899 | socrata        | Feature | launch automated job from dataset url                                                   |
| 3877 | socrata        | Bug     | annotation field name must be set to numeric metric name                                |
| 3864 | socrata        | Feature | display summary table in Test mode                                                      |
| 3859 | socrata        | Feature | Add URL wizard                                                                          | 

### Charts

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3908 | table          | Bug     | fix sort when "display: none' is used                                                            |
| 3901 | portal         | Support | hide rows without alert and fix getDB method                                             |
| 3792 | box            | Feature | metro class                                                                                 |

