# Properties: Query
## Description 
## Path
```
/api/v1/properties
```
## Method
```
POST 
```
## Request
### Fields

| **Field**  | **Required** | **Description**  |
|---|---|---|
| entity    | yes (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | yes (1) | Array of entity names or entity name patterns |
| entityGroup | yes (1) | If `entityGroup` field is specified in the query, properties of the specified type for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | yes (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
| startTime | no           |   start of the selection interval in UNIX milliseconds. Default value: `endTime - 1 hour` |
| endTime   | no           | end of the selection interval in UNIX milliseconds. Default value: `current server time` | 
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
|timeFormat|	no|	response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|
| limit     | no           | maximum number of data samples returned. Default value: 0   | 
| type      | yes          | type of data properties. Supports reserved `$entity_tags` type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.  |
| key      | no           | JSON object containing `name=values` that uniquely identify the property record. Ignored when querying '$entity_tags' which is a reserved property type to retrieve entity tags. |
| keyExpression | no | expression for matching properties with specified keys |

<aside class="notice">
* One of the following fields is required: **entity, entities, entityGroup, entityExpression**. 
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.
</aside>

## Response 
### Fields
| **Field**  | **Description**  |
|---|---|
| type | property type name |
| entity | entity name |
| key | JSON object containing `name=value` that uniquely identify the property record |
| tags | object keys |
| timestamp | time in Unix milliseconds |
| date | date and time in ISO format |

## Example
### Request
#### URI
```elm
POST https://atsd_host:8443/api/v1/properties
```
#### Payload
```json
[{
  "queries": [
    {
      "timeFormat": "iso",
      "type": "system",
      "entity": "nurswgvml007",
      "key": {}
     }
   ]
}]
```
#### curl
```css
curl --insecure https://atsd_host:8443/api/v1/properties \
  --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
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
           "cpu_total.sys%": "1.1",
           "cpu_total.user%": "4.7",
           "cpu_total.wait%": "0.6",
           "memory_mb.active": "946.2",
           "memory_mb.bigfree": "-1.0"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```


### Additional Examples








