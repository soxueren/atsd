# Restarting


ATSD comes with a set of wrapper scripts that control the start, stop
and status of ATSD and components. Use these scripts to start and stop
ATSD.

ATSD uses these scripts to safely stop ATSD and components and start
them back up on system reboot.

## Script Directory

```sh
 /opt/atsd/bin
```

## Scripts

| **Name** | **Description** | **Arguments** | **Example** |
|---|:---|---|---|
| atsd-all.sh | Start, stop and get status of all components. | start, stop, status | `/opt/atsd/bin/atsd-all.sh status` |
| atsd-tsd.sh | Start, stop and get status of ATSD. | start, stop, status | `/opt/atsd/bin/atsd-tsd.sh stop` |
| atsd-hbase.sh | Start, stop and get status of HBase. | start, stop, status | `/opt/atsd/bin/atsd-hbase.sh status` |
| atsd-dfs.sh | Start, stop and get status of HDFS. | start, stop, status | `/opt/atsd/bin/atsd-dfs.sh start` |
| update.sh | [Update ATSD](update.md "Update ATSD") in interactive mode.<br>`-t` - Upgrade and restart ATSD silently. No HBase and HDFS restart.<br>`-a` - Upgrade and restart ATSD, HBase, and HDFS silently.| -a, -t | `/opt/atsd/bin/update.sh` |

## Examples

```sh
/opt/atsd/bin/atsd-tsd.sh status
```

```sh
/opt/atsd/bin/update.sh
```

## rocesses

Switch to `axibase` user and run `jps`

```java
27392 Jps
22110 Server 
18494 HMaster
18387 HQuorumPeer
18673 HRegionServer
25587 NameNode
25961 SecondaryNameNode
25790 DataNode
```

Process affiliation:

```java
Server - ATSD 
HMaster - HBase
HQuorumPeer - HBase
HRegionServer - HBase
NameNode - HDFS
SecondaryNameNode - HDFS
DataNode - HDFS
```

## Safe Restart

Change to script directory

```sh
cd /opt/atsd/bin
```

### Stop Services

* Stop ATSD

Stop ATSD and wait for the script to exit

```
./atsd-tsd.sh stop
```

Verify that `Server` process is **not** present in `jps` output

```sh
jps
```

If `Server` process is still running, kill it forcefully with `kill -9 {Server-pid}`

* Stop HBase and wait for the script to exit

```sh
./atsd-hbase.sh stop
```

Verify that `HMaster`, `HRegionServer`, `HQuorumPeer` processes are **not** present in `jps` output

```sh
jps
```

The `jps` output should display only HDFS processes at this time:

```
27392 Jps
25961 SecondaryNameNode
25790 DataNode
25587 NameNode
```

If HBase processes are still running, retry `./atsd-hbase.sh stop`.

If subsequent  `./atsd-hbase.sh stop` executions fail to stop HBase processes, kill HBase processes by pid with SIGTERM (no flags).

Make sure you don't kill HDFS processes.

```sh
kill 18494
```

If any HBase process fails to stop after that and is still visible in `jps`, contact Axibase support for further instructions to prevent data loss.

### Start Services

Start HBase and ATSD in the reverse order

```sh
./atsd-hbase.sh start
```

```sh
./atsd-tsd.sh start
```




