# Series URL: Query
## Description
## Path
```
GET
```
## Method 
```
/api/v1/series/{csv|json}/{entity}/{metric}?t:name=t:value
```
## Request 
### Parameters 
|Parameters|Required|Description|
|---|---|---|
|t:name|no|Tag name, prefixed by `t:`. Tag value specified as parameter value, for example, `&t:file_system=/tmp`. Multiple values for the same tag can be specified by repeating parameter, for example, `&t:file_system=/tmp&&t:file_system=/home/export`|
|startTime|no* |start of the selection interval. Specified in UNIX milliseconds.|
|endTime|no* |end of the selection interval. Specified in UNIX milliseconds.|
|startDate|no* |start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|no* |end of the selection interval. Specified in ISO format or using endtime syntax.|
|interval|no|Duration of the selection interval, specified as `count`-`timeunit`, for example, 1-hour|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
|period|no|Duration of regular time period for grouping raw values. Specified as `count`-`timeunit`, for example, 1-hour.|
|aggregate|no|Statistical function to compute aggregated values for values in each period|
|limit|no|maximum number of data samples returned. Default value: 0|
|last|no|Performs GET instead of scan. Retrieves only 1 most recent value. Boolean. Default value: false|
|columns|no|Specify which columns must be included. Possible values: time, date (time in ISO), entity, metric, t:{name}, value. Default: time, entity, metric, requested tag names, value

<aside class="notice">
* Interdependent fields. Interval start and end should be set using a combination of startTime, endTime, startDate, endDate and interval.
</aside>

## Response
### Errors

## Example
### Request
#### URI

> Request

```
/api/v1/series/json/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso
```

> Response

```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "mpstat.cpu_busy",
            "tags": {},
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-04-27T11:00:09Z",
                    "v": 5.05
                },
                {
                    "d": "2015-04-27T11:00:25Z",
                    "v": 3.03
                },
                {
                    "d": "2015-04-27T11:00:41Z",
                    "v": 5
                }
            ]
        }
    ]
}
```
### Request Fields


> Request

```
/api/v1/series/csv/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&columns=date,entity,metric,value
```

> Response

> CSV file is exported, example content:

```
time,entity,metric,value
2015-05-12T14:00:00Z,nurswgvml007,mpstat.cpu_busy,7.0
2015-05-12T14:01:00Z,nurswgvml007,mpstat.cpu_busy,2.0
2015-05-12T14:02:00Z,nurswgvml007,mpstat.cpu_busy,5.0
2015-05-12T14:03:00Z,nurswgvml007,mpstat.cpu_busy,4.95
2015-05-12T14:04:00Z,nurswgvml007,mpstat.cpu_busy,0.0
```

<aside class="notice">
If endTime is not specified, endDate is used. If endDate is not specified an error is raised.
If startTime is not specified, startDate is used. If startDate is not specified, endDate is used minus interval. If no start can be established, an error is raised.
</aside>
