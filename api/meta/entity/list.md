# Entities: List

## Description

Retrieve a list of entities matching the specified filter conditions.

## Request

| **Method** | **Path** | 
|:---|:---|---:|
| GET | `/api/v1/entities` |

### Query Parameters 

|**Name**|**Type**|**Description**|
|:---|:---|:---|
| expression |string|Include entities that match an [expression](../expression.md) filter. Use `name` variable for entity name. Wildcard `*` is supported.|
| minInsertDate |string|Include entities with `lastInsertDate` equal or greater than specified time. <br>`minInsertDate` can be specified in ISO format or using [endtime](/end-time-syntax.md) syntax.|
| maxInsertDate |string|Include entities with `lastInsertDate` less than `maxInsertDate`.<br>`maxInsertDate` can be specified in ISO format or using [endtime](/end-time-syntax.md) syntax.|
| limit |integer|Maximum number of entities to retrieve, ordered by name.|
| tags |string|Comma-separated list of entity tag names to be displayed in the response.<br>For example, `tags=OS,location`<br>Specify `tags=*` to print all entity tags.|

## Response

### Fields

| **Name** | **Type** | **Description** |
|:---|:---|:---|
| name | string | Entity name. |
| enabled | boolean | Enabled status. Incoming data is discarded for disabled entities. |
| timeZone | string | Time Zone ID, for example `America/New_York` or `EST`.<br>Refer to [Java Time Zone](../../network/timezone-list.md) table for a list of supported Time Zone IDs.<br>The timezone is applied by date-formatting functions to return local time in entity-specific timezone.|
| lastInsertDate | string |Last time, in ISO format, when a value was received by the database for this entity. |
| tags | object | Entity tags, as requested with `tags` parameter. |

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/entities?timeFormat=iso&limit=2&expression=name%20like%20%27nurs*%27
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/api/v1/entities?timeFormat=iso&limit=2&expression=name%20like%20%27nurs*%27 \
  --insecure --verbose --user {username}:{password} \
  --request GET
```

### Response

```json
 [
    {
        "name": "nurswgdkr002",
        "enabled": true,
        "lastInsertDate": "2015-09-04T15:43:36.000Z"
    },
    {
        "name": "nurswgvml001",
        "enabled": false
    }
]
```

## Additional examples
* [List entities by name](./examples/list-entities-by-name.md)
* [List entities by maxInsertDate](./examples/list-entities-by-maxinsertdate.md)
* [List entities by minInsertDate](./examples/list-entities-by-mininsertdate.md)
* [List all tags for all entities starting with name](examples/list-all-tags-for-all-entities-with-name.md)
* [List entities by name and tag](examples/list-entities-by-tag-containing-hbase.md)

