# Restoring a corrupted zookeeper


If the HBase server did not start correctly and the file
`/opt/atsd/hbase/logs/hbase-axibase-zookeeper-{hostname}.out` contains:

```java
 java.io.IOException: Failed to process transaction type: 1 error: Keeper 
 ErrorCode = NoNode for /hbase                                            
     at org.apache.zookeeper.server.persistence.FileTxnSnapLog.restore(Fi 
 leTxnSnapLog.java:153)                                                   
     at org.apache.zookeeper.server.ZKDatabase.loadDataBase(ZKDatabase.ja 
 va:223)                                                                  
     at org.apache.zookeeper.server.ZooKeeperServer.loadData(ZooKeeperSer 
 ver.java:250)                                                            
     at org.apache.zookeeper.server.ZooKeeperServer.startdata(ZooKeeperSe 
 rver.java:377)                                                           
     at org.apache.zookeeper.server.NIOServerCnxnFactory.startup(NIOServe 
 rCnxnFactory.java:122)                                                   
     at org.apache.zookeeper.server.ZooKeeperServerMain.runFromConfig(Zoo 
 KeeperServerMain.java:112)                                               
     at org.apache.hadoop.hbase.zookeeper.HQuorumPeer.runZKServer(HQuorum 
 Peer.java:85)                                                            
     at org.apache.hadoop.hbase.zookeeper.HQuorumPeer.main(HQuorumPeer.ja 
 va:70)                                                                   
 Caused by: org.apache.zookeeper.KeeperException$NoNodeException: KeeperE 
 rrorCode = NoNode for /hbase                                             
     at org.apache.zookeeper.server.persistence.FileTxnSnapLog.processTra 
 nsaction(FileTxnSnapLog.java:211)                                        
     at org.apache.zookeeper.server.persistence.FileTxnSnapLog.restore(Fi 
 leTxnSnapLog.java:151)                                                   
     ... 7 more                                                           
```
Then execute the following steps to fix the issue:

> Note: If your ATSD installation has replication setup [according to our
guide](atsd-replication.md "ATSD Replication"),
be sure to execute the `add_peer` commands on the master machine again
to restart replication after you have restored the corrupted zookeeper
using this guide.*

Stop HBase:

```sh
 /opt/atsd/hbase/bin/stop-hbase.sh                                        
```

Make sure that hbase has stopped:

```sh
 ps -ef | grep '[h]base'
```

If there is an output, kill processes:

```sh
 ps -ef | grep '[h]base' | awk '{print $2}' | xargs kill
```

Make sure that HBase has stopped:

```sh
 ps -ef | grep '[h]base'
```

If there is still an output, kill processes with flag –9:

```sh
 ps -ef | grep '[h]base' | awk '{print $2}' | xargs kill -9
```

Make sure that HBase has stopped:

```sh
 ps -ef | grep '[h]base'
```

Determine folder that contains zookeeper temporary files. This folder is
specified in `/opt/atsd/hbase/conf/hbase-site.xml` . Property has the
name `hbase.zookeeper.property.dataDir`. For example:

```sh
 /opt/atsd/hbase/zookeeper                                                
```

Remove this folder:

```sh
 rm -r  /opt/atsd/hbase/zookeeper                                         
```

Create a new folder with the same name:

```sh
 mkdir /opt/atsd/hbase/zookeeper                                          
```

Start HBase:

```sh
 /opt/atsd/hbase/bin/start-hbase.sh                                       
```

Check HBase shell:

```sh
 /opt/atsd/hbase/bin/hbase shell                                          
```

The console should start, execute the `list` command:

```sh
 list                                                                     
```

The output should be a list of tables, close the console:

```sh
 exit                                                                     
```

Start ATSD:

```sh
 /opt/atsd/atsd/bin/start-atsd.sh
```