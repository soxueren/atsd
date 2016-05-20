# IBM Tivoli Monitoring

## Overview

In order to offload detailed data from ITM infrastructure with minimal latency you need to enable ITM Warehouse Proxy Agent to dump incoming analytical data into CSV files on the local file system. The dump directory will be continously monitored by an `inotify` script which will upload new CSV files into ATSD as soon as they are created.

This enabled ATSD to server as a long-term repository of historical data that is typically deleted after a few months in order to minimize the disk space usage in Tivoli Data Warehouse.

Since the from ITM agents will be received by ATSD without any delay, it can be used for real-time analytics and peformance dashboards.

![](images/Warehouse-Proxy-Agent-diagram1.jpg "Warehouse Proxy Agent diagram")

## Installation steps

## Configure ITM Warehouse Proxy Agent

* Configure WPA to store analytical data received from agents into CSV files on the local file system as described [here](http://www-01.ibm.com/support/knowledgecenter/SSATHD_7.7.0/com.ibm.itm.doc_6.3fp2/adminuse/history_analytics_scenarios.htm "WPA")

Set `hd.ini` settings to enable private history streaming:

```ini
KHD_CSV_OUTPUT_ACTIVATE=Y
KHD_CSV_OUTPUT=/tmp/itm/csv
KHD_CSV_OUTPUT_TAGGED_ONLY=Y
KHD_CSV_ISO_DATE_FORMAT=Y
KHD_CSV_MAXSIZE=400
KHD_CSV_EVAL_INTERVAL=60
```

## Configure ITM Agents

- Enable private history collection on the agents:
  - [Linux OS](csv-configs/agents/lz-situation.xml)
  - [VMware](csv-configs/agents/vm-situation.xml)
  - [IBM MQ](csv-configs/agents/mq-situation.xml)

## Upload CSV Parsers into ATSD

- Import CSV parser definitions into ATSD for particular agent codes: UX, PA, LZ, NT, VM, T3, UD, etc.
  - [Linux OS](csv-configs/atsd/klz-csv-configs.xml)
  - [VMware](csv-configs/atsd/kvm-csv-configs.xml)
  - [IBM MQ](csv-configs/atsd/mq-csv-configs.xml)

## Configure `inotify` script to read CSV files and upload them into ATSD

Download [inotify_sender](inotify_sender.sh) script.

```sh
chmod a+x inotify_sender.sh
```

Run the script with the following command:

```sh
./inotify_sender.sh
```

Check script logs in `/tmp/itm/logs` directory.

## Verifying Data in ATSD

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

