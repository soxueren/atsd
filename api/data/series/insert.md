# Series: Insert

## Description

Insert a timestamped array of numeric samples for a given metric, entity, and series tags. 

This method can also be used to insert a array of custom forecast and forecast deviation samples.

New entities and metrics will be automatically created provided they meet naming requirements.

New metrics will be initialized with `float` data type by default. To insert metric samples with another datatype, create or update metric properties using the web interface or [Meta API](/api/meta/metric/update.md).

## Request

### Path

```elm
/api/v1/series/insert
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Fields

The request must contain an array of series objects each containing an array of timestamped samples. It is recommended that samples for each series are ordered by time ascendingly.

|**Field**|**Required**|**Description**|
|:---|:---|:---|
| entity | yes | Entity name |
| metric | yes | Metric name |
| tags | no | Object containing series tags, where field name represents tag name and field value is tag value.<br>`{"tag-1":string,"tag-2":string}` |
| type | no | Type ype of inserted data: `HISTORY`, `FORECAST`. Default value: `HISTORY` |
| version |no| Object containing version source and status fields for versioned metrics.<br>`{"source":string, "status":string}` |
|forecastName| no | Forecast name. <br>Applicable when `type` is set to `FORECAST`. <br>`forecastName` can be used to store a custom forecast identified by name. <br>If `forecastName` is omitted, the values overwrite the default forecast.  |
| data | yes | Array of `{"t":number,"v":number}` objects, <br>where `t` is time in UNIX milliseconds and `v` is the metric's value at time `t`. <br>Time can be also specified in ISO format using `d` field. <br>To insert `NaN` (not a number), set `v` to `null`, for example: `{t:1462427358127, v:null}`<br>If `type` is set to `FORECAST`, the object `{t,v}` can include an additional `s` field containing standard deviation of the forecast value `v`, for example  `{t:1462427358127, v:80.4, s:12.3409}` |

## Response

### Fields

None.

### Errors

|  Status Code  |  Description  |
|---------------|:---------------|
| 400 |IllegalArgumentException: Empty entity|
| 400 |IllegalArgumentException: Negative timestamp|
| 400 | IllegalArgumentException: No data |
| 500 | JsonParseException: Unexpected character "}" | 
| 500 | JsonMappingException: No enum constant in field type|

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/series/insert
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

* `--data` Payload

```elm
curl https://atsd_host:8443/api/v1/series/insert \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'
  ```
  
* file

```elm
curl https://atsd_host:8443/api/v1/series/insert \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
  ```

## Additional Examples
* [ISO Time Format](/api/data/series/examples/series-insert-iso-time-format.md)
* [Scientific Notation](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-scietific-notation.md)
* [Not A Number](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-nan.md)
* [Multiple Samples](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-multiple-samples.md)
* [Series with Tags](/api/data/series/examples/series-insert-with-tags.md)
* [Multiple Series](/api/data/series/examples/insert-multiple-series.md)
* [Forecast](/api/data/series/examples/insert-forecast.md)
* [Named Forecast](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/insert-named-forecast.md)
* [Forecast Deviation](https://github.com/axibase/atsd-docs/blob/master/api/data/series/examples/series-insert-forecast-deviation.md)
* [Versioned Metric](/api/data/series/examples/versioned-metric.md)



