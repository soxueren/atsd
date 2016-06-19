# Filter by Tag

Select series without specified tag, using `NOT tags.tag4 != ''` condition.

`tags.tag4 = ''` cannot be use since tag4 value is NULL.

`tags.tag4 != ''` matches series that have this tag, `NOT tags.tag4 != ''` is the negation.

## Data

```ls
series d:2016-06-19T11:00:00.000Z e:e-sql-1 m:m-metric1=1 t:tag1=val1
series d:2016-06-19T11:00:00.000Z e:e-sql-2 m:m-metric1=2 t:tag1=val2 t:tag2=val2
series d:2016-06-19T11:00:00.000Z e:e-sql-3 m:m-metric1=3 t:tag2=val3
series d:2016-06-19T11:00:00.000Z e:e-sql-4 m:m-metric1=4 t:tag4=val4
```

## Query

Select series without tag4:

```sql
SELECT entity, datetime, value, tags.*
  FROM metric1
WHERE datetime >= "2016-06-19T11:00:00.000Z" and datetime < "2016-06-19T12:00:00.000Z"
  AND NOT tags.tag4 != ''
```

## Results

```ls
| entity  | datetime                 | value | tags.tag4 | tags.tag2 | tags.tag1 | 
|---------|--------------------------|-------|-----------|-----------|-----------| 
| e-sql-1 | 2016-06-19T11:07:06.018Z | 1.0   | null      | null      | val1      | 
| e-sql-2 | 2016-06-19T11:07:06.018Z | 2.0   | null      | val2      | val2      | 
| e-sql-3 | 2016-06-19T11:07:06.018Z | 3.0   | null      | val3      | null      | 
```

## Query

Select all series:

```sql
SELECT entity, datetime, value, tags.*
  FROM metric1
WHERE datetime >= "2016-06-19T11:00:00.000Z" and datetime < "2016-06-19T12:00:00.000Z"
```

## Results

```ls
| entity  | datetime                 | value | tags.tag4 | tags.tag2 | tags.tag1 | 
|---------|--------------------------|-------|-----------|-----------|-----------| 
| e-sql-1 | 2016-06-19T11:07:06.018Z | 1.0   | null      | null      | val1      | 
| e-sql-2 | 2016-06-19T11:07:06.018Z | 2.0   | null      | val2      | val2      | 
| e-sql-3 | 2016-06-19T11:07:06.018Z | 3.0   | null      | val3      | null      | 
| e-sql-4 | 2016-06-19T11:07:06.018Z | 4.0   | val4      | null      | null      | 
```
