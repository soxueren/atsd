# Axibase Time Series Database Installation from RPM Package


This installation requires RedHat Enterprise Linux, CentOS, and Amazon
Linux versions 6.x, 7.x

Make sure that the target machine has at least 1 GB of RAM. For
production installations see
[Requirements](../administration/requirements.md "ATSD Requirements")

If the target machine does not have Internet connection to download
dependencies, use the [Manual ATSD Installation guide](../administration/update-manual.md "Manual ATSD Installation").ll-rpm-package/manual-rpm-package/ "Manual ATSD Installation from RPM Package").

```sh
 sudo yum install java-1.7.0-openjdk-devel openssh-server cronie sysstat  
 sed passwd iproute net-tools                                             
```

```sh
 sudo rpm -i atsd_ce_8645_amd64.rpm                                       
```

If some of the above dependencies are not found, for example in case of
installation on new systems, perform `sudo yum update` to upgrade all
packages on your operating system to the latest versions.

## Verifying Installation

At the end of the installation, the installer displays the list of IPs
and ports (8088, 8443) of the ATSD web interface.

![](images/atsd_install_shell.png "atsd_install_shell")

Open your browser and navigate to port `8088` on the target machine.

When accessing the ATSD web interface for the first time, you will need
to setup an administrator account.

## Verifying ATSD Portals

Click on Portals tab in the ATSD web interface.

Open pre-defined ‘ATSD’ portal to view key operating system and database
metrics.

![](images/fresh_atsd_portal21.png "ATSD Host")

## Installation Troubleshooting

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