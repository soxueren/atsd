# Manual update


#### Login with the Axibase user

```sh
 su axibase                                                               
```

#### Download the latest ATSD update file

**[atsd\_latest.tar.gz](http://axibase.com/public/atsd_ce_update_latest.htm "ATSD Update")**

The download will contain an archive with the latest ATSD release. For
example: atsd\_9972.tar.gz

#### Unpack the archive

```sh
 tar xzf atsd_9972.tar.gz                                                 
```

```sh
 cd target                                                                
 ls                                                                       
```

#### View downloaded jar files

```sh
 atsd-executable.jar                                                      
 atsd.jar                                                                 
```

#### Stop ATSD

```sh
 /opt/atsd/bin/atsd-all.sh stop                                           
```

#### Backup previous versions

```sh
 cp /opt/atsd/hbase/lib/atsd.jar /opt/atsd/hbase/lib/atsd.jar_old         
```

```sh
 cp /opt/atsd/atsd/bin/atsd-executable.jar /opt/atsd/atsd/bin/atsd-execut 
 able.jar_old                                                             
```

#### Copy new versions

```sh
 cp atsd.jar /opt/atsd/hbase/lib                                          
```

```sh
 cp atsd-executable.jar /opt/atsd/atsd/bin                                
```

#### Start ATSD

```sh
 /opt/atsd/bin/atsd-all.sh start                                          
```

#### Navigate to the ATSD user interface in your browser

```sh
 http://"ip or hostname of ATSD_hostname":8088/                             
```

Open Admin -\> Build Info page and verify that the Revision Number is the same as the revision number of the installed ATSD update.

![](images/ATSD_build_info.png "ATSD_build_info")
