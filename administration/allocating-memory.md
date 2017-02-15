# Allocating Memory to Components


## Change Maximum ATSD Heap Memory

Open ATSD start-up script and locate the java command line. Set new maximum memory size (`-Xmx` parameter) in megabytes:

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

## Change Maximum HBase Heap Memory

Open the HBase environment file and uncomment the `export HBASE_HEAPSIZE` line.
Set new maximum memory size in megabytes:

```sh
nano /opt/atsd/hbase/conf/hbase-env.sh
```

```sh
export HBASE_HEAPSIZE=4096
```

##### Change Maximum HDFS Heap Memory

Open the HDFS environment file and uncomment the `export HADOOP_HEAPSIZE` line.
Set new maximum memory size in megabytes:

```sh
nano /opt/atsd/hadoop/conf/hadoop-env.sh
```

```sh
export HADOOP_HEAPSIZE=4096
```

##### Restart All Components

```sh
/opt/atsd/bin/atsd-all.sh stop
/opt/atsd/bin/atsd-all.sh start
```

##### Verify that `-Xmx` Parameter is Set Accordingly:


```
ps aux | grep Xmx
```
