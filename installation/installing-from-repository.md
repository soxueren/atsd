# Ubuntu/Debian: apt-get

## Supported Versions

- Ubuntu 14.x
- Ubuntu 15.x
- Debian 6.x
- Debian 7.x

## Requirements

- Minimum RAM: 1 GB 
- See [Requirements](../administration/requirements.md "ATSD Requirements") for additional information.

## Installation Steps

```sh
sudo apt-get update
```

Add **axibase.com/public/repository/deb/** repository:

```sh
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 26AEE425A57967CFB323846008796A6514F3CB79                             
```

```sh
sudo echo "deb [arch=amd64] http://axibase.com/public/repository/deb/ ./" >> /etc/apt/sources.list.d/axibase.list
```

Update repositories and follow the prompts to install ATSD:

```sh
sudo apt-get update && sudo apt-get install atsd                       
```

## Troubleshooting

If ATSD web interface is not accessible on port 8088, open **atsd.log** and review it for errors.


```sh
 tail -f /opt/atsd/atsd/logs/atsd.log                                     
```


Send the log file to Axibase support in case the problem cannot be resolved with a restart.

## Optional Steps

- [Veryfing installation](veryfing-installation.md)
- [Post-installation](post-installation.md)
