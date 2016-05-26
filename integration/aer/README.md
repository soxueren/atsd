# ATSD Adapter

## Overview

ATSD adapter in Axibase Enterprise Reporter and Axibase Fabrica creates a hierarchical representation of metrics 
collected in ATSD and makes them available for querying in reports.

In order for Axibase Server to group ATSD metrics into virtual tables, metrics need to be tagged with `table` tag. 

## Enable `table` tag in Metrics List

* Login into ATSD
* Open **Admin:Server Properties** page
* Add `table` tag to `metric.display.tags` property

![Server Properties](metric-table-tags.png)

* Click Apply Changes and open Metrics tab to verify that the `table` tag is visible in Metrics list

![Metrics List: table tag](metrics-table-tag.png)

## Apply `table` tag to Metrics

* Enter partial metric name in Name Mask to filter Metric list by name. `*` and `?` wildcards are supported.
* Set Page Size (Display on Page) to 1000

![Metric List: filter](metric-list-filter.png)

* Click checkbox to select filtered metrics.
* Make sure that grouped metrics have the same tags, for example, group df.disk_used, df.disk_used_percent, and other df.* metrics into one table since their shared tags are file_system and mount_point.
* Enter a name that describes this group of selected metrics and click [Apply]

![Metric List: apply tag](metric-table-tag-apply.png)

* Verify that selected metrics have the `table` tag set

![Metric List: applied tag](metric-table-tag-applied.png)

* Repeat the process to group metrics into tables

![Metric List: tag all](metric-table-tag-all.png)

## Discover Metric Tables in Axibase Server

* Login into Axibase Server
* Open Admin:Warehouse Tools page
* Select `Reload Schema Cache` action
* Select `View Schema Cache` to ensure that new metric groups are present as tables in reloaded schema.

## Configure ATSD Proxy

Configure Axibase Server to serve as an ATSD proxy so that widgets stored in Axibase Server can optionally query data in ATSD transparently. In this configuration Axibase Server redirects an API request received from the client, executes the request against 

* Open **Admin:Settings:** page in AxibaseServer, expand SERVER section
* Enter a full URL to ATSD into `REDIRECT URL` field including username and password as follows:
`schema://atsd_user:atsd_user_password@atsd_host:atsd_port`

Example:

`http://reader:my_password@10.102.0.6:8088`

![](redirect_settings.png)

* Save Settings
* Add `url = /proxy` setting in widget configuration files that need to query ATSD directly:

```ls
[configuration]
  title = Performance/Daily 
  width-units = 4
  height-units = 3
  url = /proxy
  
  [group]
  [widget]
    type = chart
    /*
    	Query entities and metrics defined in ATSD, even if not exposed in Axibase Server
    */
    metric = mpstat.cpu_busy
    entity = nurswghbs001
```




