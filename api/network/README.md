# Network API

Network API provides a set of plain text commands for inserting numeric time series, key=value properties, and tagged messages into Axibase Time Series Database via TCP and UDP network protocols.

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

By default ATSD server listenes for incoming commands on the following ports:

* 8081 TCP
* 8082 UDP

## Encryption

To encrypt TCP traffic, setup an IPSEC VPN or establish an [SSH tunnel] (http://axibase.com/products/axibase-time-series-database/writing-data/nmon/ssh-tunneling/).

## Authentication

Authentication and authorization are not supported for plain text commands received over TCP and UDP protocols. 

Utilize [HTTP command](./data/command.md) to send plain-text commands over http/https protocols with authentication and authorization enabled.

## Connection

### Single Command

To send a single command, connect to an ATSD server, send the command in plain text and terminate the connection.

* netcat:echo

```elm
echo series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z | nc 10.102.0.6 8081
```

* netcat:printf

```elm
printf 'series e:station_2 m:temperature=32.2 m:humidity=81.4 s:1463271035' | nc 10.102.0.6 8081
```

* UNIX pipe

```elm
echo series e:station_3 m:temperature=32.2 m:humidity=81.4 > /dev/tcp/10.102.0.6/8081
```

* telnet:one line

```sh
telnet 10.102.0.6 8081 << EOF
series e:station_4 m:temperature=32.2 m:humidity=81.4
EOF
```

* telnet:session

```sh
$ telnet 10.102.0.6 8081
Trying 10.102.0.6...
Connected to 10.102.0.6.
Escape character is '^]'.
series e:station_5 m:temperature=32.2 m:humidity=81.4
^C
Connection closed by foreign host.
```

* java:socket

```java
Socket s = new Socket("10.102.0.6", 8081);
PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
writer.println("series e:station_6 m:temperature=32.2");
s.close();
```

The above examples insert timestamped **temperature** and **humidity** metric samples for **station** entities.

### Multiple Commands

Separate commands by line feed symbol `\n` (LF, `0x0A`) when sending a batch containing multiple commands over the same connection.

Trailing line feed is not required for the last command in the batch.

Use `-e` flag in `echo` commands to enable interpretation of backslash escapes.

```elm
echo -e series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z\\nseries e:station_1 m:temperature=32.1 m:humidity=82.4 d:2016-05-15T00:25:00Z | nc 10.102.0.6 8081
```

```java
Socket s = new Socket("10.102.0.6", 8081);
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

```
$ telnet 10.102.0.6 8081
Trying 10.102.0.6...
Connected to 10.102.0.6.
Escape character is '^]'.
series e:station_1 m:temperature=32.2
property e:station_2 t:location v:city=Cupettino v:state=CA v:country=USA
^C
Connection closed by foreign host.
```

Note that the server will **terminate** the connection if it receives an unknown or malformed command.


```sh
$ telnet 10.102.0.6 8081
Trying 10.102.0.6...
Connected to 10.102.0.6.
Escape character is '^]'.
unknown_command e:station_1 m:temperature=32.2
Connection closed by foreign host.
```

### UDP Datagrams

The UDP protocol doesn't guarantee delivery but may have a higher throughput compared to TCP due to lower overhead. In addition, sending commands with UDP datagrams decouples the client application from the server to minimize the risk of freezes read timeouts.

```elm
echo series e:station_3 m:temperature=32.2 m:humidity=81.4 | nc -u -w1 10.102.0.6 8082
```

```elm
printf 'series e:station_3 m:temperature=32.2 m:humidity=81.4' | nc -u -w1 10.102.0.6 8082
```

Unlike TCP, the last command in a multi-command UDP datagram must terminate with the line feed character.

```elm
echo -e series e:station_33 m:temperature=32.2\\nseries e:station_34 m:temperature=32.1 m:humidity=82.4\\n | nc -u -w1 10.102.0.6 8082
```

### Duplicate Commands

Multiple commands with the same timestamp and key fields may override each others value. 

If such commands are submitted at approximately the same time, there is no guarantee that they will be processed in the order they were received.

* Duplicate example: same key, same current time  

```elm
echo -e series e:station_1 m:temperature=32.2\\nseries e:station_1 m:temperature=42.1 | nc 10.102.0.6 8081
```

* Duplicate example: same key, same time  

```elm
echo -e series e:station_1 m:temperature=32.2 d:2016-05-15T00:10:00Z\\nseries e:station_1 m:temperature=42.1  d:2016-05-15T00:10:00Z | nc 10.102.0.6 8081
```

## Syntax

### Line Syntax

* Command must start with command name such as `series` followed by space-separated fields each identified with a prefix followed by colon symbol and optional field value.

```css
command-name field-prefix:field-name[=field-value]
```

### Fields

* The order of fields is not important.
* Tag and key names are case-insensitive. Text values are case-sensitive and will be stored as submitted.
* Fields such as tag or key start with prefix, colon symbol followed by key=value.
* If field name or value contains white space it needs to be enclosed in double-quotes, for example: `v:os="Ubuntu 14.04"`. 
* Double-quote appearing inside a value must be escaped with backslash, for example: `t:descr="Version is \"Ubuntu 14.04\""`
* Entity, metric and tag names as well as property type and key names must not contain space, quote, and double-quote characters. <br>hen inserted via CSV upload or HTTP API, these characters are converted to underscore. <br>Multiple underscores are replaced with one underscore character.

### Command Length

The server enforces the following maximum length constraints to command lines. 
The client is advised to split a command that is too long into multiple commands.

| **Command** | **Maximum Length, bytes** |
|---|---|---|
| series | 256*1024  |
| property | 256*1024  |
| message  | 256*1024  |
| other | 1024  |

### New Entities/Metrics

* Entity and metric names will be automatically created provided they meet naming requirements.
* The number of unique identifiers in ATSD is subject to the following limits: 

|**Type**| **Max Identifier**|
|-------|---------|
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
|d|ISO 8601 date: yyyy-MM-dd'T'HH:mm:ss.SSSZ |

* If timestamp field in seconds or milliseconds is less than or equal 0, or if it's empty in case of d: prefix, the time is set to server's current time.
* If timestamp field is not specified, time is set to current server time.

## Debugging

By default ATSD doesn't return acknowledgements to the client after processing data commands.
Include `debug` command at the start of the line to instruct the server to respond with `ok` for each processed command.

* `debug` with valid command

```sh
$ echo debug series e:station_1 m:temperature=32.2 | nc 10.102.0.6 8081
ok
```

* `debug` with unknown command

```sh
$ echo debug my_command e:station_1 m:temperature=32.2 | nc 10.102.0.6 8081
>no response, connection closed
```

## Dropped Commands

Reasons why ATSD server can drop commands:

* If multiple commands are sent without including the end of line in the last command.
* If timestamp is earlier than `1970-01-01T00:00:00Z`.
* If ATSD cannot parse the timestamp, for example: if `s` or `ms` value is not numeric or if `d` value is not in ISO format.
* If multiple data points for the same entity, metric and tags have the same timestamp (commands are considered duplicate and dropped).
* If commands are sent without a timestamp, in this case ATSD will assign current server time as the timestamp - this case lead to the same entity, metric and tags having identical timestamps - resulting in duplicate commands being dropped.
* If data is sent using UDP protocol the receive buffer can sometimes become oversaturated.
* If metric 'Min/Max Value' setting is set together with the `DISCARD` 'Invalid Value Action' setting in ATSD, then values that fall outside the 'Min/Max Value' are discarded.
* If entity, metric or tag names are not valid.
* If value cannot be parsed into double - decimal point must be a period (`.`), scientific notation is supported.

