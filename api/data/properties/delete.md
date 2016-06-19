# Properties: Delete

## Description

Delete property records that match specified filters.

## Request

### Path

```elm
/api/v1/properties/delete
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | application/json |

### Fields

An array of objects containing fields for filtering records for deletion.

| **Field**  | **Type** | **Description**  |
|:---|:---|:---|
| type | string | [**Required**] Property type name. <br>This method does not allow removal of the reserved `$entity_tags` type.|
| entity | string | [**Required**] Entity name. <br>Set entity to `*` to delete records for all entities.|
| startDate | string | ISO 8601 date or [endtime](/end-time-syntax.md) keyword. <br>Delete records updated at or after the specified time. |
| endDate | string | ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Delete records updated before the specified time. |
| key | object | Object with `name=value` fields, for example `{"file_system": "/"}`.<br>Deletes records with _exact_ or _partial_ key fields based on `exactMatch` parameter below.|
| exactMatch | boolean | `key` match operator. _Exact_ match if true, _partial_ match if false. Default: **true**.<br>_Exact_ match deletes a record with exactly the same `key` as requested.<br>_Partial_ match deletes records with key that contains requested fields but may also include other fields.|

* Key and tag values are case-insensitive.

## Response

### Fields

None.

### Errors

None.

## Key Match Example

Assuming property records A,B,C, and D exist:

```ls
| record | type   | entity | key-1 | key-2 | 
|--------|--------|--------|-------|-------| 
| A      | type-1 | e-1    | val-1 | val-2 | 
| B      | type-1 | e-2    | val-1 |       | 
| C      | type-1 | e-3    |       | VAL-3 | 
| D      | type-1 | e-4    |       |       | 
```

Queries would delete the following record:

```ls
| exactMatch | key                     | delete  | 
|------------|-------------------------|---------| 
| true       |                         | D       | 
| false      |                         | A;B;C;D | 
| true       | key-1=val-1             | B       | 
| false      | key-1=val-1             | A;B     | 
| true       | key-1=val-1;key-2=val-2 | A       | 
| false      | key-1=val-1;key-2=val-2 | A       | 
| false      | key-2=val-3             |         | 
| false      | key-2=VAL-3             | C       | 
```

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/properties/delete
```

#### Payload

```json
[{
    "type":"disk",
    "entity":"nurswgvml007",
    "key":{"file_system":"/","name":"sda1"}
},{
    "type":"disk",
    "entity":"nurswgvml006",
    "exactMatch": false
}]
``` 

#### curl

``` elm
curl https://atsd_host:8443/api/v1/properties/delete \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{ "type":"disk", "entity":"nurswgvml007", "key":{"file_system":"/","name":"sda1"} }]'
```

### Response

None.
