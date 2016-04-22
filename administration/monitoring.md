# Monitoring

ATSD performance metrics can be retrieved via JMX, file or http/JSON
request.

These metrics are updated every 15 seconds and can be used to monitor
the internal components of the Axibase Time Series Database such as the
amount of data received, memory usage, and read/write activity.

You can retrieve, test or view the metrics using the following methods:

-   [JSON](atsd-metrics/json.md "ATSD Metrics – JSON")
-   [JMX](atsd-metrics/jmx.md "ATSD Metrics – JMX")
-   [File](atsd-metrics/json.md  "ATSD Metrics – File")
-   [Rule
    Engine](atsd-metrics/json.md  "ATSD Metrics – Rule Engine")
-   [Ingestion
    Statistics](atsd-metrics/json.md  "ATSD Metrics – Ingestion Statistics")
-   [Database
    Tables](atsd-metrics/json.md  "ATSD Metrics – Database Tables")
-   [I/O
    Tests](atsd-metrics/json.md  "ATSD Metrics – I/O Tests")
-   [HBase Write
    Test](atsd-metrics/json.md  "ATSD Metrics – HBase Write Test")
-   [Portals](atsd-metrics/json.md  "ATSD Metrics – Portals")

## Collected Metrics

Retrieve a full list of collected metrics in JSON:

```sh
 http://atsd_server:8088/jmx?query=com.axibase.tsd:name=metrics           
```

List of collected metrics:

| Metric | Description |
| --- | --- |
| actions_per_minute | number of actions taken (based on triggered rules) by ATSD Rule Engine per minute. |
| alert_log_writes_per_minute | number of alerts raised by ATSD Rule Engine per minute. |
| email_notifications_per_minute | number of email notifications sent by ATSD Rule Engine per minute. |
| expired_metric_received_per_second | number of metrics that satisfy the following rule: `now - timestamp > 1 hour` |
| forward_metric_received_per_second | number of metrics that satisfy the following rule: `timestamp - now > 1 hour` |
| gc_invocations_per_minute_Copy
gc_invocations_per_minute_MarkSweepCompact | number of garbage collection calls per minute. |
| gc_time_percent_Copy
gc_time_percent_MarkSweepCompact | the percentage of time in between calls that garbage collection took. |
| hbase_scans_per_second | number of HBase searches per second. |
| invalid_message_received_per_second | number of invalid messages received per second. |
| jvm_memory_free | number of free memory bytes available to the java virtual machine. |
| jvm_memory_max | maximum number of memory space available to the java virtual machine, in bytes. |
| jvm_memory_used | number of used memory bytes by the java virtual machine. |
| jvm_memory_used_percent | percentage of memory used by the java virtual machine. |
| message_received_per_second | number of messages received per second. |
| message_writes_per_second | number of messages stored on disk per second. |
| metric_gets_per_second | number of metrics retrieved per second. |
| metric_reads_per_second | number of metrics read from disk per second. |
| metric_received_per_second | number of metrics received per second. |
| metric_writes_per_second | number of metrics stored on disk per second. |
| property_gets_per_second | number of properties retrieved per second. |
| property_reads_per_second | number of properties read from disk per second. |
| property_received_per_second | number of properties received per second. |
| property_writes_per_second | number of properties stored on disk per second. |
| web_service_notifications_per_minute | number of web service notifications sent by ATSD Rule Engine per minute. |
| ws_simple_msg_per_second | number of simple web socket messages handled per second. |
| java_method_invoke_average java_method_invoke_count_per_second java_method_invoke_last | Tracks storage performance methods. Three different aggregations, `average`, `count per second` and `last`, are collected for the following methods: `dao.MessageDaoImpl.putBatch` `dao.PropertyDaoImpl.search` `dao.TimeSeriesDaoImpl.putBatch` `service.TimeSeriesServiceImpl.putBatch` Last and Average are collected as time in ms. |