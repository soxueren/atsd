# Installation on AWS EMR S3

## Overview

Axibase Time Series Database can be installed on top of HBase using AWS EMRFS on s3 as the underlying file system.
This option enables scaling the database cluster based on compute demands as opposed to storage requirements.

## Requirements

* The system can be installed on a minumum of 2 EC2 instances with ATSD co-installed on the HMaster node.


## Create Cluster Configuration File

The configuration specifies the HBase root directory, the storage mode (s3), and the consistency mode. 

```sh
nano emr-atsd.json
```

```json
[
   {
     "Classification": "hbase-site",
     "Properties": {
       "hbase.rootdir": "s3://atsd/hbase-root"
     }
   },
   {
     "Classification": "hbase",
     "Properties": {
       "hbase.emr.storageMode": "s3"
     }
   },
   {
     "Classification": "emrfs-site",
     "Properties": {
       "fs.s3.consistent": "true"
     }
   }
]
```


## Create s3 Bucket

The bucket, in this case named `atsd`, will contain the HBase root directory.

```sh
aws s3 mb s3://atsd
```

If the bucket already exists, check its contents:

```sh
aws s3 ls --summarize --human-readable --recursive s3://atsd
```

## Copy ATSD Co-processor Jar File to s3

Download the ATSD co-processor and filter library for HBase.

```sh
curl -o atsd-hbase.17180.jar https://axibase.com/public/atsd-hbase.17180.jar
```

Copy the file to s3.

```sh
aws s3 cp atsd-hbase.17180.jar s3://atsd/hbase-root/lib/atsd-hbase.jar
```

The file will be placed into the `hbase.rootdir/lib` directory based on the `emr-atsd.json` file. 

This is the same directory as `hbase.dynamic.jars.dir` configured in HBase.

This type of deployment allows HBase region servers to automatically load ATSD filter and co-processor classes when they're started.

Note that the revision should be removed from the file name so that co-processor definitions in HBase table descriptors are valid when the jar file is replaced with a newer version.

Check the the file is in s3:

```
aws s3 ls --summarize --human-readable --recursive s3://atsd
...
2017-08-26 01:39:01  534.8 KiB hbase-root/lib/atsd-hbase.jar
Total Objects: 1
   Total Size: 534.8 KiB
```

## Launch Cluster

Replace `<key-name>`, `<subnet>`, `<masterSg>`, `<slaveSg>` parameters accordingly.

```sh
export CLUSTER_ID=$(                                                           \
aws emr create-cluster                                                         \
--name "ATSD HBase"                                                            \
--applications Name=HBase                                                      \
--release-label emr-5.3.1                                                      \
--output text                                                                  \
--use-default-roles                                                            \
--ec2-attributes KeyName=<key-name>,SubnetId=<subnet>,EmrManagedMasterSecurityGroup=sg-<masterSg>,EmrManagedSlaveSecurityGroup=sg-<slaveSg>  \
--instance-groups                                                              \
  Name=Master,InstanceCount=1,InstanceGroupType=MASTER,InstanceType=m4.large   \
  Name=RegionServers,InstanceCount=1,InstanceGroupType=CORE,InstanceType=m4.large   \
--configurations file://some-path/emr-atsd.json                                \
)
```

Monitor the cluster status until the bootstrapping process is complete.

```sh
watch 'aws emr describe-cluster --cluster-id $CLUSTER_ID | grep MasterPublic | cut -d "\"" -f 4'
```

Determine the public IP address of the HMaster node.

```
export MASTER_IP=$(aws emr describe-cluster --cluster-id $CLUSTER_ID | grep MasterPublic | cut -d "\"" -f 4)
```

Specify the path to provate ssh key and login into HMaster node.

```sh
ssh -i /path-to-key-for-<key-name> hadoop@$MASTER_IP
```

## Configure ATSD

Create a setup file.

```sh
nano atsd-setup.sh
```

The script will execute the following steps:

* Download ATSD distribution files.
* Unpack files in the `/mnt/atsd` directory.
* Change default port numbers from 8xxx to 9xxx.
* Set table copression to Gzip.

```
curl -o atsd.tar.gz https://axibase.com/public/atsd_ee_distr.17180.tar.gz
tar -xvf atsd.tar.gz -C /mnt
rm atsd.tar.gz
cd /mnt/atsd
rm ./atsd/bin/atsd*.jar
rm atsd-hbase*.jar
curl -o ./atsd/bin/atsd.17180.jar  https://axibase.com/public/atsd.17180.jar
JP=`dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"`; sed -i "s,^export JAVA_HOME=.*,export JAVA_HOME=$JP,g" ./atsd/bin/start-atsd.sh
cat ./atsd/bin/start-atsd.sh | grep "export JAVA_HOME"
echo "coprocessors.jar=s3://atsd/hbase-root/lib/atsd-hbase.jar" >> ./atsd/conf/server.properties
sed -i 's/80/90/g' ./atsd/conf/server.properties
sed -i 's/8443/9443/g' ./atsd/conf/server.properties
sed -i 's/hbase.compression.type = NONE/hbase.compression.type = gz/g' ./atsd/conf/server.properties
```

Execute the setup script:

```sh
chmod a+x atsd-setup.sh ; ./atsd-setup.sh
```

## Start ATSD

Verify that HBase services are running.

```sh
initctl list | grep hbase
```

Verify HBase version (must be 1.2.3+) and status.

```sh
echo "status" | hbase shell
```

Start ATSD process.

```sh
/mnt/atsd/bin/start-atsd.sh
```

Monitor the startup progress using the log file.

```
tail -f /mnt/atsd/logs/atsd.log
```

The startup process may take several minutes to complete. 

Login to https://master_ip_address:9443






