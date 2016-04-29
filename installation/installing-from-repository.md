# Installing from Repository


This installation guide applies to Ubuntu OS version 14.x, 15.x and
Debian OS version 6.x, 7.x.

Make sure that the target machine has at least 1 GB of RAM. For
production installations see
[Requirements](../administration/requirements.md "ATSD Requirements")

Add Axibase repository on the target machine:

```sh
sudo apt-get update
```

```SH
 sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 2 
 6AEE425A57967CFB323846008796A6514F3CB79                                  
```

```sh
 sudo echo "deb [arch=amd64] http://axibase.com/public/repository/deb/ ./" >> /etc/apt/sources.list.d/axibase.list
```

Update repositories and follow the prompts to install ATSD:

```sh
 sudo apt-get update && sudo apt-get install atsd                         
```

## Installation Troubleshooting

If ATSD web interface is not accessible, open its log file and review it
for errors.

Send the log file to Axibase support in case the problem is persistent
and cannot be fixed with a restart.

```sh
 tail -f /opt/atsd/atsd/logs/atsd.log                                     
```
## Optional Steps
- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)