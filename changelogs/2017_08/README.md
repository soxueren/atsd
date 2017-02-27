Weekly Change Log: February 20 - February 26, 2017
==================================================

### ATSD

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------| 
| 3951 | sql         | Feature | Grant permissions to USER role to cancel the user's own query.                             |
| 3942 | core        | Bug     | Fixed PermGen Error by setting MaxPermGen in start script.                                   |
| 3938 | admin       | Bug     | Add PermGen memory usage metrics.                                     |
| 3934 | admin       | Support | Set MaxPermGen to 128mb in start-atsd scripts.                           |
| 3929 | api-rest    | Bug     | Double/float datatype cast rounding error in `DELTA` and `COUNTER` aggregators. |

### Collector

| Issue| Category    | Type    | Subject                                                                              |
|------|-------------|---------|--------------------------------------------------------------------------------------| 
| 3936 | json        | Feature | Text area for custom user commands.                                    |
| 3928 | data-source | Feature | Add separate Protocol field to data source configuration page. |
| 3926 | json        | Feature | Add syntax to extract Time, Metric, and Value fields by index from an array. |
| 3771 | docker      | Bug     | Fix database locks in Docker Data Reader.                                        |

