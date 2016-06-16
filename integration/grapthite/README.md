# Graphite

[Graphite](https://graphite.readthedocs.org/en/latest/) is a monitoring tool that stores numeric time-series data and renders graphs of this data on demand.

Axibase Time Series Database supports Graphite format and Pickle protocol, so tools that send data to Graphite can also send data to ATSD.

ATSD has a Storage Finder for Graphite-Web, meaning that you can use ATSD as the back-end for Graphite replacing or augmenting the default Whisper database.

[Learn more about ATSD Storage Finder here.](storage-finder.md)

![](resources/atsd_protocols_finders3.png)

##### List of key tools that support Graphite Format.


- [collectd](http://collectd.org/) – a daemon which collects system performance statistics periodically
- [collectl](http://collectl.sourceforge.net/) – a collection tool for system metrics that can be run both interactively and as a daemon and has support for collecting from a broad set of subsystems.
- [Ganglia](http://ganglia.info/) – a scalable distributed monitoring system for high-performance computing systems such as clusters and Grids.
- [Sensu](https://sensuapp.org/) – a monitoring framework that can route metrics to Graphite.
- [Graphios](https://github.com/shawn-sterling/graphios) – a small Python daemon to send Nagios performance data (perfdata) to Graphite.
- [Gruffalo](https://github.com/outbrain/gruffalo) – an asynchronous Netty based graphite proxy, for large scale installations.
- [StatsD](../statsd) – a simple daemon for easy stats aggregation.
- [Grafana](http://grafana.org/) – a general purpose graphite dashboard replacement with feature rich graph editing and dashboard creation interface.


You can review the complete list of supported tools [here](http://graphite.readthedocs.org/en/latest/tools.html).

