# Installation on Distributed HBase Cluster using Cloudera Manager

## Install Java

Install Oracle JDK or Open JDK on the server where ATSD will be running.

### Oracle JDK Installation

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

### Open JDK Installation

* RHEL/CentOS

```
axibase@atsd:~$ sudo yum install java-1.7.0-openjdk-devel.x86_64
```

* Ubuntu/Debian

```
axibase@atsd:~$ sudo apt-get update
axibase@atsd:~$ sudo apt-get install openjdk-7-jdk
```

### Verify Java Installation

```
axibase@atsd:~$ java -version
java version "1.7.0_101"
OpenJDK Runtime Environment (rhel-2.6.6.1.el7_2-x86_64 u101-b00)
OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)
```

Add `JAVA_HOME` path to the `axibase` user environment in `.bashrc`.

```
axibase@atsd:~$ echo "export JAVA_HOME=${absolute path to JDK 7 home directory}" >> ~/.bashrc
axibase@atsd:~$ source ~/.bashrc
```

## Verify Zookeeper Connectivity

Check connection from the ATSD server to the Zookeeper service.

```
axibase@atsd:~$telnet zookeeper-host 2181
Trying 10.102.0.6...
Connected to zookeeper-host.
Escape character is '^]'.
```

The Zookeeper client port is specified in:

* Zookeeper host: `/etc/zookeeper/conf.dist/zoo.cfg` > `clientPort` setting
* HBase host: `/etc/hbase/conf.dist/hbase-site.xml` > `hbase.zookeeper.property.clientPort` setting

## Download ATSD EE

### CDH (Cloudera Distribution Hadoop) 5.5.x

```
axibase@atsd:~$ curl -O https://www.axibase.com/public/atsd_ee_hbase_1.0.3.tar.gz
```

### CDH (Cloudera Distribution Hadoop) 5.6.x+

```
axibase@atsd:~$ curl -O https://www.axibase.com/public/atsd_ee_hbase_1.2.2.tar.gz
```

## Extract Files

```
axibase@atsd:~$ sudo tar -xzvf atsd_ee_hbase_1.2.2.tar.gz -C /opt
axibase@atsd:~$ sudo chown -R axibase:axibase /opt/atsd
```

## Configure HBase Connection

Open `hadoop.properties` file.

```
axibase@atsd:~$ nano /opt/atsd/atsd/conf/hadoop.properties
```

Set `hbase.zookeeper.quorum` to Zookeeper hostname `zookeeper-host`

If Zookeeper client port is different from 2181, set `hbase.zookeeper.property.clientPort` accordingly.

If Zookeeper Znode parent is not `/hbase`, set `zookeeper.znode.parent` to the actual value.

```
hbase.zookeeper.quorum = zookeeper-host
hbase.zookeeper.property.clientPort = 2181
zookeeper.znode.parent = /hbase
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
```

## Request License Key

Remote HBase connection requires a license key. 

To obtain the license key for the given ATSD server, contact Axibase support with the following information from the machine where ATSD will be installed:

* MAC Address

```
axibase@atsd:~$ ip addr
```

* Hostname

```
axibase@atsd:~$ hostname
```

Email output of the above commands to Axibase support and copy the provided key to `/opt/atsd/atsd/conf/license/key.properties`.

## Configure HBase Region Servers

### Deploy ATSD coprocessors 

Copy `/opt/atsd/hbase/lib/atsd.jar` to `/usr/lib/hbase/lib` directory on each HBase region server.

### Enable Co-processors

Add the following `property` to `/etc/hbase/conf/hbase-site.xml`.

```
<property>
    <name>hbase.coprocessor.region.classes</name>
    <value>com.axibase.tsd.hbase.coprocessor.DeleteDataEndpoint, com.axibase.tsd.hbase.coprocessor.CompactRawDataEndpoint</value>
</property>
```

Alternatively, open Cloudera Manager, select the target cluster, HBase service, open Configuration tab, search for HBase Coprocessor Region Classes setting (`hbase.coprocessor.region.classes`) and enter the following Coprocessor names. 

* com.axibase.tsd.hbase.coprocessor.DeleteDataEndpoint
* com.axibase.tsd.hbase.coprocessor.CompactRawDataEndpoint

![](images/cloudera-manager-coprocessor-config.png)

### Restart HBase Region Servers

## Start ATSD

```
axibase@atsd:~$ /opt/atsd/atsd/bin/start-atsd.sh
```

Review the start log for any errors:

```
axibase@atsd:~$ tail -f /opt/atsd/atsd/logs/atsd.log
```

## Enable ATSD Autostart

To configure ATSD for automated restart on server reboot, add the following line to `/etc/rc.local` before `return 0` line.

```
su - axibase -c /opt/atsd/atsd/bin/start-atsd.sh
```
