# Installation from Debian Package


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
 sudo apt-get install openjdk-7-jdk sysstat openssh-server cron debconf \
 libc6 passwd adduser iproute net-tools curl
```

```sh
 sudo dpkg -i atsd_ce_8645_amd64.deb
```

*If there are any issues with installing the dependencies, [check the
repositories](modifying-ubuntu-debian-repositories.md "Modifying Repositories").*
Then try installing the dependencies again.




## Installation Troubleshooting

If ATSD web interface is not accessible, open its log file and review it
for errors.

Send the log file to Axibase support in case the problem is persistent
and cannot be fixed with a restart.

```sh
 tail -f /opt/atsd/atsd/logs/atsd.log
```

We also recommend to view [veryfing installation](veryfing-installation.md) and [post installation](post-installation.md) pages.
