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
| endDate | string | ISO 8601 date or [endtime](/end-time-syntax.md) keyword.<br>Delete records updated before the specified time. |
| key | object | Object with `name=value` fields. <br>Delete records with equal key (_exact_) or key containing requested fields with the same values (_partial_).<br>Example: `{"iftype": "eth"}` |
| exactMatch | boolean | `key` match operator. _Exact_ match if true, _partial_ match if false. Default: **true**.<br>_Exact_ match deletes a record with exactly the same `key` as requested.<br>_Partial_ match deletes records with key that contains requested fields but may also include other fields.<br>Example: `{"k-1":"v-1"}` with _exact_ match deletes a record with key `{"k-1":"v-1"}`.<br>`{"k-1":"v-1"}` with _partial_ match deletes records with key `{"k-1":"v-1"}` as well as with key `{"k-1":"v-1","k-2":"v-2"}`.|

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
    "type":"nmon-disks",
    "entity":"nurswgvml007",
    "key":{"file_system":"/","name":"sda1"}
},{
    "type":"nmon-disks",
    "entity":"nurswgvml006",
    "partialKey":{"file_system":"/"}
}]
``` 

#### curl

``` elm
curl https://atsd_host:8443/api/v1/properties/delete \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{ "type":"nmon-disks", "entity":"nurswgvml007", "key":{"file_system":"/","name":"sda1"} }]'
```

### Response

None.
