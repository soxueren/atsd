## Properties: Insert
### Method
```
POST /api/v1/properties/insert
```
### Basic Example
> Request

```json
[{
   "type":"type-1",
   "entity":"entity-1",
   "key":{"server_name":"server","user_name":"system"},
   "tags":{"name.1": "value.1"},
   "timestamp":1000
},{
   "type":"type-2",
   "entity":"entity-1",
   "tags":{"name.2": "value.2"}
}]
```

Insert an array of properties.
