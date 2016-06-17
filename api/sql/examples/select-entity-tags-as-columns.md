# Select Entity Tags as Columns

## Query

```sql
SELECT entity, entity.tags.os AS os, entity.tags.ip AS ip FROM df.disk_used 
 WHERE time BETWEEN now - 1*minute AND now GROUP BY entity
```

## Results

| entity       | os    | ip          | 
|--------------|-------|-------------| 
| nurswgvml003 | Linux | 10.102.0.2  | 
| nurswgvml006 | Linux | 10.102.0.5  | 
| nurswgvml007 | Linux | 10.102.0.6  | 
| nurswgvml010 | Linux | 10.102.0.9  | 
| nurswgvml011 | Linux | 10.102.0.10 | 
| nurswgvml102 | Linux | 10.102.0.1  | 
