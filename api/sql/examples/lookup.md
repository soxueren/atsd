# LOOKUP Function

The `LOOKUP` function translates keys into values using the specified replacement table.

## Replacement tables

Replacement tables contain a collection of `key=value` mappings, specified one per line on the `Configuration: Replacement Tables` page.

![Replacement Table](../images/replacement-table-tcp.png)

## Example: TCP status codes

### Replacement Table `tcp-status-codes`

```ls
0=Connection established successfully
1=Connection Error
2=No Route To Host
3=Unknown Host
4=Socket Timeout
5=Other Error
```

### Query

```sql
SELECT value AS 'code', ISNULL(LOOKUP('tcp-status-codes', value), value) AS 'name', COUNT(value)
  FROM 'docker.tcp-connect-status'
WHERE datetime > now - 15 * MINUTE
  GROUP BY value
```

## Results

```ls
| code | name                                | COUNT(value) |
|------|-------------------------------------|--------------|
| 0    | Connection established successfully | 852.0        |
| 1    | Connection Error                    | 104.0        |
```

## Example: PI status codes

The query translates numeric values into string codes for PI Tag digital tags.

### Replacement Table `pi-pids`

```ls
...
-188=?188
-189=?189
-190=?190
-191=?191
-192=?192
-193=No Alarm
-194=High Alarm
-195=Low Alarm
-196=Hi Alarm/Ack
-197=Lo Alarm/Ack
-198=NoAlrm/UnAck
-199=Bad Quality
-200=Rate Alarm
-201=Rate Alm/Ack
-202=Dig Alarm
...
```

### Query

```sql
SELECT datetime, metric.label, metric.tags.point_data_type AS 'pi tag type',
  value, LOOKUP('pi-pids', value)
FROM 'ba:phase.1'
  LIMIT 10
```

### Results

```ls
| datetime                 | metric.label | pi tag type | value     | LOOKUP('pi-pids',value) |
|--------------------------|--------------|-------------|-----------|-------------------------|
| 2016-11-02T17:28:36.000Z | BA:PHASE.1   | digital     | -131075.0 | Phase4                  |
| 2016-11-02T17:39:06.000Z | BA:PHASE.1   | digital     | -131076.0 | Phase5                  |
| 2016-11-02T17:50:06.000Z | BA:PHASE.1   | digital     | -131077.0 | Phase6                  |
| 2016-11-02T17:55:06.000Z | BA:PHASE.1   | digital     | -131078.0 | Phase7                  |
| 2016-11-02T18:00:06.000Z | BA:PHASE.1   | digital     | -131072.0 | Phase1                  |
| 2016-11-02T18:20:06.000Z | BA:PHASE.1   | digital     | -131073.0 | Phase2                  |
| 2016-11-02T18:27:36.000Z | BA:PHASE.1   | digital     | -131074.0 | Phase3                  |
| 2016-11-02T18:49:36.000Z | BA:PHASE.1   | digital     | -131075.0 | Phase4                  |
| 2016-11-02T18:59:06.000Z | BA:PHASE.1   | digital     | -131076.0 | Phase5                  |
| 2016-11-02T19:09:06.000Z | BA:PHASE.1   | digital     | -131077.0 | Phase6                  |
```
