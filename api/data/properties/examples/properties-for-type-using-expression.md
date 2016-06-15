# Properties for type using keyTagExpression Example

## Request

### URI

```elm
POST https://atsd_host:8443/api/v1/properties/query
```

### Payload

```json
[
    {
        "type": "manager2",
        "entity": "host2",
        "keyTagExpression": "key3 like 'nur*'"
    }
]
```
