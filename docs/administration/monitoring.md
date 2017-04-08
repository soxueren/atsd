# Monitoring

ATSD performance metrics can be retrieved via JMX, file, or a http/JSON
request.

These metrics are updated every 15 seconds and can be used to monitor
the internal components of the Axibase Time Series Database, such as the
amount of data received, memory usage, and read/write activity.

You can retrieve, test, or view the metrics using the following methods:

-   [JMX in JSON format](monitoring-metrics/json.md "ATSD Metrics – JSON")
-   [JMX](monitoring-metrics/jmx.md "ATSD Metrics – JMX")
-   [File](monitoring-metrics/file.md  "ATSD Metrics – File")
-   [Rule Engine](monitoring-metrics/rule-engine.md  "ATSD Metrics – Rule Engine")
-   [Ingestion Statistics](monitoring-metrics/ingestion-statistics.md  "ATSD Metrics – Ingestion Statistics")
-   [Database Tables](monitoring-metrics/database-tables.md  "ATSD Metrics – Database Tables")
-   [I/O Tests](monitoring-metrics/io-tests.md  "ATSD Metrics – I/O Tests")
-   [HBase Write Test](monitoring-metrics/hbase-write-test.md  "ATSD Metrics – HBase Write Test")
-   [Portals](monitoring-metrics/portals.md  "ATSD Metrics – Portals")

## Collected Metrics

Retrieve a full list of collected metrics in JSON:

```sh
 http://atsd_server:8088/jmx?query=com.axibase.tsd:name=metrics           
```

List of collected metrics:

| Metric | Description |
| --- | --- |
| actions_per_minute | Number of actions taken (based on triggered rules) by ATSD Rule Engine per minute. |
| alert_log_writes_per_minute | Number of alerts raised by ATSD Rule Engine per minute. |
| email_notifications_per_minute | Number of email notifications sent by ATSD Rule Engine per minute. |
| expired_metric_received_per_second | Number of metrics that satisfy the following rule: `now - timestamp > 1 hour` |
| forward_metric_received_per_second | Number of metrics that satisfy the following rule: `timestamp - now > 1 hour` |
| gc_invocations_per_minute_MarkSweepCompact | Number of garbage collection calls per minute. |
| gc_time_percent_MarkSweepCompact | The percentage of time in between calls that garbage collection took. |
| hbase_scans_per_second | Number of HBase searches per second. |
| invalid_message_received_per_second | Number of invalid messages received per second. |
| jvm_memory_free | Number of free memory bytes available to the java virtual machine. |
| jvm_memory_max | Maximum number of memory space available to the java virtual machine, in bytes. |
| jvm_memory_used | Number of used memory bytes by the java virtual machine. |
| jvm_memory_used_percent | Percentage of memory used by the java virtual machine. |
| message_received_per_second | Number of messages received per second. |
| message_writes_per_second | Number of messages stored on disk per second. |
| metric_gets_per_second | Number of metrics retrieved per second. |
| metric_reads_per_second | Number of metrics read from disk per second. |
| metric_received_per_second | Number of metrics received per second. |
| metric_writes_per_second | Number of metrics stored on disk per second. |
| property_gets_per_second | Number of properties retrieved per second. |
| property_reads_per_second | Number of properties read from disk per second. |
| property_received_per_second | Number of properties received per second. |
| property_writes_per_second | Number of properties stored on disk per second. |
| web_service_notifications_per_minute | Number of web service notifications sent by ATSD Rule Engine per minute.|
| ws_simple_msg_per_second | Number of simple web socket messages handled per second. |
| java_method_invoke_average java_method_invoke_count_per_second java_method_invoke_last | Tracks storage performance methods. Three different aggregations, `average`, `count per second` and `last`, are collected for the following methods: `dao.MessageDaoImpl.putBatch` `dao.PropertyDaoImpl.search` `dao.TimeSeriesDaoImpl.putBatch` `service.TimeSeriesServiceImpl.putBatch` Last and Average are collected as time in ms. |
