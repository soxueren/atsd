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

We also recommend to view [veryfing installation](veryfing-installation.md) and [post installation](post-installation.md) pages.