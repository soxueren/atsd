# ORDER BY String (Collation)

Ordering of strings is based on their Unicode value, with `NULL` having the lowest value.

## Data

```ls
series e:e-1 m:m-order=1  d:2016-08-01T00:00:00Z
series e:e-1 m:m-order=2  d:2016-08-01T00:00:00Z t:tag-1=" ." t:tag-unicode-1=U+002E
series e:e-1 m:m-order=3  d:2016-08-01T00:00:00Z t:tag-1=1  t:tag-unicode-1=U+0031
series e:e-1 m:m-order=4  d:2016-08-01T00:00:00Z t:tag-1=01 t:tag-unicode-1=U+0030 t:tag-unicode-2=U+0031
series e:e-1 m:m-order=5  d:2016-08-01T00:00:00Z t:tag-1=11 t:tag-unicode-1=U+0031 t:tag-unicode-2=U+0031
series e:e-1 m:m-order=6  d:2016-08-01T00:00:00Z t:tag-1=10 t:tag-unicode-1=U+0031 t:tag-unicode-2=U+0030
series e:e-1 m:m-order=7  d:2016-08-01T00:00:00Z t:tag-1=A  t:tag-unicode-1=U+0041
series e:e-1 m:m-order=8  d:2016-08-01T00:00:00Z t:tag-1=B  t:tag-unicode-1=U+0042
series e:e-1 m:m-order=9  d:2016-08-01T00:00:00Z t:tag-1=AB t:tag-unicode-1=U+0041 t:tag-unicode-2=U+0042
series e:e-1 m:m-order=10 d:2016-08-01T00:00:00Z t:tag-1=a  t:tag-unicode-1=U+0061
series e:e-1 m:m-order=11 d:2016-08-01T00:00:00Z t:tag-1=a  t:tag-unicode-1=U+00E1
series e:e-1 m:m-order=12 d:2016-08-01T00:00:00Z t:tag-1=a  t:tag-unicode-1=U+00E4
series e:e-1 m:m-order=13 d:2016-08-01T00:00:00Z t:tag-1=e  t:tag-unicode-1=U+00E9
series e:e-1 m:m-order=14 d:2016-08-01T00:00:00Z t:tag-1=y  t:tag-unicode-1=U+00FF
series e:e-1 m:m-order=15 d:2016-08-01T00:00:00Z t:tag-1=a  t:tag-unicode-1=U+01CE
series e:e-1 m:m-order=16 d:2016-08-01T00:00:00Z t:tag-1=a  t:tag-unicode-1=U+0101
series e:e-1 m:m-order=17 d:2016-08-01T00:00:00Z t:tag-1=à  t:tag-unicode-1=U+0430
series e:e-1 m:m-order=18 d:2016-08-01T00:00:00Z t:tag-1=?  t:tag-unicode-1=U+03B1
series e:e-1 m:m-order=19 d:2016-08-01T00:00:00Z t:tag-1=resume  t:tag-unicode-1=U+0072 t:tag-unicode-2=U+0065
series e:e-1 m:m-order=20 d:2016-08-01T00:00:00Z t:tag-1=resume  t:tag-unicode-1=U+0072 t:tag-unicode-2=U+00E9
series e:e-1 m:m-order=21 d:2016-08-01T00:00:00Z t:tag-1=Resume  t:tag-unicode-1=U+0052 t:tag-unicode-2=U+00E9
series e:e-1 m:m-order=22 d:2016-08-01T00:00:00Z t:tag-1=Resumes t:tag-unicode-1=U+0052 t:tag-unicode-2=U+0065
series e:e-1 m:m-order=23 d:2016-08-01T00:00:00Z t:tag-1=resumes t:tag-unicode-1=U+0072 t:tag-unicode-2=U+0065
series e:e-1 m:m-order=24 d:2016-08-01T00:00:00Z t:tag-1=resumes t:tag-unicode-1=U+0072 t:tag-unicode-2=U+00E9 t:tag-unicode-3=U+0073
series e:e-1 m:m-order=25 d:2016-08-01T00:00:00Z t:tag-1=a?b t:tag-unicode-1=U+0061 t:tag-unicode-2=U+00A8
series e:e-1 m:m-order=26 d:2016-08-01T00:00:00Z t:tag-1=ab  t:tag-unicode-1=U+00E4 t:tag-unicode-2=U+0062
series e:e-1 m:m-order=27 d:2016-08-01T00:00:00Z t:tag-1=aa  t:tag-unicode-1=U+00E4 t:tag-unicode-2=U+0061
series e:e-1 m:m-order=28 d:2016-08-01T00:00:00Z t:tag-1=ac  t:tag-unicode-1=U+00E4 t:tag-unicode-2=U+0063
```

## Query

```sql
SELECT tags.'tag-1' AS 'Tag Value', tags.'tag-unicode-1' AS 'unicode-1', tags.'tag-unicode-2' AS 'unicode-2', tags.'tag-unicode-3' AS 'unicode-3'
  FROM 'm-order'
WHERE entity = 'e-1'
  ORDER BY tags.'tag-1' ASC
```

## Results

```ls
| Tag Value | unicode-1 | unicode-2 | unicode-3 | 
|-----------|-----------|-----------|-----------| 
| null      | null      | null      | null      | 
| .         | U+002E    | null      | null      | 
| 01        | U+0030    | U+0031    | null      | 
| 1         | U+0031    | null      | null      | 
| 10        | U+0031    | U+0030    | null      | 
| 11        | U+0031    | U+0031    | null      | 
| A         | U+0041    | null      | null      | 
| AB        | U+0041    | U+0042    | null      | 
| B         | U+0042    | null      | null      | 
| Resumes   | U+0052    | U+0065    | null      | 
| Resume    | U+0052    | U+00E9    | null      | 
| a         | U+0061    | null      | null      | 
| a?b       | U+0061    | U+00A8    | null      | 
| resume    | U+0072    | U+0065    | null      | 
| resumes   | U+0072    | U+0065    | null      | 
| resume    | U+0072    | U+00E9    | null      | 
| resumes   | U+0072    | U+00E9    | U+0073    | 
| a         | U+00E1    | null      | null      | 
| a         | U+00E4    | null      | null      | 
| aa        | U+00E4    | U+0061    | null      | 
| ab        | U+00E4    | U+0062    | null      | 
| ac        | U+00E4    | U+0063    | null      | 
| e         | U+00E9    | null      | null      | 
| y         | U+00FF    | null      | null      | 
| a         | U+0101    | null      | null      | 
| a         | U+01CE    | null      | null      | 
| ?         | U+03B1    | null      | null      | 
| à         | U+0430    | null      | null      | 
```

