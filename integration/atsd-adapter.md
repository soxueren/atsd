# ATSD Adapter

## Overview

ATSD adapter in Axibase Enterprise Reporter and Axibase Fabrica creates a hierarchical representation of metrics 
collected in ATSD and makes them available for querying in reports.

In order for Axibase Server to group ATSD metrics into virtual tables, metrics need to be tagged with `table` tag. 

## Enable `table` tag in Metrics List

* Login into ATSD
* Open **Admin:Server Properties** page
* Add `table` tag to `metric.display.tags` property
* Click Apply Changes and open Metrics tab to verify that the `table` tag is visible in Metrics list

[Metrics List: table tag](metrics-table-tag.png)

## Apply `table` tag to Metrics

* Enter partial metric name in Name Mask to filter Metric list by name. `*` and `?` wildcards are supported.
* Set Page Size (Display on Page) to 1000

[Metric List: filter](metric-list-filter.png)

* Click checkbox to select filtered metrics.
* Enter a name that describes selected metrics and click [Apply]

[Metric List: apply tag](metric-list-tag-apply.png)

* Verify that selected metrics have the `table` tag set

[Metric List: applied tag](metric-list-tag-applied.png)

* Repeat the process to group metrics into tables

[Metric List: tag all](metric-list-tag-all.png)

## Discover Metric Tables in Axibase Server

* Login into Axibase Server
* Open Admin:Warehouse Tools page
* Select `Reload Schema Cache` action
* Select `View Schema Cache` to ensure that new metric groups are present as tables in reloaded schema.
