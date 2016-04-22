# Monitoring metrics : Ingestion Statistics


ATSD ingestion statistics can be viewed directly from the UI, under
Admin -\> Receive Statistics.

Direct url: `atsd_server:8088/admin/receive-statistics?n=20`

The number of top-N rows displayed can be adjusted by changing `n`
parameter in the request URL.

Receive statistics allows to:

-   View top-N metric and entities for the last hour and the last 24
    hours ranked by the number of series samples inserted into ATSD.
-   Configure metric filters to discard particular entity/metric/tag
    combination from being stored in the database.
-   Modify metric retention interval (default is unlimited retention) to
    reduce the amount of storage used.
-   Qualify some metrics as non-persistent to use their data only in the
    rule engine without storing it on disk.

[Learn to configure the Metric Persistence Filter
here.](../metric-persistence-filter.md "Metric Persistence Filter")

![](images/ingestion_statistics.png "ingestion_statistics")