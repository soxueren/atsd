# Series CSV: Insert
## Descriprion
## Path 
```
/api/v1/series/csv/{entity}?tag1=value1&tag2=value2
```
## Method
```
POST 
```
## Request
### Fields
| **Field** | **Required** | **Description**                                   |
|---|---|---|---|
| entity   | yes          | entity name                                       |
| tag      | no           | one or multiple `tag=value` request parameter pairs |
### Payload
Payload - CSV containing time column and one or multiple metric columns.

* Separator must be comma.
* Time must be specified in Unix milliseconds.
* Time column must be first, name of the time column could be arbitrary.
* Content-type: text/plain or text/csv

## Response 
### Fields
Empty if insert was successful.


## Example 
### Request 
#### URI
```
POST https://atsd_host:8443/api/v1/series/csv/nurswgvml007?file_system=/sda&mount_point=/
```
#### Payload
```
time,df.disk_used_percent,disk_size,df.disk_used
1423139581216,22.2,25165824,5586812
1423139581216,22.2,25165824,5586812
```
#### curl
```
curl --insecure https://atsd_host:8443/api/v1/series/csv/nurswgvml007?file_system=/sda&mount_point=/ \
 -verbose -user {username}:{password} \
  -header "Content-Type: text/csv" \
  -request POST \
  -data ??? 
```


