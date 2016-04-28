# Installation on ARM Devices

This installation requires Ubuntu OS or Debian OS installed on Raspberry
Pi 2 or another ARM based device.

Be sure that the target device has at least 1 GB of RAM and 16GB hard
drive or SD card (we recommend using SD cards with high write speeds, at
least 60mb/s), depending on the device.

[Download the latest tar.gz distribution of
ATSD.](http://axibase.com/products/axibase-time-series-database/download-atsd/ "Download ATSD")

```sh
 sudo wget http://axibase.com/public/atsd_ce_9808.tar.gz                  
```

```sh
 sudo tar -xzvf atsd_ce_9808.tar.gz -C /opt/                              
```

```sh
 sudo /opt/atsd/install_sudo.sh                                           
```

```sh                    
 sudo /opt/atsd/install_user.sh                                           
```

