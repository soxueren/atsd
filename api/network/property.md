## Command: `property`

Insert property of specified type with keys and values for a given entity.

```ls
property e:{entity} s:{unix_seconds} t:{type} k:{key}={value} k:{key}={value} v:{name}={value} v:{name}={value}
```

> Example

```ls
property e:abc001 t:disk k:name=sda v:size=203459 v:fs_type=nfs
```

> UDP command

```sh
echo -e "property e:DL1866 t:disk k:name=sda v:size=203459 v:fs_type=nfs" | nc -u -w1 atsd_server 8082
```

```sh
printf 'property e:DL1866 t:disk k:name=sda v:size=203459 v:fs_type=nfs' | nc -u -w1 atsd_server 8082
```

| **Field** | **Required** |
|-----------|--------------|
| e         | yes          |
| s         | no           |
| ms        | no           |
| d         | no           |
| t         | yes          |
| k         | no           |
| v         | yes          |
