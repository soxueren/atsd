# Restarting ATSD


ATSD comes with a set of wrapper scripts that control the start, stop
and status of ATSD and components. Use these scripts to start and stop
ATSD.

ATSD uses these scripts to safely stop ATSD and components and start
them back up on system reboot.

## Script directory:

```sh
 /opt/atsd/bin
```

## Scripts:

| Name | Description | Example |
| --- | --- | --- |
| atsd-all.sh | Start, stop and status of ATSD and all components.Possible commands: start, stop, status | /opt/atsd/bin/atsd-all.sh stop |
| atsd-hbase.sh | Start, stop and status of HBase. Possible commands: start, stop, status | /opt/atsd/bin/atsd-hbase.sh status |
| atsd-tsd.sh | Start, stop and status of ATSD. Possible commands: start, stop, status | /opt/atsd/bin/atsd-tsd.sh start |
| atsd-dfs.sh | Start, stop and status of Hadoop. Possible commands: start, stop, status | /opt/atsd/bin/atsd-dfs.sh status |
| update.sh | Update ATSD. [View the Update ATSD guide](update-atsd.md "Update ATSD") | /opt/atsd/bin/update.sh |

