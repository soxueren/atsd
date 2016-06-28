# Date Filter Fields

Date fields define the time range for selecting the data.

* Date filter is **required**. 
* `startDate` is inclusive and `endDate` is exclusive.

| **Name** | **Type** | **Description** |
|:---|:---|:---|
|startDate|	string | Start of the selection interval. <br>ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Matches records timestamped at or after `startDate`.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	string | End of the selection interval. <br>ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Matches records timestamped before `endDate`.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	object | Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|

* One of the following field combinations is required:

| **Fields**  | **Notes** |
|:---|:---|
|`startDate` and `endDate`||
|`startDate` and `interval`|`endDate` will be set to `startDate` + `interval`|
|`endDate` and `interval`|`startDate` will be set to `endDate` - `interval`|
|`interval`|`endDate` will be set to current server time, `startDate` will be set to `endDate` - `interval`|



## Supported ISO Date Formats

See [date format](date-format.md) for supported ISO formats.

## Examples

At or after `2016-05-30T07:00:00Z` and before `2016-05-30T08:00:00Z`

- `"startDate":"2016-05-30T07:00:00Z", "endDate":"2016-05-30T08:00:00Z"`

At or after `2016-05-30T10:00:00Z` and before current server time.

- `"startDate":"2016-05-30T10:00:00Z", "endDate":"now"`

Last 2 hours, ending with current server time.

- `"interval":{"count":2, "unit":"HOUR"}`

Last 2 hours.

- `"interval":{"count":2, "unit":"HOUR"}, "endDate":"now"`

Last 2 hours of the previous day.

- `"interval":{"count":2, "unit":"HOUR"}, "endDate":"current_day"`

Last hour, rounded.

- `"startDate":"previous_hour", "interval":{"count":1, "unit":"HOUR"}`

All the data until now.

- `"startDate":"1970-01-01T00:00:00Z", "endDate":"now"`



