# SQL Query API Endpoint

## Description

Execute an SQL query and retrieve results in CSV or JSON format.

## Authorization

The rows returned for the SQL query are filtered by the server according to the user's **entity:read** permissions.

This means that the same query executed by users with different entity permissions may produce different results.

Scheduled queries are executed under full permissions.

## Request

| **Method** | **Path** | **Content-Type Header**|
|:---|:---|---:|
| POST | `/api/sql` | `application/x-www-form-urlencoded` |

### Parameters

| **Name**| **Type** | **Description** |
|:---|:---|:---|
| q | string | [**Required**] Query text. |
| outputFormat | string | Output format: `csv` or `json`. Default: `csv`. |
| metadataFormat | string | Metadata format for CSV format. Default: `HEADER`. <br>Supported values: `NONE`, `HEADER`, `EMBED`, `COMMENTS`. |
| queryId | string | User-defined handle submitted at the request time in order to identify the query, if it needs to be cancelled. |
| limit | integer | Maximum number of rows to return. Default: 0 (not applied).<br>The number of returned rows is equal to the `limit` parameter or the `LIMIT` clause, whichever is lower.  |

As an alternative, the query can be submitted with Content-Type `text/plain` as text payload with the other parameters included in the query string.

#### `limit` parameter vs `LIMIT` clause

| `limit` | `LIMIT` | **Result** |
|:---|:---|:---|
| 5 | 3 | 3 |
| 5 | 10 | 5 |
| 5 | - | 5 |
| 0 | 3 | 3 |
| - | 3 | 3 |
| - | - | - |

```java
  statement.setMaxRows(5);
	statement.executeQuery("SELECT datetime, value FROM mpstat.cpu_busy LIMIT 3");
	//results will be limited to 3 records
```

### Cancelling the Query

The client may cancel an active query by submitting a request to `/api/sql/cancel?queryId=myid` endpoint.

The `queryId` identifies the query to be cancelled.

## Response

The response in CSV format is subject to the following formatting rules:

* String values are enclosed in double-quotes, even if special characters are not present.
* `NULL` is printed as an empty string.
* Numeric values, including `NaN` (Not a Number), are not enclosed in quotes.

```ls
string,empty_string,null,number,number(NaN)
"hello","",,10.3,NaN
```

### Metadata

The response can include optional metadata to assist API clients in processing results, for example to convert text values in CSV or JSON field values into language-specific data types.

The metadata is specified as JSON-LD (JSON linked data) according to the [W3C Model for Tabular Data](https://www.w3.org/TR/tabular-data-model/).

ATSD JSON-LD schema is published [here](http://www.axibase.com/schemas/2015/11/atsd.jsonld).

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
				"@id": "https://10.102.0.6:8443"
			}
		},
		"dc:title": "SQL Query",
		"rdfs:comment": "SELECT tbl.value*100 AS \"cpu_percent\", tbl.datetime 'sample-date'\r\n
		                 FROM \"mpstat.cpu_busy\" tbl \r\nWHERE datetime > now - 1*MINUTE",
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

### Metadata in JSON Output Format

Results in the JSON output format incorporates metadata by default, including table and column schema.

### Metadata in CSV Output Format

The `metadataFormat` parameter controls how to incorporate metadata into the CSV text.

| **Value**| **Description** |
|:---|:---|
| NONE | Do not include metadata in the response. |
| HEADER | [**Default**] Add JSON-LD metadata into Base64-encoded `Link` header according to [W3C Model for Tabular Data](http://w3c.github.io/csvw/syntax/#link-header).<br>`<data:application/csvm+json;base64,eyJAY29...ifX0=>; rel="describedBy"; type="application/csvm+json"`<br>Be aware that maximum response header size is 12 Kb and avoid Link header option if the response contains many columns or columns with long names.|
| EMBED | Append JSON-LD metadata to CSV header as comments prefixed by hash symbol. |
| COMMENTS | Append CSV metadata to CSV header as comments prefixed by hash symbol. |

## Example

### `curl` Query Example

```sh
curl https://atsd_server:8443/api/sql  \
  --insecure  --verbose --user {username}:{password} \
  --request POST \
  --data 'q=SELECT entity, value FROM mpstat.cpu_busy WHERE datetime > now - 1*MINUTE'
```

### Bash Client Example

Execute query specified in a `query.sql` file and write CSV results to `/tmp/report-1.csv`.

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

## Response

* [Sample CSV response](sql.csv)
* [Sample JSON response](sql.json)
