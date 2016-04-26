# Install on Docker

This installation requires Docker 1.6+. [Use the official Docker
installation guides to install Docker on your target
machine.](https://docs.docker.com/installation/ "Docker Installation Guides")

Axibase Time-Series Database Dockerfile on
GitHub: [https://github.com/axibase/dockers/blob/master/atsd/Dockerfile](https://github.com/axibase/dockers/blob/master/atsd/Dockerfile "ATSD Docker GitHub")

Start ATSD Docker container:

```sh
 docker run \                                                             
   -d \                                                                   
   -p 8088:8088 \                                                         
   -p 8443:8443 \                                                         
   -p 8081:8081 \                                                         
   -p 8082:8082/udp \                                                     
   -h atsd \                                                              
   --name=atsd \                                                          
   --restart=always \                                                     
   axibase/atsd:latest                                                    
```
> Note: 8088 – http, 8443 – https, 8081 – network commands TCP, 8082 –
network commands UDP.*\
 *The full list of exposed ports is specified in ATSD Dockerfile.*

In the command above, be sure to set the following parameters correctly:

`-h` sets the hostname of the container

`--name` sets the name of the container

`-e AXIBASE_USER_PASSWORD=` optionally, set password for ‘axibase’ user
in Ubuntu

`-e ATSD_USER_NAME=` optionally, set username for the default
administrator account in ATSD

`-e ATSD_USER_PASSWORD=` optionally, sets password for the default
administrator account in ATSD. Minimum password length is 6 characters.

Depending on your Docker host configuration, you may need to change port
mappings for the container.

For example:

```sh
 docker run \                                                             
   -dit \                                                                 
   -p 9088:8088 \                                                         
   -p 9081:8081 \                                                         
   -p 9443:8443 \                                                         
   -p 9082:8082/udp \                                                     
   -h atsd \                                                              
   --name=atsd \                                                          
   --restart=always \                                                     
   axibase/atsd:latest \                                                  
   tail -f /opt/atsd/atsd/logs/start.log                                  
```

![](images/atsd_install_shell.png "atsd_install_shell")

## Verifying Installation

Open your browser and navigate to port `8443` on the Docker host.

If `ATSD_USER_NAME` was not specified in environmental properties, you
will need to setup an administrator account when accessing ATSD for the
first time.

#### Known Issues

If ATSD fails to start in the container, verify that your Docker host
runs on a supported kernel level.

```sh
 uname -a                                                                 
```

```sh
 3.13.0-79.123+                                                           
 3.19.0-51.57+                                                            
 4.2.0-30.35+                                                             
```

See “Latest Quick Workarounds” for Docker issue \#18180 on
[https://github.com/docker/docker/issues/18180](https://github.com/docker/docker/issues/18180)

## Viewing Portals

Click on Portals tab in the ATSD web interface, select ‘ATSD’ portal.

This portal displays various database and operating system metrics.

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

In order to setup the Email Client in ATSD, use: [Setting up the Email
Client
guide](../administration/setting-up-email-client.md "Email Client").

### Restarting ATSD

Stop ATSD container:

```sh
 docker stop -t 100 atsd
```

Start ATSD container:

```sh
 docker start atsd                                                        
```

### Updating ATSD

In order to update ATSD to the latest version, use the [Updating ATSD
guide](../administration/update.md "Update ATSD").
