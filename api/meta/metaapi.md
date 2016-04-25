# Meta API

## Overview

The Meta API lets you query and update meta-data about metrics, entities, entity groups, rules, and tags from Axibase Time-Series Database (ATSD) server. 

The API uses standard HTTP requests, such as: `GET`, `POST`, and `PATCH`. 

All requests must be authorized using BASIC AUTHENTICATION. 

In response, the ATSD server sends an HTTP status code (such as a 200-type status for success or 400-type status for failure) that reflects the result of each request. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses.


### Authentication

* User authentication is required.
* Authentication method: `HTTP BASIC`.
* Client may use session cookies to execute multiple requests without repeating authentication.
* Cross-domain requests are allowed. The server includes the following headers in each response:

`Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization`

`Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE`
    
`Access-Control-Allow-Origin: *`

### Compression

* Clients may send compressed data by specifying Content-Encoding: gzip

### Expression Syntax

**Syntax enhancements**

> Examples

```
name like 'nur*' and tags.app != ''
```

```
name = 'nurswgvml003'
```

```
name like 'nur*' and tags.os = 'Linux'
```

```
tags.ip like '10*01'
```

You can use both `=` and `==` as equality operator

You can use `and`, `or`, `not` logical operators as well as `&&` , `||`, `!`

You can use `in` operator for string collections, for example name in `('test1', 'test2')`

You can use `like` operator instead of regular expression: `name like nur*`. Placeholders `*` and `%` mean zero or more characters. Placeholder `.` means any character.

**Additional Functions**

* Collection list(String value);
* Collection list(String value, String delimiter);
* boolean likeAll(Object message, Collection values);
* boolean likeAny(Object message, Collection values);
* String upper(Object value);
* String lower(Object value);
* Collection collection(String name);

| Function   | Description                                                                         |
|------------|-------------------------------------------------------------------------------------|
| list       | Splits a string by delimiter. Default delimiter is comma                            |
| likeAll    | returns true, if every element in the collection of patterns matches message        |
| likeAny    | returns true, if at least one element in the collection of patterns matches message |
| upper      | converts the argument to upper case                                                 |
| lower      | converts the argument to lower case                                                 |
| collection | returns ATSD named collection                                                       |

**Variables**

You can access Entity/Metric tags by name, and access Entity/Metic name using special variable `name`

All the variables are string variables.

if entity or metric does not have some tag, expression engine treats this tag variable as an empty string.

For example expression `tags.app != ''` will find all entities that have app tag

## Metric: List

### Request Parameters

```
GET /api/v1/metrics
```

> Request

```
http://atsd_server:8088/api/v1/metrics?limit=2
```

```
http://atsd_server:8088/api/v1/entities/{entity}?timeFormat=iso
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for metric name. Use `*` placeholder in `like` expresions|
|active|no|Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|tags|no|Specify metric tags to be included in the response, use `tags=*` as a wildcard (returns all existing tags)|
|minInsertDate|no|return metrics with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return metrics with lastInsertTime less than specified time, accepts iso date format|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields

> Response

```json
[
    {
        "name": "m-vers",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1445205600000,
        "versioned": true
    },
    {
        "name": "24h_average",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "versioned": false
    }
]
```

|**Field**|**Description**|
|---|---|
|name|Metric name (unique)|
|label|Metric label|
|enabled|Enabled status. Incoming data is discarded for disabled metrics|
|dataType|short, integer, float, long, double|
|timePrecision|seconds, milliseconds|
|persistent |Persistence status. Non-persistent metrics are not stored in the database and are only used in rule engine.|
|counter|Metrics with continuously incrementing value should be defined as counters|
|filter |If filter is specified, metric puts that do not match the filter are discarded|
|minValue |Minimum value. If value is less than Minimum value, Invalid Action is triggered|
|maxValue|Maximum value. If value is greater than Maximum value, Invalid Action is triggered|
|invalidAction |None - retain value as is; Discard - don't process the incoming put, discard it; Transform - set value to `min_value` or `max_value`; `Raise_Error` - log error in ATSD log|
|description |Metric description|
|retentionInterval|Number of days to retain values for this metric in the database|
|lastInsertTime|Last time value was received by ATSD for this metric. Time specified in epoch milliseconds.|
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
|tags as requested by tags parameter|User-defined tags|
|versioned| If set to true, enables versioning for the specified metric. When metrics is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.|

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>

### Examples

### Fetch all 'disk' metrics

Fetch all metrics whos name includes `disk`, including all tags.

```
http://atsd_server:8088/api/v1/metrics?tags=*&expression=name%20like%20%27*disk*%27
```

> Response

```json
[
    {
        "name": "aws_ec2.diskreadbytes.average",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.maximum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    },
    {
        "name": "aws_ec2.diskreadbytes.minimum",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {},
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertTime": 1447858020000,
        "versioned": false
    }
]
```

### Fetch metrics with tag 'table'

```
http://atsd_server:8088/api/v1/metrics?timeFormat=iso&tags=table&limit=2&expression=tags.table%20!=%20%27%27
```

```
expression=tags.table != ''
```

> Response

```json
[
    {
        "name": "collector-csv-job-exec-time",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "versioned": false
    },
    {
        "name": "collector-http-connection",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "axibase-collector"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:57:22.649Z",
        "versioned": false
    }
]
```

### Fetch metrics by name and tag

Fetch metrics starting with `nmon` and with tag `table` starting with `CPU`

```
http://atsd_server:8088/api/v1/metrics?timeFormat=iso&active=true&tags=table&limit=2&expression=name%20like%20%27nmon*%27%20and%20tags.table%20like%20%27*CPU*%27
```

> Expression:

```
name like 'nmon*' and `tags.table` like '*CPU*'
```

> Response

```json
[
    {
        "name": "nmon.cpu.busy%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    },
    {
        "name": "nmon.cpu.idle%",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "tags": {
            "table": "CPU Detail"
        },
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-11-18T14:59:14.000Z",
        "versioned": false
    }
]
```

## Metric: Get

```
GET /api/v1/metrics/{metric}
```

> Request

```
http://atsd_server:8088/api/v1/metrics/mpstat.cpu_busy?timeFormat=iso
```

> Response

```json
{
    "name": "mpstat.cpu_busy",
    "enabled": true,
    "dataType": "FLOAT",
    "counter": false,
    "persistent": true,
    "tags": {},
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "NONE",
    "lastInsertDate": "2015-10-20T12:13:26.000Z",
    "versioned": false
}
```

Displays metric properties and its tags.

**Response Fields:**

See: [Metrics: List](#metrics:-list)

## Metric: Create or Replace

```
PUT /api/v1/metrics/{metric}
```

Create a metric with specified properties and tags or replace an existing metric.
This method creates a new metric or replaces an existing metric. 

### Request Fields

> Request

```json
{
    "enabled": true,
    "counter": false,
    "persistent": true,
    "dataType": "FLOAT",
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "TRANSFORM",
    "versioned": true
}
```

|**Field**|**Description**|
|---|---|
|label|Metric label|
|enabled|Enabled status. Incoming data is discarded for disabled metrics|
|dataType|short, integer, float, long, double|
|timePrecision|seconds, milliseconds|
|persistent |Persistence status. Non-persistent metrics are not stored in the database and are only used in rule engine.|
|counter|Metrics with continuously incrementing value should be defined as counters|
|filter |If filter is specified, metric puts that do not match the filter are discarded|
|minValue |Minimum value. If value is less than Minimum value, Invalid Action is triggered|
|maxValue|Maximum value. If value is greater than Maximum value, Invalid Action is triggered|
|invalidAction |None - retain value as is; Discard - don't process the incoming put, discard it; Transform - set value to `min_value` or `max_value`; `Raise_Error` - log error in ATSD log|
|description |Metric description|
|retentionInterval|Number of days to retain values for this metric in the database|
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"table": "axibase-collector"}`|
|versioned| If set to true, enables versioning for the specified metric. When metrics is versioned, the database retains the history of series value changes for the same timestamp along with version_source and version_status.|

<aside class="notice">
If only a subset of fields is provided for an existing metric, the remaining properties and tags will be deleted.
</aside>

## Metric: Update

```
PATCH /api/v1/metrics/{metric}
```
> Request

```
{
    "tags": {
        "table": "CPU Detail"
    }
}
```

Update specified properties and tags for the given metric.
This method updates specified properties and tags for an existing metric. 

### Request Fields

See: [Metric: Create or Replace](#metric:-create-or-replace)

<aside class="notice">
Properties and tags that are not specified are left unchanged.
</aside>

## Metric: Delete

```
DELETE /api/v1/metrics/{metric}
```

Delete the metric. Data collected for the metric will be removed asynchronously in the background.

## Entities: List

### Request Parameters

```
GET /api/v1/entities
```

> Request

```
http://atsd_server:8088/api/v1/entities?timeFormat=iso&limit=2&expression=name%20like%20%27nurs*%27
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
http://atsd_server:8088/api/v1/entities?tags=*&expression=name%20like%20%27nur*%27
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
http://atsd_server:8088/api/v1/entities?timeFormat=iso&limit=2&tags=app&expression=name%20like%20%27nur%27%20and%20lower%28tags.app%29%20like%20%27hbase%27
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
http://atsd_server:8088/api/v1/entities?timeFormat=iso&expression=name%20like%20%27nurswgvml00*%27&tags=*
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

## Entity: Get

```
GET /api/v1/entities/{entity}
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml006?timeFormat=iso
```

> Response

```json
{
    "name": "nurswgvml006",
    "enabled": true,
    "lastInsertDate": "2015-09-04T15:39:40.000Z",
    "tags": {
        "app": "Hadoop/HBASE",
        "ip": "10.102.0.5",
        "os": "Linux"
    }
}
```

Displays entity properties and all tags.

**Response Fields:**

See: [Entities: List](#entities:-list)

## Entity: Entity Groups

```
GET /api/v1/entities/{entity}/groups
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/groups
```

> Resonse

```json
[
    {
        "name": "VMware VMs",
        "tags": {}
    },
    {
        "name": "environment-prod",
        "tags": {
            "tag1": "v1"
        }
    },
    {
        "name": "nmon-linux",
        "tags": {}
    },
    {
        "name": "nur-entities-name",
        "tags": {}
    }
    {
        "name": "nurswg-dc1",
        "tags": {}
    },
    {
        "name": "scollector-linux",
        "tags": {}
    },
    {
        "name": "scollector-nur",
        "tags": {}
    },
    {
        "name": "scollector-uptime",
        "tags": {}
    },
    {
        "name": "solarwind-vmware-vm",
        "tags": {}
    },
    {
        "name": "tcollector - linux",
        "tags": {}
    }
]
```

Returns an array of Entity Groups to which the entity belongs. Entity-group tags are included in the reponse.

## Entity: Create or Replace

```
PUT /api/v1/entities/{entity}
```

```
{
    "tags": {
        "alias": "vmware_host"
    }
}
```
Create an entity with specified properties and tags or replace the properties and tags of an existing entity.
This method creates a new entity or replaces the properties and tags of an existing entity. 

<aside class="notice">
If only a subset of fields is provided for an existing entity, the remaining properties will be set to default values and tags will be deleted.
</aside>

### Request Fields

| **Field**                            | **Description**                                                                             |
|---|---|
| enabled                             | Enabled status. Incoming data is discarded for disabled entities.                           |
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"alias": "vmware_host"}`|

## Entity: Update

Update specified properties and tags for the given entity.

### Request Fields

```
PATCH /api/v1/entities/{entity}
```

> Request

```
{
    "tags": {
        "alias": "cadvisor"
    }
}
```

See: [Entity: Create or Replace](#entity:-create-or-replace)

<aside class="notice">
PATCH method updates specified properties and tags for an existing entity. Properties and tags that are not specified are left unchanged.
</aside>

## Entity: Delete

```
DELETE /api/v1/entities/{entity}
```

Delete the entity. Delete the entity from any Entity Groups that it belongs to.
Data collected by the entity will be removed asynchronously in the background.

## Entity Groups: List

### Request Parameters

```
GET /api/v1/entity-groups
```

> Request

```
http://atsd_server:8088/api/v1/entity-groups
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|expression|no|Use `name` variable for entity group name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity group tags to be included in the response|
|limit|no|Limit response to first N entity groups, ordered by name.|

### Response Fields

> Response

```json
[
{"name":"HP Servers"},
{"name":"Linux"},
{"name":"MQ black list"},
{"name":"MQ white list"},
{"name":"environment-ProD","expression":"environment = 'ProD'"}
]
```

| **Name**   | **Description**                                   |
|------------|---------------------------------------------------|
| name       | Entity group name (unique)                        |
| expression | Entity group expression                           |
| tags       | Entity group tags, as requested by tags parameter |

### Sample Request

> Request

```
http://atsd_server:8088/api/v1/entity-groups?tags=os_level&limit=2&expression=name%20like%20%27nmon*%27
```

> Expression

```
    name like 'nmon*'
```

> Response

```json
[{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3"
    }
},
{
    "name": "nmon-linux"
}]
```

## Entity Group: Get

```
GET /api/v1/entity-groups/{group}
```

> Request

```
http://atsd_server:8088/api/v1/entity-groups/nmon-aix
```

> Response

```json
{
    "name": "nmon-aix",
    "tags": {
        "os_level": "aix 6.3" 
    }
}
```

Displays entity group properties and all tags.

**Response Fields:**

See: [Entity Groups: Get](#entity-groups:-get)

## Entity Group: Create or Replace

```
PUT /api/v1/entity-groups/{entity-group}
```

Create an entity group with specified properties and tags or replace properties and tags for an existing entity group.
This method creates a new entity group or replaces the properties and tags of an existing entity group. 

<aside class="notice">
If only a subset of fields is provided for an existing entity group, the remaining properties and tags will be deleted.
</aside>

### Request Fields

| **Field**   | **Description**                                   |
|------------|---------------------------------------------------|
| expression | Entity group expression                           |
|tags|User-defined tags, `"tagKey": "tagValue"`, like `"tags": {"os_level": "aix 6.3"}`|

## Entity Group: Update

```
PATCH /api/v1/entity-groups/{entity-group}
```

Update specified properties and tags for the given entity group.
This method updates specified properties and tags for an existing entity group. 

### Request Fields

See: [Entity Group: Create or Replace](#entity-group:-create-or-replace)

<aside class="notice">
Properties and tags that are not specified are left unchanged.
</aside>

## Entity Group: Delete

```
DELETE /api/v1/entity-groups/{entity-group}
```

Delete the entity group. 

<aside class="notice">
Entities that are members of the group are retained.
</aside>

## Entity Group: Get Entities

### Request Parameters

```
GET /api/v1/entity-groups/{group}/entities
```

> Request

```
http://atsd_server:8088/api/v1/entity-groups/nur-entities-name/entities?timeFormat=iso&tags=*&limit=3
```

|**Parameter**|**Required**|**Description**|
|---|---|---|
|active|no| Filter entities by `last_insert_time`. If `active = true`, only entities with positive `last_insert_time` are included in the response|
|expression|no|Use `name` variable for entity name. Use `*` placeholder in `like` expressions|
|tags|no|Specify entity tags to be included in the response|
|limit|no|Limit response to first N entities, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields

> Response

```json
[
    {
        "name": "atsd_server",
        "enabled": true,
        "tags": {}
    },
    {
        "name": "nurswgvml003",
        "enabled": true,
        "lastInsertDate": "2015-09-04T15:43:36.000Z",
        "tags": {
            "app": "Shared NFS/CIFS disk, ntp server",
            "ip": "10.102.0.2",
            "os": "Linux"
        }
    },
    {
        "name": "nurswgvml004",
        "enabled": true,
        "tags": {}
    }
]
```

| **Field**                                 | **Description**                                                                             |
|------------------------------------------|---------------------------------------------------------------------------------------------|
| name                                     | Entity name (unique)                                                                        |
| enabled                                  | Enabled status. Incoming data is discarded for disabled entities                            |
| lastInsertTime                           | Last time value was received by ATSD for this entity. Time specified in epoch milliseconds. |
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
|tags as requested by tags parameter|User-defined tags|

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>

## Entity Group: Add Entities

Add specified entities to entity group.

### `add` action fields

```
PATCH /api/v1/entity-groups/{group}/entities
```

> Request

```json
[
  {
    "action" : "add",
    "createEntities": true,
    "entities" : 
        [
            {"name":"nurswgvml010"},
            {"name":"nurswgvml011"}
        ]
  }
]
```

| **Field**  | **Required** | **Description**                                                                                |
|----------------|--------------|-------------------|------------------------------------------------------------------------------------------------|
| createEntities | no       | Automatically create new entities from the submitted list if such entities don't already exist. Default value: true |

### Multiple actions

> Request

```json
[
  {
    "action" : "add",
    "createEntities": true,
    "entities" : 
        [
            {"name":"nurswgvml010"},
            {"name":"nurswgvml011"}
        ]
  },
  {
    "action" : "delete",
    "deleteAll": false,
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  }
]
```

This method supports multiple actions for the same entity group. It can be used to delete and add entities within one request.

<aside class="notice">
The server cannot execute multiple actions atomically. The server will abort processing on first error, previously executed actions will not be rolled back.
</aside>

## Entity Group: Set (Replace) Entities

Replace entities in the entity group with the specified collection.

### Request Fields

```
PUT /api/v1/entity-groups/{group}/entities
```

> Request

```json
[
{"name":"nurswgvml007"},
{"name":"nurswgvml006"}
]
```

| **Field**  | **Required** | **Description**                                                                                |
|----------------|--------------|------------------------------------------------------------------------------------------------|
| createEntities | no       | Automatically create new entities from the submitted list if such entities don't already exist. Default value: true|

<aside class="notice">
All existing entities that are not included in the collection will be removed.
If the specified collection is empty, all entities are removed from the group (replace with empty collection).
</aside>

## Entity Group: Delete Entities

Delete entities from entity group.

### `delete` 

```
PATCH /api/v1/entity-groups/{group}/entities
```

> Request

```json
[
  {
    "action" : "delete",
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  }
]
```

`delete` action removes specified entities from the entity group:

### `delete-all`

`delete-all` action removes all entities from the entity group:

> Request

```json
[
  {
    "action" : "delete-all"
  }
]
```

### Multiple actions

> Request

```json
[
  {
    "action" : "delete",
    "entities" : 
        [
            {"name":"nurswgvml007"},
            {"name":"nurswgvml006"}
        ]
  },
  {
    "action" : "add",
    "createEntities": true,
    "entities" : 
        [
            {"name":"nurswgvml010"},
            {"name":"nurswgvml011"}
        ]
  }
]
```

This method supports multiple actions for the same entity group. It can be used to delete and add entities within one request.

<aside class="notice">
The server cannot execute a request containing multiple actions atomically. The server will abort processing on first error, previously executed actions will not be rolled back.
</aside>

## Metric: Entities and Tags

Returns a list of unique series tags for the metric. The list is based on data stored on disk for the last 24 hours.

### Request Parameters

```
GET /api/v1/metrics/{metric}/entity-and-tags
```

> Request

```
http://atsd_server:8088/api/v1/metrics/disk_used/entity-and-tags?timeFormat=iso
```

| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| entity        | no       | Filter entities by entity name. |
|minInsertDate|no|return entities and tags with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return entities with lastInsertTime less than specified time, accepts iso date format|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields

> Response

```json
[
    {
        "entity": "nurswgvml007",
        "tags": {
            "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
            "mount_point": "/"
        },
        "lastInsertDate": "2015-09-04T15:48:58.000Z"
    },
    {
        "entity": "nurswgvml007",
        "tags": {
            "file_system": "10.102.0.2:/home/store/share",
            "mount_point": "/mnt/share"
        },
        "lastInsertDate": "2015-09-04T15:48:58.000Z"
    },
    {
        "entity": "nurswgvml006",
        "tags": {
            "file_system": "/dev/mapper/vg_nurswgvml006-lv_root",
            "mount_point": "/"
        },
        "lastInsertDate": "2015-09-04T15:48:47.000Z"
    },
    {
        "entity": "nurswgvml006",
        "tags": {
            "file_system": "/dev/sdc1",
            "mount_point": "/media/datadrive"
        },
        "lastInsertDate": "2015-09-04T15:48:47.000Z"
    }
]
```

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| entity         | Entity name                                                                                            |
| lastInsertTime | Maximium last time for metric, entity and one of the tag names . Time specified in epoch milliseconds. |
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
| tags           | map of tag names and values                                                                            |

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>


## Entity: Property Types

Returns an array of property types for the entity. 

### Request Parameters

```
GET /api/v1/entities/{entity}/property-types
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/property-types
```

| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| startTime        | no        | Return only property types that have been collected after the specified time. |

### Response Fields

> Response

```json
[
   "configuration", 
   "system",
   "process"
]
```

| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| type | Property type name                                                                                            |

## Metrics: Entity

```
GET /api/v1/entities/{entity}/metrics
```

> Request

```
http://atsd_server:8088/api/v1/entities/nurswgvml007/metrics?timeFormat=iso&limit=2
```

Returns a list of metrics collected by the entity. The list is based on memory cache which is rebuilt on ATSD restart.

### Request Parameters

> Response

```json
[
    {
        "name": "active",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-09-04T16:10:21.000Z"
    },
    {
        "name": "active(anon)",
        "enabled": true,
        "dataType": "FLOAT",
        "counter": false,
        "persistent": true,
        "timePrecision": "MILLISECONDS",
        "retentionInterval": 0,
        "invalidAction": "NONE",
        "lastInsertDate": "2015-09-04T16:10:21.000Z"
    }
]
```

|**Parameter**|**Required**|**Description**|
|---|---|---|---|
|active|no| Filter metrics by `last_insert_time`. If `active = true`, only metrics with positive `last_insert_time` are included in the response|
|expression|no|Use name variable for entity name. Use * placeholder in like expressions|
|tags|no|Specify metric tags to be included in the response|
|limit|no|Limit response to first N metrics, ordered by name.|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

**Response Fields:**

See: [Metrics: List](#metrics:-list)
