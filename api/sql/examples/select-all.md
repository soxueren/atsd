# SELECT All

## Query

```sql
SELECT * FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' 
 AND time between now - 1 * hour AND now
```

## Results 

| entity       | time          | value | 
|--------------|---------------|-------| 
| nurswgvml007 | 1446034244000 | 35.71 | 
| nurswgvml007 | 1446034260000 | 39.78 | 
| nurswgvml007 | 1446034276000 | 16.0  | 
| nurswgvml007 | 1446034292000 | 10.1  | 
| nurswgvml007 | 1446034308000 | 9.0   | 
| nurswgvml007 | 1446034324000 | 12.12 | 
| nurswgvml007 | 1446034340000 | 10.31 | 
| nurswgvml007 | 1446034356000 | 8.08  | 
| nurswgvml007 | 1446034372000 | 10.1  | 
| nurswgvml007 | 1446034388000 | 12.87 | 
| nurswgvml007 | 1446034404000 | 9.09  | 
| nurswgvml007 | 1446034420000 | 8.25  | 
