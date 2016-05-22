# End Time Syntax

## Description

Endtime syntax implements convenient calendar [keywords](#keywords) and [arithmetic](#expressions) used as an alternative to ISO format in API requests, widget configurations, export settings, etc. to specify interval start and end time.

Endtime [keyword](#keywords) calculates the specified time based on current server time.

For example, if current time is `May 15, 2016 15:20:00`, expression `current_hour - 1 * day` would return `May 14, 2016 15:00:00`.

Calendar arithmetic such as adding or subtracting a time interval is implemented with [expressions](#expressions).

## Expressions

### Syntax

```elm
{keyword} (+/-) {interval-count} * {interval-unit} 
```

### Examples

| **Name** | **Description** |
|:---|:---|
| `next_month + 3 * day` | 00:00:00 on the 3rd day of next month |
| `today – 1 * month` | 00:00:00 on the same day of previous month. |

## Keywords

| **Name** | **Alias** | **Description** |
|:---|:---|:---|
| time | now | current time | 
| current_minute | | current time rounded to the beginning of the current minute | 
| previous_minute | | 00 of the previous minute | 
| next_minute | | 00 of the next minute | 
| current_hour | | current time rounded to the beginning of the current hour | 
| previous_hour | | 00:00 of the previous hour | 
| next_hour | | 00:00 of the next hour | 
| current_day | today | 00:00:00 of the current day | 
| previous_day | yesterday | 00:00:00 of the previous day | 
| next_day | tomorrow | 00:00:00 of the next day | 
| previous_working_day | | 00:00:00 of the previous working day | 
| next_working_day | | 00:00:00 of the next working day | 
| previous_vacation_day | | 00:00:00 of the previous vacation day | 
| next_vacation_day | | 00:00:00 of the next vacation day | 
| next_week | | 00:00:00 on Monday of next week | 
| next_month | | 00:00:00 on the first day of next month | 
| next_quarter | | 00:00:00 on the first day of next quarter | 
| next_year | | 00:00:00 on the first day of next year | 
| previous_week | | 00:00:00 on Monday of the previous week | 
| previous_month | | 00:00:00 on the first day of previous month | 
| previous_quarter | | 00:00:00 on the first day of of previous quarter | 
| previous_year | | 00:00:00 on the first day of previous year | 
| current_week | | 00:00:00 on Monday of the current week | 
| current_month | | 00:00:00 on the first day of the current month | 
| current_quarter | | 00:00:00 on the first day of the current quarter | 
| current_year | | 00:00:00 on the first day of the current year | 
| last_working_day | | 00:00:00 of the last working day of the current month | 
| last_vacation_day | | 00:00:00 of the last vacation day of the current month | 
| first_day | | 00:00:00 of the first day of the current month | 
| first_working_day | | 00:00:00 of the first working day of the current month | 
| first_vacation_day | | 00:00:00 of the first vacation day of the current month | 
| monday | mon | 00:00:00 on the most recent Monday | 
| tuesday | tue | 00:00:00 on the most recent Tuesday | 
| wednesday | wed | 00:00:00 on the most recent Wednesday | 
| thursday | thu | 00:00:00 on the most recent Thursday | 
| friday | fri | 00:00:00 on the most recent Friday | 
| saturday | sat | 00:00:00 on the most recent Saturday | 
| sunday | sun | 00:00:00 on the most recent Sunday |
| 'date-format' | | Supported formats: <br>`YYYY-MM-DD HH:mm:ss` – Specific date and time <br>`YYYY-MM-DD` – 00:00:00 on specific day <br> `2015-04-22T00:00:00Z` – Date in ISO format <br>Example 1: `2015-04-22 00:00:00` <br>Example 2: `2015-04-22 00:00` <br>Example 3: `2015-04-22` | 


## Interval Units

- millisecond
- second
- minute
- hour
- day
- week
- month
- quarter
- year
