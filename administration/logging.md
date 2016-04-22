Logging
=======

Axibase Time Series Database collects a set of logs, all of which are
available directly in the ATSD UI.

You can find the logs on the main menu of ATSD under Admin -\> Server
Logs.

There are four different logs collect in ATSD by default:

| Log Name | Description |
| --- | --- |
| gc.log | garbage collection log |
| atsd.log | ATSD database log. By default `atsd.log` is retained for 60 days, has a maximum file size of 10MB and a maximum of 20 log files are kept at once. When the log file reaches maximum size it is archived and a new `atsd.log` file is created. |
| metrics.txt | Storage performance methods log, [learn more here](/products/axibase-time-series-database/download-atsd/administration/monitoring-atsd/ "Internal Metrics"). |
| err.log | operating system level error log |


![server logs](images/server_logs_atsd.png "server_logs_atsd")