# Installation on SLES


This installation requires SUSE Linux Enterprise Server version 11 SP3
or 12.xx

Be sure that the target machine has at least 1 GB of RAM for test
installations. For production installations see:
[Requirements](../administration/requirements.md "ATSD Requirements")

If the target machine does not have internet connection to download
dependencies, use the [Manual ATSD Installation guide](../administration/update-manual.md "Manual ATSD Installation").

Install Java

Download Oracle Java rpm
package: [http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

```sh
 sudo rpm -i jdk-7u80-linux-x64.rpm                                       
```

Install Dependencies

```sh
 sudo zypper -n install sysstat sed iproute net-tools openssh cron pwduti 
 ls                                                                       
```

Install ATSD

```sh
 sudo rpm -i atsd_ce_8645_amd64_sles.rpm                                  
```

## Verifying Installation

At the end of the installation, the installer displays the IP and ports
of ATSD. You can use them to access the ATSD web interface.

![](images/atsd_install_shell.png "atsd_install_shell")

To find the IP address of the target machine manually, enter `ifconfig`
or `ip addr` command.

> Open your browser and navigate to port `8088` on the target machine.
When accessing the ATSD web interface for the first time, you will need
to setup an administrator account.

## Verifying ATSD Portals

Click on Portals tab in the ATSD web interface. A pre-defined ‘ATSD’
portal consisting of 12 widgets should be visible. This portal displays
various system usage metrics for ATSD and the machine where it’s
installed.

![](images/fresh_atsd_portal21.png "ATSD Host")
