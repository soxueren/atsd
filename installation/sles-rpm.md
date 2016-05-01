# SUSE Linux Enterprise Server: rpm package

## Supported Versions

- SUSE Linux Enterprise Server 11 SP3
- SUSE Linux Enterprise Server 12.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Connection

If the target machine does not have Internet connection to download
dependencies, use the [Manual Installation guide](https://axibase.com/products/axibase-time-series-database/download-atsd/atsd-installation-on-sles/manual-installation-on-sles/).

## Download

Download rpm package to the target server from [axibase.com](https://axibase.com/public/atsd_ce_rpm_sles_latest.htm)

## Installation Steps

Download [Oracle Java 7 JDK rpm package](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

Install Java

```sh
 sudo rpm -i jdk-7u80-linux-x64.rpm
```

Install Dependencies

```sh
sudo zypper -n install sysstat sed iproute net-tools openssh cron pwdutils
```

Follow the prompts to install ATSD

```sh
 sudo rpm -i atsd_ce_${VERSION}_amd64_sles.rpm                                  
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
