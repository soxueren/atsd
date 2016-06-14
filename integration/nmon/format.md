# Format

[Snapshot Example](#extract)

[Collected Metrics](#complete-list-of-collected-metrics)

[Derived Metrics](#derived-metrics)

Below is a snapshot extract from an nmon file displaying its format and how it records the collected data:
<a name="extract"></a>
```csv
ZZZZ,T0009,23:02:43,14-OCT-2014
CPU01,T0009,58.9,27.9,6.6,6.6
CPU02,T0009,0.4,0.6,0.0,99.0
CPU03,T0009,0.1,0.3,0.0,99.6
CPU04,T0009,0.0,0.3,0.0,99.7
....
SCPU255,T0009,0.00,0.00,0.00,0.01
SCPU256,T0009,0.00,0.00,0.00,0.01
CPU_ALL,T0009,16.0,1.7,0.7,81.6,,256
PCPU_ALL,T0009,10.22,1.12,0.1,6.42,64.00
SCPU_ALL,T0009,10.22,1.12,0.1,6.42
LPAR,T0009,17.817,64,256,124,64.00,128,0.00,13.92,14.37,1,0,15.98,1.75,0.09,10.03,15.98,1.75,0.09,10.03,0,0
POOLS,T0009,124,124.00,124.00,0.00,0.00,0.00,0.00,0,64.00
MEM,T0009,43.1,98.5,451646.0,129618.0,1048576.0,131584.0
MEMNEW,T0009,40.4,4.3,12.1,43.1,15.0,41.6
MEMUSE,T0009,4.3,3.0,40.0,960,1088,4.3,40.0, 259828592.0
PAGE,T0009,19186.2,17033.4,673.6,0.0,0.0,0.0,0.0,0.0
MEMPAGES4KB,T0009,229089856,106620616,11661852,0,11661852,110641309,960,1088,0,0,110641309
MEMPAGES64KB,T0009,2459100,562547,0,0,0,1896553,60,68,0,0,1896553,0,0,1843706
LARGEPAGE,T0009,0,0,0,0,16.0
PROC,T0009,20.79,0.52,24224,113015,9105,6041,5,6,41235,0,0,0,10
FILE,T0009,0,486,0,89481716,29209504,0,0,0
NET,T0009,0.0,1569.1,4748.1,0.0,21043.1,4752.3
NETPACKET,T0009,0.6,10604.5,4517.1,0.6,10604.5,4517.1
NETSIZE,T0009,56.8,151.5,1076.4,60.0,2921.5,1076.5
NETERROR,T0009,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
IOADAPT,T0009,20985.2,834.6,797.9,21476.7,882.3,814.1,21053.4,861.9,807.3,21326.5,911.4,812.4,744.0,138.7,36.1,0.1,138.7,33.0
JFSFILE,T0009,88.2,9.5,59.9,44.7,1.3,0.3,15.6,0.3,36.4,73.7,68.1,89.5,59.0,88.9,88.9,88.9,88.9,0.7,46.8,88.6,93.1,93.0,93.5,93.8
JFSINODE,T0009,13.8,0.0,12.4,4.4,0.1,0.0,0.8,0.0,11.1,0.3,18.0,3.2,2.9,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
DISKBUSY,T0009,5.1,6.4,3.9,4.2,4.9,4.4,4.1,4.1,4.6,3.1,3.9,3.6,2.8,2.2,14.0,22.6,8.5,0.0,5.5,5.6,6.7,10.5,0.5,11.1,0.0,24.9,7.8,5.0
DISKREAD,T0009,1136.4,447.6,382.6,305.1,322.3,352.8,321.1,326.3,355.4,97.8,95.5,87.6,152.3,92.2,979.4,2052.8,10432.8,0.0,438.8,559.2,4
DISKWRITE,T0009,2701.0,6.9,14.3,2.8,10.6,3.0,3.8,3.0,34.0,0.4,0.2,0.6,23.2,278.8,5.2,4.8,0.4,0.0,20.5,38.4,0.8,138.7,0.0,138.7,0.0
DISKXFER,T0009,124.5,56.8,44.8,38.5,40.8,44.2,40.4,41.2,45.6,12.4,12.1,11.3,24.5,15.0,122.6,185.4,166.0,0.0,55.2,70.9,56.7,33.2,3.0,33
DISKRXFER,T0009,74.8,56.0,43.7,38.3,40.4,44.1,40.2,40.9,43.4,12.4,12.1,11.2,19.1,11.0,122.3,185.0,166.0,0.0,53.3,68.5,56.6,0.2,3.0,0.0
DISKBSIZE,T0009,30.8,8.0,8.9,8.0,8.2,8.0,8.0,8.0,8.5,7.9,7.9,7.8,7.2,24.8,8.0,11.1,62.9,0.0,8.3,8.4,8.0,4.8,244.9,4.2,0.5,60.2,8.0,8
```

#### Complete List of Collected Metrics:

| CPU Metrics | Memory Metrics | 
| --- | --- | 
|  <p>asynchronous_i/o.aiocpu</p>  <p>asynchronous_i/o.aioprocs</p>  <p>asynchronous_i/o.aiorunning</p>  <p>cpu.idle%</p>  <p>cpu.sys%</p>  <p>cpu.user%</p>  <p>cpu.wait%</p>  <p>cpu_total.busy</p>  <p>cpu_total.idle%</p>  <p>cpu_total.sys%</p>  <p>cpu_total.user%</p>  <p>cpu_total.wait%</p>  <p>logical_partition.capped</p>  <p>logical_partition.ec_idle%</p>  <p>logical_partition.ec_sys%</p>  <p>logical_partition.ec_user%</p>  <p>logical_partition.ec_wait%</p>  <p>logical_partition.entitled</p>  <p>logical_partition.folded</p>  <p>logical_partition.logicalcpus</p>  <p>logical_partition.physicalcpu</p>  <p>logical_partition.pool_id</p>  <p>logical_partition.poolcpus</p>  <p>logical_partition.poolidle</p>  <p>logical_partition.sharedcpu</p>  <p>logical_partition.usedallcpu%</p>  <p>logical_partition.usedpoolcpu%</p>  <p>logical_partition.virtualcpus</p>  <p>logical_partition.vp_idle%</p>  <p>logical_partition.vp_sys%</p>  <p>logical_partition.vp_user%</p>  <p>logical_partition.vp_wait%</p>  <p>logical_partition.weight</p>  <p>pcpu.idle</p>  <p>pcpu.sys</p>  <p>pcpu.user</p>  <p>pcpu.wait</p>  <p>pcpu_total.entitled_capacity</p>  <p>pcpu_total.idle</p>  <p>pcpu_total.sys</p>  <p>pcpu_total.user</p>  <p>pcpu_total.wait</p>  <p>processes.asleep_bufio</p>  <p>processes.asleep_diocio</p>  <p>processes.asleep_rawio</p>  <p>processes.blocked</p>  <p>processes.exec</p>  <p>processes.fork</p>  <p>processes.msg</p>  <p>processes.pswitch</p>  <p>processes.read</p>  <p>processes.runnable</p>  <p>processes.sem</p>  <p>processes.swap-in</p>  <p>processes.syscall</p>  <p>processes.write</p>  <p>scpu.idle</p>  <p>scpu.sys</p>  <p>scpu.user</p>  <p>scpu.wait</p>  <p>scpu_total.idle</p>  <p>scpu_total.sys</p>  <p>scpu_total.user</p>  <p>scpu_total.wait</p>  <p>multiple_cpu_pools.entitled</p>  <p>multiple_cpu_pools.entitled_pool_capacity</p>  <p>multiple_cpu_pools.max_pool_capacity</p>  <p>multiple_cpu_pools.pool_busy_time</p>  <p>multiple_cpu_pools.pool_id</p>  <p>multiple_cpu_pools.pool_max_time</p>  <p>multiple_cpu_pools.shcpu_busy_time</p>  <p>multiple_cpu_pools.shcpu_tot_time</p>  <p>multiple_cpu_pools.shcpus_in_sys</p>  |  <p>memory.real_free(mb)</p>  <p>memory.real_free_%</p>  <p>memory.real_total(mb)</p>  <p>memory.virtual_free(mb)</p>  <p>memory.virtual_free_%</p>  <p>memory.virtual_total(mb)</p>  <p>memory_mb.active</p>  <p>memory_mb.bigfree</p>  <p>memory_mb.buffers</p>  <p>memory_mb.cached</p>  <p>memory_mb.highfree</p>  <p>memory_mb.hightotal</p>  <p>memory_mb.inactive</p>  <p>memory_mb.lowfree</p>  <p>memory_mb.lowtotal</p>  <p>memory_mb.memfree</p>  <p>memory_mb.memshared</p>  <p>memory_mb.memtotal</p>  <p>memory_mb.swapcached</p>  <p>memory_mb.swapfree</p>  <p>memory_mb.swaptotal</p>  <p>memory_new.free%</p>  <p>memory_new.fscache%</p>  <p>memory_new.pinned%</p>  <p>memory_new.process%</p>  <p>memory_new.system%</p>  <p>memory_new.user%</p>  <p>memory_use.%maxclient</p>  <p>memory_use.%maxperm</p>  <p>memory_use.%minperm</p>  <p>memory_use.%numclient</p>  <p>memory_use.%numperm</p>  <p>memory_use.lruable_pages</p>  <p>memory_use.maxfree</p>  <p>memory_use.minfree</p>  <p>memorypages.cycles</p>  <p>memorypages.exfills</p>  <p>memorypages.maxfree</p>  <p>memorypages.minfree</p>  <p>memorypages.nonsys_pgs</p>  <p>memorypages.numclient</p>  <p>memorypages.numclsegpin</p>  <p>memorypages.numclseguse</p>  <p>memorypages.numcompress</p>  <p>memorypages.numframes</p>  <p>memorypages.numfrb</p>  <p>memorypages.numiodone</p>  <p>memorypages.numperm</p>  <p>memorypages.numpermio</p>  <p>memorypages.numpgsp_pgs</p>  <p>memorypages.numpout</p>  <p>memorypages.numpsegpin</p>  <p>memorypages.numpseguse</p>  <p>memorypages.numralloc</p>  <p>memorypages.numremote</p>  <p>memorypages.numsios</p>  <p>memorypages.numvpages</p>  <p>memorypages.numwsegpin</p>  <p>memorypages.numwseguse</p>  <p>memorypages.pageins</p>  <p>memorypages.pageouts</p>  <p>memorypages.pfavail</p>  <p>memorypages.pfpinavail</p>  <p>memorypages.pfrsvdblks</p>  <p>memorypages.pgexct</p>  <p>memorypages.pgrclm</p>  <p>memorypages.pgspgins</p>  <p>memorypages.pgspgouts</p>  <p>memorypages.pgsteals</p>  <p>memorypages.scans</p>  <p>memorypages.system_pgs</p>  <p>memorypages.zerofills</p>  <p>paging_and_virtual.allocstall</p>  <p>paging_and_virtual.kswapd_inodesteal</p>  <p>paging_and_virtual.kswapd_steal</p>  <p>paging_and_virtual.nr_dirty</p>  <p>paging_and_virtual.nr_mapped</p>  <p>paging_and_virtual.nr_page_table_pages</p>  <p>paging_and_virtual.nr_slab</p>  <p>paging_and_virtual.nr_unstable</p>  <p>paging_and_virtual.nr_writeback</p>  <p>paging_and_virtual.pageoutrun</p>  <p>paging_and_virtual.pgactivate</p>  <p>paging_and_virtual.pgalloc_dma</p>  <p>paging_and_virtual.pgalloc_high</p>  <p>paging_and_virtual.pgalloc_normal</p>  <p>paging_and_virtual.pgdeactivate</p>  <p>paging_and_virtual.pgfault</p>  <p>paging_and_virtual.pgfree</p>  <p>paging_and_virtual.pginodesteal</p>  <p>paging_and_virtual.pgmajfault</p>  <p>paging_and_virtual.pgpgin</p>  <p>paging_and_virtual.pgpgout</p>  <p>paging_and_virtual.pgrefill_dma</p>  <p>paging_and_virtual.pgrefill_high</p>  <p>paging_and_virtual.pgrefill_normal</p>  <p>paging_and_virtual.pgrotated</p>  <p>paging_and_virtual.pgscan_direct_dma</p>  <p>paging_and_virtual.pgscan_direct_high</p>  <p>paging_and_virtual.pgscan_direct_normal</p>  <p>paging_and_virtual.pgscan_kswapd_dma</p>  <p>paging_and_virtual.pgscan_kswapd_high</p>  <p>paging_and_virtual.pgscan_kswapd_normal</p>  <p>paging_and_virtual.pgsteal_dma</p>  <p>paging_and_virtual.pgsteal_high</p>  <p>paging_and_virtual.pgsteal_normal</p>  <p>paging_and_virtual.pswpin</p>  <p>paging_and_virtual.pswpout</p>  <p>paging_and_virtual.slabs_scanned</p>  <p>paging.cycles</p>  <p>paging.faults</p>  <p>paging.pgin</p>  <p>paging.pgout</p>  <p>paging.pgsin</p>  <p>paging.pgsout</p>  <p>paging.reclaims</p>  <p>paging.scans</p>  | 


| Disk and i/o Metrics | Network Metrics | 
| --- | --- | 
|  <p>disk_%busy</p>  <p>disk_adapter.kb/s</p>  <p>disk_adapter.tps</p>  <p>disk_block_size</p>  <p>disk_io_average_reads_per_second</p>  <p>disk_io_average_writes_per_second</p>  <p>disk_io_reads_per_second</p>  <p>disk_io_writes_per_second</p>  <p>disk_read_kb/s</p>  <p>disk_read_service_time_msec/xfer</p>  <p>disk_service_time_msec/xfer</p>  <p>disk_transfers_per_second</p>  <p>disk_wait_queue_time_msec/xfer</p>  <p>disk_write_kb/s</p>  <p>disk_write_service_time_msec/xfer</p>  <p>file_i/o.dirblk</p>  <p>file_i/o.iget</p>  <p>file_i/o.namei</p>  <p>file_i/o.readch</p>  <p>file_i/o.ttycanch</p>  <p>file_i/o.ttyoutch</p>  <p>file_i/o.ttyrawch</p>  <p>file_i/o.writech</p>  <p>jfs_filespace_%used</p>  <p>jfs_inode_%used</p>  <p>large_page_use.freepages</p>  <p>large_page_use.highwater</p>  <p>large_page_use.pages</p>  <p>large_page_use.sizemb</p>  <p>large_page_use.usedpages</p>  <p>transfers_from_disk_(reads)_per_second</p>  |  <p>network_errors.collisions</p>  <p>network_errors.ierrs</p>  <p>network_errors.oerrs</p>  <p>network_i/o.read-kb/s</p>  <p>network_i/o.write-kb/s</p>  <p>network_packets.read/s</p>  <p>network_packets.reads/s</p>  <p>network_packets.write/s</p>  <p>network_packets.writes/s</p>  <p>network_size.readsize</p>  <p>network_size.writesize</p>  | 


#### Derived Metrics:


- ATSD computes derived metrics to simplify downstream rule development and visualization tasks.
- The derived metrics are stored similar to original metrics and are also available in rule expressions, forecasts, and widgets.


| Derived Metric | Original Metrics | 
| --- | --- | 
|  <p>pcpu_total.busy</p>  |  <p>pcpu_total.sys + pcpu_total.user + pcpu_total.wait</p>  | 
|  <p>scpu_total.busy</p>  |  <p>scpu_total.sys + scpu_total.user + scpu_total.wait</p>  | 
|  <p>pcpu_total.entitled_capacity_used%</p>  |  <p>pcpu_total.busy / pcpu_total.entitled_capacity * 100</p>  | 
|  <p>pcpu.total</p>  |  <p>pcpu.sys + pcpu.user + pcpu.wait — computed by tag (by processor id) </p>  | 
|  <p>scpu.total</p>  |  <p>scpu.sys + scpu.user + scpu.wait — computed by tag (by processor id)</p>  | 
|  <p>memory_mb.memused%</p>  |  <p>(1 – memory_mb.memfree/memory_mb.memtotal) * 100</p>  | 
|  <p>memory_mb.memused</p>  |  <p>memory_mb.memtotal – memory_mb.memfree</p>  | 
|  <p>memory_mb.swapused%</p>  |  <p>(1 – memory_mb.swapfree/memory_mb.swaptotal) * 100</p>  | 
|  <p>memory_mb.swapused</p>  |  <p>memory_mb.swaptotal – memory_mb.swapfree</p>  | 
|  <p>cpu_total.busy%</p>  |  <p>100 – cpu_total.idle%</p>  | 
|  <p>logical_partition.entitled_used%</p>  |  <p>logical_partition.physicalcpu / logical_partition.entitled * 100 </p>  | 
|  <p>logical_partition.physicalcpu_used%</p>  |  <p>logical_partition.physicalcpu / logical_partition.virtualcpus * 100</p>  | 
|  <p>memory.real_used_%</p>  |  <p>100 – memory.real_free_%</p>  | 
|  <p>memory.virtual_used_%</p>  |  <p>100 – memory.virtual_free_%</p>  | 
|  <p>memory.real_used(mb)</p>  |  <p>memory.real_total(mb) – memory.real_free(mb)</p>  | 
|  <p>memory.virtual_used(mb)</p>  |  <p>memory.virtual_total(mb) – memory.virtual_free(mb)</p>  | 
|  <p>memory_new.used%</p>  |  <p>100 – memory_new.free%</p>  | 
|  <p>disk_read_kb/s</p>  <p>nmon.disk_write_kb/s</p>  <p>nmon.disk_%busy</p>  <p>nmon.disk_transfers_per_second</p>  |  <p>Sum of values for all disks for each snapshot</p>  | 
|  <p>nmon.network_packets.write/s</p>  <p>nmon.network_packets.read/s</p>  |  <p>Sum of values for all interfaces for each snapshot except local</p>  | 


