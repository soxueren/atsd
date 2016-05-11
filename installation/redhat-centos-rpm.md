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

Download rpm package to the target server:

* `curl -O https://www.axibase.com/public/atsd_ce_amd64.rpm`
* [https://axibase.com/public/atsd_ce_rpm_latest.htm](https://axibase.com/public/atsd_ce_rpm_latest.htm)

## Installation Steps

Install ATSD with dependencies:

```sh
sudo yum install -y atsd_ce_amd64.rpm
```

It may take up to 5 minutes to initialize the database.

## Check Installation

```sh
tail -f /opt/atsd/atsd/logs/start.log                                   
```

You should see **ATSD start completed** message at the end of the start.log.

Web interface is accessible on port 8088 (http) and 8443 (https).

## Troubleshooting

* Review [Troubleshooting Guide](troubleshooting.md).

## Optional Steps

- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)
