# Expression


Expression is a condition which is evaluated each time a data sample is
received by the window For example, expression ‘`value > 50`‘ checks if
received value is greater than 50.

If the expression evaluates to ‘true’, it raises an alert, followed by
execution of triggers such as system command or email notification. Once
the expression returns ‘false’, the alert is closed and another set of
triggers is invoked.

The expression consists of one or multiple checks combined with OR and
AND operators. Exceptions specified in the Thresholds table take
precedence over expression.

## Supported Fields

`value` – last data sample

`tags.'tag-name'` – value of tag ‘tag-name’, for example,
tags.file _system

`entity` – entity name

`metric` – metric name

## Supported Aggregation Functions

`avg()` – average value.

`mean()` – average value. Same as `avg()`.

`sum() `– sum of values.

`min()` – minimum value.

`max()` – maximum value.

`wavg()` – weighted average. Weight = sample index which starts from 0
for first sample.

`wtavg()` – weighted time average.

`Weight = (sample.time - first.time)/(last.time - first.time + 1)`. Time
measured in epoch seconds.

`count()` – count of values.

`percentile(D)` – Dth percentile. D can be a fractional number.

`median()` – 50% percentile. Same as `percentile(50)`.

`variance()` – standard deviation.

`stdev()` – standard deviation. Aliases: `variance`, `stdev`,
`std_dev`.

`slope()` – linear regression slope.

`intercept()` – linear regression intercept.

`first()` – first value. Same as `first(0)`.

`first(N)` – Nth value from start. First value has index of 0.

`last()` – last value. Same as `last(0)`.

`last(N)` – Nth value from end. Last value has index of 0.

`diff()` – difference between `last` and `first` values. Same as

`last() - first()`.

`diff(N)` – difference between `last(N)` and `first(N)` values. Same
as` last(N) - first(N)`.

`diff(interval)` – difference between `last value` and `value` at

`currentTime - interval`. Interval specified as ‘`count unit`‘, e.g.
‘`5 minute`‘.

`new_maximum()` – returns true if last value is greater than any
previous value.

`new_minimum()` – returns true if last value is smaller than any
previous value.

`threshold_time(D)` – number of minutes until the sample value reaches
specified threshold D based on extrapolation of difference between last
and first value.

`threshold_linear_time(D)` – number of minutes until the sample value
reaches specified threshold D based on linear extrapolation.

`rate_per_second()` – difference between last and first value per
second. Same as `diff()/(last.time-first.time)`. Time measured in epoch
seconds.

`rate_per_minute()` – difference between last and first value per
minute. Same as `rate_per_second()/60`.

`rate_per_hour()` – difference between last and first value per hour.
Same as `rate_per_second()/3600`.

`slope_per_second()` – Same as` slope()`.

`slope_per_minute()` – `slope_per_second()/60`.

`slope_per_hour()` – `slope_per_second()/3600`.

## Forecast functions

`forecast()` – forecast value for the entity, metric, tags in the
current window

`forecast_stdev()` – forecast standard deviation

`forecast(name)` – named forecast value for the entity, metric, tags in
the current window

`forecast_deviation()` – `(D-forecast())/forecast_stdev()`

## Math functions

`abs(D)`, `ceil(D)`, `floor(В)`, `pow(D, D)`, `round(D)`, `round(D, N)`,
`random() max(double a, double b)`, `min(D, D)`, `sqrt(D)`, `exp(D)`,
`log(D)`

## Supported Numeric Operators

`=` – equal

`!=` – not equal

`>` – greater than

`>=` – greater than or equal

`<` - less than

`<=` - less than or equal

## Supported Text Operators

`=` - equal

`!=` - not equal

`t.contains(str)` - check if text 't' contains text 'str'

`t.startsWidth(str)` - check if text 't' starts with text 'str'

`t.endsWidth(str)` - check if text 't' ends with text 'str'

> Note: `=` and `!=` operators are case-insensitive.

## Supported Text Functions

`upper(t)` - convert text 't' to upper case

`lower(t)` - convert text 't' to lower case