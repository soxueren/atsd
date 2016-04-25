## Series: Insert

Payload - an array of series with `data` arrays.

### Request Parameters

```
POST /api/v1/series/insert
```

> Request

```json
[{
    "entity": "nurswgvml007",
    "metric": "disk_used_percent",
    "tags": {"file_system": "/sda", "mount_point": "/"},
    "data": [
      { "t": 1423139581216, "v": 22.0},
      { "t": 1423139640573, "v": 24.0}
    ]
},{
    "entity": "nurswgvml007",
    "metric": "disk_used_percent",
    "tags": {"file_system": "/sdb", "mount_point": "/export"},
    "data": [
      { "t": 1423139581210, "v": 42.0},
      { "t": 1423139640570, "v": 44.0}
    ]
}]
```

|**Name**|**Required**|**Description**|
|---|---|---|---|
| entity | yes | entity name |
| metric | yes | metric name |
| tags | no | an object with named keys, where a key is a tag name and a value is tag value |
| data | yes | an array of key-value objects, where key 't' is unix milliseconds anf 'v' is the metrics value at time 't' |
