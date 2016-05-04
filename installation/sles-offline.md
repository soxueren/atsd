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
sysstat sed iproute net-tools openssh cron pwdutils
```

### Option 2: Install dependencies from an archive.

On a separate machine with internet access download the provided archive containing all the dependencies:

```sh
curl -O https://axibase.com/public/sles_dependencies.tar.gz
```

Copy the archive containing the dependencies to target machine, unpack and then install the dependencies:

```sh
tar xzf sles_dependencies.tar.gz
cd sles_dependencies
sudo zypper install *
```

### Option 3: Install dependencies using a script.

On a separate machine with internet access download the dependencies using the provided script (dependencies will be downloaded to the same folder where the script is located):

```sh
mkdir ./sles_dependencies
cd sles_dependencies
```

```sh
curl -O https://axibase.com/public/zypper_download.tar.gz
tar xzf zypper_download.tar.gz
sudo ./zypper_download.sh
```

Copy the folder containing the dependencies to target machine and install them:

```sh
sudo zypper install sles_dependencies/*
```

## Install Java

Download and install [Oracle Java 7 JDK rpm package](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

```sh
 sudo rpm -i jdk-7u80-linux-x64.rpm
```

## Install ATSD

Download ATSD rpm package:

* `curl -O https://axibase.com/public/atsd_ce_amd64_sles.rpm`
* [axibase.com](https://axibase.com/public/atsd_ce_rpm_sles_latest.htm)

Follow the prompts to install ATSD:

```sh
 sudo rpm -i atsd_ce_amd64_sles.rpm                                  
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
