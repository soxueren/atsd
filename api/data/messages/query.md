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

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

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
