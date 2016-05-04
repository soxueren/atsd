## Series: Insert

Payload - an array of series with `data` arrays.
### Description 
### Path
```
/api/v1/series/insert
```

### Method
```
POST 
```
### Request 
#### Fields
|**Field**|**Required**|**Description**|
|---|---|---|---|
| entity | yes | entity name |
| metric | yes | metric name |
| tags | no | an object with named keys, where a key is a tag name and a value is tag value |
| type | no | specifies source for underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default value: HISTORY |
|forecastName| no | Unique forecast name. You can store an unlimited number of named forecasts for any series using `forecastName`. If `forecastName` is not set, then the default ATSD forecast will be overwritten. `forecastName` is applicable only when `type` is set to `FORECAST` or `FORECAST_DEVIATION` |
| data | yes | an array of key-value objects, where key 't' is unix milliseconds anf 'v' is the metrics value at time 't' |
|version |no| An object. Contains source, status and change time fields for versioned metrics. |


### Example
####Request

```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "d": "2015-02-05T12:33:00Z", "v": 22.0},
      { "d": "2015-02-05T12:34:00Z", "v": 24.0}
    ]
},{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "d": "2015-02-05T12:33:00Z", "v": 42.0},
      { "d": "2015-02-05T12:34:00Z", "v": 44.0}
    ]
}]
```
### Additional Examples
* [Named Forecast](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/insert-named-forecast.md)
* [Versioned Metric](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/versioned-metric.md)



