# Series Query: Rate

## Description

Compute rate of change (first derivative) by dividing change in value by change in time (milliseconds). 

Return rate of change per specified time unit. The rate is return for each sample in the detailed dataset, except 1st.

### Data

```ls
| datetime                 | value         | 
|--------------------------|---------------| 
| 2016-06-27T14:10:06.000Z | 2416571149884 | no rate return
| 2016-06-27T14:10:21.000Z | 2416575751447 | rate at 14:10:21
| 2016-06-27T14:10:36.000Z | 2416579173021 |    
| 2016-06-27T14:10:51.000Z | 2416581490451 | 
```

Rate at 14:10:21: 

```ls
(2416575751447 - 2416571149884)/(2016-06-27T14:10:21.000Z - 2016-06-27T14:10:06.000Z) =
  = 4601563 / 15000 milliseconds = 306.77 per millisecond
  Return 306.77 * (1*SECOND / 1*MILLISECOND) = 306770
```

## Request

### URI

```elm
POST https://atsd_host:8443/api/v1/series/query
```

### Payload

```json
[
  {
    "startDate": "2016-06-27T14:10:00Z",
    "endDate":   "2016-06-27T14:11:00Z",
    "entity": "nurswgvml007",
    "metric": "net_tx_bytes",
    "rate": {
        "period": {"count": 1, "unit": "SECOND"}
    }      
  }
]
```

## Response

### Payload

```json
[{"entity":"nurswgvml007","metric":"net_tx_bytes",
	"tags":{"name":"eth1"},"type":"HISTORY",
	"aggregate":{"type":"DETAIL"},
	"rate":{"period":{"count":1,"unit":"SECOND"},"counter":true},
"data":[
	{"d":"2016-06-27T14:10:21.000Z","v":306770.86666666664},
	{"d":"2016-06-27T14:10:36.000Z","v":228104.93333333332},
	{"d":"2016-06-27T14:10:51.000Z","v":154495.33333333334}
]}]
```


