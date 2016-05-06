# RedHat/Centos: Offline

## Overview

The installation process involves downloading dependencies to an intermediate machine with Internet access  
and copying them to the target machine with similar characteristics for offline installation. 

## Supported Versions

- RedHat Enterprise Linux 6.x
- RedHat Enterprise Linux 7.x
- CentOS 6.x
- CentOS 7.x
- Amazon Linux 6.x
- Amazon Linux 7.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Installation Steps

Download ATSD rpm package to an intermediate machine with Internet access:

* `curl -O https://www.axibase.com/public/atsd_ce_amd64.rpm`
* [https://axibase.com/public/atsd_ce_rpm_latest.htm](https://axibase.com/public/atsd_ce_rpm_latest.htm)

Download ATSD dependencies to an intermediate machine with connected repositories:

```sh
sudo yum install yum-utils
```

```sh
sudo yumdownloader --resolve java-1.7.0-openjdk-devel openssh-server \
cronie sysstat sed passwd iproute net-tools
```

> See a RedHat [note](https://access.redhat.com/solutions/10154) on using yum to download packages without installation.

> If some of the above dependencies are not found, for example in case of installation on new systems, run `sudo yum update` to upgrade all packages on your operating system to the latest versions.

Copy the downloaded *.rpm packages to the target machine and install them as follows:

```sh
sudo yum localinstall ./folder_with_dependencies/*
```

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
