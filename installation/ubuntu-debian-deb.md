# Ubuntu/Debian: Debian Package

## Supported Versions

- Ubuntu 14.x
- Ubuntu 15.x
- Debian 6.x
- Debian 7.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Connectivity

If the target machine is not connected to public or private repositories
to install dependencies with APT, use the [Manual ATSD Installation
guide](../administration/update-manual.md "Manual ATSD Installation").

## Download

```sh
wget 
```

## Installation Steps

```sh
sudo apt-get update                                                      
```

```sh
sudo apt-get install openjdk-7-jdk sysstat openssh-server cron debconf \
libc6 passwd adduser iproute net-tools curl
```

```sh
sudo dpkg -i atsd_ce_${VERSION}_amd64.deb
```

*If there are any issues with installing the dependencies, [check the
repositories](modifying-ubuntu-debian-repositories.md "Modifying Repositories").*
Then try installing the dependencies again.

## Troubleshooting

If ATSD web interface is not accessible on port 8088, open **atsd.log** and review it for errors.

```sh
tail -f /opt/atsd/atsd/logs/atsd.log                                     
```

## Optional Steps
- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)
