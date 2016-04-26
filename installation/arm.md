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

## Verifying Installation

At the end of the installation, the installer displays the IP and ports
of ATSD. You can use them to access the ATSD web interface.

Depending on your device and hard drive, it can take another 15-20
minutes for ATSD to start after the message below has been displayed
(this is the case on Raspberry Pi 2).

![](images/atsd_install_shell.png "atsd_install_shell")

To find the IP address of the target machine manually, enter `ifconfig`
or `ip addr` command.

Open your browser and navigate to port `8088` on the target device. When
accessing the ATSD web interface for the first time, you will need to
setup an administrator account.

## Verifying Portals {#portals}

Click on Portals tab in the ATSD web interface. A pre-defined ‘ATSD’
portal consisting of 12 widgets should be visible. This portal displays
various system usage metrics for ATSD and the machine where it’s
installed.

![](images/fresh_atsd_portal21.png "ATSD Host")

## Optional Steps

Verifying System Time

> Open Admin \> Server Time tab in the ATSD web interface and verify the
time and timezone information.

Modify system time or setup NTP in order to keep the server time
accurate.

![Server\_time](images/Server_time.png)

Network Settings

If you’re anticipating high data insertion rate with bursts of 100000
packets per second or more, increase maximum receiving buffer on Linux
OS: [Read Network Settings
Guide](../administration/networking-settings.md "Network Settings")

### Setting up the Email Client

See [Setting up the Email Client
guide](../administration/setting-up-email-client.md "Email Client").

### Updating ATSD

```sh
 /opt/atsd/bin/update.sh
```

See [Updating ATSD
guide](../administration/update.md "Update ATSD").

### Restarting ATSD

See [Restarting ATSD
guide](../administration/restarting.md "Restarting ATSD").

### Uninstalling ATSD

See [Uninstalling ATSD
guide](../administration/uninstalling.md "Uninstalling ATSD").