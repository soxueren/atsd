# Installation on Distributed HBase Cluster using Cloudera Manager

## Create `axibase` user

Create `axibase` user on the server where ATSD will be running.

```
sudo adduser axibase
```

## Install Java

Install Oracle JDK or Open JDK.

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

* Zookeeper host: `/etc/zookeeper/conf.dist/zoo.cfg` > `clientPort` setting
* HBase host: `/etc/hbase/conf.dist/hbase-site.xml` > `hbase.zookeeper.property.clientPort` setting

## Download ATSD EE

### CDH (Cloudera Distribution Hadoop) 5.5.x

```
curl -O https://www.axibase.com/public/atsd_ee_hbase_1.0.3.tar.gz
```

### CDH (Cloudera Distribution Hadoop) 5.6.x+

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

If Zookeeper Znode parent is not `/hbase`, set `zookeeper.znode.parent` to the actual value.

```elm
hbase.zookeeper.quorum = zookeeper-host
hbase.zookeeper.property.clientPort = 2181
zookeeper.znode.parent = /hbase
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
```

## Kerberos Authentication

ATSD can be enabled for Kerberos authentication with Zookeeper and Hadoop services by following these steps.

### Copy `keytab` file

* Locate an existing `keytab` file on an HBase master/region server.

```
find / -name "*.keytab" | xargs ls -la

-rw------- 1 hbase        hbase        448 Jul 29 16:44 /var/run/cloudera-scm-agent/process/30-hbase-MASTER/hbase.keytab
```

* Alternatively, create a new `keytab` file using [ktutil](https://kb.iu.edu/d/aumh#create) utility.

Copy the `keytab` file to `/opt/atsd/atsd/conf` directory on the ATSD server.

### Kerberos Settings

Add Kerberos principal and `keytab` path settings to `/opt/atsd/atsd/conf/server.properties` in ATSD:

```elm
# Kerberos principal, identified with username (hbase) and realm (CLOUDERA).
# Note that the login is specified without /_HOST placeholder.
kerberos.login=hbase@CLOUDERA
# Absolute path to Kerberos keytab file, containing encrypted key for the above principal.
kerberos.keytab.path=/opt/atsd/atsd/conf/hbase.keytab
```

> The `keytab` file needs to be updated whenever the password is changed.

> For added security, ensure that `keytab` file has 400 permission (read by owner).

### Create `hbase-site.xml` file

Create `hbase-site.xml` file in `/opt/atsd/atsd/conf` directory. Update `keytab` file paths to actual locations.

```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
  <property>
    <name>hbase.master.kerberos.principal</name>
    <value>hbase/_HOST@CLOUDERA</value>
  </property>
   <property>
	<name>hbase.master.keytab.file</name>
	<value>/opt/atsd/atsd/conf/hbase.keytab</value>
   </property>    
  <property>
    <name>hbase.regionserver.kerberos.principal</name>
    <value>hbase/_HOST@CLOUDERA</value>
  </property>
   <property>
	<name>hbase.regionserver.keytab.file</name>
	<value>/opt/atsd/atsd/conf/hbase.keytab</value>
   </property>  
</configuration>
```

### Debugging Kerberos

Debugging for Kerberos authentication can be enabled by adding `-Dsun.security.krb5.debug=true` option in `/opt/atsd/bin/atsd-start.sh`.

Debug output will be redirected to `/opt/atsd/atsd/logs/error.log`

```
8893 [main] WARN  o.a.hadoop.util.NativeCodeLoader - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
9049 [main] INFO  com.axibase.tsd.hbase.KerberosBean - Login user from keytab starting...
Java config name: null
Native config name: /etc/krb5.conf
Loaded from native config
>>> KdcAccessibility: reset
>>> KeyTabInputStream, readName(): CLOUDERA
...
>>> EType: sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType
>>> KrbAsRep cons in KrbAsReq.getReply hbase/host01
5569 [main] INFO  o.a.h.security.UserGroupInformation - Login successful for user hbase/host01@CLOUDERA using keytab file /opt/atsd/atsd/conf/aws.keytab 
5570 [main] INFO  com.axibase.tsd.hbase.KerberosBean - Login user from keytab successful 
```

## Request License Key

To obtain a license key, contact Axibase support with the following information from the machine where ATSD will be installed.

* Output of `ip addr` command.

```
[axibase@NURSWGVML007 ~]$ ip addr
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 16436 qdisc noqueue state UNKNOWN
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP qlen 1000
    link/ether 00:50:56:b9:35:31 brd ff:ff:ff:ff:ff:ff
    inet 10.102.0.6/24 brd 10.102.0.255 scope global eth1
    inet6 2a01:4f8:140:53c6::7/64 scope global
       valid_lft forever preferred_lft forever
    inet6 fe80::250:56ff:feb9:3531/64 scope link
       valid_lft forever preferred_lft forever
```

* Output of `hostname` command.

```
[axibase@NURSWGVML007 ~]$ hostname
NURSWGVML007
```

Email output of the above commands to Axibase support and copy the provided key to `/opt/atsd/atsd/conf/license/key.properties`.

## Configure HBase Region Servers

### Deploy ATSD coprocessors 

Copy `/opt/atsd/hbase/lib/atsd.jar` to `/usr/lib/hbase/lib` directory on each HBase region server.

### Enable ATSD Coprocessors

Open Cloudera Manager, select the target HBase cluster/service, open Configuration tab, search for setting `hbase.coprocessor.region.classes` and enter the following names. 

* com.axibase.tsd.hbase.coprocessor.CompactRawDataEndpoint
* com.axibase.tsd.hbase.coprocessor.DeleteDataEndpoint

![](images/cloudera-manager-coprocessor-config.png)

### Increase Maximum Heap Size on Region Servers

![](images/cdh-region-heap.png)

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

You should see **ATSD start completed** message at the end of the start.log.

Web interface is accessible on port 8088 (http) and 8443 (https).

## Enable ATSD Autostart

To configure ATSD for automated restart on server reboot, add the following line to `/etc/rc.local` before `return 0` line.

```
su - axibase -c /opt/atsd/atsd/bin/start-atsd.sh
```

## Troubleshooting

* Review [troubleshooting guide](troubleshooting.md).

## Validation

* [Verify database installation](verifying-installation.md).

## Post-installation Steps

* [Basic configuration](post-installation.md).
* [Getting Started guide](/tutorials/getting-started.md).
