# Filter Series without Specified Tag

Select series without the specified tag using `tags.{name} IS NULL` condition.

## Data

```ls
| entity  | datetime                 | value | tags.tag4 | tags.tag2 | tags.tag1 | 
|---------|--------------------------|-------|-----------|-----------|-----------| 
| e-sql-1 | 2016-06-19T11:00:00.000Z | 1.0   | null      | null      | val1      | 
| e-sql-2 | 2016-06-19T11:00:00.000Z | 2.0   | null      | val2      | val2      | 
| e-sql-3 | 2016-06-19T11:00:00.000Z | 3.0   | null      | val3      | null      | 
| e-sql-4 | 2016-06-19T11:00:00.000Z | 4.0   | val4      | null      | null      | 
```

## Query

Select series without `tag4` using `IS NULL` condition:

```sql
SELECT entity, datetime, value, tags.*
  FROM "m-metric1"
WHERE datetime >= "2016-06-19T11:00:00.000Z" and datetime < "2016-06-19T12:00:00.000Z"
  AND tags.tag4 IS NULL
```

## Results

```ls
| entity  | datetime                 | value | tags.tag1 | tags.tag2 | tags.tag4 | 
|---------|--------------------------|-------|-----------|-----------|-----------| 
| e-sql-1 | 2016-06-19T11:00:00.000Z | 1.0   | val1      | null      | null      | 
| e-sql-2 | 2016-06-19T11:00:00.000Z | 2.0   | val2      | val2      | null      | 
| e-sql-3 | 2016-06-19T11:00:00.000Z | 3.0   | null      | val3      | null      |
```

## Alternative using `NOT tags.{name} != ''` 

Given that tag value cannot be an empty string, series with a given tag `{name}` match the following condition `tags.{name} != ''`.

`NOT tags.{name} != ''` is the negation that matches series without the given tag.

In the example below, `NOT tags.tag4 != ''` selects series without `tag4`.

## Query

Select series without tag4 using `NOT tags.{name} != ''` negation:

```sql
SELECT entity, datetime, value, tags.*
  FROM "m-metric1"
WHERE datetime >= "2016-06-19T11:00:00.000Z" and datetime < "2016-06-19T12:00:00.000Z"
  AND NOT tags.tag4 != ''
```

## Results

```ls
| entity  | datetime                 | value | tags.tag1 | tags.tag2 | tags.tag4 | 
|---------|--------------------------|-------|-----------|-----------|-----------| 
| e-sql-1 | 2016-06-19T11:00:00.000Z | 1.0   | val1      | null      | null      | 
| e-sql-2 | 2016-06-19T11:00:00.000Z | 2.0   | val2      | val2      | null      | 
| e-sql-3 | 2016-06-19T11:00:00.000Z | 3.0   | null      | val3      | null      | 

```
