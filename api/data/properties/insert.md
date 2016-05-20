# Properties: Insert
## Description
Insert an array of properties.
## Path
```
/api/v1/properties/insert
```
## Method
```
POST 
```
## Example
### Request
#### URI
```elm
POST https://atsd_host:8443/api/v1/properties/insert
```
#### Payload
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
#### curl
```css
curl --insecure https://atsd_host:8443/api/v1/properties/insert  \
  --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request  POST \
  --data @file.json

```
## Response 
```
```
