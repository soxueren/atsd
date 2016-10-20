# Parser

The nmon parser ingests raw nmon files and converts them to properties and metrics in ATSD.

During the installation of ATSD, a default nmon parser is created for ingesting standard nmon files. Settings of the default parser are shown in the table below.

#### Parser Settings

| Setting | Description | Default Parser Settings | 
| --- | --- | --- | 
|  <p>Name</p>  |  <p>Name of the current parser.</p>  |  <p>default</p>  | 
|  <p>Metric Prefix</p>  |  <p>Prefix to be added before each nmon metric in order to distinguish and sort metrics with same or similar names. For example: using the prefix nmon will convert the metric `cpu_total.busy` to `nmon.cpu_total.busy`.</p>  |  <p>nmon</p>  | 
|  <p>Ignored</p>  |  <p>nmon metrics to be ignored. Metrics listed here will not be imported. Acts as a filter.</p>  |  <p>none</p>  | 
|  <p>Process TOP</p>  |  <p>Ingest TOP data.</p>  |  <p>Yes</p>  | 
|  <p>Process UARG</p>  |  <p>Ingest user and arg columns.</p>  |  <p>Yes</p>  | 
|  <p>Enable Properties</p>  |  <p>Store entity properties and configurations in ATSD from the nmon file.</p>  |  <p>Yes</p>  | 
|  <p>Ignore Errors</p>  |  <p>Ignore all errors. Parses only known metrics and properties.</p>  |  <p>No</p>  | 
|  <p>Retention Interval</p>  |  <p>How long the uploaded nmon file will be stored on the ATSD server. 2 days by default.</p>  |  <p>2 days</p>  |


![](resources/nmon-parser-default.png)
