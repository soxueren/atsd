# Overview

SQL client provides a convenient way to query Axibase Time Series Database using SQL and export results to a file or standard output in a variety of formats.

The client is a bash script that parses and validates input parameters and executes an http/s request to `/api/sql` API endpoint in the database using credentials stored in `atsd.config` file. 

## Security

Username and password of the user executing the query must be specified in `atsd.config` file located in the directory as `sql.sh` file.

The client is stateless with each query triggering a separate http request with `Basic` authentication.

The user should have `API_DATA_READ` role and relevant entity read permissions.

If the database url specified in `atsd.config` file is secure, both the query as well as results are encrypted with SSL.

## `atsd.config` file

```ls
url=https://10.102.0.6:8443/api/sql
insecure=true
user=axibase
password=********
```

## Parameters


| **Name** | **Type** | **Description** |
|:---|:---|:---|
| -o, --output | string | Output file to store the result set. If not specified, results are printed to stdout. |
| -i, --input | string | Input file containing SQL query to execute. |
| -q, --query | string | SQL query text enclosed with double quotes. Ignored if query is read from input file. <br>Double quotes contained in query text can be escaped with backslash.|
| -f, --format | string | Format. Default: `csv`. Supported options: `csv`, `json`. |
| -m, --metadata | boolean | Include metadata. Default: `false` for csv, `true` for json. |

## Examples

Execute query specified in `/home/axibase/query.sql` file and write CSV results to `/tmp/report-1.csv`.

```ls
sql.sh -o /tmp/report-1.csv -i /home/axibase/query.sql -f csv
```

Execute query specified inline and store results in `/tmp/report-2.csv`.

```ls
sql.sh --output /tmp/report-2.csv --query "SELECT entity, value FROM cpu_busy WHERE datetime > now - 1*minute"
```

