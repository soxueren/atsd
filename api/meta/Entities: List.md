## Entities: List

### Request Parameters

```
GET /api/v1/entities
```

> Request

```
http://atsd_hostname:8088/api/v1/entities?timeFormat=iso&limit=2&expression=name%20like%20%27nurs*%27
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|active|no|Filter entities by last insert time. If `active = true`, only entities with positive `last_insert_time` are included in the response|
|expression|no|Use `name` variable for entity name. Use `*` placeholder in `like` expresions|
|tags|no|Specify entity tags to be included in the response, use `tags=*` as a wildcard (returns all existing tags)|
|minInsertDate|no|return entities with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return entities with lastInsertTime less than specified time, accepts iso date format|
|limit|no|Limit response to first N entities, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|


### Response Fields

> Response

```json
 [
    {
        "name": "nurswgdkr002",
        "enabled": true,
        "lastInsertDate": "2015-09-04T15:43:36.000Z"
    },
    {
        "name": "nurswgdkr002/",
        "enabled": true
    }
]
```

| **Field**                            | **Description**                                                                             |
|---|---|
| name                                | Entity name (unique)                                                                        |
| enabled                             | Enabled status. Incoming data is discarded for disabled entities                            |
| lastInsertTime                      | Last time value was received by ATSD for this entity. Time specified in epoch milliseconds. |
 |lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
|tags as requested by tags parameter|User-defined tags|

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>

### Examples

**Fetch entities starting with `nur` and containing any tags.**

```
http://atsd_hostname:8088/api/v1/entities?tags=*&expression=name%20like%20%27nur*%27
```

> Response

```json
[
    {
        "name": "nurswgvml003",
        "enabled": true,
        "lastInsertTime": 1442331411000,
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "app-test": "1",
            "ip": "10.102.0.2",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml006",
        "enabled": true,
        "lastInsertTime": 1442331411000,
        "tags": {
            "app": "Hadoop/HBASE",
            "ip": "10.102.0.5",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml007",
        "enabled": true,
        "lastInsertTime": 1442331411000,
        "tags": {
            "alias": "007",
            "app": "ATSD",
            "ip": "10.102.0.6",
            "loc_area": "dc2",
            "loc_code": "nur,nur",
            "os": "Linux"
        }
    }
]
```
 
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

**Fetch entities starting with `nur` and with tag `app` containing `hbase` (case insensitive)**

```
http://atsd_hostname:8088/api/v1/entities?timeFormat=iso&limit=2&tags=app&expression=name%20like%20%27nur%27%20and%20lower%28tags.app%29%20like%20%27hbase%27
```

> Expression

```
name like 'nur*' and `lower(tags.app)` like '*hbase*'
```

> Response

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

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

**Fetch all tags for all entities starting with `nurswgvml00`**

> Request

```
http://atsd_hostname:8088/api/v1/entities?timeFormat=iso&expression=name%20like%20%27nurswgvml00*%27&tags=*
```

> Response

```json
[
    {
        "name": "nurswgvml003",
        "enabled": true,
        "lastInsertDate": "2015-09-15T14:31:34.000Z",
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "ip": "10.102.0.2",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml006",
        "enabled": true,
        "lastInsertDate": "2015-09-15T14:31:37.000Z",
        "tags": {
            "app": "Hadoop/HBASE",
            "ip": "10.102.0.5",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml007",
        "enabled": true,
        "lastInsertDate": "2015-09-15T14:31:32.000Z",
        "tags": {
            "alias": "007",
            "app": "ATSD",
            "ip": "10.102.0.6",
            "loc_area": "dc2",
            "loc_code": "nur,nur",
            "os": "Linux"
        }
    }
]
```
