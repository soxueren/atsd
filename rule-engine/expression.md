# Expression

Expression is a condition which is evaluated each time a data sample is
received by the window. For example, the expression **`value > 50`** checks if a
received value is greater than 50.

If the expression evaluates to `TRUE`, it raises an alert, followed by
execution of triggers such as system command and email notification. Once
the expression returns `FALSE`, the alert is closed and another set of
triggers is invoked.

The expression consists of one or multiple checks combined with `OR` and
`AND` operators. Exceptions specified in the Thresholds table take
precedence over expression.

## Fields

**Field** | **Description**
:--- | :---
`value` | Last data sample.
`tags.'tag-name'` | Value of series tag 'tag-name', for example, tags.file _system.
`entity` | Entity name.
`metric` | Metric name.

## Functions

### Time Functions

**Function** | **Description**
:--- | :---
`window_length_time()` | Length of the time-based window in seconds, as configured.
`window_length_count()` | Length of the count-based window, as configured.
`windowStartTime()` | Time when the first command was received by the window, in UNIX milliseconds.
`milliseconds(string isodate)` | Converts ISO8601 date string into epoch time in milliseconds.
`milliseconds(string datetime, string format)` | Converts provided datetime string  into epoch time in milliseconds according to the specified format string. If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion. The format string syntax is described in the document [datetime format](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html).
`milliseconds(string datetime, string format, string timezone)` | Converts provided datetime string into epoch time in milliseconds according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception.
`seconds(string isodate)` | Converts ISO8601 date string into epoch time in seconds.
`seconds(string datetime, string format)` | Converts provided datetime string into epoch time in seconds according to the specified format string. If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion.
`seconds(string datetime, string format, string timezone)` | Converts provided datetime string into epoch time in seconds according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception.
`date(string isodate)` | Converts ISO8601 date string into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object. The object can return [numeric codes](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTimeConstants.html) or string names for calendar constants.
`date(string datetime, string format)` | Converts provided datetime string  into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object according to the specified format string. If the timezone or offset from UTC are not specified in the format string, then the server timezone will be used for the conversion.
`date(string datetime, string format, string timezone)` | Converts provided datetime string into a [Joda-time](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) DateTime object according to the specified format string and timezone (or offset from UTC). Available timezones and their standard offsets are listed in [time zones](http://joda-time.sourceforge.net/timezones.html). If the timezone (or offset from UTC) is specified in the datetime string, and it differs from the timezone (offset) provided as the third argument, then the function will throw an exception.
`formatted_date(long timestamp, string pattern, string timezone)` | Converts timestamp to formatted time string according to the format pattern and the timezone. Timestamp is an epoch timestamp in milliseconds. The format string syntax is described in the [datetime format](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html). List of available timezones: [time zones](http://joda-time.sourceforge.net/timezones.html).

```java
/* Returns true if the difference between event time and start 
time (ISO) retrieved from the property record is greater than 5 minutes. */
timestamp - milliseconds(property('docker.container::startedAt')) >  5*60000

/* Returns true if the specified date is a working day. */
date(property('config::deleted')).dayOfWeek().get() < 6
  
/* Uses the server time zone to construct a DateTime object. */
date("31.01.2017 12:36:03:283", "dd.MM.yyyy HH:mm:ss:SSS");

/* Uses the offset specified in the datetime string to construct a DateTime object. */
date("31.01.2017 12:36:03:283 -08:00", "dd.MM.yyyy HH:mm:ss:SSS ZZ");

/* Uses the time zone specified in the datetime string to construct a DateTime object. */
date("31.01.2017 12:36:03:283 Europe/Berlin", "dd.MM.yyyy HH:mm:ss:SSS ZZZ");

/* Constructs a DateTime object from the time zone provided as the third argument. */
date("31.01.2017 12:36:03:283", "dd.MM.yyyy HH:mm:ss:SSS", "Europe/Berlin");

/* Constructs a DateTime object from the UTC offset provided as the third argument. */
date("31.01.2017 12:36:03:283", "dd.MM.yyyy HH:mm:ss:SSS", "+01:00");

/* If the time zone (offset) is specified in the datetime string, 
it should be exactly the same as provided by the third argument. */
date("31.01.2017 12:36:03:283 Europe/Berlin", "dd.MM.yyyy HH:mm:ss:SSS ZZZ", "Europe/Berlin");

/* These expressions lead to exceptions. */
date("31.01.2017 12:36:03:283 +01:00", "dd.MM.yyyy HH:mm:ss:SSS ZZ", "Europe/Berlin");
date("31.01.2017 12:36:03:283 Europe/Brussels", "dd.MM.yyyy HH:mm:ss:SSS ZZZ", "Europe/Berlin");

/* Return formatted time sring  "2017-02-22 11:18:00:000 Europe/Berlin"*/
date(1487758680000L, "yyyy-MM-dd HH:mm:ss:SSS ZZZ", "Europe/Berlin")

```


### Aggregation Functions

**Function** | **Description**
:--- | :---
`avg()` | Average value.
`mean()` | Average value. Same as `avg()`.
`sum()` | Sum of values.
`min()` | Minimum value.
`max()` | Maximum value.
`wavg()` | Weighted average. Weight = sample index which starts from 0 for first sample.
`wtavg()` | Weighted time average.<br>`Weight = (sample.time - first.time)/(last.time - first.time + 1)`. <br>Time measured in epoch seconds.
`count()` | Count of values.
`percentile(D)` | Dth percentile. D can be a fractional number.
`median()` | 50% percentile. Same as `percentile(50)`.
`variance()` | Standard deviation.
`stdev()` | Standard deviation. Aliases: `variance`, `stdev`, `std_dev`.
`slope()` | Linear regression slope.
`intercept()` | Linear regression intercept.
`first()` | First value. Same as `first(0)`.
`first(integer N)` | Nth value from start. First value has index of 0.
`last()` | Last value. Same as `last(0)`.
`last(integer N)` | Nth value from end. Last value has index of 0.
`diff()` | Difference between `last` and `first` values. Same as `last() - first()`.
`diff(N)` | Difference between `last(N)` and `first(N)` values. Same as` last(N)-first(N)`.
`diff(string interval)` | Difference between `last value` and `value` at `currentTime - interval`. <br>Interval specified as '`count unit`', i.e. '`5 minute`'.
`new_maximum()` | Returns true if last value is greater than any previous value.
`new_minimum()` | Returns true if last value is smaller than any previous value.
`threshold_time(double D)` | Number of minutes until the sample value reaches specified threshold D<br> based on extrapolation of difference between last and first value.
`threshold_linear_time(double D)` | Number of minutes until the sample value reaches specified threshold D<br> based on linear extrapolation.
`rate_per_second()` | Difference between last and first value per second. <br>Same as `diff()/(last.time-first.time)`. Time measured in epoch seconds.
`rate_per_minute()` | Difference between last and first value per minute. Same as `rate_per_second()/60`.
`rate_per_hour()` | Difference between last and first value per hour. Same as `rate_per_second()/3600`.
`slope_per_second()` | Same as` slope()`.
`slope_per_minute()` | `slope_per_second()/60`.
`slope_per_hour()` | `slope_per_second()/3600`.

### Forecast functions

**Function** | **Description**
:--- | :---
`forecast()` | Forecast value for the entity, metric, and tags in the current window.
`forecast_stdev()` | Forecast standard deviation.
`forecast(string name)` | Named forecast value for the entity, metric, and tags in the current window.
`forecast_deviation()` | `(D-forecast())/forecast_stdev()`.

### Math functions

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

**Function** | **Description**
:--- | :---
`upper(string t)` | Convert string 't' to upper case.
`lower(string t)` | Convert string 't' to lower case.

## Numeric Operators

**Operator** | **Description**
:--- | :---
`=` | Equal.
`!=` | Not equal.
`>` | Greater than.
`>=` | Greater than or equal.
`<` | Less than.
`<=` | Less than or equal.

## Text Operators

**Operator** | **Description**
:--- | :---
`=` | Equal.
`!=` | Not equal.
`t.contains(string t)` | Check if text 't' contains text 'str'.
`t.startsWidth(string t)` | Check if text 't' starts with text 'str'.
`t.endsWidth(string t)` | Check if text 't' ends with text 'str'.

> Note: `=` and `!=` operators are case-insensitive.
