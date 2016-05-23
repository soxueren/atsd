# Messages: Insert

## Descriprion

## Path 

```elm
/api/v1/messages/insert
```

## Method 

```
POST 
```

## Request

### Fields

| Field       | Required | Description              |
|---|---|---|
| entity | yes | an entity name, such as server name, or a entity name pattern with ? and * wildcards |
| date | no | date and time in ISO format |
| timestamp | no | time in Unix milliseconds |
| message | no | message text |
| severity | no | severity, must be upper-case. Severity Codes:  UNDEFINED, UNKNOWN, NORMAL, WARNING, MINOR, MAJOR, CRITICAL, FATAL |
| type | no | type |
| source | no | source |
| tags | no | JSON object containing name=values | 

<aside class="notice">
severity, message, type, source are reserved fields. In insert command, these reserved fields should be specified as fields, not as tags. In case of collision, tags with the same names as reserved keywords are discarded.
</aside>

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/messages/insert
```

#### Payload

```json
[
    {
        "entity": "nurswgvml007",
        "type": "application",
        "message": "NURSWGVML007 ssh: error: connect_to localhost port 8881: failed.",
        "severity": "undefined",
        "source": "atsd"
    }
]
```

#### curl
```elm
curl https://atsd_host:8443/api/v1/messages/insert \
  --insecure --verbose --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data @file.json
```
