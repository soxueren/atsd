# SUSE Linux Enterprise Server: Offline

## Overview

The installation process involves downloading dependencies to an intermediate machine with Internet access
and copying them to the target machine with similar characteristics for offline installation. 

## Supported Versions

- SUSE Linux Enterprise Server 11 SP3
- SUSE Linux Enterprise Server 12.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Install Dependencies

### Option 1: Install dependencies from local repositories.

```sh
sudo zypper -n install java-1_7_0-openjdk-devel sysstat which
```

### Option 2: Copy dependencies from a connected machine.

On a separate machine with internet access create a directory containing the dependencies:

```sh
mkdir ./sles_dependencies
cd sles_dependencies
```

Copy the following script to `dep-download.sh`file and execute it to download the dependencies:

```sh
nano dep-download.sh
```

```sh
#!/bin/sh
SCRIPT=$(readlink -f $0)
DIR="`dirname $SCRIPT`"
list="java-1_7_0-openjdk-devel sysstat which"
zypper -n install -df $list
for package in $list; do
    find /var/cache/zypp/packages -name $package*.rpm -exec cp {} $DIR \;
done
```

```sh
chmod a+x dep-download.sh
sudo ./dep-download.sh
```

Copy the folder containing the dependencies to the target machine and install them:

```sh
sudo zypper -n install sles_dependencies/*
```

## Install ATSD

Download ATSD rpm package to the target machine:

* `curl -O https://axibase.com/public/atsd_ce_amd64.rpm`
* [https://axibase.com/public/atsd_ce_rpm_latest.htm](https://axibase.com/public/atsd_ce_rpm_latest.htm)

Follow the prompts to install ATSD:

```sh
 sudo rpm -i atsd_ce_amd64.rpm                                  
```

It may take up to 5 minutes to initialize the database.

## Check Installation

```sh
tail -f /opt/atsd/atsd/logs/start.log                                   
```

You should see **ATSD start completed** message at the end of the start.log.

Web interface is accessible on port 8088 (http) and 8443 (https).

## Troubleshooting

Review the following log files for errors:

* Startup log: `/opt/atsd/atsd/logs/start.log`
* Application log: `/opt/atsd/atsd/logs/atsd.log`

## Optional Steps

- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)
