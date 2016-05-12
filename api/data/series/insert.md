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

```
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
curl https://atsd_host:8443/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
```

```css
curl https://atsd_host:8443/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d @payload
```
## Additional Examples

* [Versioned Metric](/api/data/examples/versioned-metric.md)
* [Multiple Series](/api/data/examples/insert-multiple-series.md)
* [Named Forecast](/api/data/examples/insert-named-forecast.md)
* [ISO Time Format](/api/data/examples/series-insert-1ex.md)
* [A few objects in data array](/api/data/examples/series-insert-2ex.md)
* [insert with Tags](/api/data/examples/series-insert-4ex.md)



