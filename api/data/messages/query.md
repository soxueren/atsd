# Messages: Query

## Description

Retrieve message records for the specified filters.

## Request

### Path

```elm
/api/v1/messages/query
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Parameters

None.

## Fields

An array of query objects containing the following filtering fields:

### Message Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
|type       |  string   | Message type. |
|source       |  string   | Message source. |
|tags	      | object  | Object with `name=value` fields. <br>Matches records with tags that contain the same fields but may also include other fields. |
|severity       |  string   | Severity [code or name](/api/data/severity.md).  |
|minSeverity       |  string   | Minimal [code or name](/api/data/severity.md) severity filter.  |

### Entity Filter Fields

* One of the entity fields is **required**.
* Entity name pattern may include `?` and `*` wildcards.
* `entity`, `entities`, `entityGroup` fields are mutually exclusive, only one of them can be specified in the query object. 
* `entityExpression` is applied as an additional filter to `entity`, `entities`, and `entityGroup` fields.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| entity   | string | Entity name or entity name pattern. |
| entities | list | Array of entity names or entity name patterns. |
| entityGroup | string | Entity group name. Return records for entites in the specified group.<br>Empty result is returned if the group doesn't exist or contains no entities. |
| entityExpression | string | Filter entities by name, entity tag, and properties using [syntax](/rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

### Date Filter Fields

* Date filter is **required**. 
* If `startDate` or `endDate` is not defined, the omitted field is calculated from `interval`/`endDate` and `startDate`/`interval` fields.

| **Name** | **Type** | **Description** |
|:---|:---|:---|
|startDate|	string | **[Required]** Start of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated at or after `startDate` are returned.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	string | **[Required]** End of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated before `endDate` are returned.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	string | Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|

### Result Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default: 1000. | 

## Response 

An array of matching message objects containing the following fields:

### Fields

| **Field** | **Type** | **Description** |
|:---|:---|:---|
|entity | string | Entity name. |
|type | string | Message type. |
|source | string | Message source. |
|severity | string | Message [severity](/api/data/severity.md) code or name. |
|tags | object |  Object containing `name=value` fields, for example `tags: {"path": "/", "name": "sda"}`. |
|message | string | Message text. |
|date | string | ISO 8601 date when the message record was created. |

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/messages/query
```
#### Payload

```json
[
    {
        "entity": "nurswgvml007",
        "type": "logger",
        "limit": 5,
        "endDate": "now",
        "interval": {
            "count": 30,
            "unit": "MINUTE"
        }
    }
]
```

#### curl

```elm
curl  https://atsd_host:8443/api/v1/messages/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
  ```
  
### Response

#### Payload

```json
[
   {
        "entity": "nurswgvml007",
        "type": "logger",
        "source": "com.axibase.tsd.service.entity.findentitybyexpressionserviceimpl",
        "severity": "NORMAL",
        "tags":
        {
            "level": "INFO",
            "thread": "applicationScheduler-5",
            "command": "com.axibase.tsd.Server"
        },
        "message": "Expression entity group 'scollector-linux' updated",
        "date": "2016-05-25T17:05:00Z"
    },
    {
        "entity": "nurswgvml007",
        "type": "logger",
        "source": "com.axibase.tsd.web.csv.csvcontroller",
        "severity": "NORMAL",
        "tags":
        {
            "level": "INFO",
            "thread": "qtp490763067-195",
            "command": "com.axibase.tsd.Server"
        },
        "message": "Start processing csv, config: nginx-status",
        "date": "2016-05-25T17:04:01Z"
    }
]
```

## Additional Examples
