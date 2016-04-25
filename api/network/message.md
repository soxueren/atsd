## Command: `message`

Insert a text message for a given entity and tags.

```
message e:{entity} s:{unix_seconds} t:{key}={value} t:{key}={value} m:{message_text}
```

> Example

```
message e:server001 d:2015-03-04T12:43:20+00:00 t:type=application t:source=cron t:subject="my subject" m:"Hello, world"
```

> UDP command

```
echo message e:DL1866 d:2015-03-04T12:43:20+00:00 t:type=application t:source=cron t:subject="my subject" m:"Hello, world" | nc -u atsd_server.com 8082
```

| **Field** | **Required** |                      
|---|---|
| e         | yes          |
| s         | no           |
| t         | no           |
| m         | no           |

<aside class="success">
Message text or one of the tags is required, otherwise the message will be dropped silently.
Enclose tag values and message text containing white space in double quotes.
</aside>
