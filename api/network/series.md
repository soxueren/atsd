# `series` Command

```
series e:{entity} s:{unix_seconds} t:{key}={value} t:{key}={value} m:{metric}={value} m:{metric}={value}
```

Insert one or multiple metric samples for an entity and tags into the database. The order of fields is not important.

> Command: `series`, `properties` and `message` can be submitted with UDP protocol in addition to TCP/IP.

## Fields

| **Field** | **Required** |
|-----------|--------------|
| e         | yes          |
| s         | no           |
| t         | no           |
| m         | yes          |

## Examples

```
series e:server001 s:1425482080 m:cpu_used=72.0 m:memory_used=94.5
```

```
series e:server001 ms:1425482080000 m:cpu_used=72.0 m:memory_used=94.5
```

```
series e:server001 d:2015-03-04T12:43:20Z m:cpu_used=72.0 m:memory_used=94.5
```

```
series e:server001 m:disk_used_percent=20.5 m:disk_size_mb=10240 t:mount_point=/ t:disk_name=/sda1
```

## UDP command

```
echo series e:DL1866 m:speed=1950 m:altitude=300 | nc -u atsd_server.com 8082
```


> The UDP protocol doesn't guarantee delivery of each command but may have a higher throughput compared to TCP/IP due to lower overhead.
