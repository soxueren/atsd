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
| entity | yes | Entity name |
| metric | yes | Metric name |
| tags | no | Object containing series tags, where field name represents tag name and field value is tag value.<br>`{"tag-1":string,"tag-2":string}` |
| type | no | Type ype of inserted data: `HISTORY`, `FORECAST`. Default value: `HISTORY` |
| version |no| Object containing version source and status fields for versioned metrics.<br>`{"source":string, "status":string}` |
|forecastName| no | Forecast name. <br>Applicable when `type` is set to `FORECAST`. <br>`forecastName` can be used to store a custom forecast identified by name. <br>If `forecastName` is omitted, the values overwrite the default forecast.  |
| data | yes | Array of `{t:number,v:number}` objects, <br>where `t` is time in UNIX milliseconds and `v` is the metric's value at time `t`. <br>Time can be also specified in ISO format using `d` field. <br>Set `v` to `null` to insert `NaN` (not a number), for example: `{t:1462427358127, v:null}`<br>When `type` is set to `FORECAST`, the object `{t,v}` can include an additional `s` field containing standard deviation of the forecast value in `v` |


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

### curl Example

#### http 8088 data

```css
curl http://atsd_host:8088/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
```

#### http 8088 file

```css
curl http://atsd_host:8088/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d @file.json
```

#### https 8443 data

```css
curl --insecure https://atsd_host:8443/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
  ```

## Additional Examples
* [ISO Time Format](/api/data/series/examples/series-insert-iso-time-format.md)
* [Scientific Notation](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-scietific-notation.md)
* [Null Value](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-null-value.md)
* [Multiple Samples](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-multiple-samples.md)
* [Series with Tags](/api/data/series/examples/series-insert-with-tags.md)
* [Multiple Series](/api/data/series/examples/insert-multiple-series.md)
* [Forecast](/api/data/series/examples/insert-forecast.md)
* [Named Forecast](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/insert-named-forecast.md)
* [Versioned Metric](/api/data/series/examples/versioned-metric.md)



