# Properties: Query

## Description 

Retrieve a list of property records matching for the specified filters.

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

An array of query objects containing fields for filtering.

| **Field**  | **Required** | **Description**  |
|:---|:---|:---|
| entity    | yes (1)         | Entity name or entity name pattern with `?` and `*` wildcards|
| entities | yes (1) | Array of entity names or entity name patterns |
| entityGroup | yes (1) | If `entityGroup` field is specified in the query, properties of the specified type for entities in this group are returned. `entityGroup` is used only if entity field is omitted or if entity field is an empty string. If `entityGroup` is not found or contains no entities an empty resultset will be returned. |
| entityExpression | yes (1) | `entityExpression` filter is applied in addition to other entity* fields. For example, if both `entityGroup` and `entityExpression` fields are specified, the expression is applied to members of the specified entity group. `entityExpression` supports the following [syntax](/rule-engine/functions.md). Example, `tags.location='SVL'`  |
|startDate|	no|	start of the selection interval. Specified in ISO format or using endtime syntax.|
|endDate|	no|	end of the selection interval. Specified in ISO format or using endtime syntax.|
| limit     | no           | Maximum number of records returned. Default value: 0 (all)  | 
| type      | yes          | type of data properties. Supports reserved `$entity_tags` type to retrieve entity tags. Any keys specified in a request containing this reserved type will be ignored.  |
| key      | no           | JSON object containing `name=values` that uniquely identify the property record. Ignored when querying '$entity_tags' which is a reserved property type to retrieve entity tags. |
| keyExpression | no | expression for matching properties with specified keys |

* One of the following fields is required: **entity, entities, entityGroup, entityExpression**. 
* **entity, entities, entityGroup** fields are mutually exclusive, only one field can be specified in the request. 
* entityExpression is applied as an additional filter to entity, entities, entityGroup fields.

## Response 

An array of matching property records containing the following fields:

### Fields

| **Field**  | **Description**  |
|:---|:---|
| type | Property type name. |
| entity | Entity name. |
| key | An object containing `name=value` fields that uniquely identify the property record. |
| tags | An object containing `name=value` tags, for example tags: `{"path": "/", "name": "sda"}`. |
| date | Time when the record was last updated, in ISO format. |

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
      "key": {}
     }
]
```
#### curl

```elm
curl  https://atsd_host:8443/api/v1/properties/query \
  --insecure --verbose --user {username}:{password} \
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
           "cpu_total.sys%": "1.1"
       },
       "date": "2015-02-05T16:55:02Z"
   }
]
```

### Additional Examples








