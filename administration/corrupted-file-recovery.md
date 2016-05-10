# Restoring corrupted files



It is worth to

   * wait 30-60 sec after starting hbase and before running hbase hbck;
   * restart hbase after running repair and make sure that status OK by hbck again.


## Safety Part

* Check hadoop safemode

```sh
 /opt/atsd/hadoop/bin/hadoop dfsadmin -safemode get
 # safe mode is ON.
```

* Make hadoop to leave safemode

```sh
 /opt/atsd/hadoop/bin/hadoop dfsadmin -safemode leave
 # safe mode is OFF
```

* Make sure /hbase/ contains corrupted data files

```
 /opt/atsd/hadoop/bin/hadoop fs -rmr -skipTrash /hbase/.logs/*
 /opt/atsd/hadoop/bin/hadoop fsck /hbase/ -openforwrite -files | grep "Status: CORRUPT"
```

If there is no corrupted data files, you are able to use ```/opt/atsd/bin/atsd-all.sh start``` to start ATSD.


* Check hbase status

```sh
 /opt/atsd/hbase/bin/hbase hbck
 # status incosistent
```

* Repair corrupted data files using hbase util

```sh
 /opt/atsd/hbase/bin/hbase hbck -repair
 # status incosistent
```

If repairing doesn't help it comes to remove corrupted files.

## Danger part

* Stop hbase

```sh
 /opt/atsd/hbase/bin/stop-hbase.sh
```

* Get list of corrupted data files 

```sh
 /opt/atsd/hadoop/bin/hadoop fsck / | egrep -v '^\.+$' | grep -v eplica
```

* Remove corrupted files using hadoop 

```sh
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_d/3bcbe238eea99b09bc33bf72129414d7/r/5f301b8315684225b726ec598b1344b1
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_d/3bcbe238eea99b09bc33bf72129414d7/r/83b84b797f7946e09791cbd275bc62e6
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_d/3bcbe238eea99b09bc33bf72129414d7/r/9048d1195b0e4db5a7fae975ab144876
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_entity_group/59c862e98e4cc49c9519615ea6e9784b/e/5140d038336940e5ac97585af9b4ed06
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_entity_group/59c862e98e4cc49c9519615ea6e9784b/e/6178ace282494d14b4bdb21a7a2a405e
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_entity_group/59c862e98e4cc49c9519615ea6e9784b/e/6f8e2d70a48d4654b734539498d1a4e5
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_notification/d02386ead1fc18d1793d1a20049cf6a7/c/0c56a2b84e5f493681b127f2c4d99934
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_notification/d02386ead1fc18d1793d1a20049cf6a7/c/5f76b391cfaa49a8b1d8b9156a21a6e8
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_notification/d02386ead1fc18d1793d1a20049cf6a7/c/b0e84a7daefc4f0d95fc6ba253e91a74
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_properties/a601f43dfbc09c444212b25bc2c20c35/c/eef512e242df476891ca90a10bb2b919
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_properties/a601f43dfbc09c444212b25bc2c20c35/c/f66d6b95624643e489725f940b0bdb39
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_properties/a601f43dfbc09c444212b25bc2c20c35/c/f7f82e4f353b4345964849e770a83675
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_rule/f0af921234254fd479811973067cb3c9/r/0d4a254d7288490a985c636353df77c7
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_rule/f0af921234254fd479811973067cb3c9/r/41262b47fe9f44e286b9981eb71b9675
 /opt/atsd/hadoop/bin/hadoop fs -rm /hbase/atsd_rule/f0af921234254fd479811973067cb3c9/r/495d100f9f204af5b6123ee715b9f74a
```

* Restart hadoop

```sh
 /opt/atsd/hadoop/bin/stop-dfs.sh
 /opt/atsd/hadoop/bin/start-dfs.sh
```

* Wait for leaving safemode by himself about 1 minute, check hadoop safemode and if it is OFF start hbase

```sh
 /opt/atsd/hadoop/bin/hadoop dfsadmin -safemode get
 # safe mode is OFF
 /opt/atsd/hbase/bin/start-hbase.sh
```


* Repair corrupted files using hbase again

```sh
 /opt/atsd/hbase/bin/hbase hbck -repair
 # status OK
```

* Restart hbase

```sh
 /opt/atsd/hbase/bin/stop-hbase.sh
 /opt/atsd/hbase/bin/start-hbase.sh
```

* Check hbase tables are available

```sh
/opt/atsd/hbase/bin/hbase shell
hbase(main):003:0> scan "atsd_entity"
ROW COLUMN+CELL                                                                                                                                                
ERROR: Unknown table atsd_entity!
```

* If you get "Unknown table" exception, run ```repair``` again:

```sh
 /opt/atsd/hbase/bin/hbase hbck -repair
 # status OK
```

* Start atsd and it will be available on the web interface now.

```sh
/opt/atsd/bin/atsd-all.sh start
```
