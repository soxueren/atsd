Allocating Memory to Axibase Time Series Database Components
============================================================

**Change maximum ATSD process memory**

Open ATSD start-up script and locate java command line. Set new maximum
memory size (-Xmx parameter) in megabytes:

```sh
nano /opt/atsd/atsd/bin/start-atsd.sh
```

```sh
 if grep -qi "arm" /proc/cpuinfo; then
     "$java_command" -server  -Xmx512M -XX:+PrintCommandLineFlags -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="$atsd_home"/logs $DParams -$
else
     "$java_command" -server  -Xmx4096M -XX:+HeapDumpOnOutOfMemoryError XX:HeapDumpPath="$atsd_home"/logs $DParams -classpath "$atsd_home"/con$
fi
```

**Change maximum HBase process memory**

Open HBase environment file and uncomment `export HBASE_HEAPSIZE` line.
Set new maximum memory size in megabytes:

```sh
nano /opt/atsd/hbase/conf/hbase-env.sh
```

```sh
export HBASE_HEAPSIZE=4096
```

**Change maximum HDFS process memory**

Open HDFS environment file and uncomment `export HADOOP_HEAPSIZE` line.
Set new maximum memory size in megabytes:

```sh
nano /opt/atsd/hadoop/conf/hadoop-env.sh
```

```sh
export HADOOP_HEAPSIZE=4096
```

Restart all components

```sh
/opt/atsd/bin/atsd-all.sh stop
```
```sh
/opt/atsd/bin/atsd-all.sh start
```

Verify that -Xmx parameter is set accordingly:


```
ps -ef | grep Xmx
```
