# Properties: Batch

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

| **Field**  | **Required** | **Description**  |
|:---|:---|:---|
| type | yes | Property type name. |
| entity | no | Entity name. |
| endDate | ISO format. | Delete records updated before the specified time. |
| key **or** <br>partialKey | yes | An object containing `name=value` fields. <br>`key` - deletes records with the same key. <br>`partialKey` - deletes records containing fields with the same values in the key. |

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
  --request POST 
  --data '[{ "type":"nmon-disks", "entity":"nurswgvml007", "key":{"file_system":"/","name":"sda1"} }]'
```

### Response

None.
