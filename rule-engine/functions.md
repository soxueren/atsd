# Functions


### Statistical Functions

| **Name** | **Description** |
| :--- | :--- |
| `avg()` | Average value. |
| `mean()` | Average value. Same as `avg()`. |
| `sum()` | Sum of values. |
| `min()` | Minimum value. |
| `max()` | Maximum value. |
| `wavg()` | Weighted average. Weight = sample index which starts from 0 for first sample. |
| `wtavg()` | Weighted time average.<br>`Weight = (sample.time - first.time)/(last.time - first.time + 1)`. <br>Time measured in epoch seconds. |
| `count()` | Count of values. |
| `percentile(D)` | Dth percentile. D can be a fractional number. |
| `median()` | 50% percentile. Same as `percentile(50)`. |
| `variance()` | Standard deviation. |
| `stdev()` | Standard deviation. Aliases: `variance`, `stdev`, `std_dev`. |
| `slope()` | Linear regression slope. |
| `intercept()` | Linear regression intercept. |
| `first()` | First value. Same as `first(0)`. |
| `first(integer N)` | Nth value from start. First value has index of 0. |
| `last()` | Last value. Same as `last(0)`. |
| `last(integer N)` | Nth value from end. Last value has index of 0. |
| `diff()` | Difference between `last` and `first` values. Same as `last() - first()`. |
| `diff(N)` | Difference between `last(N)` and `first(N)` values. Same as` last(N)-first(N)`. |
| `diff(string interval)` | Difference between `last value` and `value` at `currentTime - interval`. <br>Interval specified as '`count unit`', i.e. '`5 minute`'. |
| `new_maximum()` | Returns true if last value is greater than any previous value. |
| `new_minimum()` | Returns true if last value is smaller than any previous value. |
| `threshold_time(double D)` | Number of minutes until the sample value reaches specified threshold D<br> based on extrapolation of difference between last and first value. |
| `threshold_linear_time(double D)` | Number of minutes until the sample value reaches specified threshold D<br> based on linear extrapolation. |
| `rate_per_second()` | Difference between last and first value per second. <br>Same as `diff()/(last.time-first.time)`. Time measured in epoch seconds. |
| `rate_per_minute()` | Difference between last and first value per minute. Same as `rate_per_second()/60`. |
| `rate_per_hour()` | Difference between last and first value per hour. Same as `rate_per_second()/3600`. |
| `slope_per_second()` | Same as` slope()`. |
| `slope_per_minute()` | `slope_per_second()/60`. |
| `slope_per_hour()` | `slope_per_second()/3600`. |

### Statistical Forecast Functions

| **Name** | **Description** |
| :--- | :--- |
| `forecast()` | Forecast value for the entity, metric, and tags in the current window. |
| `forecast_stdev()` | Forecast standard deviation. |
| `forecast(name)` | Named forecast value for the entity, metric, and tags in the current window. |
| `forecast_deviation(number)` | Difference between a number (such as last value) and forecast, divided by forecast standard deviation.<br>`(number - forecast())/forecast_stdev()`. |

## Data Query Functions

| **Type** | **Example** | **Description** |
| --- | --- | --- |
| atsd_last | `atsd_last(metric: 'transq')` | Query historical database for last value. |
| atsd_values | `avg(atsd_values(entity: 'e1', metric: 'm1', type: 'avg', interval: '5-minute', shift: '1-day', duration: '3-hour'))` | Query historical database for a range of values. Apply analytical functions to the result set. |

### Math Functions

* `abs(D)`
* `ceil(D)`
* `floor(В)`
* `pow(D, D)`
* `round(D)`
* `round(D, N)`
* `random()`
* `max(double a, double b)`
* `min(D, D)`
* `sqrt(D)`
* `exp(D)`
* `log(D)`

### String Functions

| **Name** | **Description** |
| :--- | :--- |
| `upper(string s)` | Convert string to upper case. |
| `lower(string s)` | Convert string to lower case. |
| `t.contains(string s)` | Check if field 't' contains the specified string. |
| `t.startsWidth(string s)` | Check if field 't' starts with the specified string. |
| `t.endsWidth(string s)` | Check if field 't' ends with the specified string. |
| `coalesce([string s])` | Return first non-empty string from the array of strings. See [examples](functions-coalesce.md).|

### Collection Functions

| **Name** | **Description** |
| :--- | :--- |
| `contains(string s)` | Returns true if collection contains the specified string. <br>`properties['command'].toString().contains('java')`|
| `isEmpty()` | Returns true if collection has no elements. <br>`entity.tags.isEmpty()`|
| `size()` | Returns number of elements in the collection. <br>`entity.tags.size() > 1`|
| `matches(string pattern, [string s])` | Returns true if one of the collection elements matches the specified pattern. <br>`matches('*atsd*', property_values('docker.container::image'))`|


### Time Functions

| **Name** | **Description** |
| :--- | :--- |
| `window_length_time()` | Length of the time-based window in seconds, as configured. |
| `window_length_count()` | Length of the count-based window, as configured. |
| `windowStartTime()` | Time when the first command was received by the window, in UNIX milliseconds. |
| `milliseconds(string isodate)` | Converts ISO8601 date string into epoch time in milliseconds. |
| `milliseconds(string datetime, string format)` | Converts provided datetime string  into epoch time in milliseconds according to the specified format string.  If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion. The format string syntax is described in the document [datetime format](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html). |
| `milliseconds(string datetime, string format, string timezone)` | Converts provided datetime string into epoch time in milliseconds according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception. |
| `seconds(string isodate)` | Converts ISO8601 date string into epoch time in seconds. |
| `seconds(string datetime, string format)` | Converts provided datetime string into epoch time in seconds according to the specified format string. If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion. |
| `seconds(string datetime, string format, string timezone)` | Converts provided datetime string into epoch time in seconds according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception. |
| `date(string isodate)` | Converts ISO8601 date string into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object. The object can return [numeric codes](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTimeConstants.html) or string names for calendar constants. |
| `date(string datetime, string format)` | Converts provided datetime string  into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object according to the specified format string. If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion. |
| `date(string datetime, string format, string timezone)` | Converts provided datetime string into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception. |
| `formatted_date(long timestamp, string pattern, string timezone)` | Converts timestamp to formatted time string according to the format pattern and the timezone. Timestamp is an epoch timestamp in milliseconds. The format string syntax is described in the [datetime format](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html). List of available timezones: [time zones](http://joda-time.sourceforge.net/timezones.html). |

## Property Functions

| **Name** | **Description** |
| :--- | :--- |
| `property_values(string search)` | Returns a list of property tag values for the current entity given the property [search string](../property-search-syntax.md). |
| `property_values(string entity, string search)` |  Same as `property_values`(string search) but for an explicitly specified entity.  |
| `property(string search)` |  Returns the first value in a collection of strings returned by the `property_values()` function. The function returns an empty string if no property records are found.  |
| `property_compare_except([string key])` | Compares previous and current property tags and returns a difference map containing the list of changed tag values.   |
| `property_compare_except([string key], [string prevValue])` |   Same as `property_compare_except([string key])` with the list of previous values that are excluded from the difference map. |
