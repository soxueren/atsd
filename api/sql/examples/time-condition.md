# Time Condition

## Query

```sql
SELECT time, value FROM mpstat.cpu_busy 
 WHERE entity = 'nurswgvml007' 
 AND time > 1466100000000 
 AND time < 1466200000000
```

## Results

```
| time          | value | 
|---------------|-------| 
| 1466100003000 | 37.2  | 
| 1466100019000 | 3.1   | 
| 1466100035000 | 4.0   | 
| 1466100051000 | 32.6  | 
| 1466100067000 | 6.1   | 
| 1466100083000 | 1.0   | 
| 1466100099000 | 3.1   | 
| 1466100115000 | 5.0   | 
```