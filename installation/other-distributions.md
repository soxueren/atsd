# Other Distributions

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

> For ARM devices, make sure that the target device has at least a 16GB hard
drive or SD card (we recommend using SD cards with high write speeds, at
least 60mb/s), depending on the device.

## Download

Download tar.gz archive to the target server from [axibase.com](https://axibase.com/public/atsd_ce_distrib_latest.htm)

## Installation

```sh
 sudo tar -xzvf atsd_ce_${VERSION}.tar.gz -C /opt/                              
```

```sh
 sudo /opt/atsd/install_sudo.sh                                           
```

```sh                    
 sudo /opt/atsd/install_user.sh                                           
```

It may take up to 5 minutes, and up to 20 minutes on ARM devices, to initialize the database.

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
