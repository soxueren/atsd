# ATSD Installation from Debian Package


This installation guide applies to Ubuntu OS version 14.x, 15.x and
Debian OS version 6.x, 7.x.

Make sure that the target machine has at least 1 GB of RAM. For
production installations see
[Requirements](../administration/requirements.md "ATSD Requirements")

If the target machine is not connected to public or private repositories
to install dependencies with apt-get, use the [Manual ATSD Installation
guide](../administration/update-manual.md "Manual ATSD Installation").

**If you would like to install ATSD from repositories with apt-get, use
the: [Install ATSD Using Axibase Repositories
guide](installing-from-repository.md "Install ATSD Using Axibase Repositories").**

```sh
 sudo apt-get update                                                      
```

```sh
 sudo apt-get install openjdk-7-jdk sysstat openssh-server cron debconf l 
 ibc6 passwd adduser iproute net-tools curl
```

```sh
 sudo dpkg -i atsd_ce_8645_amd64.deb
```

*If there are any issues with installing the dependencies, [check the
repositories](http://axibase.com/products/axibase-time-series-database/download-atsd/install-atsd/modifying-repositories/ "Modifying Repositories").*
Then try installing the dependencies again.


## Verifying Installation

At the end of the installation, the installer displays the list of IPs
and ports (8088, 8443) of the ATSD web interface.

![](images/atsd_install_shell.png "atsd_install_shell")

Open your browser and navigate to port `8088` on the target machine.

When accessing the ATSD web interface for the first time, you will need
to setup an administrator account.

## Verifying Portals

Click on Portals tab in the ATSD web interface.

Open pre-defined ‘ATSD’ portal to view key operating system and database
metrics.

![](images/fresh_atsd_portal21.png "ATSD Host")

## Installation Troubleshooting {#portals}

If ATSD web interface is not accessible, open its log file and review it
for errors.

Send the log file to Axibase support in case the problem is persistent
and cannot be fixed with a restart.

```sh
 tail -f /opt/atsd/atsd/logs/atsd.log
```

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