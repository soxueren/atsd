# Properties for type using expression Example

## Request
### URI
POST https://atsd_host:8443/api/v1/properties
### Payload
```json
{
    "queries": [
        {
            "type": "manager2",
            "entity": "host2",
            "keyExpression": "key3 like 'nur*'"
        }
    ]
}
```
