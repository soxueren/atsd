# Properties: Query

## Description 

Retrieve property records matching specified filters.

## Request

### Path

```elm
/api/v1/properties/query
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

## Fields

An array of query objects containing the following filtering fields:

### Property Filter Fields

* `key` and `keyExpression` fields are mutually exclusive, only one field can be specified in the query object.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | **[Required]** Property type name. <br>Use `$entity_tags` type to retrieve entity tags. |
| key | object | Object with `name=value` fields for matching records containing specified fields with the same values.<br>Example: `{"iftype": "eth"}` to match records with key `{"iftype": "eth", "name": "en0"}` or `{"iftype": "eth", "name": "en1"}`.<br>The key is ignored when querying `$entity_tags` type. |
| keyExpression | string | Expression for matching properties with specified keys.<br>Example: `queue_name LIKE 'qm-*'`<br>The expression is ignored when querying `$entity_tags` type. |

### Entity Filter Fields

* Entity name pattern may include `?` and `*` wildcards.
* `entity`, `entities`, `entityGroup` fields are mutually exclusive, only one field can be specified in the query object. 
* `entityExpression` is applied as an additional filter to `entity`, `entities`, and `entityGroup` fields.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| entity   | string | Entity name or entity name pattern. |
| entities | list | Array of entity names or entity name patterns. |
| entityGroup | string | Entity group name. Return properties for entites in the specified group.<br>Empty result is returned if the group doesn't exist or contains no entities. |
| entityExpression | string | Filter entities by name, entity tag, and properties using [syntax](/rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

### Date Filter Fields

* Date filter is **required**. 
* If `startDate` or `endDate` is not defined, the omitted field is calculated from `interval`/`endDate` and `startDate`/`interval` fields.

| **Name** | **Type** | **Description** |
|:---|:---|:---|
|startDate|	string | **[Required]** Start of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated at or after this time are returned.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	string | **[Required]** End of the selection interval. ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Only records updated before this time are returned.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	string | Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|

### Result Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default value is 0 (all). | 

## Response 

An array of matching property records containing the following fields:

### Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | Property type name. |
| entity |string |  Entity name. |
| key | object | Object containing `name=value` fields that uniquely identify the property record. `{"iftype": "eth", "name": "en1"}`|
| tags | object | Object containing `name=value` tags, for example `{"label": "Eth Interface (1)"}`. |
| date | string | ISO 8601 date when the property record was last modified. |

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/properties/query
```

#### Payload

```json
[
    {
      "type": "system",
      "entity": "nurswgvml007",
      "interval": {"count": 1, "unit": "HOUR"},
      "endDate": "2016-02-05T18:00:00Z"
     }
]
```
#### curl

```elm
curl  https://atsd_host:8443/api/v1/properties/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"type":"system","entity":"nurswgvml007","interval":{"count":1,"unit":"HOUR"},"endDate":"2016-02-05T18:00:00Z"}]'
```

### Response

```json
[
   {
       "type": "system",
       "entity": "nurswgvml007",
       "key": {},
       "tags": {
           "cpu_total.busy": "1",
           "cpu_total.idle%": "93.6",
           "cpu_total.sys%": "1.1"
       },
       "date": "2016-02-05T17:15:00Z"
   }
]
```

### Additional Examples








