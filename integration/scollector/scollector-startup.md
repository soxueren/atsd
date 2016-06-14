# scollector Startup

To setup scollector using Puppet, please use our guide and distribution on [GitHub](https://github.com/axibase/axibase-puppet-modules/tree/master/scollector).

#### Setting up scollector to stream data into ATSD

##### Windows:

1. Download scollector Windows executable from [http://bosun.org/scollector/ ](http://bosun.org/scollector/)

2. Navigate to the directory with exe file and create `scollector.toml` file in notepad.

NOTE: Make sure the name of the file is `scollector.toml` and not `scollector.toml.txt`

3. Add Host setting to scollector.toml:

`Host = "http://atsd_username:atsd_password@atsd_server:8088/"`

NOTE: If you installed a root-signed SSL certificate into ATSD, you can change the above url to the secure https endpoint.

> At this time scollector does not support communication with ATSD if it’s SSL certificate is self-signed.

4. Open prompt as Administrator and create scollector service with automated startup by executing the following command:

`scollector-windows-amd64.exe -winsvc=install`

5. Start scollector service by executing the following command:

`scollector-windows-amd64.exe -winsvc=start`

> NOTE: If the service exits a few seconds after startup, it either cannot locate `scollector.toml` file, this file is not valid/empty or the Host parameter is specified without double quotes.
Open Windows event log and review service startup error.

> If the service is running but there are no scollector metrics in ATSD, verify the protocol, url and user credentials specified in `scollector.toml` file.

##### Linux:

Download the binary file from: [http://bosun.org/scollector/](http://bosun.org/scollector/)

You can find the official configuration guides here: [http://godoc.org/bosun.org/cmd/scollector](http://godoc.org/bosun.org/cmd/scollector)

Alternatively you can use “scollector Cookbook” to install scollector on your machines: [https://supermarket.chef.io/cookbooks/scollector](https://supermarket.chef.io/cookbooks/scollector)

###### Create scollector.toml configuration file:

Navigate to the directory with binary files and create `scollector.toml` file.

NOTE: Make sure the name of the file is `scollector.toml`

Add Host setting to scollector.toml:

`Host = "http://atsd_username:atsd_password@atsd_server:8088/"`

NOTE: If you installed a root-signed SSL certificate into ATSD, you can change the above url to the secure https endpoint.

> At this time scollector does not support communication with ATSD if it’s SSL certificate is self-signed.

###### Launching scollector as a sudo user

Create an init script called `scollector` in `/etc/init.d/` directory with the following contents:

```
#chkconfig: 2345 90 10
#description: scollector is a framework to collect data points and store them in a TSDB.
### BEGIN INIT INFO
# Provides: scollector
# Required-Start:
# Required-Stop:
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: start scollector
# Description:
### END INIT INFO
 
SCOLLECTOR_BIN=/home/axibase/scollector-linux-amd64
SCOLLECTOR_CONF=/home/axibase/scollector.toml
 
"$SCOLLECTOR_BIN" -conf="$SCOLLECTOR_CONF"
```

Be sure to change `SCOLLECTOR_BIN` and `SCOLLECTOR_CONF` to actual scollector directory path.

Set execute flag:

```
chmod a+x /etc/init.d/scollector
```

Add scollector to startup:

On Ubuntu/Debian:

```
sudo update-rc.d scollector defaults 90 10
```

On RHEL/SUSE/SLES:

```
sudo chkconfig --add scollector
sudo touch /var/lock/subsys/scollector
```

###### Launching scollector as a non sudo user

Modify `/etc/init.d/tcollector` content:

```
#chkconfig: 2345 90 10
#description: scollector is a framework to collect data points and store them in a TSDB.
### BEGIN INIT INFO
# Provides: scollector
# Required-Start:
# Required-Stop:
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: start scollector
# Description:
### END INIT INFO
 
SCOLLECTOR_BIN=/home/axibase/scollector-linux-amd64
SCOLLECTOR_CONF=/home/axibase/scollector.toml
SCOLLECTOR_USER=axibase
 
if [ `whoami` != "$SCOLLECTOR_USER" ]; then
su - "$SCOLLECTOR_USER" -c "$SCOLLECTOR_BIN" -conf="$SCOLLECTOR_CONF"
else
"$SCOLLECTOR_BIN" -conf="$SCOLLECTOR_CONF"
fi
```

Be sure to change `SCOLLECTOR_BIN` and `SCOLLECTOR_CONF` to actual scollector directory path.

Set `SCOLLECTOR_USER` to the user that will run scollector.

##### MacOS:

Download the binary file from: [http://bosun.org/scollector/](http://bosun.org/scollector/)

You can find the official configuration guides here: [http://godoc.org/bosun.org/cmd/scollector](http://godoc.org/bosun.org/cmd/scollector)

###### Create scollector.toml configuration file:

Navigate to the directory with binary files and create `scollector.toml` file.

NOTE: Make sure the name of the file is `scollector.toml`

Add Host setting to scollector.toml:

`Host = "http://atsd_username:atsd_password@atsd_server:8088/"`

NOTE: If you installed a root-signed SSL certificate into ATSD, you can change the above url to the secure https endpoint.

> At this time scollector does not support communication with ATSD if it’s SSL certificate is self-signed.

###### Add scollector to startup

To start scollector on system boot add `com.axibase.scollector.plist xml` file to `/Library/LaunchDaemons` folder and replace `$SCOLLECTOR_BIN` and `$SCOLLECTOR_CONF` placeholders with the correct directories.

`$SCOLLECTOR_BIN` – path to scollector binary file
`$SCOLLECTOR_CONF` – path to scollector.toml configuration file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
  <dict>
    <key>Label</key>
      <string>scollector</string>
    <key>ProgramArguments</key>
      <array>
        <string>$SCOLLECTOR_BIN</string>
        <string>-conf=$SCOLLECTOR_CONF</string>
      </array>
    <key>OnDemand</key>
      <false/>
  </dict>
</plist>
view raw
```
###### Controlling scollector

`sudo launchctl load -w /Library/LaunchDaemons/com.axibase.scollector.plist` – load scollector launchd configuration file
`sudo launchctl unload -w /Library/LaunchDaemons/com.axibase.scollector.plist` – unload scollector launchd configuration file

`sudo launchctl stop scollector` – stop scollector daemon

