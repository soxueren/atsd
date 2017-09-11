# SUSE Linux Enterprise Server: Offline

## Overview

The installation process involves downloading dependencies to an intermediate machine with Internet access
and copying them to the target machine with similar characteristics for offline installation.

## Supported Versions

- SUSE Linux Enterprise Server 12.x

## Requirements

- Minimum RAM: 2 GB
- See [Requirements](../administration/requirements.md) for additional information.

## Install Dependencies

### Option 1: Install Dependencies from Local Repositories

```bash
sudo zypper -n install java-1_8_0-openjdk-devel which net-tools iproute2
```

### Option 2: Copy Dependencies from a Connected Machine

On a separate machine with internet access create a directory to download the RPM packages which are required by ATSD.

```bash
mkdir ./sles_dependencies
cd sles_dependencies
```

Create `dep-download.sh` script file and execute it to download the packages: `java-1_8_0-openjdk-devel which net-tools iproute2`.

```bash
nano dep-download.sh
```

```bash
#!/bin/sh
SCRIPT=$(readlink -f $0)
DIR=$(dirname ${SCRIPT})
list="java-1_8_0-openjdk-devel which net-tools iproute2"
zypper -n install -df ${list}
for package in ${list}; do
find /var/cache/zypp/packages -name ${package}*.rpm -exec cp {} $DIR \;
done
```

```bash
chmod a+x dep-download.sh
sudo ./dep-download.sh
```

Make sure that the download directory contains the following packages:

```
iproute2-4.4-14.7.x86_64.rpm
java-1_8_0-openjdk-devel-1.8.0.101-14.3.x86_64.rpm
net-tools-1.60-764.185.x86_64.rpm
which-2.20-3.180.x86_64.rpm
```

Copy the RPM files in the download directory to the target machine and install the packages:

```bash
sudo zypper -n install ~/sles_dependencies/*.rpm
```

Sample output:

```
...
update-alternatives: using /usr/lib64/jvm/java-1.8.0-openjdk/bin/javac to provide /usr/bin/javac (javac) in auto mode
update-alternatives: using /usr/lib64/jvm/java-1.8.0-openjdk to provide /usr/lib64/jvm/java-opnejdk (java_sdk_openjdk) in auto mode
update-alternatives: using /usr/lib64/jvm/java-1.8.0-openjdk to provide /usr/lib64/jvm/java-1.8.0 (java_sdk_1.8.0) in auto mode
```

## Install ATSD

Download the [ATSD rpm package for SLES](https://axibase.com/public/atsd_rpm_sles_latest.htm) to the machine with internet access.

```sh
curl -O https://axibase.com/public/atsd_amd64_sles.rpm
```

Copy the `atsd_amd64_sles.rpm` file to the target machine and install ATSD.

```sh
sudo zypper -n install atsd_amd64_sles.rpm
```

It may take up to 5 minutes to initialize the database.

## Check Installation

```sh
tail -f /opt/atsd/atsd/logs/start.log
```

You should see an **ATSD start completed** message at the end of the `start.log`.

Web interface is accessible on port 8088 (http) and 8443 (https).

## Troubleshooting

* Review [troubleshooting guide](troubleshooting.md).

## Validation

* [Verify database installation](verifying-installation.md).

## Post-installation Steps

* [Basic configuration](post-installation.md).
* [Getting Started guide](../tutorials/getting-started.md).
