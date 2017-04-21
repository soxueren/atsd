# Restarting

ATSD provides wrapper scripts that control the start, stop,
and status of ATSD and components.

Use these scripts to gracefully restart ATSD.

## Script Directory

```sh
 /opt/atsd/bin
```

## Scripts

| **Name** | **Description** | **Arguments** | **Example** |
|---|:---|---|---|
| `atsd-all.sh` | Start, stop, and get status of all components. | start, stop, status | `/opt/atsd/bin/atsd-all.sh status` |
| `atsd-tsd.sh` | Start, stop, and get status of ATSD. | start, stop, status | `/opt/atsd/bin/atsd-tsd.sh stop` |
| `atsd-hbase.sh` | Start, stop, and get status of HBase. | start, stop, status | `/opt/atsd/bin/atsd-hbase.sh status` |
| `atsd-dfs.sh` | Start, stop, and get status of HDFS. | start, stop, status | `/opt/atsd/bin/atsd-dfs.sh start` |
| `update.sh` | [Update ATSD](update.md) in interactive mode.<br>`-t` - Upgrade and restart ATSD silently. No HBase and HDFS restart.<br>`-a` - Upgrade and restart ATSD, HBase, and HDFS silently.| -a, -t | `/opt/atsd/bin/update.sh` |

## Examples

```sh
/opt/atsd/bin/atsd-tsd.sh status
```

```sh
/opt/atsd/bin/update.sh
```

## Docker Container

To control ATSD running inside a Docker container, open a `/bin/bash` session with `docker exec -it` and execute commands as usual:

```sh
docker exec -it atsd /bin/bash
/opt/atsd/bin/update.sh 
```

```sh
docker exec -it atsd tail -f /opt/atsd/atsd/logs/atsd.log
```

## Processes

Switch to `axibase` user and run `jps`:

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

Change to script directory:

```sh
cd /opt/atsd/bin
```

### Stop Services

* Stop ATSD

Stop ATSD and wait for the script to exit:

```
./atsd-tsd.sh stop
```

Verify that the `Server` process is **not** present in `jps` output:

```sh
jps
```

If the `Server` process is still running, kill it forcefully with `kill -9 {Server-pid}`:

* Stop HBase and wait for the script to exit

```sh
./atsd-hbase.sh stop
```

Verify that the `HMaster`, `HRegionServer`, `HQuorumPeer` processes are **not** present in the `jps` output:

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

### Permissions

The `/opt/atsd/bin/atsd-all.sh` script relies on the **[jps](http://docs.oracle.com/javase/7/docs/technotes/tools/share/jps.html)** command to ensure that Java processes are started in the correct order.

The `jps` command requires write permissions to the `/tmp/hsperfdata_axibase` directory in order to store temporary files. If permissions to this directory are missing (i.e. it's owned by root), `jps` fails to identify other running Java processes under the current user and returns an imcomplete list, despite the fact that such processes are running and are visible with `ps aux | grep java`. 

If `jps` output is incomplete, the `atsd-all.sh` script aborts the startup procedure with the following message:

```
nurswgvml007 atsdService: * [ATSD] DataNode is not running. 
```

Execute the following steps to fix this issue:

* [Stop](#stop-services) ATSD services
* Remove /tmp/hsperfdata_axibase directory
* Start ATSD with `/opt/atsd/bin/atsd-all.sh`



