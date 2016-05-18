# Logging

The database collects a set of logs in `/opt/atsd/atsd/logs` directory 
which are also downloadable from **Admin:Server Logs** page.

Logs are rolled over and archived according to `/opt/atsd/atsd/conf/logging.properties` settings.

|**Log Name**|**Description**|
|---|:---|
|atsd.log|Application log|
|command.log|Received command log|
|command_discarded.log|Discarded commands|
|command_malformed.log|Malformed command log, e.g. invalid syntax| 
|command_ignored.log|Ignored commands|
|command_rule_engine_expired.log|Commands with old timestamp, ignored by rule engine|
|command_rule_engine_forward.log|Commands with ahead timestamp, ignored by rule engine|
|gc.log|Garbage collection log|
|metrics.txt|Current database metrics|
|stopstart.log|Start/stop log for ATSD, HBase, HDFS|
|stdout.log|Standard out|
|err.log|Standard err|

![server logs](images/server_logs_atsd.png "server_logs_atsd")
