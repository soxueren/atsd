# Select: Escape Quotes in Column Names

## Data

```ls
series e:e-q-1 m:m-q-1=12.4 t:"tag""double-quote"=tv1 t:tag'single-quote=tv2 t:"tag""both'quotes"=tv3
```

```json
[{
    "entity": "e-q-1",
    "metric": "m-q-1",
    "tags": {    
      "tag\"double-quote": "tv1",
      "tag'single-quote": "tv2",
	  "tag\"both'quotes": "tv3"
    },
    "data": [ { "d": "2016-07-27T22:41:50.407Z", "v": 12.4 } ]
}]
```

## Query

```sql
SELECT * FROM "m-q-1"
WHERE datetime > '2016-07-27T22:40:00.000Z' 
```

```ls
| entity | datetime                 | value | tags.tag"double-quote | tags.tag"both'quotes | tags.tag'single-quote | 
|--------|--------------------------|-------|-----------------------|----------------------|-----------------------| 
| e-q-1  | 2016-07-27T22:41:50.407Z | 12.4  | tv1                   | tv3                  | tv2                   | 
```

## Query with Escaped Column Names

```sql
SELECT * FROM "m-q-1"
WHERE datetime > '2016-07-27T22:40:00.000Z' 
```

```ls
| entity | datetime                 | value | tags.tag"double-quote | tags.tag"both'quotes | tags.tag'single-quote | 
|--------|--------------------------|-------|-----------------------|----------------------|-----------------------| 
| e-q-1  | 2016-07-27T22:41:50.407Z | 12.4  | tv1                   | tv3                  | tv2                   | 
```