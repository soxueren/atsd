# Columnar Period Schema Example

#### Input File

```csv
2015-10-15, Sensor-0001,      Status, Sensor-0020,      Status
     00:00,        35.5,      provis,        20.4,          ok
     00:10,        35.6,          ok,        20.5,          ok
```

#### Parser Settings

`Timestamp Pattern: yyyy-MM-dd HH:mm`          # Used to parse timestamp values from 1st column

#### Schema

```javascript
select("#cell=2,2-*,*!1,2").
addSeries().
metric('temperature').
entity(cell(1, col)).
tag('status',cell(row, col+1)).
timestamp(cell(1, 1) + ' ' + cell(row, 1));
```


- For each cell in the cell range, starting with 4th row and until the last row and starting from 2nd column and until last column, iterating over EVEN columns (2 + step 2);
- Set metric name to a constant (temperature);
- Set entity name to value of cell located in 1nd row in the current column;
- Set tag ‘status’ to value of cell located in the current row to the right from the current column (column + 1);
- Set timestamp to value of cell located in 1rd row and 1st column (date part 2015-11-15) concatenated with value of cell located in the current row in the 1st column (hour part). The concatenated value will be parsed using ‘Timestamp Pattern’;
- Set series value to value of current cell.


#### Commands

```ls
series e:sensor-0001 d:2015-11-15T00:00:00Z m:temperature=35.5 t:status=provis
series e:sensor-0020 d:2015-11-15T00:00:00Z m:temperature=20.4 t:status=ok
series e:sensor-0001 d:2015-11-15T00:10:00Z m:temperature=35.6 t:status=ok
series e:sensor-0020 d:2015-11-15T00:10:00Z m:temperature=20.5 t:status=ok
```

