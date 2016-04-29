## COMMAND

```
POST /api/v1/command
```

```
series e:DL1866 m:speed=650 m:altitude=12300
property e:abc001 t:disk k:name=sda v:size=203459 v:fs_type=nfs
series e:DL1867 m:speed=450 m:altitude=12100
message e:server001 d:2015-03-04T12:43:20+00:00 t:subject="my subject" m:"Hello, world"
```

Content-type: text/plain

Payload: text containing multiple insert commands: [Series](#command-'series'), [Property](#command-'property'), [Message](#command-'message')

Supported commands:

* series
* property
* message



