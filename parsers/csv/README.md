# CSV

CSV files can be [uploaded](https://axibase.com/products/axibase-time-series-database/writing-data/csv/uploading-csv-files/) into Axibase Time-Series Database via HTTP API or manually through the user interface.

![](resources/csv.gif)

Uploaded CSV files are processed by a user-defined CSV parser which converts the text into a tabular model and creates series, properties and message commands from cell values depending on column name.

In addition to column-based parsing, ATSD supports [schema-based](/products/axibase-time-series-database/writing-data/csv/csv-schema/) parsing using [RFC 7111](https://tools.ietf.org/html/rfc7111#section-2) selections:

```ls
select("#row=2-*").select("#col=3-*").
addSeries().
metric(cell(1, col)).
entity(cell(row, 2)).
tag(cell(1, 3),cell(row, 3)).
timestamp(cell(row, 1));
```

Produced series commands:

```ls
series e:nurswgvml007 d:2015-11-15T00:00:00Z m:space_used_%=72.2 t:disk=/dev/sda
series e:nurswgvml007 d:2015-11-15T00:00:00Z m:space_used_%=14.5 t:disk=/dev/sdb
series e:nurswgvml001 d:2015-11-15T00:00:00Z m:space_used_%=14.4 t:disk=/dev/sda1
```

#### Configuration Settings

| Setting | Description | 
| --- | --- | 
|  <p>Enabled</p>  |  <p>Parser status. If parser is disabled, uploaded CSV files referencing this parser will be discarded.</p>  | 
|  <p>Name</p>  |  <p>Unique parser name used as identifier when uploading files.</p>  | 
|  <p>Command Type</p>  |  <p>Type of data contained in the file: time series, properties, messages.</p>  | 
|  <p>Write Property</p>  |  <p>Enable writing data both as series and as properties.</p>  | 
|  <p>Entity Column</p>  |  <p>Name of column in csv file containing the entities, for example: host or node.</p>  <p>Multiple columns can be specified in Entity Column field in order to concatenate their values into a composite entity name using dash symbol – as a token.</p>  <p>For example:</p>  <p>Souce CSV file:</p>  <p>`Year,Source,Destination,Travelers`</p>  <p>`1995,Moscow,Berlin,2000000`</p>  <p>Entity Columns:</p>  <p>`Source,Destination`</p>  <p>Resulting Entity:</p>  <p>`Moscow-Berlin`</p>  | 
|  <p>Entity Prefix</p>  |  <p>Prefix added to entity names.</p>  | 
|  <p>Default Entity</p>  |  <p>All data written to specific entity</p>  | 
|  <p>Replace Entities</p>  |  <p>Replace entity names in the input file with their aliases from the selected [Replacement Table](/products/axibase-time-series-database/download-atsd/administration/entity-lookup/).</p>  <p>For example if Replacement Table contains a mapping `103323213=sensor001` and the entity of the CSV file is named `103323213` then it will be saved in ATSD as `sensor001`.</p>  | 
|  <p>Process Events</p>  |  <p>Process incoming data in the [Rule Engine](/products/axibase-time-series-database/rule-engine/) in addition to storing it in the database.</p>  | 
|  <p>Metric Prefix</p>  |  <p>Prefix added to metric names.</p>  | 
|  <p>Metric Name Column</p>  |  <p>Column containing metric names</p>  | 
|  <p>Metric Value Column</p>  |  <p>Column containing metric values</p>  | 
|  <p>Message Column</p>  |  <p>Column containing message text</p>  | 
|  <p>Timestamp Columns</p>  |  <p>Columns containing the timestamp for each data sample. In some cases, depending on the CSV file, may be split into multiple columns, for example: Date, Time</p>  <p>If there are two columns containing the Timestamp, then they are concatenated with a dash symbol (-) in Timestamp Pattern field.</p>  <p>For example:</p>  <p>Source CSV File:</p>  <p>`Date,Time,Sensor,Power`</p>  <p>`2015.05.15,09:15:00,sensor01,15`</p>  <p>Timestamp Columns:</p>  <p>`Date,Time`</p>  <p>Result:</p>  <p>`Date-Time`</p>  <p>`2015.05.15-09:15:00`</p>  <p>Timestamp Pattern Setting:</p>  <p>`yyyy.MM.dd-HH:mm:ss`</p>  | 
|  <p>Timestamp Type</p>  |  <p>Pattern, Seconds (Unix Seconds), Milliseconds (Unix Milliseconds)</p>  | 
|  <p>Predefined Pattern</p>  |  <p>Predefined Timestamp formats</p>  | 
|  <p>Timestamp Pattern</p>  |  <p>Custom timestamp format, specified manually, for example: dd-MMM-yy HH:mm:ss</p>  <p>If there are two columns containing the Timestamp, then in they are divided with a dash (-) in the pattern.</p>  | 
|  <p>Timezone Diff Column</p>  |  <p>Column containing the time difference calculated from UTC</p>  | 
|  <p>Time Zone</p>  |  <p>Time zone for interpreting timestamps</p>  | 
|  <p>Filter</p>  |  <p>Expression applied to row. If expression returns false, the row is discarded.</p>  <p>Filter syntax:</p>  <p>Fields:</p>  <p>timestamp – timestamp in milliseconds. Computed by parsing date from Time Column with specified Time Format and converted into milliseconds.</p>  <p>row[‘columnName’] – text value of cell in columnName column</p>  <p>Functions:</p>  <p>number(‘columnName’) – returns numeric value of columnName cell, or NaN (Not a Number) if the cell contains unparsable text.</p>  <p>isNumber(‘columnName’) – returns true if columnName cell is a valid number</p>  <p>isBlank(‘columnName’) – returns true is columnName cell is empty string</p>  <p>upper(‘columnName’) – converts columnName cell text to uppercase</p>  <p>lower(‘columnName’) – converts columnName cell text to lowercase</p>  <p>date(‘endtime expression’) – returns time in milliseconds</p>  <p>Filter examples:</p>  <p>number(‘columnName’) > 0</p>  <p>isNumber(‘columnName’)</p>  <p>row[‘columnName’] like ‘abc*’</p>  <p>upper(‘columnName’) != ‘INVALID’</p>  <p>timestamp > date(‘current_day’)</p>  <p>timestamp > date(‘2015-08-15T00:00:00Z’)</p>  <p>timestamp > date(‘now – 2 * year’)</p>  | 
|  <p>Tag Columns</p>  |  <p>Columns converted to series tags</p>  | 
|  <p>Default Tags</p>  |  <p>Predefined series tags, specified as name=value on multiple lines.</p>  | 
|  <p>Ignored Columns</p>  |  <p>List of columns ignored in METRIC and MESSAGE commands.</p>  <p>These columns are retained in PROPERTY commands.</p>  | 
|  <p>Renamed Columns</p>  |  <p>List of column names to substitute input column headers, one mapping per line.</p>  <p>Usage: `inputname=storedname`</p>  | 
|  <p>Header</p>  |  <p>Header to be used if the file contains no header or to replace existing header.</p>  | 
|  <p>Schema</p>  |  <p>[Schema](/products/axibase-time-series-database/writing-data/csv/csv-schema/) defines how to process cells based on their position.</p>  | 


Columns contained in the CSV file that are not specified in any field in the parser will be imported as metrics.

#### Parse Settings

| Setting | Description | 
| --- | --- | 
|  <p>Delimiter</p>  |  <p>Separator dividing values: comma, semicolon, or tab.</p>  | 
|  <p>Line Delimiter</p>  |  <p>End-of-line symbol: EOL `(\\n, \\rn)` or semicolon `;`</p>  | 
|  <p>Text Qualifier</p>  |  <p>Escape character to differentiate separator as literal value.</p>  | 
|  <p>Comment Symbol</p>  |  <p>Lines starting with comment symbol such as hash `#` are ignored.</p>  | 
|  <p>Padding Symbol</p>  |  <p>Symbol appended to text values until all cells in the given column have the same width.</p>  <p>Applies to fixed-length formats such as .dat format.</p>  | 
|  <p>Decimal Separator</p>  |  <p>Symbol used to mark the border between the integral and the fractional parts of a decimal numeral.</p>  <p>Default value: comma</p>  <p>Possible values: period, comma</p>  | 
|  <p>Grouping Separator</p>  |  <p>Symbol used to group thousands within the number.</p>  <p>Default value: none</p>  <p>Possible values: none, period, comma, space</p>  | 
|  <p>Fields Lengths</p>  |  <p>Width of columns in symbols. Padding symbols added to the end of field to obey the fields lengths.</p>  <p>For files in dat format.</p>  | 
|  <p>Discard NaN</p>  |  <p>NaN (Not a Number) values will be discarded</p>  | 
|  <p>Ignore Line Errors</p>  |  <p>If enabled, any errors while parsing the given line are ignored, including date parse errors, number parse errors, split errors, mismatch of row and header column counts.</p>  | 


![](resources/csv_parser_example.png)

#### Column-based Parser Examples:


- [Weather Data](/products/axibase-time-series-database/writing-data/csv/weather-csv-example/)
- [Air Quality](/products/axibase-time-series-database/writing-data/csv/air-quality-csv-example/)


#### Schema-based Parser Examples:


- [Basic Example](/products/axibase-time-series-database/writing-data/csv/basic-schema-field/)
- [Columnar Format](http://axibase.com/products/axibase-time-series-database/writing-data/csv/columnar-schema/)
- [Columnar Period Format](http://axibase.com/products/axibase-time-series-database/writing-data/csv/columnar-period-schema/)
- [No Header](/products/axibase-time-series-database/writing-data/csv/no-header-schema/)
- [Multi-Column Timestamp](/products/axibase-time-series-database/writing-data/csv/multi-column-timestamp/)
- [Multiple Metrics in Header](/products/axibase-time-series-database/writing-data/csv/multiple-metrics-in-header/)
- [Metric Column](/products/axibase-time-series-database/writing-data/csv/metric-column-schema/)
- [Messages](/products/axibase-time-series-database/writing-data/csv/message-schema/)
- [Properties](/products/axibase-time-series-database/writing-data/csv/properties-schema/)
- [Messages with Filter](/products/axibase-time-series-database/writing-data/csv/message-with-filter-schema/)
- [Series with Tags](/products/axibase-time-series-database/writing-data/csv/series-tags-schema/)
- [notEmptyUp](/products/axibase-time-series-database/writing-data/csv/notemptyup-schema/)
- [notEmptyLeft](/products/axibase-time-series-database/writing-data/csv/not-empty-left-schema/)
- [Versioned Series](/products/axibase-time-series-database/writing-data/csv/versioned-series-schema/)
- [Block-Appended](/products/axibase-time-series-database/writing-data/csv/block-appended-schema/)


