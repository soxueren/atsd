## Properties: Batch

```
PATCH /api/v1/properties
```

Insert keys and delete keys by id or by partial key match in one request.

### Actions

> Request

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

| **Name**     | **Description**                                                                   |
|---|---|
| insert       | Insert an array of properties for a given entity, type                            |
| delete       | Delete an array of properties for entity, type, and optionally for specified keys |
| delete-match | Delete rows that partially match the specified key                                |

<aside class="success">
For 'delete-match' action, 'createdBeforeTime' specifies an optional time condition. The server should delete all keys that have been created before the specified time.
'createdBeforeTime' is specified in unix milliseconds.
</aside>
