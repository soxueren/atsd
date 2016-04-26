# Install on VMware ESXi Server/vSphere


ATSD can be installed by importing an .ova image in VMware ESXi Server,
VMware vSphere Hypervisor, VMware Fusion, VMware Workstation and VMware
Player. This method automatically creates a virtual machine running
Ubuntu 14.04 64bit LTS with ATSD and dependencies pre-installed and
fully configured. The process take a few minutes with minimal input
required from the user, this is the easiest and fastest way to install
ATSD.

[If you will be installing ATSD in VMware player, please use our
separate guide for this installation
method.](vmware.md "Install ATSD on VMware")

## Prerequisites

To setup the ATSD virtual machine in VMware you must have the following
resources available to you:

Requirements:

-   A 64-bit machine with a processor that supports virtualization. Note
    that not all 64-bit processors have this capability.
-   Have virtualization enabled in your BIOS, this is normally enabled
    by default, but in some cases you may have to enter your system bios
    manually to enable virtualization.
-   At least 4 GB of RAM on Host Machine and at least 1 GB of RAM
    allocated to the Virtual Machine.

## Installing ATSD in VMware ESXi Server/vSphere

​1. Connect to a vCenter Server with the vSphere Web Client, then
navigate to File –\> Deploy OVF Template.

![](images/Screenshot_1.png "Screenshot_1")

​2. Click on Browse, then select the atsd\_ce\_8760.ova file.

![](images/Screenshot_3.png "Screenshot_3")

​3. Click next on the OVF Template Details window, then read and accept
the End User License Agreement.

![](images/Screenshot_5.png "Screenshot_5")

​4. Give a name to the virtual machine and select the inventory
location.

![](images/Screenshot_7.png "Screenshot_7")

​5. Select a host for the virtual machine from the list.

![](images/Screenshot_8.png "Screenshot_8")

​6. Select Disk Format, we recommend either Thick Provision Lazy Zeroed
or Thick Provision Eager Zeroed.

![](images/Screenshot_9.png "Screenshot_9")

​7. Choose the Network Mapping (network to which the adapter of the
virtual machine will be attached to).

![](images/Screenshot_10_attention.png "Screenshot_10_attention")

​8. Confirm the virtual machine configuration.

![](images/Screenshot_11.png "Screenshot_11")

​9. Wait for the virtual machine to be created.

![](images/Screenshot_12.png "Screenshot_12")

​10. Power on the virtual machine.

![](images/Screenshot_13.png "Screenshot_13")

​11. Wait for ATSD and Components to start.

ATTENTION: The machine is configured to receive the IP address by DHCP.
If there is no DHCP server in the network, the loading will slow down at
this point. This is normal since DHCP is not used. After loading is
complete, you will need to setup a static IP in /etc/network/interfaces.

![](images/Screenshot_14_attention.png "Screenshot_14_attention")

​12. Login to the virtual machine:

**username = `axibase`**

**password = `axibase`**

**`axibase` user is a sudoer.**

![](images/Screenshot_17.png "Screenshot_17")

​13. Use your browser to navigate to atsd\_server:8088. For example:
192.168.137.128:8088. Login to ATSD, username = axibase and password =
axibase.

![](images/atsd-login1.png "atsd login")

#### Verifying ATSD Portals 

Using your browser, navigate to the Portals tab in the ATSD web
interface.

A pre-defined portal consisting of 12 widgets should be visible and
should display various system usage metrics for the machine where the
ATSD is installed.

Be sure to check server time after installation. If server time is
incorrect, portals may be empty. See [Optional
Steps](vmware.md#optional).

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
