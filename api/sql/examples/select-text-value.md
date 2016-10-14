# Text Value Column

The difference between series tags and text values is that tag names/values are converted to unique identifiers when stored.

Series tags are part of each series composite primary key, whereas the text value is not.

Since the total number of unique tag value identifiers is [limited](README.md#schema) to `16,777,215`, series tag values are not well suited for values with high cardinality such as random values or continuously incrementing values (time, counters).

The text value, on the other hand, is stored `as is`, without converting it to an identifier. It can be used as an annotation, or order to describe a numeric observation without changing it's primary key. 

```ls
series d:2016-10-13T08:00:00Z e:sensor-1 m:temperature=20.3
series d:2016-10-13T08:15:00Z e:sensor-1 m:temperature=24.4 x:temperature="Provisional"
```

In this example, temperature reading at `2016-10-13T08:15:00Z` is characterized as `Provisional`. No new series (by key) is created.

The text value can also be used to record observations for series that contain only text values in which case their numeric values are set to `NaN` (not a number).

```ls
series d:2016-10-13T10:30:00Z e:sensor-1 x:status="Shutdown by adm-user, RFC-5434"
```

Unlike [message](message.md) commands, series text values are available for SQL querying using the `text` column.

```sql
SELECT entity, datetime, value, text 
  FROM atsd_series 
WHERE metric IN ('temperature', 'status') AND datetime >= '2016-10-13T08:00:00Z'
```

```ls
| entity   | datetime             | value | text                           | 
|----------|----------------------|-------|--------------------------------| 
| sensor-1 | 2016-10-13T08:00:00Z | 20.3  | null                           | 
| sensor-1 | 2016-10-13T08:15:00Z | 24.4  | Provisional                    | 
| sensor-1 | 2016-10-13T10:30:00Z | NaN   | Shutdown by adm-user, RFC-5434 | 
```

## Data

```ls
series e:default d:2016-09-20T12:57:49Z m:Memory_Avail_MBytes=NaN t:status_text="Pt Created" t:status=-253 
series e:default d:2016-10-11T15:38:00Z m:Memory_Avail_MBytes=6139.0 
series e:default d:2016-10-11T15:38:00Z m:Memory_Avail_MBytes=6139.1 t:_index=2
series e:default d:2016-10-11T15:38:00Z m:Memory_Avail_MBytes=6139.2 t:_index=3
series e:default d:2016-10-11T15:38:01Z m:Memory_Avail_MBytes=6141.0
series e:default d:2016-08-24T15:02:55Z x:BA:ACTIVE.1="" t:status_text="Pt Created" t:status=-253
series e:default d:2016-08-24T15:03:17Z x:BA:ACTIVE.1=Inactive
series e:default d:2016-08-24T15:04:17Z x:BA:ACTIVE.1=Active  
series e:default d:2016-08-24T16:15:17Z x:BA:ACTIVE.1=Inactive
series e:default d:2016-08-24T15:01:09Z m:CDEP158=NaN t:status_text="Shutdown" t:status=-254
series e:default d:2016-08-24T15:03:17Z m:CDEP158=0
series e:default d:2016-08-24T15:43:17Z m:CDEP158=12
series e:default d:2016-08-24T16:21:20Z m:CDEP158=NaN t:status_text="Shutdown" t:status=-254
series e:default d:2016-08-24T16:23:30Z m:CDEP158=1
series e:default d:2016-08-24T22:28:30Z m:CDEP158=43
```

## Query

```sql
SELECT entity, metric AS pitag, datetime, value, ISNULL(text, '') AS svalue, 
  ISNULL(tags._index, '1') AS '_index', ISNULL(tags.status, '0') AS status, ISNULL(tags.status_text, 'Good') AS status_text,
  ISNULL(tags.questionable, 'false') AS questionable, ISNULL(tags.substituted, 'false') AS substituted, ISNULL(tags.annotated, 'false') AS annotated, 
  ISNULL(tags.annotations, '') AS annotations
FROM atsd_series 
  WHERE metric IN ('Memory_Avail_MBytes', 'BA:ACTIVE.1', 'CDEP158') 
AND entity = 'default'
```

## Results

```ls
| entity  | pitag               | datetime             | value  | svalue   | _index | status | status_text | questionable | substituted | annotated | annotations | 
|---------|---------------------|----------------------|--------|----------|--------|--------|-------------|--------------|-------------|-----------|-------------| 
| default | memory_avail_mbytes | 2016-09-20T12:57:49Z | NaN    |          | 1      | -253   | Pt Created  | false        | false       | false     |             | 
| default | memory_avail_mbytes | 2016-10-11T15:38:00Z | 6139.0 |          | 1      | 0      | Good        | false        | false       | false     |             | 
| default | memory_avail_mbytes | 2016-10-11T15:38:00Z | 6139.1 |          | 2      | 0      | Good        | false        | false       | false     |             | 
| default | memory_avail_mbytes | 2016-10-11T15:38:00Z | 6139.2 |          | 3      | 0      | Good        | false        | false       | false     |             | 
| default | memory_avail_mbytes | 2016-10-11T15:38:01Z | 6141.0 |          | 1      | 0      | Good        | false        | false       | false     |             | 
| default | ba:active.1         | 2016-08-24T15:02:55Z | NaN    |          | 1      | -253   | Pt Created  | false        | false       | false     |             | 
| default | ba:active.1         | 2016-08-24T15:03:17Z | NaN    | Inactive | 1      | 0      | Good        | false        | false       | false     |             | 
| default | ba:active.1         | 2016-08-24T15:04:17Z | NaN    | Active   | 1      | 0      | Good        | false        | false       | false     |             | 
| default | ba:active.1         | 2016-08-24T16:15:17Z | NaN    | Inactive | 1      | 0      | Good        | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T15:01:09Z | NaN    |          | 1      | -254   | Shutdown    | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T15:03:17Z | 0.0    |          | 1      | 0      | Good        | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T15:43:17Z | 12.0   |          | 1      | 0      | Good        | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T16:21:20Z | NaN    |          | 1      | -254   | Shutdown    | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T16:23:30Z | 1.0    |          | 1      | 0      | Good        | false        | false       | false     |             | 
| default | cdep158             | 2016-08-24T22:28:30Z | 43.0   |          | 1      | 0      | Good        | false        | false       | false     |             | 
```
