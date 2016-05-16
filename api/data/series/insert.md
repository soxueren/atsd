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
| data | yes | an array of `{t:number,v:number}` objects, <br>where `t` is time in UNIX milliseconds and `v` is the metric's value at time `t`. <br>Time can be also specified in ISO format using `d` field. <br>Set `v` to `null` to insert `NaN` (not a number), for example: `{t:1462427358127, v:null}` |
|version |no| An object. Contains source, status and change time fields for versioned metrics. |

## Response

### Fields

Empty if insert was successful.

### Errors
|  Status Code  |  Description  |
|---------------|---------------|
| 400 |IllegalArgumentException: Empty entity|
| 400 |IllegalArgumentException: Negative timestamp|
| 400 | IllegalArgumentException: No data |
| 500 | JsonParseException: Unexpected character "}" | 
| 500 | JsonMappingException: No enum constant in field type|

## Example

### Request

#### URI

```elm
https://atsd_host:8443/api/v1/series/insert
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
curl http://atsd_host:8088/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
```

```css
curl http://atsd_host:8088/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d @file.json
```
```css
curl --insecure https://atsd_host:8443/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
  ```

## Additional Examples
* [ISO Time Format](/api/data/examples/series-insert-iso-time-format.md)
* [Scientific Notation](https://github.com/axibase/atsd-docs/blob/master/api/data/examples/series-insert-scietific-notation.md)
* [Multiple Samples](https://github.com/axibase/atsd-docs/blob/master/api/data/examples/series-insert-multiple-samples.md)
* [Series with Tags](/api/data/examples/series-insert-with-tags.md)
* [Multiple Series](/api/data/examples/insert-multiple-series.md)
* [Forecast](/api/data/examples/insert-forecast.md)
* [Named Forecast](https://github.com/axibase/atsd-docs/blob/master/api/data/examples/insert-named-forecast.md)
* [Versioned Metric](/api/data/examples/versioned-metric.md)



