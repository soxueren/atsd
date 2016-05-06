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
```
https://atsd_host:8443/api/v1/series/json/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso
```
#### curl
```css
curl https://atsd_host:8443/api/v1/series/json/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso \
  -v -u {username}:{password} \
    -H "Content-Type: application/json" \
    -X GET
```

### Response

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




## Additional Examples
[CSV file Query](https://github.com/axibase/atsd-docs/blob/master/api/data/examples/series-url-query-csv-format.md)
