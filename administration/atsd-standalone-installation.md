### Make sure *hbase-host* is listening on external ip-address
```
axibase@hbasehost:~$ netstat -tulpn
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
tcp6       0      0 10.102.0.53:60020     :::*                    LISTEN      19101/java        
tcp6       0      0 :::60030                :::*                    LISTEN      19101/java      
tcp6       0      0 10.102.0.53:60000     :::*                    LISTEN      19013/java      
tcp6       0      0 :::2181                 :::*                    LISTEN      18939/java      
tcp6       0      0 :::60010                :::*                    LISTEN      19013/java      
```

### Java-7 should be installed (oracle jdk or openjdk-7-jdk)

For RHEL\CentOS

```
axibase@base:~$ sudo yum install java-1.7.0-openjdk.x86_64
```

For Ubuntu\Debian

```
axibase@base:~$ sudo apt-get update
axibase@base:~$ sudo apt-get install openjdk-7-jdk
```

To be sure, run the following command:

```
axibase@base:~$ java -version
java version "1.7.0_101"
OpenJDK Runtime Environment (rhel-2.6.6.1.el7_2-x86_64 u101-b00)
OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)

```
### Download & Extract ATSD:

```
axibase@base:~$ curl -O https://www.axibase.com/public/atsd_ce.tar.gz
axibase@base:~$ tar -xzvf atsd_ce.tar.gz
```

### Leave only ATSD-distributive

```
axibase@base:~$ mv atsd/atsd atsd_standalone
axibase@base:~$ rm -rf atsd/
```



### Make sure **hbase-host** can be resolved by it's *hostname*:

```
axibase@base:~$ ping hbasehost
PING hbasehost (10.102.0.53) 56(84) bytes of data.
64 bytes from hbasehost (10.102.0.53): icmp_seq=1 ttl=64 time=0.471 ms
```

To provide it, we able to add *hbase-hostname* to **/etc/hosts** :

```
axibase@base:~$ cat /etc/hosts
127.0.0.1	localhost
127.0.1.1	base
10.102.0.53	hbasehost
```

### Set *hbase-hostname* to **atsd_standalone/conf/hadoop.properties**, for example:

```
axibase@base:~$ cat atsd_standalone/conf/hadoop.properties
hbase.zookeeper.quorum = hbasehost
hbase.rpc.timeout = 120000 
hbase.client.scanner.timeout.period = 120000
hbase.regionserver.lease.period = 120000
```

If **zookeeper quorum** is listening on non-default port, you can specify it by the following property:

```
hbase.zookeeper.property.clientPort = 2182
```

### Set permission to **jmx.password** file:

```
sudo chmod 600 atsd_standalone/conf/jmx.password
```

### Activate ATSD

ATSD should be activated by some license. To get license key, we should know **mac-address** and **hostname** of machine, which will be used to host the ATSD.

To get mac-address:

```
axibase@base:~$ ip addr
```

To get hostname:

```
axibase@base:~$ hostname
```

Put received license key to **atsd_standalone/conf/license/key.properties**

### Start ATSD:

```
axibase@base:~$ atsd_standalone/bin/start-atsd.sh
```

To monitor the start process:

```
axibase@base:~$ tail -f atsd_standalone/logs/atsd.log
```

### Autostart

To get autostart of ATSD add the following line to **/etc/rc.local**:

```
su - axibase -c /path/to/distrib/atsd_standalone/bin/start-atsd.sh
```
