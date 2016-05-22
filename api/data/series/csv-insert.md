# Series: CSV Insert

## Description

Insert series values for the specified entity and series tags in CSV format.

The request accepts samples only for one entity and tag combination. 

One or multiple metric columns are specified in CSV header.

## Request

### Path 

```elm
/api/v1/series/csv/{entity}?[&{tag-name}={tag-value}]
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | `text/csv` |

### Fields

| **Field** | **Required** | **Description** |
|:---|:---|:---|
| entity   | yes          | entity name |
| tag      | no           | one or multiple `tag=value` request parameter pairs |

### Payload

* Payload is plain text in CSV format containing a header line and data rows.
* The header must begin with `time` or `date` column, followed by at least one metric column containing numeric values.
* Time must be specified in Unix milliseconds if `time` column is used, and in ISO format if `date` column is used.
* Separator must be comma.
* It is recommended that samples are sorted by time in ascending order

#### Unix millisecond format

```ls
time,metric-1,metric-2,...,metric-N
1423139581000,5.0,2.1,...,10.4
1423139592016,5.0,2.1,...,10.4
```

#### ISO format

```ls
date,metric-1,metric-2,...,metric-N
2016-05-16T00:14:36.000Z,5.0,2.1,...,10.4
2016-05-16T00:14:45.012Z,5.0,2.1,...,10.4
```

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
time,cpu_user,cpu_system,waitio
1423139581000,12.4,1.4,0
1423139592016,16.0,4.2,0
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


