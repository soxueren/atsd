# Network API

Network API provides a set of plain text commands for inserting numeric time series, key=value properties, and tagged messages into Axibase Time Series Database via **TCP** and **UDP** network protocols.

You can use `netcat`, `telnet`, `UNIX pipes`, and any programming language that lets you connect to ATSD server via TCP/UDP protocol.

## Supported Commands

### Data Commands

* [series](series.md)
* [property](property.md)
* [message](message.md)
* [csv](csv.md)
* [nmon](nmon.md)

### Control Commands

* [ping](ping.md)
* [debug](#debugging)
* [time](time.md)
* [version](version.md)
* [exit](exit.md)

### Extended Data Commands

* [tcollector](extended-commands.md#tcollector)
* [statsd](extended-commands.md#statsd)
* [graphite](extended-commands.md#graphite)

## Ports

By default ATSD server listens for incoming commands on the following ports:

* 8081 TCP
* 8082 UDP

## Encryption

To encrypt TCP traffic, setup an [SSH tunnel](http://axibase.com/products/axibase-time-series-database/writing-data/nmon/ssh-tunneling/) or create a VPN connection.

## Authentication

Authentication and authorization are not supported for plain text commands received over TCP and UDP protocols. 

Utilize [HTTP command](../data/command.md) to send plain-text commands over http/https protocols with authentication and authorization enabled.

## Connection

### Single Command

To send a single command, connect to an ATSD server, send the command in plain text and terminate the connection.

* netcat:echo

```ls
echo -e "series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z" | nc atsd_host 8081
```

* netcat:printf

```ls
printf 'series e:station_2 m:temperature=32.2 m:humidity=81.4 s:1463271035' | nc atsd_host 8081
```

* UNIX pipe

```ls
echo -e "series e:station_3 m:temperature=32.2 m:humidity=81.4" > /dev/tcp/atsd_host/8081
```

* telnet:one line

```ls
telnet atsd_host 8081 << EOF
series e:station_4 m:temperature=32.2 m:humidity=81.4
EOF
```

* telnet:session

```ls
$ telnet atsd_host 8081
Trying atsd_host...
Connected to atsd_host.
Escape character is '^]'.
series e:station_5 m:temperature=32.2 m:humidity=81.4
^C
Connection closed by foreign host.
```

* java:socket

```java
Socket s = new Socket("atsd_host", 8081);
PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
writer.println("series e:station_6 m:temperature=32.2");
s.close();
```

The above examples insert timestamped **temperature** and **humidity** metric samples for **station** entities.

### Multiple Commands

Separate commands by line feed symbol `\n` (LF, `0x0A`) when sending a batch containing multiple commands over the same connection.

Trailing line feed is not required for the last command in the batch.

Use `-e` flag in `echo` commands to enable interpretation of backslash escapes.

```ls
echo -e "series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z\nseries e:station_1 m:temperature=32.1 m:humidity=82.4 d:2016-05-15T00:25:00Z" | nc atsd_host 8081
```

```java
Socket s = new Socket("atsd_host", 8081);
PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
writer.println("series e:station_6 m:temperature=30.1\nseries e:station_7 m:temperature=28.7");
s.close();
```

### Persistent Connection

A client application can establish a persistent connection in order to continously write commands, one command per line, and close the connection. 

Trailing line feed is not required for the last command when the connection is closed.

The commands are processed as they're received by the server, without buffering.

To prevent the connection from timing out the client may send [`ping`](ping.md) command at a regular interval.

Clients can submit commands of different types over the same connection.

```ls
$ telnet atsd_host 8081
Trying atsd_host...
Connected to atsd_host.
Escape character is '^]'.
series e:station_1 m:temperature=32.2
property e:station_2 t:location v:city=Cupertino v:state=CA v:country=USA
^C
Connection closed by foreign host.
```

Note that the server will **terminate** the connection if it receives an unsupported or malformed command.


```ls
$ telnet atsd_host 8081
Trying atsd_host...
Connected to atsd_host.
Escape character is '^]'.
unknown_command e:station_1 m:temperature=32.2
Connection closed by foreign host.
```

### UDP Datagrams

The UDP protocol doesn't guarantee delivery but may have a higher throughput compared to TCP due to lower overhead. 

In addition, sending commands with UDP datagrams decouples the client application from the server to minimize the risk of blocking I/O time-outs.

```ls
echo -e "series e:station_3 m:temperature=32.2 m:humidity=81.4" | nc -u -w1 atsd_host 8082
```

```ls
printf 'series e:station_3 m:temperature=32.2 m:humidity=81.4' | nc -u -w1 atsd_host 8082
```

Unlike TCP, the last command in a multi-command UDP datagram must be terminated with the line feed character.

```ls
echo -e "series e:station_33 m:temperature=32.2\nseries e:station_34 m:temperature=32.1 m:humidity=82.4\n" | nc -u -w1 atsd_host 8082
```

### Duplicate Commands

Multiple commands with the same timestamp and key fields may override each others value. 

If such commands are submitted at approximately the same time, there is no guarantee that they will be processed in the order they were received.

* Duplicate example: same key, same current time  

```ls
echo -e "series e:station_1 m:temperature=32.2\nseries e:station_1 m:temperature=42.1" | nc atsd_host 8081
```

* Duplicate example: same key, same time  

```ls
echo -e "series e:station_1 m:temperature=32.2 d:2016-05-15T00:10:00Z\nseries e:station_1 m:temperature=42.1  d:2016-05-15T00:10:00Z" | nc atsd_host 8081
```

## Syntax

### Line Syntax

* Command must start with command name such as `series` followed by space-separated fields each identified with a prefix followed by (:) colon symbol and field name=value.

```elm
command-name field-prefix:field-name[=field-value]
```

* The order of fields is not important.
* Field names are case-insensitive.
* Field values are case-sensitive and are stored as submitted.
* Field names must not contain a space character. <br>When inserted via CSV upload or HTTP API, space is converted to an underscore symbol. <br>Multiple underscores are replaced with one underscore character.
* Double-quote must be escaped with backslash, for example: `t:descr="Version is \"Ubuntu 14.04\""`.
* Field values are trimmed of starting and trailing CR,LF symbols.
* If field value contains space it needs to be enclosed in double-quotes, for example: `v:os="Ubuntu 14.04"`.

### Command Length Limits

The server enforces the following maximum lengths for command lines. 
The client must split a command that is too long into multiple commands.

| **Command** | **Maximum Length, bytes** |
|:---|:---|
| series | 256*1024  |
| property | 256*1024  |
| message  | 256*1024  |
| other | 1024  |

### Schema

* New entities, metrics, and tags are automatically created when inserting data.
* The number of unique identifiers is subject to the following default limits: 

|**Type**| **Max Identifier**|
|:---|:---|
|metric| 65535|
|entity| 16777215|
|tag_key| 65535|
|tag_value| 16777215|
|message_type| 65535|
|message_source| 65535|

### Time Field

The timestamp field encodes the time of an observation or message as determined by the source and can be specified with `ms`, `s`, or `d` fields.

|**Field**|**Description**|
|---|:---|
|ms|UNIX milliseconds|
|s|UNIX seconds|
|d|ISO 8601 date: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' |

* If timestamp field in seconds or milliseconds is less than or equal 0, or if it's empty in case of d: prefix, the time is set to server's current time.
* If timestamp field is not specified, time is set to current server time.

### Number Formatting

* Decimal separator is period (`.`).
* No thousands separator.
* No digit grouping.
* Negative numbers use negative sign (`-`) at the beginning of the number.
* Not-a-Number is literal `NaN`.

## Debugging

By default ATSD doesn't return acknowledgements to the client after processing data commands.
Include `debug` command at the start of the line to instruct the server to respond with `ok` for each processed command.

* `debug` with valid command

```ls
$ echo -e "debug series e:station_1 m:temperature=32.2" | nc atsd_host 8081
ok
```

* `debug` with unknown command

```ls
$ echo -e "debug my_command e:station_1 m:temperature=32.2" | nc atsd_host 8081
>no response, connection closed
```

## Command Validation

To validate network received from a client, launch `netcat` utility in server mode, reconfigure the client to send data to netcat port, and dump incoming data to file:

```elm
nc -lk localhost 2081 > command-in.log &

echo -e "series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z" | nc localhost 2081

cat command-in.log
```

## Dropped Commands

Reasons why ATSD server can drop commands:

* Entity, metric or tag names are not valid.
* Timestamp is negative or earlier than `1970-01-01T00:00:00Z`.
* Timestamp field `s:`/`ms:` is not numeric or if `d` field is not in ISO format.
* Metric value could not be parsed as a number using `.` as the decimal separator. Scientific notation is supported.
* Multiple data points for the same entity, metric and tags have the same timestamp in which case commands are considered duplicates and some of them are dropped. This could occur when commands with the same key are sent without timestamp.
* Data is sent using UDP protocol and the client UDP send buffer or the server UDP receive buffer overflows.
* Value is below 'Min Value' or above 'Max Value' limit specified for the metric and the 'Invalid Value Action' is set to `DISCARD`.
* Last command in multi-line UDP packed doesn't terminate with line feed symbol.

To review dropped commands, open command*.log files in ATSD.

![](dropped-commands-logs.png)
