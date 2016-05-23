# Logging

The database logs are located in `/opt/atsd/atsd/logs` directory.

The logs can be also downloaded from **Admin:Server Logs** page.

Logs are rolled over and archived according to `/opt/atsd/atsd/conf/logging.properties` settings.

|**Log Name**|**Description**|
|---|:---|
|atsd.log|Application log|
|command.log|Received command log|
|command_discarded.log|Discarded commands for disabled metrics.|
|command_malformed.log|Malformed commands with invalid syntax etc.| 
|command_ignored.log|Commands ignored by parsers, e.g. nmon. |
|command_rule_engine_expired.log|Commands with old timestamp, ignored by rule engine|
|command_rule_engine_forward.log|Commands with forward timestamp, ignored by rule engine|
|gc.log|Garbage collection log|
|metrics.txt|Current database metrics|
|stopstart.log|Start/stop log for ATSD, HBase, HDFS|
|stdout.log|Standard out|
|err.log|Standard error|

* Rule Engine ignores commands that are 1 minute behind or 1 minute ahead of the current server time.<br>When enabled, the ignored commands are logged to `*_expired.log` and `*_forward.log` files respectively.

![server logs](images/server_logs_atsd.png "server_logs_atsd")

Command processings logs should be enabled on **Admin:Input Settings** page:

![](server-logs-command-files.png)
