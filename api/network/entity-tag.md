## Command: `entity-tag`

`entity-tag` command creates the entity if it doesn't exist, and updates only the tags that are specified in the request.
Other tags that exist for the given entity should remain unchanged.

> entity-tag e:{entity} t:{tag-name}={tag-value}

```
printf 'entity-tag e:server2 t:fqdn=server2.corp.axibase.com t:environment="Testing"' | nc atsd-server 8081
```
