# Datetime Format

A `datetime` column returns time in ISO format.

## Query

```sql
SELECT datetime, time, value
  FROM mpstat.cpu_busy 
WHERE entity = 'nurswgvml007'
  AND datetime BETWEEN '2016-04-09T14:00:00Z' AND '2016-04-09T14:05:00Z'
```

## Results

```ls
| datetime             | time          | value |
|----------------------|---------------|------:|
| 2016-04-09T14:00:01Z | 1428588001000 | 3.8   |
| 2016-04-09T14:00:18Z | 1428588018000 | 14.0  |
| 2016-04-09T14:00:34Z | 1428588034000 | 16.83 |
| 2016-04-09T14:00:50Z | 1428588050000 | 10.2  |
| 2016-04-09T14:01:06Z | 1428588066000 | 4.04  |
| 2016-04-09T14:01:22Z | 1428588082000 | 9.0   |
| 2016-04-09T14:01:38Z | 1428588098000 | 2.0   |
| 2016-04-09T14:01:54Z | 1428588114000 | 8.0   |
| 2016-04-09T14:02:10Z | 1428588130000 | 10.23 |
| 2016-04-09T14:02:26Z | 1428588146000 | 14.0  |
| 2016-04-09T14:02:42Z | 1428588162000 | 20.2  |
```

## date_format Function

The `date_format` function can print out the `time` column as well as any numeric column containing Epoch milliseconds, formatted with the user-defined format and time zone.

```sql
SELECT time, 
  date_format(time), 
  date_format(time, "yyyy-MM-dd'T'HH:mm:ssZ"),
  date_format(time, 'yyyy-MM-dd HH:mm:ss'),
  date_format(time, 'yyyy-MM-dd HH:mm:ss', 'PST'),
  date_format(time, 'yyyy-MM-dd HH:mm:ss', 'GMT-08:00'),
  date_format(time, 'yyyy-MM-dd HH:mm:ss ZZ', 'PST'),
  date_format(time, 'yyyy-MM-dd HH:mm:ss ZZ', 'PST')
FROM "mpstat.cpu_busy"
  WHERE datetime > now - 5 * minute
  LIMIT 1
```

```ls
| time          | date_format(time)        | date_format(time,'yyyy-MM-dd'T'HH:mm:ssZ') | date_format(time,'yyyy-MM-dd HH:mm:ss') | date_format(time,'yyyy-MM-dd HH:mm:ss','PST') | date_format(time,'yyyy-MM-dd HH:mm:ss','GMT-08:00') | date_format(time,'yyyy-MM-dd HH:mm:ss ZZ','PST') | date_format(time,'yyyy-MM-dd HH:mm:ss ZZ','PST') | 
|---------------|--------------------------|--------------------------------------------|-----------------------------------------|-----------------------------------------------|-----------------------------------------------------|--------------------------------------------------|--------------------------------------------------| 
| 1468581897000 | 2016-07-15T11:24:57.000Z | 2016-07-15T11:24:57+0000                   | 2016-07-15 11:24:57                     | 2016-07-15 04:24:57                           | 2016-07-15 03:24:57                                 | 2016-07-15 04:24:57 -07:00                       | 2016-07-15 04:24:57 -07:00                       | 
```

```ls
| format                                                 | date_format value          | 
|--------------------------------------------------------|----------------------------| 
| time                                                   | 1468411675000              | 
| date_format(time)                                      | 2016-07-13T12:07:55.000Z   | 
| date_format(time,'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'','UTC') | 2016-07-13T12:07:55.000Z   | 
| date_format(time,'yyyy-MM-dd HH:mm:ss')                | 2016-07-13 12:07:55        | 
| date_format(time,'yyyy-MM-dd HH:mm:ss','PST')          | 2016-07-13 05:07:55        | 
| date_format(time,'yyyy-MM-dd HH:mm:ss','GMT-08:00')    | 2016-07-13 04:07:55        | 
| date_format(time,'yyyy-MM-dd HH:mm:ss Z','PST')        | 2016-07-13 05:07:55 -0700  | 
| date_format(time,'yyyy-MM-dd HH:mm:ss ZZ','PST')       | 2016-07-13 05:07:55 -07:00 | 
```
