Weekly Change Log: June 05, 2017 - June 11, 2017
==================================================
### ATSD

| Issue| Category    | Type    | Subject              |
|------|-------------|---------|----------------------|
| 4264 |rule editor | Bug | Unable to use set schedule feature in Rule Editor|
| 4256 | statistics | Bug | invalid interval gap in series statistics |
| 4249 | core | Bug | Upon start-up, ATSD can delete unneeded column families not described in schema. |
| [4243](#Issue-4243) | admin | Feature | Fields added to daily .xml backup |
| 4237 | UI | Bug | Change standard tooltips to bootstrap tooltips |
| 4234 | sql | Bug | OK response on incorrect query with JSON response format corrected |
| 4233 | sql | Bug | Date error when using [`CONCAT`](https://github.com/axibase/atsd/tree/master/api/sql#string-functions) function |
| 4225 | sql | Feature | [`DATETIME`](https://github.com/axibase/atsd/tree/master/api/sql#predefined-columns) added as a defined column |
| 4221 | sql | Bug | SQL: isnull return long for string data types |
| 4217 | sql | Bug | Incorrect inner interpolation with two time filters |
| 4203 | rule engine | Feature | Move default alert logs from /tmp/atsd/alert.log to atsd/logs with logback |
| [4194](#Issue-4194) | core | Feature | Compression version items <---not really sure what to make of this, I read the log, but could not really find any features to catalog below |
| 4157 | sql | Bug | Incorrect result from [`OUTER JOIN USING ENTITY`](https://github.com/axibase/atsd/blob/master/api/sql/examples/outer-join-with-aggregation.md) clause. |
| 4108 | UI | Bug | Clean-up of client dependency instances |
| 4089 | sql | Feature | [`JOIN`](https://github.com/axibase/atsd/tree/master/api/sql#joins) clause is now supported in [`ATSD_SERIES`](https://github.com/axibase/atsd/tree/master/api/sql#atsd_series-table) table. |
| 4084 | api-network | Bug | OoM Error on a large number of commands sent to /api/v1/command |
| 3556 | UI | Feature | Text field in Data Entry form enabled. |

### ATSD

#### Issue 4243

Admin > Diagnostics > Backup, daily .xml backups made for the following metrics:
* Replacement Tables
* Named Collections
* Users
* User Groups (with membership)
* Entities (with entity tags, and non-default settings)

![4243](Images/4243.png)

#### Issue 3556

Non-required text field added to Metrics > Data Entry.

![3556](Images/3556.png)