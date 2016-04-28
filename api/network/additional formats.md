## Additional Formats

### tcollector

> put `<metric> <timestamp> <value> <tagk1=tagv1[ tagk2=tagv2 ...tagkN=tagvN]>`

> put sys.cpu.user 1356998400 42.5 host=webserver01 cpu=0

ATSD supports tcollector format.

[Learn more about tcollector](https://axibase.com/products/axibase-time-series-database/writing-data/tcollector/)

tcollector is a data collection framework for Linux operating system.

ATSD uses the `host` tag as the entity, if `host` tag is missing then entity will be `tcollector`.

### Graphite

> servers.nurswgvml007.cpu_busy 24.5 1232312313

ATSD supports Graphite format.

[Learn more about Graphite](http://axibase.com/products/axibase-time-series-database/writing-data/graphite-format/)

Graphite is a monitoring tool that stores numeric time-series data and renders graphs of this data on demand.

### StatsD

> cpu.busy:20.5|c

> nurswfvml007/cpu.busy:20.5|c

> nurswgvml007.disk_used_percent:24.5|c|@0.5|#mount_point:/,disk_name:/sda

ATSD supports StatsD format.

[Learn more about StatsD](http://axibase.com/products/axibase-time-series-database/writing-data/statsd/)

StatsD is a network daemon that runs on the Node.js platform and listens for statistics, like counters and timers, sent over UDP or TCP and sends aggregates to one or more pluggable backend services.

Forward slash is supported as a control character to extract entity name. If no entity name is set, the default entity will be set as `statsd`.







