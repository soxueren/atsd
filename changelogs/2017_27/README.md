Weekly Change Log: July 03, 2017 - July 09, 2017
==================================================

### ATSD
 
| Issue| Category    | Type    | Subject              |
|------|-------------|---------|----------------------|
| 4352 | SQL| Feature | Standardized the list of columns for wildcard [`SELECT *`](https://github.com/axibase/atsd/blob/master/api/data/series/examples/query-tags-wildcard-expression.md) expressions. |
| 4350 | SQL| Bug | Metadata `titles` field now contains column labels instead of column names. JDBC driver compatibility is v.1.3.+. |
| 4347 | Search| Feature | Full search re-indexing can be scheduled separately from incremental (delta) re-indexing. |
| 4346 | JDBC | Feature | Database and Resulset metadata standardized [JDBC driver](https://github.com/axibase/atsd-jdbc#jdbc-driver) for compatibility.  |
| 4344 | SQL | Bug | Remove unnecessary quotes from CSV files produced with the [SQL Console](https://github.com/axibase/atsd/tree/master/api/sql#overview) export option. |
| 4343 | JDBC | Feature | `assignColumnNames=true|false` setting added to control the behavior of `getColumnName()` and `getColumnLabel()` methods in the [JDBC](https://github.com/axibase/atsd-jdbc#jdbc-connection-properties-supported-by-driver) driver. |
| [4320](#Issue-4320) | Search | Feature | Live search added to ATSD to quickly find series with metadata queries. |
| [4226](#Issue-4226) | SQL | Feature | Support added for [`COALESCE`](https://github.com/axibase/atsd/blob/master/rule-engine/functions-coalesce.md#functions-coalesce) function.|
| 4117 | SQL | Bug | Fixed a defect with some [metric columns](https://github.com/axibase/atsd/blob/master/api/sql/README.md#metric-columns) not accessible in [`WHERE`](https://github.com/axibase/atsd/tree/master/api/sql#where-clause)/[`HAVING`](ttps://github.com/axibase/atsd/tree/master/api/sql#having-filter) filters, for example `WHERE metric.units = 'Celcius'`. |
| 3888 | SQL | Bug | Fixed a defect with some [entity columns](https://github.com/axibase/atsd/blob/master/api/sql/README.md#entity-columns) not accessible in [`WHERE`](https://github.com/axibase/atsd/tree/master/api/sql#where-clause)/[`HAVING`](ttps://github.com/axibase/atsd/tree/master/api/sql#having-filter) filters, for example `WHERE entity.label = 'SVL'`. |

### ATSD

##### Issue 4320

![](Images/4320.png)

> Live Search Search

##### Issue 4226

> The `COALESCE` function returns the first argument that is not `NULL` or `NaN`.

![](Images/4226.png)
