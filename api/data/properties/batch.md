# Properties: Batch
## Description
## Path
```
/api/v1/properties
```
## Method
```
PATCH 
```
## Request
###  Fields
| **Name**     | **Description**                                                                   |
|---|---|
| insert       | Insert an array of properties for a given entity, type                            |
| delete       | Delete an array of properties for entity, type, and optionally for specified keys |
| delete-match | Delete rows that partially match the specified key                                |

<aside class="success">
For 'delete-match' action, 'createdBeforeTime' specifies an optional time condition. The server should delete all keys that have been created before the specified time.
'createdBeforeTime' is specified in unix milliseconds.
</aside>

Insert keys and delete keys by id or by partial key match in one request.

## Example

### Request
#### URI
```elm
PATCH https://atsd_host:8443/api/v1/properties
```
#### Payload

```json
[{
    "action": "insert",
    "properties": [{
            "type":"type-1",
            "entity":"entity-1",
            "key":{"server_name":"server","user_name":"system"},
            "tags":{"name.1": "value.1"},
            "timestamp":1000
        },{
            "type":"type-2",
            "entity":"entity-2",
            "tags":{"name.2": "value.2"}
        }
    ]
},{
    "action": "delete",
    "properties": [{
            "type":"type-1",
            "entity":"entity-1",
            "key":{"server_name":"server","user_name":"system"}
        },{
            "type":"type-1",
            "entity":"entity-2",
            "key":{"server_name":"server","user_name":"system"}
        }
    ]
},{
    "action": "delete-match",
    "matchers": [{
            "type":"type-1",
            "createdBeforeTime":1000
        },{
            "type":"type-2","entity":"entity-2"
        },{
            "type":"type-3",
            "key":{"server_name":"server"}
        }
    ]
}]
``` 
#### curl
``` css
curl https://atsd_host:8443/api/v1/properties \
  -verbose -user {username}:{password} \
  -header "Content-Type: application/json" \
  -request PATCH 
  -data '[{"action": "insert", "properties": [{ "type":"type-1","entity":"entity-1","key":{"server_name":"server", "user_name":"system"}, "tags":{"name.1": "value.1"},"timestamp":1000},{"type":"type-2","entity":"entity-2","tags":{"name.2": "value.2"}}]},{"action": "delete", "properties": [{ "type":"type-1","entity":"entity-1","key":{"server_name":"server", "user_name":"system"}}, {"type":"type-1","entity":"entity-2","key":{"server_name":"server","user_name":"system"}}]},{"action":"delete-match","matchers": [{"type":"type-1","createdBeforeTime":1000},{"type":"type-2","entity":"entity-2"},{"type":"type-3","key":{"server_name":"server"}}]}]'

```
### Response
