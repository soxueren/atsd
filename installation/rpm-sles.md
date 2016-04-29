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

We also recommend to view [veryfing installation](veryfing-installation.md) and [post installation](post-installation.md) pages.
