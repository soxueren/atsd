# Metric: Get
## Description 
## Path 
```
/api/v1/metrics/{metric}
```
## Method 
```
GET
```
## Request

## Response
### Erorrs 


## Example
### Request
#### URI 
```
https://atsd_host:8443/api/v1/metrics/md-2
```
#### Curl 
````css
curl https://atsd_host:8443/api/v1/metrics/md-2 \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X GET

````
### Response

```json
{
    "name": "md-2",
    "enabled": true,
    "dataType": "FLOAT",
    "counter": false,
    "persistent": true,
    "tags": {},
    "timePrecision": "MILLISECONDS",
    "retentionInterval": 0,
    "invalidAction": "NONE",
    "lastInsertDate": "2015-10-20T12:13:26.000Z",
    "versioned": false
}
```

Displays metric properties and its tags.



See: [Metrics: List](#metrics:-list)
