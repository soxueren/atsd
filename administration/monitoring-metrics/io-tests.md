# Monitoring metrics : I/O Tests


I/O Tests can be run directly from the ATSD UI under Admin -\> I/O
Tests.

Direct url: `atsd_server:8088/admin/io-tests`

I/O Tests allow to:

-   Execute disk read and write tests for the server on which ATSD
    instance is running
-   Identify any abnormalities such as slower than expected write speed

| Field | Description |
| --- | --- |
| Thread count | number of threads that will be tested. |
| File size, MB | size of the file that will be written to disk. |
| Buffer Size | size of the buffer for writing/reading the file. |
| Directory: absolute path | absolute path where the test file will be written to or from which the file will be read. |
| Disk force | force the data to be written to disk. |
| Read the same file | use a single prepared file to test all threads. |

![](images/atsd_io_tests.png "atsd_io_tests")