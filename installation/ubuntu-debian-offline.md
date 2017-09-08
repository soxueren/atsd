# Ubuntu/Debian: Offline

## Overview

The installation process involves downloading dependencies to an intermediate machine with Internet access
and copying them to the target machine with similar characteristics for offline installation.

## Supported Versions

- Ubuntu 16.04
- Debian 8.x/9.x

## Requirements

- Minimum RAM: 2 GB
- See [Requirements](../administration/requirements.md) for additional information.

## Installation Steps

Enable the **axibase.com/public/repository/deb/** repository on the machine with Internet access:

```sh
sudo apt-get update
```

```sh
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 \
--recv-keys 26AEE425A57967CFB323846008796A6514F3CB79
```

```sh
sudo sh -c 'echo "deb [arch=amd64] http://axibase.com/public/repository/deb/ ./" \
>> /etc/apt/sources.list.d/axibase.list'
```

Download the ATSD package, including its dependencies, to the `dependencies` directory.

```
sudo apt-get update
mkdir ~/dependencies
cd dependencies
apt-get download atsd $(apt-cache depends --recurse --no-recommends --no-suggests \
  --no-conflicts --no-breaks --no-replaces --no-enhances \
  --no-pre-depends atsd| grep "Depends"| cut -d ":" -f2|  grep "^\ \w")  
mkdir ~/atsd
mv atsd* ~/atsd
```
It may take up a several minutes to download all required packages.

Copy the `dependencies` and `atsd` directories to the target machine where ATSD will be installed.

Install dependencies:

```sh
sudo dpkg -i dependencies/*
```

Follow the prompts to install ATSD:

```sh
sudo dpkg -i atsd*
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
