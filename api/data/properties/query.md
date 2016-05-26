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

| **Field**  | **Description**  |
|:---|:---|
| type | [Required] Property type name. <br>Use `$entity_tags` type to retrieve entity tags. |
| key | Object with `name=value` fields for matching records containing specified fields with the same values.<br>Example: `{"queue_name": "qm-1", "queue_type": "recv"}`<br>The key is ignored when querying `$entity_tags` type. |
| keyExpression | Expression for matching properties with specified keys.<br>Example: `queue_name LIKE 'qm-*'`<br>The expression is ignored when querying `$entity_tags` type |

### Entity Filter Fields

* Entity filter is **optional**. 
* Entity name pattern may include `?` and `*` wildcards.
* `entity`, `entities`, `entityGroup` fields are mutually exclusive, only one field can be specified in the request. 
* `entityExpression` is applied as an additional filter to `entity`, `entities`, `entityGroup` fields.

| **Field**  | **Description**  |
|:---|:---|
| entity    | Entity name or entity name pattern. |
| entities | Array of entity names or entity name patterns. |
| entityGroup | Entity group name. <br>Empty result is returned if the group doesn't exist or contains no entities. |
| entityExpression | Entity name, tag, and properties filter specified using [syntax](/rule-engine/functions.md). <br>Example: `tags.location = 'SVL'`  |

### Date Filter Fields

* Date filter is **required**. 
* The filter matches property records where `startDate` <= `property.date` < `endDate`.
* If `startDate` or `endDate` is not defined, the remaining field is calculated from `interval`/`endDate` and `startDate`/`interval` fields.

| **Field** | **Description** |
|:---|:---|
|startDate|	Start of the selection interval. Date in ISO format or [endtime](/end-time-syntax.md) keyword.<br>Examples: `2016-05-25T00:15:00.194Z`, `2016-05-25T`, `current_hour` |
| endDate |	End of the selection interval. Date in ISO format or [endtime](/end-time-syntax.md) keyword.<br>Examples: `2016-05-25T00:15:00Z`, `previous_day - 1 * HOUR`|
| interval|	Duration of the selection interval, specified as `count` and `unit`. <br>Example: `{"count": 5, "unit": "MINUTE"}`|

### Result Filter Fields

| **Field**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default value is 0 (all). | 

## Response 

An array of matching property records containing the following fields:

### Fields

| **Field**  | **Description**  |
|:---|:---|
| type | Property type name. |
| entity | Entity name. |
| key | Object containing `name=value` fields that uniquely identify the property record. |
| tags | Object containing `name=value` tags, for example `{"path": "/", "name": "sda"}`. |
| date | Date when the property record was last modified, in ISO format. |

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








