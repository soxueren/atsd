# Installation on Distributed HBase Cluster using Cloudera Manager

## Create `axibase` user

```
sudo adduser axibase
```

## Install Java

Install Oracle JDK or Open JDK on the server where ATSD will be running.

### Oracle JDK Installation

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

### Open JDK Installation

* RHEL/CentOS

```
sudo yum install java-1.7.0-openjdk-devel.x86_64
```

* Ubuntu/Debian

```
sudo apt-get update
sudo apt-get install openjdk-7-jdk
```

### Verify Java Installation

```
java -version
java version "1.7.0_101"
OpenJDK Runtime Environment (rhel-2.6.6.1.el7_2-x86_64 u101-b00)
OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)
```

Add `JAVA_HOME` path to the `axibase` user environment in `.bashrc`.

```
sudo su axibase
echo "export JAVA_HOME=${absolute path to JDK 7 home directory}" >> ~/.bashrc
exit
```

## Verify Zookeeper Connectivity

Check connection from the ATSD server to the Zookeeper service.

```
telnet zookeeper-host 2181
Trying 10.102.0.6...
Connected to zookeeper-host.
Escape character is '^]'.
```

The Zookeeper client port is specified in:

* Zookeeper host: `/usr/hdp/{hdp_version}/etc/zookeeper/conf.dist/zoo.cfg` > `clientPort` setting
* HBase host: `/usr/hdp/{hdp_version}/hbase/conf/hbase-site.xml` > `hbase.zookeeper.property.clientPort` setting

## Download ATSD EE

### HDP (Horton Data Platform) 2.3.x

```
curl -O https://www.axibase.com/public/atsd_ee_hbase_1.2.2.tar.gz
```

## Extract Files

```
sudo tar -xzvf atsd_ee_hbase_1.2.2.tar.gz -C /opt
sudo chown -R axibase:axibase /opt/atsd
```

## Configure HBase Connection

Open `hadoop.properties` file.

```
nano /opt/atsd/atsd/conf/hadoop.properties
```

Set `hbase.zookeeper.quorum` to Zookeeper hostname `zookeeper-host`

If Zookeeper client port is different from 2181, set `hbase.zookeeper.property.clientPort` accordingly.

Set Zookeeper Znode parent accordingly to `HBase host` > `/usr/hdp/{hdp_version}/hbase/conf/hbase-site.xml` > `zookeeper.znode.parent`

```
hbase.zookeeper.quorum = zookeeper-host
hbase.zookeeper.property.clientPort = 2181
zookeeper.znode.parent = /hbase-unsecure
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
```

## Request License Key

Remote HBase connection requires a license key. 

To obtain the license key for the given ATSD server, contact Axibase support with the following information from the machine where ATSD will be installed:

```
ip addr
```

```
hostname
```

Email output of the above commands to Axibase support and copy the provided key to `/opt/atsd/atsd/conf/license/key.properties`.

## Configure HBase Region Servers

### Deploy ATSD coprocessors 

Copy `/opt/atsd/hbase/lib/atsd.jar` to `/usr/lib/hbase/lib` directory on each HBase region server.

### Enable ATSD Coprocessors

Open Ambari Services, select the target HBase, open Configs tab, search for setting `hbase.coprocessor.region.classes` and enter the following names separated by comma. 

* com.axibase.tsd.hbase.coprocessor.CompactRawDataEndpoint
* com.axibase.tsd.hbase.coprocessor.DeleteDataEndpoint

![](images/ambari-manager-coprocessor-config.png)

### Restart HBase Region Servers

## Check for port conflicts

```
sudo netstat -tulpn | grep "8081\|8082\|8084\|8088\|8443"
```

If some of the above ports are taken, open `/opt/atsd/atsd/conf/server.properties` file and change ATSD listening ports accordingly.

```
http.port = 8088
input.port = 8081
udp.input.port = 8082
pickle.port = 8084
https.port = 8443
```

## Start ATSD

```
/opt/atsd/atsd/bin/start-atsd.sh
```

Review the start log for any errors:

```
tail -f /opt/atsd/atsd/logs/atsd.log
```

## Enable ATSD Autostart

To configure ATSD for automated restart on server reboot, add the following line to `/etc/rc.local` before `return 0` line.

```
su - axibase -c /opt/atsd/atsd/bin/start-atsd.sh
```
