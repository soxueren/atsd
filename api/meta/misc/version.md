# Version

## Description

Returns database version including licensing details.

## Request

### Path

```elm
/version
```
### Method

```
GET
```

### Headers

None.

### Parameters

None.

### Fields

None.

## Response

### Fields

| **Field**  | **Description** |
|:---|:---|
| buildInfo  | Database version information    |
| license | Database license details  |

### Errors

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/version
```

#### Payload

None.

#### curl

```elm
curl https://atsd_host:8443/version \
  --insecure --verbose --user {username}:{password} \
  --request GET 
  ```
  
### Response

```json
{
	"buildInfo": {
		"revisionNumber": "13390",
		"buildNumber": "4129",
		"buildId": "4129"
	},
	"license": {
		"forecastEnabled": true,
		"hbaseServers": 5,
		"remoteHbase": true,
		"productVersion": "Enterprise Edition",
		"dataVersioningEnabled": true
	}
}
```

## Additional examples


