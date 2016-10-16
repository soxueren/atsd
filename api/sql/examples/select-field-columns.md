# Select Field Columns

The list of predefined columns available in the `SELECT` expression includes columns are are mapped to entity and metric model fields. 

Such columns include:

* `metric.label`
* `metric.timezone`
* `metric.interpolate`
* `entity.label`
* `entity.timezone`
* `entity.interpolate`

## Query

```sql
SELECT datetime, entity, metric, value,
  metric.label, metric.timezone, metric.interpolate,
  entity.label, entity.timezone, entity.interpolate  
FROM atsd_series WHERE metric IN ('mpstat.cpu_busy', 'mpstat.cpu_user', 'mpstat.cpu_system')
  AND datetime BETWEEN '2016-10-16T17:10:00Z' AND '2016-10-16T17:10:20Z'
  AND entity LIKE 'nurswgvml0*'
  ORDER BY datetime
```

## Results

```ls
| datetime                 | entity       | metric     | value | metric.label | metric.timezone | metric.interpolate | entity.label                  | entity.timezone | entity.interpolate | 
|--------------------------|--------------|------------|-------|--------------|-----------------|--------------------|-------------------------------|-----------------|--------------------| 
| 2016-10-16T17:10:05.000Z | nurswgvml010 | cpu_busy   | 25.9  | null         | null            | LINEAR             | NURSWGVML010.corp.axibase.com | null            | null               | 
| 2016-10-16T17:10:05.000Z | nurswgvml010 | cpu_system | 0.8   | null         | null            | LINEAR             | NURSWGVML010.corp.axibase.com | null            | null               | 
| 2016-10-16T17:10:05.000Z | nurswgvml010 | cpu_user   | 25.1  | null         | null            | LINEAR             | NURSWGVML010.corp.axibase.com | null            | null               | 
| 2016-10-16T17:10:07.000Z | nurswgvml009 | cpu_busy   | 1.0   | null         | null            | LINEAR             | null                          | UTC             | null               | 
| 2016-10-16T17:10:07.000Z | nurswgvml009 | cpu_system | 1.0   | null         | null            | LINEAR             | null                          | UTC             | null               | 
| 2016-10-16T17:10:07.000Z | nurswgvml009 | cpu_user   | 0.0   | null         | null            | LINEAR             | null                          | UTC             | null               | 
| 2016-10-16T17:10:08.000Z | nurswgvml006 | cpu_busy   | 5.0   | null         | null            | LINEAR             | NURSWGVML006                  | CST             | null               | 
| 2016-10-16T17:10:08.000Z | nurswgvml006 | cpu_system | 2.0   | null         | null            | LINEAR             | NURSWGVML006                  | CST             | null               | 
| 2016-10-16T17:10:08.000Z | nurswgvml006 | cpu_user   | 3.0   | null         | null            | LINEAR             | NURSWGVML006                  | CST             | null               | 
| 2016-10-16T17:10:13.000Z | nurswgvml007 | cpu_busy   | 15.5  | null         | null            | LINEAR             | null                          | PST             | LINEAR             | 
| 2016-10-16T17:10:13.000Z | nurswgvml007 | cpu_system | 2.1   | null         | null            | LINEAR             | null                          | PST             | LINEAR             | 
| 2016-10-16T17:10:13.000Z | nurswgvml007 | cpu_user   | 13.4  | null         | null            | LINEAR             | null                          | PST             | LINEAR             | 
```

