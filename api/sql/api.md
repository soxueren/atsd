# SQL Query API Endpoint

## Description 

Execute an SQL query and retrieve results in CSV or JSON format.

## Authorization

The rows returned for the SQL query are filtered by the server according to the user's **entity:read** permissions.

This means that the same query executed by users with different entity permissions may produce different results.

Scheduled queries are executed under full permissions.

## GET Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| GET | `/api/sql` | `text/plain` |

### Parameters

|**Parameter**|**Type**|**Description**|
|:---|:---|:---|
|outputFormat | query string|Output format: `csv` or `json`. Default: `json`|
|q | query string| Query text which must be URL-encoded.|

## POST Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| POST | `/api/sql` | `text/plain` |

### Parameters

|**Parameter**|**Type**|**Description**|
|:---|:---|:---|
|outputFormat | query string|Output format: `csv` or `json`. Default: `json`|

### Fields

Payload is an SQL query text, for example:

```sql
SELECT entity, datetime, value FROM mpstat.cpu_busy WHERE datetime > now - 1*MINUTE
```

## Response 

### CSV

* [Example](sql.csv)

### JSON

* [Example](sql.json)

## Metadata

Resultset in JSON format incorporates metadata including table and column schema.

The schema can be used by API clients to cast primitive JSON types (number, string, boolean) into language-specific data types.

Since structured metadata cannot be easily embedded into the CSV format, the server includes metadata as Base64-encoded `Link` header according to [W3C Model for Tabular Data](http://w3c.github.io/csvw/syntax/#link-header).

```
<data:application/csvm+json;base64,eyJAY29...ifX0=>; rel="describedBy"; type="application/csvm+json"
```

Sample metadata:

```json
{
	"metadata": {
		"@context": ["http://www.w3.org/ns/csvw", {
			"atsd": "http://www.axibase.com/schemas/2015/11/atsd.jsonld#"
		}],
		"dc:created": {
			"@value": "2016-06-28T14:28:05.424Z",
			"@type": "xsd:date"
		},
		"dc:publisher": {
			"schema:name": "Axibase Time-Series Database",
			"schema:url": {
				"@id": "https://nur.axibase.com"
			}
		},
		"dc:title": "SQL Query",
		"rdfs:comment": "SELECT tbl.value*100 AS \"cpu_percent\", tbl.datetime 'sample-date'\r\n  
		                 FROM \"cpu_busy\" tbl \r\nWHERE datetime > now - 1*MINUTE",
		"@type": "Table",
		"url": "sql.csv",
		"tableSchema": {
			"columns": [{
				"columnIndex": 1,
				"name": "cpu_percent",
				"titles": "tbl.value*100",
				"datatype": "float",
				"table": "tbl"
			}, {
				"columnIndex": 2,
				"name": "sample-date",
				"titles": "datetime",
				"datatype": "xsd:dateTimeStamp",
				"table": "tbl",
				"propertyUrl": "atsd:datetime",
				"dc:description": "Sample time in ISO8601 format"
			}]
		}
	},
	"data": [{
		"tbl.value*100": "303.0",
		"datetime": "2016-06-28T14:27:16.000Z"
	}]
}
```

## Example

### URL Query Example

```elm
GET https://atsd_server:8443/api/sql?q=SELECT%20*%20FROM%20cpu_busy%20WHERE%20datetime%20%3E%20now%20-%201*HOUR
```

### `curl` Query Example

```sh
curl https://atsd_server:8443/api/sql  \
  --insecure  --verbose --user {username}:{password} \
  --request POST \
  --data 'outputFormat=csv&q=SELECT entity, value FROM mpstat.cpu_busy WHERE datetime > now - 1*MINUTE'
```

### Bash Client Example

Execute query specified in `query.sql` file and write CSV results to `/tmp/report-1.csv`.

```ls
./sql.sh -o /tmp/report-1.csv -i query.sql -f csv
```

Execute query specified inline and store results in `/tmp/report-2.csv`.

```ls
./sql.sh --output /tmp/report-2.csv --query "SELECT entity, value FROM mpstat.cpu_busy WHERE datetime > now - 1*minute LIMIT 3"
```

Bash client [parameters](client/README.md).

### Java Client Example
 
[SQL to CSV example in Java](client/SqlCsvExample.java).