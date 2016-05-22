# Series: CSV Insert

## Description

Insert series values for the specified entity in CSV format

## Request

### Path 

```
/api/v1/series/csv/{entity}?[&{tag-name}={tag-value}]
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | `text/plain` or `text/csv` |

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| entity   | yes          | entity name |
| tag      | no           | one or multiple `tag=value` request parameter pairs |

### Payload

Payload - CSV containing time column and one or multiple metric columns.

* Separator must be comma.
* Time must be specified in Unix milliseconds.
* Time column must be first, name of the time column could be arbitrary.

## Response 

### Fields

None.

### Errors

None.

## Example 

### Request 

#### URI

```elm
POST https://atsd_host:8443/api/v1/series/csv/nurswgvml007
```

#### Payload

```ls
time,cpu_busy
1423139581216,12.4
1423139581216,16.0
```

#### curl

```elm
curl https://atsd_host:8443/api/v1/series/csv/nurswgvml007 \
 --insecure --verbose --user {username}:{password} \
  --header "Content-Type: text/csv" \
  --request POST \
  --data @file.csv
```

### Response

None.


## Additional Examples


