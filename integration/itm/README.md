# IBM Tivoli Monitoring

## Overview
ATSD extends IBM Tivoli Monitoring with streaming analytics and
long-term detailed data retention capabilities.

## Installation steps

### Enabling ITM to stream data into the ATSD is done through the Warehouse Proxy Agent

- Import CSV parser definitions into ATSD for particular agent codes: UX, PA, LZ, NT, VM, T3, UD, etc.
- Configure [Warehouse Proxy Agent](http://www-01.ibm.com/support/knowledgecenter/SSATHD_7.7.0/com.ibm.itm.doc_6.3fp2/adminuse/history_analytics_scenarios.htm "WPA") to store analytical data into CSV files on the local file system.
- Enable private history collection on the agent.
- Read and upload CSV files into ATSD continuously using scripts.
- To minimize latency, watch for new CSV files using inotify or similar utility.


#### `hd.ini` Settings to enable private history streaming in ITM.
```ini
KHD_CSV_OUTPUT_ACTIVATE=Y
KHD_CSV_OUTPUT=/tmp/itm/csv
KHD_CSV_OUTPUT_TAGGED_ONLY=Y
KHD_CSV_ISO_DATE_FORMAT=Y
KHD_CSV_MAXSIZE=400
KHD_CSV_EVAL_INTERVAL=60
```

### Enabling private history on agent

- `Linux OS agent`
```xml
<PRIVATECONFIGURATION>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_CPU"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_CPU_Averages"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Disk"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Disk_IO"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Disk_Usage_Trends"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_IO_Ext"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Network"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_NFS_Statistics"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_LPAR"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Process"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Process_User_Info"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_RPC_Statistics"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_System_Statistics"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Sockets_Detail"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Sockets_Status"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_Swap_Rate"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_TCP_Statistics"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_User_Login"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="KLZ_VM_Stats"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_All_Users"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_CPU_Config"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_Group"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_IP_Address"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_Machine_Information"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_OS_Config"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_File_Comparison"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_File_Information"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_File_Pattern"></HISTORY>
    <HISTORY Interval="1" Export="1" RETAIN="1" USE="A" Table="Linux_Host_Availability"></HISTORY>
</PRIVATECONFIGURATION>
```
- `VMware agent`
```xml
<PRIVATECONFIGURATION>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_AGENT_EVENTS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_CLUSTERED_DATASTORES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_CLUSTERED_RESOURCE_POOLS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_CLUSTERED_SERVERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_CLUSTERED_VIRTUAL_MACHINES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_CLUSTERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DATACENTERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DATASTORES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DATASTORE_HOST_DISKS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DATASTORE_TOPOLOGY"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DIRECTOR"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DISTRIBUTED_VIRTUAL_PORTGROUPS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_DISTRIBUTED_VIRTUAL_SWITCHES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_ESX_PERFORMANCE_OBJECT_STATUS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_EVENTS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_MONITORED_SERVERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_NETWORKED_SERVERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_NETWORKED_VIRTUAL_MACHINES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_NETWORKED_VIRTUAL_SWITCHES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_NETWORKS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_PERFORMANCE_OBJECT_STATUS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_RESOURCE_POOL_CPU"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_RESOURCE_POOL_GENERAL"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_RESOURCE_POOL_MEMORY"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_CPU"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_DATASTORE"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_DISK"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_HBA"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_HEALTH"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_MEMORY"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_NETWORK"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_SAN"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_VIRTUAL_SWITCHES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SERVER_VM_DATASTORE_UTILIZATION"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_SUBNODE_EVENTS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_THREAD_POOL_STATUS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_TOPOLOGICAL_EVENTS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_TOPOLOGY"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_TRIGGERED_ALARMS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VCENTERS"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VIRTUAL_MACHINES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VIRTUAL_SWITCHES"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_CPU"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_DATASTORE_UTILIZATION"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_DISK"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_MEMORY"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_NETWORK"/>
    <HISTORY Interval="10" Export="10" RETAIN="1" USE="A" Table="KVM_VM_PARTITION"/>
</PRIVATECONFIGURATION>

```


#### Sender Script

Sender script that checks the specified directory for new CSV files and
uploads them into ATSD. 

You can check the script’s logs in `/tmp/itm/logs` directory.

You can download script file from [here](inotify_sender.sh)


![](images/Warehouse-Proxy-Agent-diagram1.jpg "Warehouse Proxy Agent diagram")

## Verifying  in ATSD

* Login into ATSD
* Click on Metrics tab and filter metrics by following prefixes:

 - `klz`
  ![](images/klz_metrics.png)

 - `lnx`
  ![](images/lnx_metrics.png)



## Viewing Data in ATSD

### Metrics
* List of collected [ITM metrics](metric-list.md)

### Entity Groups

- `ITM - Linux OS`

### Portals
- [ITM – Linux OS Portal](http://apps.axibase.com/chartlab/43f054ee)

![](images/itm_linux_portal.png "itm_linux_portal")



## CSV Parser definitions
- Linux OS [configs](klz-csv-configs.xml)
- VMware [configs](kvm-csv-configs.xml)