## Alerts: Update

### Method
```
PATCH /api/v1/alerts
```

**Supported update actions:**

* update - set fields of specified alerts to specified values.
* delete - delete specified alerts.

### Acknowledge Alerts

To acknowledge alerts, specify action `update`, field `'acknowledge':true` and array of alert identifiers to acknowledge.

### Basic Examples
> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": true
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### De-acknowledge Alerts

To de-acknowledge alerts, specify action `update`, field `'acknowledge':false` and array of alert identifiers to de-acknowledge.

> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": false
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### Delete Alerts

To delete alerts, specify action `delete` and array of alert identifiers to delete.

> Request

```json
[{
    "action": "delete",
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
}]
```

### Multiple Actions 

Multiple actions can be combined in one request.

> Request

```json
[{
    "action": "update",
    "fields": {
        "acknowledge": true
    },
    "alerts": [
        {"id": "evt-1"},
        {"id": "evt-2"}
    ]
},{
    "action": "update",
    "fields": {
        "acknowledge": false
    },
    "alerts": [
        {"id": "evt-3"},
        {"id": "evt-4"}
    ]
}]
```
