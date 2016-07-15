# Grouped Last Time

Returns the last time of a stored value in a table for a key (metric + entity + tags).

## Query

```sql
SELECT entity, datetime, AVG(mpstat.cpu_busy.value)
  FROM mpstat.cpu_busy
WHERE datetime > now - 1 * hour 
  GROUP BY entity, period(15 minute)
  WITH time > last_time - 30 * minute
```

## Results

```ls
| entity       | datetime             | AVG(cpu_busy.value) | 
|--------------|----------------------|---------------------| 
| awsswgvml001 | 2015-11-13T09:15:00Z | 4.0                 | 
| awsswgvml001 | 2015-11-13T09:30:00Z | 2.9978571428571428  | 
| awsswgvml001 | 2015-11-13T09:45:00Z | 1.3618181818181818  | 
| nurswgvml003 | 2015-11-13T09:15:00Z | 9.485               | 
| nurswgvml003 | 2015-11-13T09:30:00Z | 3.659107142857143   | 
| nurswgvml003 | 2015-11-13T09:45:00Z | 0.9069767441860465  | 
| nurswgvml006 | 2015-11-13T09:15:00Z | 14.61357142857143   | 
| nurswgvml006 | 2015-11-13T09:30:00Z | 19.994285714285716  | 
| nurswgvml006 | 2015-11-13T09:45:00Z | 26.842790697674417  | 
| nurswgvml007 | 2015-11-13T09:15:00Z | 13.569285714285714  | 
| nurswgvml007 | 2015-11-13T09:30:00Z | 16.40140350877193   | 
| nurswgvml007 | 2015-11-13T09:45:00Z | 14.703095238095237  | 
| nurswgvml010 | 2015-11-13T09:15:00Z | 3.5299999999999994  | 
| nurswgvml010 | 2015-11-13T09:30:00Z | 2.8894642857142854  | 
| nurswgvml010 | 2015-11-13T09:45:00Z | 3.54280701754386    | 
| nurswgvml011 | 2015-11-13T09:15:00Z | 1.404               | 
| nurswgvml011 | 2015-11-13T09:30:00Z | 2.895178571428571   | 
| nurswgvml011 | 2015-11-13T09:45:00Z | 1.9095348837209303  | 
| nurswgvml102 | 2015-11-13T09:15:00Z | 0.7986666666666666  | 
| nurswgvml102 | 2015-11-13T09:30:00Z | 1.0714285714285714  | 
| nurswgvml102 | 2015-11-13T09:45:00Z | 0.8595348837209302  | 
```
