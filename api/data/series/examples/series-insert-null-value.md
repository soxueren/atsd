# Series Insert Null value 

## Request
### URI
```elm
POST https://atsd_host:8443/api/v1/series/insert
```
### Payload
```json
[
    {
        "entity": "nurswgvml007",
        "metric": "mpstat.cpu_busy",
        "data": [
            {
                "t": 1462427358127,
                "v": null
            }
        ]
    }
]
```
## Response 
```
```
