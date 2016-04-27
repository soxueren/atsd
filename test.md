[](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/#)

-   [Home](http://axibase.com/)
-   [Customers](http://axibase.com/customers/)
-   [Products](http://axibase.com/products/)
    -   [Axibase Time Series
        Database](http://axibase.com/products/axibase-time-series-database/)
    -   [Axibase Enterprise
        Reporter](http://axibase.com/products/axibase-enterprise-reporter/)
    -   [Axibase Fabrica](http://axibase.com/products/axibase-fabrica/)
-   [Downloads](http://axibase.com/products/axibase-time-series-database/download-atsd/)
-   [Support](http://axibase.com/customer-support/)
    -   [Resources](http://axibase.com/resources/)
-   [Blog](http://axibase.com/blog/)
-   [About Us](http://axibase.com/about-us/)
    -   [Management](http://axibase.com/about-us/management/)
    -   [Partners](http://axibase.com/about-us/partners/)
    -   [News](http://axibase.com/news/)
-   [Contact Us](http://axibase.com/feedback/)

**[![Axibase](./test_files/axibase_logo_orange-2.png)](http://axibase.com/)**[](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/#)

-   [Home](http://axibase.com/)
-   [Customers](http://axibase.com/customers/)
-   [Products](http://axibase.com/products/)
    -   [Axibase Time Series
        Database](http://axibase.com/products/axibase-time-series-database/)
    -   [Axibase Enterprise
        Reporter](http://axibase.com/products/axibase-enterprise-reporter/)
    -   [Axibase Fabrica](http://axibase.com/products/axibase-fabrica/)
-   [Downloads](http://axibase.com/products/axibase-time-series-database/download-atsd/)
-   [Support](http://axibase.com/customer-support/)
    -   [Resources](http://axibase.com/resources/)
-   [Blog](http://axibase.com/blog/)
-   [About Us](http://axibase.com/about-us/)
    -   [Management](http://axibase.com/about-us/management/)
    -   [Partners](http://axibase.com/about-us/partners/)
    -   [News](http://axibase.com/news/)
-   [Contact Us](http://axibase.com/feedback/)
-   [Search](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/?s=)

[Allocating Memory](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/ "Permanent Link: Allocating Memory") {.main-title .entry-title}
=================================================================================================================================================================

You are here: [Home](http://axibase.com/ "Axibase") /
[Products](http://axibase.com/products/ "Products") / [Axibase Time
Series
Database](http://axibase.com/products/axibase-time-series-database/ "Axibase Time Series Database")
/ [Download Axibase Time Series
Database](http://axibase.com/products/axibase-time-series-database/download-atsd/ "Download Axibase Time Series Database")
/
[Administration](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/ "Administration")
/ Allocating Memory

Allocating Memory to Axibase Time Series Database Components
============================================================

**Change maximum ATSD process memory**

Open ATSD start-up script and locate java command line. Set new maximum
memory size (-Xmx parameter) in megabytes:

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| nano /opt/atsd/atsd/bin/start-atsd.sh                                    |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| #GC logs disabled                                                        |
| if grep -qi "arm" /proc/cpuinfo; then                                    |
|     "$java_command" -server  -Xmx512M -XX:+PrintCommandLineFlags -XX:+He |
| apDumpOnOutOfMemoryError -XX:HeapDumpPath="$atsd_home"/logs $DParams -$  |
| else                                                                     |
|     "$java_command" -server  -Xmx4096M -XX:+HeapDumpOnOutOfMemoryError - |
| XX:HeapDumpPath="$atsd_home"/logs $DParams -classpath "$atsd_home"/con$  |
| fi                                                                       |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

**Change maximum HBase process memory**

Open HBase environment file and uncomment `export HBASE_HEAPSIZE` line.
Set new maximum memory size in megabytes:

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| nano /opt/atsd/hbase/conf/hbase-env.sh                                   |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| export HBASE_HEAPSIZE=4096                                               |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

**Change maximum HDFS process memory**

Open HDFS environment file and uncomment `export HADOOP_HEAPSIZE` line.
Set new maximum memory size in megabytes:

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| nano /opt/atsd/hadoop/conf/hadoop-env.sh                                 |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| export HADOOP_HEAPSIZE=4096                                              |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

Restart all components

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| /opt/atsd/bin/atsd-all.sh stop                                           |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| /opt/atsd/bin/atsd-all.sh start                                          |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

Verify that -Xmx parameter is set accordingly:

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| ps -ef | grep Xmx                                                        |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

### Administration {.widgettitle}

-   [Deployment](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/deployment/)
-   [Setting up the Email
    Client](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/email-client/)
-   [User
    Authentication](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/user-authentication/)
-   [User
    Authorization](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/user-authorization/)
-   [Restarting
    ATSD](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/restarting-atsd/)
-   [Uninstalling
    ATSD](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/uninstalling-atsd/)
-   [Migrate ATSD to Java
    7](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/migrate-to-java7/)
-   [Network
    Settings](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/network-settings/)
-   [Enabling Swap
    Space](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/enabling-swap-space/)
-   [Enabling
    Replication](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/atsd-replication/)
-   [Allocating
    Memory](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/)
-   [Changing Data
    Directory](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/changing-data-directory/)
-   [Editing Configuration
    Files](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/editing-configuration-files/)
-   [Logging](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/logging/)
-   [Metric Persistence
    Filter](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/metric-persistence-filter/)
-   [Entity
    Lookup](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/entity-lookup/)
-   [Corrupted
    Zookeeper](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/restoring-corrupted-zookeeper/)
-   [Compaction](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/compaction/)
-   [Compaction
    Test](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/compaction-test/)
-   [Monitoring
    ATSD](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/monitoring-atsd/)

### Axibase {.widgettitle}

-   [Terms of Use](http://axibase.com/terms-of-use/)

### Products {.widgettitle}

-   [Axibase Time Series
    Database](http://axibase.com/products/axibase-time-series-database/)
-   [Axibase Enterprise
    Reporter](http://axibase.com/products/axibase-enterprise-reporter/)
-   [Axibase Fabrica](http://axibase.com/products/axibase-fabrica/)

### About Us {.widgettitle}

-   [Management](http://axibase.com/about-us/management/)
-   [Partners](http://axibase.com/about-us/partners/)

### Contact Us {.widgettitle}

-   [Contact Us](http://axibase.com/feedback/)

© Copyright 2016 - Axibase

\<div class="statcounter"\>\<a title="web analytics"
href="https://statcounter.com/"\>\<img class="statcounter"
src="http://c.statcounter.com/10201325/0/473703da/1/" alt="web
analytics" /\>\</a\>\</div\>

[Scroll to
top](http://axibase.com/products/axibase-time-series-database/download-atsd/administration/memory-allocation/#top "Scroll to top")
