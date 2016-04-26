# Install ATSD on VMware


ATSD can be installed by importing an .ova image in VMware Fusion,
VMware Workstation, VMware ESXi Server, VMware vSphere Hypervisor and
VMware Player. This method automatically creates a virtual machine
running Ubuntu 14.04 64bit LTS with ATSD and dependencies pre-installed
and fully configured. The process take a few minutes with minimal input
required from the user, this is the easiest and fastest way to install
ATSD.

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

## Installing ATSD in VMware Player

​1. Open VMware

![](images/open.png "open")

​2. Click on: Open a Virtual Machine. Then select the atsd\_ce\_8760.ova
file.

![](images/select-ova1.png "select ova")

​3. Click: Import. Read and accept the License Agreement for ATSD in the
popup window.

![](images/accept.png "accept")

​4. Wait for the virtual machine to be imported into VMware.

![](images/wait.png "wait")

​5. Navigate to: Edit virtual machine settings. Allocate at least 1 GB
of RAM and at least 1 CPU to the virtual machine.

![](images/ram1.png "ram")

​6. Under Network Adapter select Bridged, if the virtual machine does
not start with this setting, then change this setting to Host-only.
Click on Advanced and generate a new MAC address for the VM.

![](images/mac.png "mac")

​7. Start the virtual machine by clicking on: Play virtual machine. Wait
for ATSD and Components to start.

![](images/atsd-start1.png "atsd start")

​8. Login to the virtual machine:

**username = `axibase`**

**password = `axibase`**

**`axibase` user is a sudoer.**

![](images/vm-login.png "vm login")

​9. Check the IP address of the VM:

+--------------------------------------------------------------------------+
| ~~~~ {.javascript style="font-family:monospace;"}                        |
| ip addr                                                                  |
| ~~~~                                                                     |
+--------------------------------------------------------------------------+

![](images/screenshot_280415_15-22-59.png "screenshot_280415_15-22-59")

​10. Use your browser to navigate to atsd\_server:8088. For example:
192.168.137.128:8088. Login to ATSD, username = axibase and password =
axibase.

![](images/atsd-login1.png "atsd login")

## Verifying ATSD Portals

Using your browser, navigate to the Portals tab in the ATSD web
interface.

A pre-defined portal consisting of 12 widgets should be visible and
should display various system usage metrics for the machine where the
ATSD is installed.

Be sure to check server time after installation. If server time is
incorrect, portals may be empty. See [Optional
Steps](http://axibase.com/products/axibase-time-series-database/download-atsd/install-vmware/install-atsd-on-vmware/#optional).

![](images/fresh_atsd_portal21.png "ATSD Host")

<a name="optional></a>
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
