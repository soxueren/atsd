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
| type | string | [**Required**] Property type name. |
| entity | string | Entity name. |
| startDate | string | ISO 8601 date or [endtime](/end-time-syntax.md) keyword. Delete records updated at or after the specified time. |
| endDate | string | ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Delete records updated before the specified time. |
| key | object | Object with `name=value` fields. <br>Deletes records with _exact_ or _partial_ key fields. See `exactMatch` parameter below. <br>Example: `{"file_system": "/"}`.<br>Example: assuming records `{"k-1":"v-1"}` (**A**) and `{"k-1":"v-1","k-2","v-2"}` (**B**) exist.<br> _Exact_ match for key `{"k-1":"v-1"}` will delete record **A**.<br>_Partial_ match for key `{"k-1":"v-1"}` will delete records **A** and **B**.<br>_Exact_ match for empty key `{}` will delete no records.<br>_Partial_ match for empty key `{}` will delete records **A** and **B**. |
| exactMatch | boolean | `key` match operator. _Exact_ match if true, _partial_ match if false. Default: **true**.<br>_Exact_ match deletes a record with exactly the same `key` as requested.<br>_Partial_ match deletes records with key that contains requested fields but may also include other fields.|

## Response

### Fields

None.

### Errors

None.

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
