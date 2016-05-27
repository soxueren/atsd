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

### Parameters

None.

## Fields

An array of query objects containing the following filtering fields:

### Property Filter Fields

* `key` and `keyExpression` fields are mutually exclusive, only one of them can be specified in the query object.
* `key` and `keyExpression` fields are ignored when querying `$entity_tags` type.

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | **[Required]** Property type name. <br>Use `$entity_tags` type to retrieve entity tags. |
| key | object | Object with `name=value` fields. <br>Matches records with _exact_ or _partial_ key fields. See `exactMatch` parameter below. <br>Example: `{"file_system": "/"}`.<br>Example: assuming records `{"k-1":"v-1"}` (**A**) and `{"k-1":"v-1","k-2","v-2"}` (**B**) exist.<br> _Exact_ match for key `{"k-1":"v-1"}` will select record **A**.<br>_Partial_ match for key `{"k-1":"v-1"}` will select records **A** and **B**.<br>_Exact_ match for empty key `{}` will select no records.<br>_Partial_ match for empty key `{}` will select records **A** and **B**. |
| exactMatch | boolean | `key` match operator. _Exact_ match if true, _partial_ match if false. Default: **false**.<br>_Exact_ match selects a record with exactly the same `key` as requested.<br>_Partial_ match selects records with key that contains requested fields but may also include other fields.|
| keyExpression | string | Expression for matching properties with specified keys.<br>Example: `file_system LIKE '/u*'` |

### Entity Filter Fields

* [**Required**]
* Refer to [entity filter](../filter-entity.md).

### Date Filter Fields

* [**Required**]
* Refer to [date filter](../filter-date.md).

### Result Filter Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| limit   | integer | Maximum number of records to be returned. Default: 0. | 
| last | boolean | Return only records with the update time equal to the maximum update time of matched records. Default: false. |
| offset | integer | Difference, in milliseconds, between maximum update time of matched records and update time of the current record. Default: 0.<br>If the difference exceeds `offset`, the record is excluded from results. |   
| tagOffset | integer | Difference, in milliseconds, between update time of the current record and update time of the tag field. Default: 0.<br>If the difference exceeds `tagOffset`, the tag field is excluded from `tags` object. |   

## Response 

An array of matching property objects containing the following fields:

### Fields

| **Name**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | Property type name. |
| entity |string |  Entity name. |
| key | object | Object containing `name=value` fields that uniquely identify the property record. <br>Example: `{"file_system": "/","mount_point":"sda1"}`|
| tags | object | Object containing `name=value` fields that are not part of the key and contain descriptive information about the property record. <br>Example: `{"fs_type": "ext4"}`. |
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
      "type": "disk",
      "entity": "nurswgvml007",
      "key": { "file_system": "/" },
      "startDate": "2016-05-25T04:00:00Z",
      "endDate":   "2016-05-25T05:00:00Z"
     }
]
```
#### curl

```elm
curl  https://atsd_host:8443/api/v1/properties/query \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"type":"disk","entity":"nurswgvml007","key":{"file_system":"/"},"startDate":"2016-05-25T04:00:00Z","endDate":"2016-05-25T05:00:00Z"}]'
```

### Response

```json
[
   {
       "type": "disk",
       "entity": "nurswgvml007",
       "key": {
           "file_system": "/",
           "mount_point": "sda1"
       },
       "tags": {
           "fs_type": "ext4"
       },
       "date": "2016-05-25T04:15:00Z"
   }
]
```

### Additional Examples








