# Series: URL Query

## Description

Retrieve series values for the specified entity, metric, and optional series tags in CSV and JSON format. 

## Request 

### Path

```elm
/api/v1/series/{csv|json}/{entity}/{metric}
```

### Method 

```
GET
```

### Headers

None.

### Parameters 

|**Parameters**|**Required**|**Description**|
|:---|:---|:---|
|t:name|no|Tag name, prefixed by `t:`. Tag value specified as parameter value, for example, `&t:file_system=/tmp`. <br>Multiple values for the same tag can be specified by repeating parameter, for example, `&t:file_system=/tmp&&t:file_system=/home/export`|
|startDate|yes* |Start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|yes* |End of the selection interval. Specified in ISO format or using endtime syntax.|
|interval|yes* |Duration of the selection interval, specified as `count`-`timeunit`, for example, 1-hour|
|timeFormat|no|Timestamp format in response: `iso` or `milliseconds`. <br>Default format: `milliseconds`|
|period|no|Duration of regular time period for grouping raw values. <br>Specified as `count`-`timeunit`, for example, 1-hour.|
|aggregate|no|Statistical function to compute aggregated values for values in each period|
|limit|no|Maximum number of samples returned in response. Default value: 0|
|last|no|Performs GET instead of scan. Retrieves only 1 most recent value. Default value: false|
|columns|no|Columns included in CSV format response. <br>Possible values: time, date (time in ISO), entity, metric, t:{name}, value. <br>Default: time, entity, metric, requested tag names, value

> Interdependent fields. Interval start and end should be set using a combination of startDate, endDate and interval.

## Response

### Fields

#### json

See response fields in [Series: Query](query.md#response-fields)

#### csv

Header: `time/date, entity, metric, tag names as specified in t: parameters or column, value`

Data rows.

Example:

```ls
time,entity,metric,value
2016-05-22T12:00:08Z,nurswgvml007,cpu_busy,26.53
```

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/series/json/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/series/csv/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&timeFormat=iso \
  --insecure --verbose -user {username}:{password} \
  --request GET
```

### Response

#### json

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

#### csv

```ls
time,entity,metric,value
2016-05-22T12:00:08Z,nurswgvml007,cpu_busy,26.53
2016-05-22T12:00:24Z,nurswgvml007,cpu_busy,17.35
2016-05-22T12:00:40Z,nurswgvml007,cpu_busy,12.24
2016-05-22T12:00:56Z,nurswgvml007,cpu_busy,15
2016-05-22T12:01:12Z,nurswgvml007,cpu_busy,6.06
```

## Additional Examples

[CSV file Query](https://github.com/axibase/atsd-docs/blob/master/api/data/examples/series-url-query-csv-format.md)
