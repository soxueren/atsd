#Insert Multiple Series Example

## Request 
### URI
```
https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[{
    "entity": "nurswgvml007",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 17.7 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 17.8 }
    ]
},{
    "entity": "c",
    "metric": "df.disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "d": "2016-05-05T05:49:18.127Z", "v": 42.2 },
      { "d": "2016-05-05T05:50:18.312Z", "v": 41.8 }
    ]
}]
```
### curl 
``` css
curl https://atsd_host:8443/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "df.disk_used_percent", "tags": {"file_system": "/sda", "mount_point": "/"},"data": [{ "t": 1462427358127, "v": 22.0 }]}, {"entity": "df.disk_used_percent", "metric": "df.disk_used_percent", "tags": {"file_system": "/sdb", "mount_point": "/export"},"data": [{ "t": 1462427358666, "v": 22.0 }]}]'
```
## Response

