Compaction
==========

Compaction is a scheduled procedure to store data for each time series
in the most efficient schema.

By default, ATSD compaction runs every night and compacts values that
are older than 24 hours.

If required, ATSD compaction can be run on demand using the following
URL: `http://atsd_server:8088/compaction?day={yyyy-MM-dd}`

ATSD compaction can also be run for a specific metric using the
following
URL: `http://atsd_server:8088/compaction?day=2015-04-22&historical=true&metric={my-metric}`