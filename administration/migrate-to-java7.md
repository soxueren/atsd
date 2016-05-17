# Migrate ATSD to JAVA 7


**This migration procedure from Java 6 to Java 7 applies to Axibase Time
Series Database revision 11938 and earlier.**

#### Install openjdk-7-jdk

```sh
sudo apt-get install -y openjdk-7-jdk      
```

#### Manual Configuration

Stop ATSD processes

```sh
/opt/atsd/bin/atsd-all.sh stop
```

Modify old `java_home` (for example, `/usr/lib/jvm/java-1.6.0-openjdk-amd64`) to the new `java_home`
(`/usr/lib/jvm/java-1.7.0-openjdk-amd64`) in the following files:

```sh
 /home/axibase/.bashrc                                                    
 /opt/atsd/hadoop/conf/hadoop-env.sh                                      
 /opt/atsd/bin/update.sh                                                  
 /opt/atsd/bin/atsd-hbase.sh                                              
 /opt/atsd/bin/atsd-dfs.sh                                                
 /opt/atsd/hbase/conf/hbase-env.sh                                        
```

Set new `JAVA_HOME` variable

```sh
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-amd64
```

Start ATSD

```sh
/opt/atsd/bin/atsd-all.sh start
```

#### Automated Configuration

Stop ATSD

```sh
/opt/atsd/bin/atsd-all.sh stop
```

Replace old `java_home` with new `java_home` in each file

```sh
 $: printf "/home/axibase/.bashrc\n/opt/atsd/hadoop/conf/hadoop-env.sh\n\
 /opt/atsd/bin/update.sh\n/opt/atsd/bin/atsd-hbase.sh\n/opt/atsd/bin/atsd-dfs.sh\n/opt/atsd/hbase/conf/hbase-env.sh\n"\
 xargs sed -i 's,/usr/lib/jvm/java-1.6.0-openjdk-amd64,/usr/lib/jvm/java-1.7.0-openjdk-amd64,g'    
```

Apply changes to your environment variables

```sh
source /home/axibase/.bashrc
```

Start ATSD

```sh
/opt/atsd/bin/atsd-all.sh start
```
