# List entities starting with `nur` and with tag `app` containing `hbase` 

## Request

### URI

```elm
GET https://atsd_server:8443/api/v1/entities?timeFormat=iso&limit=2&tags=app&expression=name%20like%20%27nur%27%20and%20lower%28tags.app%29%20like%20%27hbase%27
```

### Expression

```
name like 'nur*' and `lower(tags.app)` like '*hbase*'
```

## Response

```json
[{
    "name": "nurswgvml006",
    "enabled": true,
    "lastInsertDate": "2015-09-04T15:43:36.000Z",
    "tags": {
        "app": "Hadoop/HBASE"
    }
},
{
    "name": "nurswgvml203",
    "enabled": true,
    "tags": {
        "app": "Hadoop/Hbase master node"
    }
}]
```
<aside class="success">
Note: 'lower(text)' is a utility function. Alternatively, any Java string functions can be used to modify values, for example: 'tags.app.toLowerCase()'
</aside>
