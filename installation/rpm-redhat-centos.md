# RedHat/Centos: rpm package

## Supported Versions

- RedHat Enterprise Linux 6.x
- RedHat Enterprise Linux 7.x
- CentOS 6.x
- CentOS 7.x
- Amazon Linux 6.x
- Amazon Linux 7.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Connectivity

If the target machine does not have Internet connection to download
dependencies, use the [Manual ATSD Installation guide](../administration/update-manual.md "Manual ATSD Installation").

## Download

## Installation Steps

```sh
sudo yum install java-1.7.0-openjdk-devel openssh-server cronie sysstat sed passwd iproute net-tools                 
```

If some of the above dependencies are not found, for example in case of
installation on new systems, perform `sudo yum update` to upgrade all
packages on your operating system to the latest versions.

```sh
sudo rpm -i atsd_ce_${VERSION}_amd64.rpm                                   
```

## Troubleshooting

If ATSD web interface is not accessible on port 8088, open **atsd.log** and review it for errors.

```sh
 tail -f /opt/atsd/atsd/logs/atsd.log                                     
```

## Optional Steps

- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)
