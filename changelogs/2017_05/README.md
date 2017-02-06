Weekly Change Log: January 30 - February 5, 2017
================================================

### ATSD

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------| 
| 3878 | api-network | Bug     | [series](/api/network/series.md#series-command) command doens't support line break in tag value and in x (annotation) field.     |
| 3874 | api-network | Bug     | [series](/api/network/series.md#series-command) command fails to overwrite value when inserted in batch with append flag            | 
| 3873 | sql         | Bug     | Malformed tag names [JOIN](/api/sql#joins) query                                                                  | 
| 3870 | rule engine | Bug     | expression evaluation error is propagated to clients instead of catching the exception  | 
| 3869 | sql         | Bug     | unexpected IllegalArgumentException                                                             | 
| 3862 | rule engine | Bug     | html escape for rule.value fields (fields used to define aliases)                       | 
| 3861 | client      | Feature | [python](https://github.com/axibase/atsd-api-python#sql-queries) - support for SQL                                                                 | 
| 3858 | sql         | Bug     | Decimal precision sometimes ignored in scheduled SQL report                                     | 
| 3854 | rule engine | Feature | extend date, seconds, and milliseconds functions to allow custom time format            | 
| 3853 | sql         | Bug     | keyword [QUARTER](/api/data/series/time-unit.md#time-unit) does not exist                                                                  | 
| 3851 | data-in     | Bug     | udp                                                                                | 
| 3843 | sql         | Bug     | keywords are case-sensitive                                                                     | 
| 3841 | sql         | Bug     | [CAST](/api/sql#cast) in [WHERE](/api/sql#where-clause) filter changes results                                                            | 
| 3840 | sql         | Bug     | [ORDER BY](/api/sql#ordering-1) not applied when using column alias                                                    | 

### Collector

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------|
| 3876 | socrata     | Bug     | job fails with invalid expression                                                           |
| 3866 | socrata     | Bug     | form                                                                                        |
| 3865 | socrata     | Bug     | time hueristics                                                                             |
| 3849 | socrata     | Bug     | numeric field with non-standard N/A constant                                        |
| 3848 | socrata     | Bug     | extract command time from updated_at field                                                  | 
| 3826 | socrata     | Feature | add annotation field                                                                    | 

### Charts

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------| 
| 3839 | time-chart  | Bug     | negative style no applied to marker                                                      | 
| 3836 | bar         | Bug     | remove onload animation                                                         | 
| 3794 | box         | Bug     | tooltip issues                                                                                  | 