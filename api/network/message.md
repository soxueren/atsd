## message Command

## Description

Insert a text message or a log event for a given entity and tags into the database.

Special tags supported by the command:

* `type` - message type, using in index
* `source` - message source, using in index
* `severity` - criticality [level](#severity), can be specified in numeric or text format.

Type, source, and entity fields are indexed and therefore provide fast response time when used in search.

## Syntax

```css
message e:{entity} t:type={type} t:source={source} t:severity={} m:{message-text} s:{seconds}
```

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| e         | yes          | Entity name. |
| t         | no           | Tags, including reserved tags `type`, `source`, `severity`. |
| m         | no           | Message text. |
| s         | no           | Time in UNIX seconds. | 
| ms        | no           | Time in UNIX milliseconds. | 
| d         | no           | Time in ISO format. | 

> If time fields are omitted, the record is inserted with the current server time.

Message text or one of the tags is required, otherwise the message will be dropped silently.

Enclose tag values and message text containing space in double quotes.

### ABNF Syntax

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
  ; MSP - one or multiple spaces
  ; entity or at least one tag is required
command = "message" MSP entity MSP message [MSP tag-type] [MSP tag-source] [MSP tag-severity] *(MSP tag) [MSP time]
  ; NAME consists of visible characters. 
  ; double-quote must be escaped with backslash.
entity = "e:" NAME
message = "m:" TEXTVALUE
  ; TEXTVALUE consists of visible characters and space. 
  ; double-quote must be escaped with backslash. 
  ; tag values containing space must me enclosed with double-quote.  
tag-type = "t:type=" TEXTVALUE
tag-source = "t:source=" TEXTVALUE
  ; severity value is case-insensitive
  ; https://tools.ietf.org/html/rfc7405#section-2.1
tag-severity = "t:severity=" (%x30-37 / %i("UNDEFINED" / "UNKNOWN" / "NORMAL" / "WARNING" / "MINOR" / "MAJOR" / "CRITICAL" / "FATAL" ))
tag = "t:" NAME "=" TEXTVALUE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE-INTEGER
time-second = "s:" POSITIVE-INTEGER
time-iso = "d:" ISO-DATE
```

## Severity

| **Code** | **Name** |
|:---|:---|
|0 | UNDEFINED|
|1 | UNKNOWN|
|2 | NORMAL|
|3 | WARNING|
|4 | MINOR|
|5 | MAJOR|
|6 | CRITICAL|
|7 | FATAL|

## Examples

```ls
message e:server001 d:2015-03-04T12:43:20Z t:type=application t:source=cron t:subject="my subject" m:"Hello, world"
```

```ls
message e:server-001 t:type="basic" m:"notify message"
```

```ls
message e:server-001 t:type="advanced" t:severity=6 t:source="alert_monitor" m:"warning message"
```
