# Sender Script

**SSH Tunnel**

The sender script is used to stream nmon data into ATSD as it is written into the nmon file.

The `nmon_sender_ssh.sh` script creates an ssh-tunnel and uses it to send nmon data to ATSD.

[resources/nmon_sender_ssh.sh](https://github.com/axibase/nmon/blob/master/nmon_sender_ssh.sh)

The [SSH Tunneling](http://axibase.com/products/axibase-time-series-database/writing-data/nmon/ssh-tunneling/) guide explains how to setup and test the tunnel manually.

Unpack the script to the `/opt/nmon` directory, as described in the [SSH File Streaming guide](https://axibase.com/products/axibase-time-series-database/writing-data/nmon/file-streaming/).

**Telnet**

The `nmon_sender_telnet.sh script` uses Telnet to send nmon data to ATSD.

[resources/nmon_sender_telnet.sh](https://github.com/axibase/nmon/blob/master/nmon_sender_telnet.sh)

Unpack the script to the `/opt/nmon directory`, as described in the [Telnet File Streaming guide](https://axibase.com/products/axibase-time-series-database/writing-data/nmon/nmon-file-streaming/).

#### Script Arguments

Example crontab setup:

```
0 0 * * * /opt/nmon/nmon -f -s 60 -c 1440 -T -m /opt/nmon/nmon_logs/
0 0 * * * /opt/nmon/nmon_sender_ssh.sh {atsdhostname} -p 22 -s 60 -c 1440 -m /opt/nmon/nmon_logs/ -i /opt/nmon/id_rsa_atsdreadonly >>/opt/nmon/full.log 2>&1
```

The first line is a task to start nmon.

The second line is a task to run the script. The `{atsdhostname}` must be replaced with a hostname or IP address where the ATSD is installed (NAT address if you are using port forwarding).

Argument `-p` specifies the port of the machine where the ATSD is installed.

`-s` , `-c`, and `-m` arguments must have the same values in both lines.

All arguments, except `-h`, must have a value.

To stop the script and all involved processes, just run (with the correct pid of nmon sender script):

```
kill $nmonsenderPID
```

You can find the right `$nmonsenderPID` in output of command:

```
ps -ef | grep nmon_sender_ssh.sh
```

| Argument | Description | 
| --- | --- | 
|  <p>-h</p>  |  <p>Show help message.</p>  | 
|  <p>-s [second]</p>  |  <p>Set period of making snapshot of nmon (60 by default).</p>  | 
|  <p>-c [count]</p>  |  <p>Set count of snapshot (1440 by default).</p>  | 
|  <p>-m [dir]</p>  |  <p>Set nmon output directory or filename (“./” by default).</p>  | 
|  <p>-u [user]</p>  |  <p>Set user for ssh-connect (“atsdreadonly” by default).</p>  | 
|  <p>-i [keypath]</p>  |  <p>Set path to private key (~/.ssh/id_rsa_atsdreadonly by default).</p>  | 
|  <p>-p [port]</p>  |  <p>Set port to connect by ssh (22 by default).</p>  | 
|  <p>-r [parser_id]</p>  |  <p>Set parser id (“default” by default).</p>  | 
