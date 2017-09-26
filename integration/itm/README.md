# IBM Tivoli Monitoring

## Overview

There are two options to integrate ATSD with IBM Tivoli Monitoring (ITM):

* Run a scheduled JDBC job with Axibase Collector to copy incremental data from detailed tables in Tivoli Data Warehouse to ATSD.
* Configure ITM Warehouse Proxy Agent (WPA) to store analytical data in CSV files.

This document describes the second option, which provides minimal latency at the expense of introducing additional overhead on the WPA server.

In order to offload detailed data from ITM-managed systems with minimal latency you need to enable an ITM Warehouse Proxy Agent (WPA) to write incoming analytical data from ITM agents into CSV files on the local file system. The CSV directory is continously monitored by an `inotify` script, which uploads new CSV files into ATSD the moment they are created.

This integration enables ATSD to act as a long-term repository for historical data such as attribute groups with aggressive pruning settings like process tables, which are typically configured to only retain old data for an interval of 3 to 7 days.

Because statistics from ITM agents are received by ATSD with no delay, this type of integration can be used for real-time analytics and peformance dashboards with no display latency.

![](images/itm_diag.png "Warehouse Proxy Agent diagram")

## Installation steps

### Configure ITM Warehouse Proxy Agent

* Configure WPA to store analytical data received from agents into CSV files on the local file system as described [here](http://www-01.ibm.com/support/knowledgecenter/SSATHD_7.7.0/com.ibm.itm.doc_6.3fp2/adminuse/history_analytics_scenarios.htm "WPA").

* Set the `hd.ini` settings to activate private history streaming:

    * Change to the `/opt/ITM directory` and append the following settings to the `config/hd.ini` file:

        ```ini
        KHD_CSV_OUTPUT_ACTIVATE=Y
        KHD_CSV_OUTPUT=/tmp/itm/csv
        KHD_CSV_OUTPUT_TAGGED_ONLY=Y
        KHD_CSV_ISO_DATE_FORMAT=Y
        KHD_CSV_MAXSIZE=400
        KHD_CSV_EVAL_INTERVAL=60
        ```
    * Restart WareHouse Proxy agent:

        ```sh
        bin/itmcmd stop hd
        bin/itmcmd start hd
        ```

### Configure ITM Agents

* Download situation configuration files for the following products:

    - [Linux OS](csv-configs/agents/lz_situations.xml)
    - [VMware](csv-configs/agents/vm_situations.xml)
    - [WebSphere MQ](csv-configs/agents/mq_situations.xml)

* Copy the configuration file to the `localconfig/${PRODUCT_CODE}/` directory on the agent machine, where `${PRODUCT_CODE}` is the agent product code. You can look up commonly used product codes [here](http://www-01.ibm.com/support/docview.wss?uid=swg21265222).

    > Agent situation files adhere to the following naming convention: ${PRODUCT_CODE}_situations.xml

* Restart the agent:
    
    ```sh
    bin/itmcmd stop ${PRODUCT_CODE}
    bin/itmcmd start ${PRODUCT_CODE}
    ```

### Download CSV Parsers for UX, VM, and MQ Product Codes

   - [Linux OS](csv-configs/atsd/klz-csv-configs.xml)
   - [VMware](csv-configs/atsd/kvm-csv-configs.xml)
   - [WebSphere MQ](csv-configs/atsd/mq-csv-configs.xml)

### Upload CSV Parsers into ATSD

- Log in to the ATSD web interface.
- Open the **Configuration > Parsers: CSV** page.
- Click the [Сreate] drop-down, select [Import].
- Upload the CSV parser xml files that you downloaded previously.

### Configure `inotify` Script to Read CSV files and Upload Them into ATSD

* Download [inotify_sender](inotify_sender.sh) script to your WPA server.

* Specify the ATSD hostname by editing the following line:

    ```sh
    if [ "$url" = "" ]; then
        url="http://atsd_host:8088"
    fi
    ```
	
	
* Set username and password in the `inotify_sender.sh` script

```bash
--user=[[USER]] --password=[[PASSWORD]]
```

* Set permissions to execute the script:
    ```sh
    chmod a+x inotify_sender.sh
    ```

* Run the script with the following command:
    ```sh
    ./inotify_sender.sh
    ```
* Review script logs in the `/tmp/itm/logs` directory.

* Add script to auto-start. The auto-start configuration is dependent on your operating system.

## Verifying Data in ATSD

* Log in to ATSD.
* Click to the Metrics tab and filter metrics with the following prefixes:

 - `klz`
  ![](images/klz_metrics.png)

 - `lnx`
  ![](images/lnx_metrics.png)

 - `kvm`
  ![](images/kvm_metrics.png)


 - `mq`
  ![](images/mq_metrics.png)


## Viewing Data in ATSD

### Metrics

* List of collected [ITM metrics](metric-list.md)

### Entity Groups

- `ITM - Linux OS`

### Portals
- [ITM – Linux OS Portal](http://apps.axibase.com/chartlab/43f054ee)

![](images/itm_linux_portal.png "itm_linux_portal")
