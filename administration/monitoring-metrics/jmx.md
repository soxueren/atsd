# Monitoring Metrics: JMX

JMX tools can be used to fetch ATSD metrics, for example:
[JConsole](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html "jconsole"), [jmxterm](http://wiki.cyclopsgroup.org/jmxterm/ "jmxterm") or
any other application that support JMX.

#### Setup JMX in ATSD:

In `/etc/hosts` change `127.0.1.1 atsd_server` to `atsd_ip atsd_server`
where `atsd_ip` is ip v4 adress of the ATSD host.

Configure the jmx username and password in two separate files located in
the `/opt/atsd/atsd/conf/` directory: `jmx.access` and `jmx.password`

Add the following lines to the `/opt/atsd/atsd/conf/server.properties`
file:

```properties
 jmx.port=1099                                                            
 jmx.host=192.168.1.178                                                   
 jmx.access.file=/opt/atsd/atsd/conf/jmx.access                           
 jmx.password.file=/opt/atsd/atsd/conf/jmx.password                       
 jmx.enabled=true                                                         
```

Restart ATSD:

```sh
 /opt/atsd/bin/atsd-tsd.sh stop                                           
```

```sh
 /opt/atsd/bin/atsd-tsd.sh start                                          
```

Now you can access ATSD on
`service:jmx:rmi:///jndi/rmi://192.168.1.178:1099/atsd`, with the
username and password set earlier in the guide.

#### Connect to JMX server with jvisualvm:

Uncomment JMX settings in the `/opt/atsd/atsd/conf/server.properties`
file:

-   set `jmx.host` to local ip,
-   set `jmx.access.file` and `jmx.password.file`

Now you should be able to access ATSD from remote machine
on: `service:jmx:rmi:///jndi/rmi://{ip}:1099/jmxrmi`
