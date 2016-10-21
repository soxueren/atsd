# Data Migration from Graphite to ATSD
 
Use the `migrate.py` utility to migrate data from the Graphite server, specifically from its built-in Whisper database, to the Axibase Time Series Database.

The `migrate.py` utility extracts stored historical data from Whisper files and transfers it in Graphite format into ATSD.

[Download the migrate.py utility here.](https://github.com/axibase/atsd-graphite-finder/blob/master/bin/migrate.py)

Below are the available configurations and examples.

`migrate.py` supports only Whisper files (`.wsp`).

Data sent into ATSD by the `migrate.py` utility is parsed according to rules specified in the `graphite.conf `file. [Learn about Graphite format and graphite.conf settings here](https://axibase.com/products/axibase-time-series-database/writing-data/graphite-format/).

Using `migrate.py`:

```
migrate.py [-h] [--whisper-base BASE] [-R] path atsd_server atsd_tcp_port
```

#### migrate.py settings:

| Setting | Required | Description | 
| --- | --- | --- | 
|  <p>`-h` OR `--help`</p>  |  <p>no</p>  |  <p>Show help message and exit.</p>  | 
|  <p>`path`</p>  |  <p>yes</p>  |  <p>Path to folder with `.wsp` files or `.wsp` file that will be exported to ATSD.</p>  <p>Path must be specified either:</p>  <p>– directly to the `.wsp` file (without `-R` setting)</p>  <p>OR</p>  <p>– to the folder containing `.wsp` files (with `-R` setting).</p>  <p>Note that the `~` symbol cannot be used when specifying path.</p>  | 
|  <p>`-R`</p>  |  <p>no</p>  |  <p>Export recursively all files in the specified folder; searches are sub folders and directories for `.wsp` files.</p>  <p>If `-R` is not specified then you must specify the direct `path` to the `.wsp` file.</p>  | 
|  <p>`--whisper-base BASE`</p>  |  <p>no</p>  |  <p>Base path to which all metric names will be resolved.</p>  <p>Recommended to set Whisper base directory.</p>  <p>Default value: `.` (current directory).</p>  | 
|  <p>`atsd_server`</p>  |  <p>yes</p>  |  <p>ATSD hostname or IP.</p>  | 
|  <p>`atsd_tcp_port`</p>  |  <p>yes</p>  |  <p>ATSD TCP listening port. Default ATSD TCP port is 8081.</p>  | 


#### Examples:

Base path to the Whisper database directory is set with `-R` to migrate all the data and metrics.

Command:

```
./migrate.py -R --whisper-root=/var/lib/graphite/whisper/ /var/lib/graphite/whisper/ atsd_server 8081
```

Messages sent to ATSD:

```
carbon.agents.NURSWGDKR002-a.avgUpdateTime 9.41753387451e-05 1436859240
carbon.agents.NURSWGDKR002-a.avgUpdateTime 9.3019925631e-05 1436859300
carbon.agents.NURSWGDKR002-a.avgUpdateTime 9.33683835543e-05 1436859360
...
collectd.NURSWGDKR002.users.users 4.0 1436878560
collectd.NURSWGDKR002.users.users 4.0 1436878620
collectd.NURSWGDKR002.users.users 4.0 1436878680
```

Direct path to a specific `.wsp` file is set without `-R` to migrate only the contained metric.

Command:

```
./migrate.py --whisper-root=/opt/graphite/whisper/ /opt/graphite/whisper/collectd/NURSWGDKR002/memory/memory-free.wsp atsd_server 8081
```

Messages sent to ATSD:

```
collectd.NURSWGDKR002.memory.memory-free 31467552768.0 1436867280
collectd.NURSWGDKR002.memory.memory-free 31480631296.0 1436867340
collectd.NURSWGDKR002.memory.memory-free 31409938432.0 1436867400
collectd.NURSWGDKR002.memory.memory-free 31384133632.0 1436867460
```

#### Test Data Migration:

To test data migration, run the following commands. Substitute `path`, `--whisper-base`, `atsd_server`, and `atsd_tcp_port` with your correct parameters. The results will be a text file containing all metrics that were migrated by the utility.

```
nc -lk 8081 > test.txt &
```

```
date +%s && ./migrate.py -R --whisper-base=/var/lib/graphite/whisper/ /var/lib/graphite/whisper/ atsd_server 8081 && date +%s
```

From the prompt and the resulting `test.txt` file, you can determine how long the migration took, the amount of lines sent, and the amount of bytes transferred.

Out test migration results:

```
9 seconds
730236 lines
49246203 bytes
```
