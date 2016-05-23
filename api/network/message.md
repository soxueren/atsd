## Command: `message`

Insert a text message for a given entity and tags.

Reserved tags for message commands:

* `severity` - the level of warning criticality. Possible values from 0 to 7 (7 being the highest).
* `type` - use to index received messages and speed up retreival of events
* `source` - use to index received messages and speed up retreival of events

```
message e:{entity} s:{unix_seconds} t:{key}={value} t:{key}={value} m:{message_text}
```

> Example

```
message e:server001 d:2015-03-04T12:43:20Z t:type=application t:source=cron t:subject="my subject" m:"Hello, world"
```

> UDP command

```
echo message e:DL1866 d:2015-03-04T12:43:20Z t:type=application t:source=cron t:subject="my subject" m:"Hello, world" | nc -u -w1 atsd_server 8082
```

```
printf 'message e:DL1866 d:2015-03-04T12:43:20Z t:type=application t:source=cron t:subject="my subject" m:"Hello, world"' | nc -u -w1 atsd_server 8082
```

| **Field** | **Required** |                      
|---|---|
| e         | yes          |
| s         | no           |
| ms        | no           |
| d         | no           |
| t         | no           |
| m         | no           |

<aside class="success">
Message text or one of the tags is required, otherwise the message will be dropped silently.
Enclose tag values and message text containing white space in double quotes.
</aside>

### ABNF Syntax

Rules inherited from [generic ABNF](generic-abnf.md).

```properties
  ; MSP - one or multiple spaces
  ; entity or at least one tag is required
command = "message" MSP entity *(MSP tag) [MSP time]
  ; NAME consists of visible characters. 
  ; double-quote must be escaped with backslash.
entity = "e:" NAME
  ; TEXTVALUE consists of visible characters and space. 
  ; double-quote must be escaped with backslash. 
  ; tag values containing space must me quoted with double-quote.  
tag = "t:" NAME "=" TEXTVALUE
time = time-millisecond / time-second / time-iso
time-millisecond = "ms:" POSITIVE-INTEGER
time-second = "s:" POSITIVE-INTEGER
time-iso = "d:" ISO-DATE
```

## Examples

```ls
property e:server-001 t:disk-config k:mount_point=/ k:name=sda1 v:size_gb=192 v:fs_type=nfs
```

```ls
property e:server-001 t:operating_system v:type=Linux d:2015-03-04T12:43:20Z
```
