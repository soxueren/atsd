# Other Distributions

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

> On ARM devices, make sure that the device has at least a 16GB hard drive or SD card. <br>
Use SD cards with 60mb/s+ write speeds.

## Download

Download tar.gz archive to the target server:

* `curl -O https://www.axibase.com/public/atsd_ce.tar.gz`
* [axibase.com](https://axibase.com/public/atsd_ce_distrib_latest.htm)

## Installation

```sh
 sudo tar -xzvf atsd_ce.tar.gz -C /opt/
```

```sh
 sudo /opt/atsd/install_sudo.sh
```

```sh                    
 sudo /opt/atsd/install_user.sh
```

It may take up to 5 minutes, to initialize the database. Installation on ARM devices may take up to 20 minutes.

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
