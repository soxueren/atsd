# RedHat/Centos: rpm Package

## Supported Versions

- RedHat Enterprise Linux 6.x
- RedHat Enterprise Linux 7.x
- CentOS 6.x
- CentOS 7.x
- Amazon Linux 6.x
- Amazon Linux 7.x

## Requirements

- Minimum RAM: 1 GB
- See [Requirements](../administration/requirements.md) for additional information.

## Connection

If the target machine does not have Internet connection to download
dependencies, use the [offline installation option](redhat-centos-offline.md).

## Download

Download the rpm package to the target server:

* `curl -O https://www.axibase.com/public/atsd_ce_amd64.rpm`
* [https://axibase.com/public/atsd_ce_rpm_latest.htm](https://axibase.com/public/atsd_ce_rpm_latest.htm)

## Installation Steps

Install ATSD with dependencies:

```sh
sudo yum install -y atsd_ce_amd64.rpm
```

It may take up to 5 minutes to initialize the database.

### Docker Container Installation

If the installation is performed in a Docker container, the `yum` command will exit with the following message:

```
Docker container installation. Initialization deferred. 
```

Execute the following additional step to complete the installation:

```sh
/opt/atsd/install_user.sh
```

Start the database:

```sh
/opt/atsd/bin/atsd-all.sh start
```

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
