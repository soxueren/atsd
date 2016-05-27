### Date Filter Fields

* Date filter is **required**. 
* If `startDate` or `endDate` is not defined, the omitted field is calculated from `interval`/`endDate` and `startDate`/`interval` fields.

| **Name** | **Type** | **Description** |
|:---|:---|:---|
|startDate|	string | **[Required]** Start of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated at or after `startDate` are returned.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	string | **[Required]** End of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated before `endDate` are returned.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	string | Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|
