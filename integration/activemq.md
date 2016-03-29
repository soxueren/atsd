#Monitoring ActiveMQ with Axibase Time Series Database
## Requirements

* ActiveMQ version 5.x.+
* Axibase Time Series Database. For installation instructions, see [Axibase Time Series Database Download Options](http://axibase.com/products/axibase-time-series-database/download-atsd/).
* Axibase Collector. For installation instructions, see [Axibase Collector Installation](http://axibase.com/products/axibase-time-series-database/writing-data/collector/axibase-collector-installation/).
# Configuring ActiveMQ Server

## Step 1: Enabling JMX and Log Aggregator

1. Login into ActiveMQ server via SSH.
2. Change to ActiveMQ installation directory.
```sh
cd /opt/apache-activemq-5.13.1
```
3.  Download log aggregation filter .jar files to the ActiveMQ lib directory:

```sh
wget --content-disposition -P ./lib/ \
   "https://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.axibase&a=aggregation-log-filter&v=LATEST"
wget --content-disposition -P ./lib/ \
   "https://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.axibase&a=aggregation-log-filter-log4j&v=LATEST"
```
4. Append aggregation filter settings to ActiveMQ log4j.properties file. Replace atsd_hostname with the hostname of the ATSD server:
```sh
cat <<EOF >> ./conf/log4j.properties
log4j.appender.logfile.filter.COLLECTOR=com.axibase.tsd.collector.log4j.Log4jCollector
log4j.appender.logfile.filter.COLLECTOR.writerHost=atsd_hostname
EOF
```
See [Aggregation Log Filter](https://github.com/axibase/aggregation-log-filter) for additional configuration options.
5. Modify JMX settings in ActiveMQ JVM launch options.
Search for ACTIVEMQ_SUNJMX_START setting and change it as specified below.
Replace activemq_hostname with full hostname or IP address of the ActiveMQ server.
This should be the same hostname that Axibase Collector will be using when connecting to ActiveMQ server.
For more information on configuring JMX in ActiveMQ, see [activemq.apache.org/jmx.html](http://activemq.apache.org/jmx.html)

ActiveMQ 5.11.x and later:
```sh
vi ./bin/env
```
```sh
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote.port=1090"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote.rmi.port=1090"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote.ssl=false"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Djava.rmi.server.hostname=activemq_hostname"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote.password.file=${ACTIVEMQ_CONF}/jmx.password"
ACTIVEMQ_SUNJMX_START="$ACTIVEMQ_SUNJMX_START -Dcom.sun.management.jmxremote.access.file=${ACTIVEMQ_CONF}/jmx.access"
```
ActiveMQ 5.10.x and earlier:
```sh
vi ./bin/activemq
```
```sh
ACTIVEMQ_SUNJMX_START="-Dcom.sun.management.jmxremote \
   -Dcom.sun.management.jmxremote.port=1090 \
   -Dcom.sun.management.jmxremote.rmi.port=1090 \
   -Dcom.sun.management.jmxremote.ssl=false \
   -Djava.rmi.server.hostname=activemq_hostname \
   -Dcom.sun.management.jmxremote.password.file=${ACTIVEMQ_BASE}/conf/jmx.password \
   -Dcom.sun.management.jmxremote.access.file=${ACTIVEMQ_BASE}/conf/jmx.access"
```
The result should be as shown on the image below:

![SUN_JMX_START_IMAGE](https://axibase.com/wp-content/uploads/2016/03/very_new_screen.png)
7. Modify JMX security credential files in ./conf directory.

jmx.access:
```
# The "monitorRole" role has readonly access.
monitorRole readonly
```
jmx.password:
```
# The "monitorRole" role has password "abc123".
monitorRole abc123
```

8. Secure access to jmx.password file by restricting permissions: 
```sh
chmod 600 ./conf/jmx.password
```

9. Restart ActiveMQ server.
```sh
./bin/activemq stop
./bin/activemq start
```
