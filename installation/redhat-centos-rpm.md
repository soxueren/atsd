# RedHat/Centos: rpm package

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

## Connection

If the target machine does not have Internet connection to download
dependencies, use the [offline installation option](redhat-centos-offline.md).

## Download

Download rpm package to the target server from [axibase.com](https://axibase.com/public/atsd_ce_rpm_latest.htm)

## Installation Steps

Install dependencies:

```sh
sudo yum install java-1.7.0-openjdk-devel openssh-server cronie sysstat sed passwd iproute net-tools
```

> If some of the above dependencies are not found, for example in case of
installation on new systems, run `sudo yum update` to upgrade all
packages on your operating system to the latest versions.

Follow the prompts to install ATSD:

```sh
sudo rpm -i atsd_ce_${VERSION}_amd64.rpm
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
