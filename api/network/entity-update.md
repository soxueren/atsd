## Command: `entity-update`

> entity-update e:{entity} t:{tag-name}={tag-value}

```
printf 'entity-update e:server1 t:fqdn=server1.corp.axibase.com t:environment="Production EMEA"' | nc atsd-server 8081
```

`entity-update` command is used to update entity tags.

* `{entity}` is case-insensitive.
* multiple `t` flags can be submitted in one request
* existing `tags` should not be deleted
* `{tag-name}` is case-insensitive and should be converted to lower case on save
* `{tag-value}` with whitespace and equal symbols must be double-quoted
