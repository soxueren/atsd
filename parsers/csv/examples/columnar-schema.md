# Columnar Schema Example

#### Input File

```
          , Sensor-0001, Sensor-0001, Sensor-0001, Sensor-0020, Sensor-0020, Sensor-0020
          ,PV120000-XG1,PV120000-XG1,PV120000-XG1,PV120000-XG1,PV120000-XG1,PV120000-XG1
2015-10-15, Temperature,    Humidity,    Pressure, Temperature,    Humidity,    Pressure
     00:00,        35.5,        40.0,         760,        20.4,        60.8,         745
     00:10,        35.6,        40.8,         750,        20.5,        60.5,         745
```

#### Parser Settings

`Timestamp Pattern: yyyy-MM-dd HH:mm`          # Used to parse timestamp values from 1st column

#### Schema

```javascript
select("#cell=4,2-*,*").
addSeries().
metric(cell(3, col)).
entity(cell(1, col)).
tag('model',cell(2, col)).
timestamp(cell(3, 1) + ' ' + cell(row, 1));
```


- For each cell in the cell range, starting with 4th row and until the last row and starting from 2nd column and until last column;
- Set metric name to value of cell located in 3rd row in the current column: Temperature, Humidity, Pressure (repeated);
- Set entity name to value of cell located in 2nd row in the current column;
- Set tag ‘model’ to value of cell located in 3rd row in the current column;
- Set timestamp to value of cell located in 3rd row and 1st column (date part 2015-11-15) concatenated with value of cell located in current row in the 1st column (hour part). The concatenated value will be parsed using ‘Timestamp Pattern’;
- Set series value to value of current cell.


#### Commands

```ls
series e:sensor-0001 d:2015-11-15T00:00:00Z m:temperature=35.5 m:humidity=40.0 m:pressure=760 t:model=PV120000-XG1
series e:sensor-0020 d:2015-11-15T00:00:00Z m:temperature=20.4 m:humidity=60.8 m:pressure=745 t:model=PV120000-XG1
series e:sensor-0001 d:2015-11-15T00:10:00Z m:temperature=35.6 m:humidity=40.8 m:pressure=750 t:model=PV120000-XG1
series e:sensor-0020 d:2015-11-15T00:10:00Z m:temperature=20.5 m:humidity=60.5 m:pressure=745 t:model=PV120000-XG1
```

