# Migrate ATSD to JAVA 7

## Overview

This migration procedure from Java 6 to Java 7 applies to Axibase Time
Series Database revision 11938 and earlier.

## Install openjdk-7-jdk

```sh
sudo apt-get install -y openjdk-7-jdk      
```

Switch to axibase user

```
su axibase
```

## Stop ATSD

```sh
/opt/atsd/bin/atsd-all.sh stop
```

## Replace JAVA_HOME

### Manual Option

Change `JAVA_HOME` variable from old path `/usr/lib/jvm/java-1.6.0-openjdk-amd64` to new path 
`/usr/lib/jvm/java-1.7.0-openjdk-amd64` in the following files:

```sh
 /home/axibase/.bashrc                                                    
 /opt/atsd/hadoop/conf/hadoop-env.sh                                      
 /opt/atsd/bin/update.sh                                                  
 /opt/atsd/bin/atsd-hbase.sh                                              
 /opt/atsd/bin/atsd-dfs.sh                                                
 /opt/atsd/hbase/conf/hbase-env.sh                                        
```

### Scripted Option

Change `JAVA_HOME` variable in the following files with `sed`

```sh
$: printf "/home/axibase/.bashrc\n/opt/atsd/hadoop/conf/hadoop-env.sh\n\
/opt/atsd/bin/update.sh\n/opt/atsd/bin/atsd-hbase.sh\n/opt/atsd/bin/atsd-dfs.sh\n\
/opt/atsd/hbase/conf/hbase-env.sh\n" |
xargs sed -i 's,/usr/lib/jvm/java-1.6.0-openjdk-amd64,/usr/lib/jvm/java-1.7.0-openjdk-amd64,g'    
```

## Reload Environment Variables

```sh
source /home/axibase/.bashrc
```

## Start ATSD

```sh
/opt/atsd/bin/atsd-all.sh start
```
