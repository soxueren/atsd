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
| POST | `/api/sql` | `text/plain` |

### Parameters

| **Name**| **Type** | **Description** |
|:---|:---|:---|
| q | string | [**Required**] Query text. |
| outputFormat | string | Output format: `csv` or `json`. Default: `csv`. |
| metadataFormat | string | Metadata format for CSV format. Default: `header`. <br>Supported values: `none`, `header`, `embed`, `embed-csv`. |
| limit | integer | Maximum number of rows to return. Default: 0 (not applied).<br>Applies if query text does not include `LIMIT` clause.  |

### Metadata

The response can include optional metadata to assist API clients in processing results, for example to convert text values in CSV or JSON field values into language-specific data types.

The metadata is specified as JSON-LD (JSON linked data) according to [W3C Model for Tabular Data](https://www.w3.org/TR/tabular-data-model/).

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

### Metadata in JSON output format

Results in JSON output format incorporates metadata by default, including table and column schema.

### Metadata in CSV output format

The `metadataFormat` parameter controls how to incorporate metadata into the CSV text.

| **Value**| **Description** |
|:---|:---|
| none | Do not include metadata in the response. |
| header | [**Default**] Add JSON-LD metadata into Base64-encoded `Link` header according to [W3C Model for Tabular Data](http://w3c.github.io/csvw/syntax/#link-header).<br>`<data:application/csvm+json;base64,eyJAY29...ifX0=>; rel="describedBy"; type="application/csvm+json"`|
| embed | Append JSON-LD metadata to CSV header as comments prefixed by hash symbol. |
| embed-csv | Append CSV metadata to CSV header as comments prefixed by hash symbol. |

## Example

### `curl` Query Example

```sh
curl https://atsd_server:8443/api/sql  \
  --insecure  --verbose --user {username}:{password} \
  --request POST \
  --data 'q=SELECT entity, value FROM mpstat.cpu_busy WHERE datetime > now - 1*MINUTE'
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

## Response

* [Sample CSV response](sql.csv)
* [Sample JSON response](sql.json)
