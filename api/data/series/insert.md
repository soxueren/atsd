# Series: Insert

## Description

Insert a timestamped array of numbers for a given series identified by metric, entity, and series tags. 

* Entity name, metric name, and tag names can contain only printable characters. Names are case-insensitive and are converted to lower case when stored.
* Tag values are case-sensitive and are stored as submitted.
* New entities, metrics, and tag names are created automatically.
* New metrics are initialized with `float` data type by default. To store number in another datatype, create or update the metric using the web interface or Meta API [metric update method](/api/meta/metric/update.md).

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

The request contains an array of series objects each containing an array of timestamped samples. 

|**Field**|**Type**|**Description**|
|:---|:---|:---|
| entity | string | [**Required**] Entity name |
| metric | string | [**Required**] Metric name |
| tags | object | Object containing series tags, where field name represents tag name and field value is tag value.<br>`{"tag-1":string,"tag-2":string}` |
| type | string | Type of inserted data: `HISTORY`, `FORECAST`. Default value: `HISTORY` |
| version | object | Object containing version source and status fields for versioned metrics.<br>`{"source":string, "status":string}` |
|forecastName| string | Forecast name. <br>Applicable when `type` is set to `FORECAST`. <br>`forecastName` can be used to store a custom forecast identified by name. <br>If `forecastName` is omitted, the values overwrite the default forecast.  |
| data | array | [**Required**] Array of `{"t":number,"v":number}` objects, <br>where `t` is time in UNIX milliseconds and `v` is the metric's numeric value at time `t`. <br>Time can be also specified in ISO format using `d` field, for example:<br>`{"d":"2016-06-01T12:08:42.518Z", "v":50.8}`<br>To insert `NaN` (not a number), set `v` to `null`, for example: `{"t":1462427358127, "v":null}`<br>If `type` is set to `FORECAST`, the object `{t,v}` can include an additional `s` field containing standard deviation of the forecast value `v`, for example  `{"t":1462427358127, "v":80.4, "s":12.3409}` |

### Number Representation

* The string representation of the inserted number consists of an optional sign, '+' ('\u002B') or '-' ('\u002D'), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by a fraction, optionally followed by an exponent.
* The exponent consists of the character 'e' ('\u0065') or 'E' ('\u0045') followed by an optional sign, '+' ('\u002B') or '-' ('\u002D'), followed by one or more decimal digits.
* The fraction consists of a decimal point followed by zero or more decimal digits. The string must contain at least one digit in either the integer or the fraction. 
* The number formed by the sign, the integer and the fraction is referred to as the [**significand**](https://en.wikipedia.org/wiki/Significand).
* The **significand** value stripped from trailing zeros should be within Long.MAX_VALUE `9223372036854775807` and Long.MIN_VALUE  `-9223372036854775808` (19 digits). Otherwise the database will throw an **llegalArgumentException: BigDecimal significand overflows the long type** for decimal metrics or round the value for non-decimal metrics. For example, significand for `1.1212121212121212121212121212121212121212121` contains 44 digits and will be rounded to `1.121212121212121212` if inserted for non-decimal metric.

## Response

### Fields

None.

### Errors

|  Status Code  |  Description  |
|---------------|:---------------|
| 400 | IllegalArgumentException: Empty entity.|
| 400 | IllegalArgumentException: Negative timestamp.|
| 400 | IllegalArgumentException: No data. |
| 400 | IllegalArgumentException: BigDecimal significand overflows the long type. |
| 500 | JsonParseException: Unexpected character "}" | 
| 500 | JsonMappingException: No enum constant in field type.|

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



