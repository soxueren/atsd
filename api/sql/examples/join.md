# Inner Join

Result table contains rows with table1.entity = table2.entity and table1.time = table2.time and table1.tags = table2.tags

## Query

```sql
SELECT *
  FROM cpu_busy
JOIN cpu_idle
  WHERE time > now - 1 * hour
```

## Results

```ls
| entity       | time          | cpu_busy.value | 
|--------------|---------------|----------------|
| awsswgvml001 | 1447400465000 | 100.0          | 
| awsswgvml001 | 1447400526000 | 100.0          | 
| awsswgvml001 | 1447400587000 | 100.0          | 
| awsswgvml001 | 1447400648000 | 1.0            | 
| awsswgvml001 | 1447400709000 | 29.41          | 
| awsswgvml001 | 1447400770000 | 0.0            | 
| awsswgvml001 | 1447400831000 | 1.0            | 
| awsswgvml001 | 1447400892000 | 1.03           | 
| awsswgvml001 | 1447400953000 | 3.03           |
| awsswgvml001 | 1447401014000 | 14.42          | 
```
