# Counter Aggregator

## Query

```sql
SELECT datetime, count(value), max(value), counter(value) 
  FROM log_event_total_counter 
WHERE entity = 'nurswgvml201' AND tags.level = 'ERROR' 
  AND datetime >= '2015-09-30T09:00:00Z' AND datetime < '2015-09-30T10:00:00Z' 
GROUP BY period(5 minute)
```

## Results

| datetime             | count(value) | max(value) | counter(value) | 
|----------------------|--------------|------------|----------------| 
| 2015-09-30T09:00:00Z | 5.0          | 3.0        | 1.0            | 
| 2015-09-30T09:05:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:10:00Z | 4.0          | 3.0        | 0.0            | 
| 2015-09-30T09:15:00Z | 6.0          | 5.0        | 5.0            | 
| 2015-09-30T09:20:00Z | 5.0          | 8.0        | 3.0            | 
| 2015-09-30T09:25:00Z | 4.0          | 3.0        | 3.0            | 
