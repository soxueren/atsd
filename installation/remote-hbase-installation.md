# Remote HBase Installation

## Verify HBase Connectivity

### Zookeeper

Login into Zookeeper server and verify that it's listening on the default Zookeeper port 2181.

```
user@zookeeper-host:~$ netstat -tulpn | grep 2181
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name   
tcp6       0      0 :::2181                 :::*                    LISTEN      18939/java          
```

On the ATSD server, open connection to Zookeeper server on port 2181 to make sure it's reachable.

```
axibase@atsd:~$telnet zookeeper-host 2181
Trying 10.102.0.6...
Connected to zookeeper-host.
Escape character is '^]'.
```

### HBase Master

Identify the HBase Master and verify that ATSD is able to connect to its services.

```
axibase@atsd:~$telnet hbase-master-host 60000
Trying 10.102.0.8...
Connected to hbase-master-host.
Escape character is '^]'.
```

## Install Java

### Install Java 7 JDK on the ATSD server

You can install either Oracle JDK or Open JDK.

#### Oracle JDK Installation

http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

#### Open JDK Installation

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

If ATSD cannot locate the required JDK 7, add it explicitly to the axibase user environment in `.bashrc`.

```
axibase@atsd:~$ echo "export JAVA_HOME=${absolute path to JDK 7 home directory}" >> ~/.bashrc
axibase@atsd:~$ source ~/.bashrc
```


## Download ATSD Distribution

```
axibase@atsd:~$ curl -O https://www.axibase.com/public/atsd_ce.tar.gz
axibase@atsd:~$ sudo tar -xzvf atsd_ce.tar.gz -C /opt
axibase@atsd:~$ sudo chown -R axibase:axibase /opt/atsd
```

### Remove Unused Files

Copy ATSD library jar file to /tmp directory for subsequent distribution to HBase servers.

```
axibase@atsd:~$ cp /opt/atsd/hbase/lib/atsd.jar /tmp/atsd.jar
```

Delete files that are not necessary for ATSD connected to a remote HBase cluster.

```
axibase@atsd:~$ mv /opt/atsd/atsd /tmp/atsd
axibase@atsd:~$ rm -rf /opt/atsd/*
axibase@atsd:~$ mv /tmp/atsd /opt/atsd/
```

## Configure HBase Connection

Open `hadoop.properties` file.

```
axibase@atsd:~$ nano /opt/atsd/atsd/conf/hadoop.properties
```

Replace `localhost` with Zookeeper server hostname `zookeeper-host` in `hbase.zookeeper.quorum` setting.

```
hbase.zookeeper.quorum = zookeeper-host
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
hbase.regionserver.lease.period = 120000
```

If Zookeeper service is listening on a non-default port other than 2181, add `hbase.zookeeper.property.clientPort` setting to `hadoop.properties` file.

```
hbase.zookeeper.quorum = zookeeper-host
hbase.zookeeper.property.clientPort = 2182
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
hbase.regionserver.lease.period = 120000
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

Email output of the above commands to Axibase support and once you receive the key, copy it to `/opt/atsd/atsd/conf/license/key.properties`.

## Configure HBase Region Servers

### Copy Library File

Copy `/tmp/atsd.jar` to `{HBASE_HOME}/lib/` on each HBase region server.

### Enable Co-processors

Add the following `property` to `{HBASE_HOME}/conf/hbase-site.xml`.

```
<property>
    <name>hbase.coprocessor.region.classes</name>
    <value>com.axibase.tsd.hbase.coprocessor.DeleteDataEndpoint, com.axibase.tsd.hbase.coprocessor.CompactRawDataEndpoint, org.apache.hadoop.hbase.coprocessor.example.BulkDeleteEndpoint</value>
</property>
```

### Restart HBase Region Servers

## Start ATSD

```
axibase@atsd:~$ /opt/atsd/atsd/bin/start-atsd.sh
```

Review the start log for any errors:

```
axibase@atsd:~$ tail -f /opt/atsd/atsd/logs/atsd.log
```

## Autostart

To configure ATSD for automated restart on server reboot, add the following line to `/etc/rc.local` before `return 0` line.

```
su - axibase -c /opt/atsd/atsd/bin/start-atsd.sh
```
