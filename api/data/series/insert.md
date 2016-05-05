# Series: Insert

## Description

Insert timestamped array of numeric samples for a given metric, entity, and tags. 

This method can also be used to insert custom forecast and forecast deviation samples.

## Path

```
/api/v1/series/insert
```

## Method

```
POST 
```

## Request

The request must contain an array of series objects each containing an array of timestamped observations. It is recommended that observations for each series are ordered ascendingly by time.

### Headers

|**Header**|**Value**|
|---|---|
| Content-Type | application/json |

### Fields

|**Field**|**Required**|**Description**|
|---|---|---|
| entity | yes | entity name |
| metric | yes | metric name |
| tags | no | an object with named keys, where a key is a tag name and a value is tag value |
| type | no | specifies source for underlying data: `HISTORY`, `FORECAST`, `FORECAST_DEVIATION`. Default value: HISTORY |
|forecastName| no | Forecast name. <br>Applicable when `type` is set to `FORECAST` or `FORECAST_DEVIATION`. <br>`forecastName` can be used to store any number of custom forecasts. <br>If `forecastName` is not set, then the default forecast will be overwritten.  |
| data | yes | an array of `t:v` key-value objects, where key `t` is time in UNIX milliseconds and `v` is the metric's value at time `t`. Alternatively, time can be specified in ISO format using `d` field. |
|version |no| An object. Contains source, status and change time fields for versioned metrics. |

## Response

### Fields

Empty if insert was successful.

### Errors

## Example

### Request

#### URI

```
https://atsd_host:8443/api/v1/series/query
```

#### Payload

```json
[{
    "entity": "nurswgvml007",
    "metric": "mpstat.cpu_busy",
    "data": [
      { "t": 1462427358127, "v": 22.0 }
    ]
}]
```

#### curl

```css
curl https://atsd_host:8443/api/v1/series/query \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
```

### Additional Examples

* [Named Forecast](examples/insert-named-forecast.md)
* [Versioned Metric](examples/versioned-metric.md)
* Multiple series

```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 17.8 }
    ]
},{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 42.2 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 41.8 }
    ]
}]
```



