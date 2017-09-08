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

```sh
sudo zypper -n install java-1_8_0-openjdk-devel which net-tools iproute2
```

### Option 2: Copy Dependencies from a Connected Machine

On a separate machine with internet access create a directory containing the dependencies:

```sh
mkdir ~/sles_dependencies
cd sles_dependencies
```

Create script `dep-download.sh` and execute it to download the dependencies:

```sh
echo -e '#!/bin/sh\nSCRIPT=$(readlink -f $0)\nDIR=$(dirname ${SCRIPT})\nlist="java-1_8_0-openjdk-devel which net-tools iproute2"\nzypper -n install -df ${list}\nfor package in ${list}; do\nfind /var/cache/zypp/packages -name ${package}*.rpm -exec cp {} $DIR \;\ndone'>dep-download.sh
```

```sh
chmod a+x dep-download.sh
sudo ./dep-download.sh
```
Copy the folder containing the dependencies to the target machine and install them:
```sh
sudo zypper -n install ~/sles_dependencies/*.rpm
```

## Install ATSD

On a separate machine with internet access create a directory for `atsd_amd64_sles.rpm` and download it:

```sh
mkdir ~/atsd
cd atsd
curl -O https://axibase.com/public/atsd_amd64_sles.rpm
```
or [https://axibase.com/public/atsd_rpm_sles_latest.htm](https://axibase.com/public/atsd_rpm_sles_latest.htm)

Copy the `atsd_amd64_sles.rpm` to the target machine, follow the prompts to install ATSD

```sh
 sudo zypper -n install ~/atsd_amd64_sles.rpm
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
