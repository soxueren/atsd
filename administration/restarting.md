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
| atsd-all.sh | Start, stop and get status of all components. | start, stop, status | `/opt/atsd/bin/atsd-all.sh stop` |
| atsd-hbase.sh | Start, stop and status of HBase. | start, stop, status | `/opt/atsd/bin/atsd-hbase.sh status` |
| atsd-tsd.sh | Start, stop and status of ATSD. | start, stop, status | `/opt/atsd/bin/atsd-tsd.sh start` |
| atsd-dfs.sh | Start, stop and status of HDFS. | start, stop, status | `/opt/atsd/bin/atsd-dfs.sh status` |
| update.sh | [Update ATSD](update.md "Update ATSD") in interactive mode.<br>`-a` - Upgrade and restart ATSD silently without HBase and HDFS restart.<br>`-t` - Upgrade and restart ATSD, HBase, HDFS silently.| -a, -t | `/opt/atsd/bin/update.sh` |

