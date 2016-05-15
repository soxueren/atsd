# Network API

Network API provides a set of plain text commands for inserting numeric time series, key=value properties, and tagged messages into Axibase Time Series Database via TCP and UDP network protocols.

You can use `netcat`, `telnet`, `UNIX pipes`, and any programming language that lets you connect to ATSD server via TCP/UDP protocol.

## Supported Commands

### Data Commands

* series
* property
* message
* csv
* nmon

### Control Commands

* ping
* debug
* time
* version
* exit

## Ports

By default ATSD server listenes for incoming commands on the following ports:

* 8081 TCP
* 8082 UDP

## Connection

### Single Command

To send a single command, connect to an ATSD server, send the command in plain text and terminate the connection.

* netcat:echo

```json
echo series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z | nc 10.102.0.6 8081
```

* netcat:printf

```json
printf 'series e:station_1 m:temperature=32.2 m:humidity=81.4 s:1463271035' | nc 10.102.0.6 8081
```

The above example insert timestamped **temperature** and **humidity** samples (observations) by **station_1** using `netcat` utility.

### Multiple Commands

Commands must be separated by line break symbol `\n` in order to send multiple commands over the same connection.

Use `-e` flag in `echo` commands to enable interpretation of backslash escapes.

```json
echo -e series e:station_1 m:temperature=32.2 m:humidity=81.4 d:2016-05-15T00:10:00Z\\nseries e:station_1 m:temperature=32.1 m:humidity=82.4 d:2016-05-15T00:25:00Z | nc 10.102.0.6 8081
```

Alternatively, the application can establish a persistent connection, write multiple commands, one command per line, and close the connection. Trailing end of line is not required when the connection is closed.

```
telnet 10.102.0.6 8081
```

The commands are processed as they're received by the server, without buffering.

Client can submit commands of different types during the same session.

> The server will break the connection if it receives an unknown or malformed command.

> A duplicate series insert with the same key and timestamp will override the previously stored value. If duplicate inserts are submitted at approximately the same time, there is no guarantee that they will be processed in the order they were received.

## Encryption

Telnet traffic can be tunneled over SSH if required: [Read Axibase guide on SSH Tunneling] (http://axibase.com/products/axibase-time-series-database/writing-data/nmon/ssh-tunneling/).

## Syntax

### Line Syntax

* Command must start with command name such as `series` followed by space-separated fields each identified with a prefix followed by colon symbol.

```css
command-name field-name:field-key[=field-value]EOL
```

### Command Length

The server enforces the following maximum length constraints to command lines. 
The client is advised to split a command that is too long into multiple commands.

| **Command** | **Maximum Length, bytes** |
|---|---|---|
| series | 256*1024  |
| property | 256*1024  |
| message  | 256*1024  |
| other | 1024  |

### Fields

* The order of fields is not important.
* Fields such as tag or key start with prefix, colon symbol followed by key=value.
* If field name or value contains white space it needs to be enclosed in double quotes.
* Entity, metric and tag names as well as property type and key names must not contain the following characters: space, quote, double quote. When inserted via CSV upload or HTTP API, these characters are converted to underscore. Multiple underscores are collapsed into one underscore character.
* Tag and key names are case-insensitive. Values are case-sensitive and will be stored as submitted.
* Double quotes must be escaped with backslash, for example: `\"Ubuntu 14.04\"`

```css
property e:axibase ms:1438178237215 t:collectd-atsd v:host=axibase v:OS_Version=\"Ubuntu 14.04\"
```

### New Entities/Metrics

* Entity and metric names will be automatically created provided they meet naming requirements.

### Time Field

The timestamp field encodes the time of an observation or message as determined by the source and can be specified with `ms`, `s`, or `d` fields.

|**Field**|**Description**|
|---|:---|
|ms|UNIX milliseconds|
|s|UNIX seconds|
|d|ISO 8601 date: yyyy-MM-dd'T'HH:mm:ss.SSSZ |

* If timestamp field in seconds or milliseconds is less than or equal 0, or if it's empty in case of d: prefix, the time is set to server's current time.
* If timestamp field is not specified, time is set to current server time.



## Maximum Records

|Type| Max Id|
|-------|---------|
|metric| 65535|
|entity| 16777215|
|tag_key| 65535|
|tag_value| 16777215|
|message_type| 65535|
|message_source| 65535|

## Entity Names

<aside class="notice">
Entity, metric and tag names as well as property type and key names must not contain the following characters: space, quote, double quote. When inserted via CSV upload or HTTP API, these characters are converted to underscore. Multiple underscores are collapsed into one underscore character.
</aside>

## Debugging

By the default the server doesn't respond to the client when processing series, property and message commands.
The client may include `debug` field at the start of the line (as the first word) which instructs the server to respond with `ok` for each processed command.

## [Dropped Comands](https://github.com/axibase/atsd-docs/blob/master/api/network/dropped-commands.md)

