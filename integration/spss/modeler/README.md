# IBM SPSS Modeler


## Overview

IBM SPSS Modeler is a predictive analytics platform that helps you build accurate predictive 
models quickly and deliver predictive intelligence to individuals, groups, systems and the enterprise.
The following guide includes examples of loading time series data from the Axibase Time Series Database (ATSD),
calculating derived time series in IBM SPSS Modeler and storing the results back in ATSD.

## Sample Dataset

For the purpose of instruction, we will use [sample series commands](resources/commands.txt).
The series contain the Consumer Price Index (CPI) for each category
of items in a consumer's basket as well as a weight for each category in the CPI
basket. The weights are stored as fractions of 1000. The CPI is tracked from 2013 to
2017 and uses Year 2016 values as the baseline. Weight values are available only for
year 2017.

To load the data into ATSD, login into the database web interface and submit
these commands on the **Metrics > Data Entry** page.

![](images/metrics_entry.png)

Also you can download ready [stream file](resources/Stream.str) for review in your own IBM SPSS Modeler installation.

## Prerequisites

- Install [IBM SPSS Modeler](http://www-01.ibm.com/support/docview.wss?uid=swg24039399), version 18
- Install [ODBC-JDBC gateway](../../odbc/README.md).

## Create Database data source

- In the bottom panel of IBM SPSS Modeler select **Sources** tab and choose **Database**

  ![](images/modeler_1.png)

- Add the **Database** source to your stream

  ![](images/modeler_2.png)

- Right click on this source and select **Edit...**

  ![](images/modeler_3.png)

- Expand **Data source** and choose **Add new database connection**

  ![](images/modeler_4.png)
  
- Choose ATSD ODBC data source. If there are no data sources - create it as described [here](../odbc/README.md#configure-odbc-data-source) and click **Refresh**

  ![](images/modeler_5.png)
  
- Enter username and password and click **Connect**

  ![](images/modeler_6.png)
  
- New connection should appear in **Connections** table

  ![](images/modeler_7.png)
  
- Click **OK**. After wizard close choose your new data source

  ![](images/modeler_8.png)
  
- Click **Select...**

  ![](images/modeler_9.png)
  
- Select `inflation.cpi.categories.price` metric and click **OK**

  ![](images/modeler_10.png)
  
If you don't find your metric, update ODBC data source as described [here](no-tables-fix.md), delete your connection in SPSS Modeler and 
create it again.

- Check **Never** in **Quote table and column names** 

  ![](images/modeler_11.png)
  
- Go to **Filter** tab and disable `time`, `text` and `metric` fields

  ![](images/modeler_12.png)
  
- Database source setup is finished. To check database source click **Preview**

  ![](images/modeler_13.png)
  
First 10 rows should be shown in preview table

  ![](images/modeler_14.png)
  
- Close preview table and click **OK** in database source settings window to save changes

- Repeat these steps to create another data source for metric `inflation.cpi.categories.weight` 
  
  ![](images/modeler_15.png)
  
## Join two tables

- In the bottom panel of IBM SPSS Modeler select **Record Ops** tab, choose **Merge** node and
add it to stream

  ![](images/modeler_16.png)
  
- Right click on any database source node and select **Connect...**

  ![](images/modeler_17.png)

- Select **Merge** node. A link should appear between two nodes

  ![](images/modeler_18.png)
  
- Connect other database source with **Merge** node

  ![](images/modeler_19.png)
  
- Right click on the **Merge** node and select **Edit...**. Set **Merge method** to **Keys**
and add `tags` field to **Keys for merge** field

  ![](images/modeler_20.png)
  
- Go to **Filter** tab and disable (click on arrow in Filter column) both entity fields and
datetime field for `inflation.cpi.categories.weight` table

  ![](images/modeler_21.png)
  
- Rename `value` field in `inflation.cpi.categories.price` table to `price` and `value` field
in `inflation.cpi.categories.weight` to `weight`

  ![](images/modeler_22.png)
  
- Click **Preview** button to check result

  ![](images/modeler_23.png)
  
- Save changes and close the editor

## Calculate weighted price for every row

- In the bottom panel of IBM SPSS Modeler select **Field Ops** tab, choose **Derive** node and
add it to stream

  ![](images/modeler_24.png)
  
- Connect **Merge** and **Derive** nodes

  ![](images/modeler_25.png)
  
- Right click on the **Derive** node and select **Edit...**
  - Set **Derive field** to **weighted_price**
  - Set field type to **Continuous**
  - Add formula **price * weight / 1000**
  
  ![](images/modeler_26.png)
  
- Click **Preview** button to check result. **weighted_price** column should be added.

  ![](images/modeler_27.png)
  
- Save changes and close the editor

## Calculate weighted inflation index for each year

- In the bottom panel of IBM SPSS Modeler select **Record Ops** tab, choose **Aggregate** node and
add it to stream

  ![](images/modeler_28.png)
  
- Connect **Derive** (weighted_price) and **Aggregate** nodes

  ![](images/modeler_29.png)
  
- Right click on the **Aggregate** node and select **Edit...**
  - Add `datetime` in **Key fields**
  - Disable **Include record count in field** checkbox. 

  ![](images/modeler_30.png)
  
- In **Aggregate expressions** table enter field name `value` and click **Launch expression builder**

  ![](images/modeler_31.png)
  
- In Expression Builder window enter formula `SUM('weighted_price')` and click **OK**

  ![](images/modeler_32.png)
  
- Click **Preview** button to check result

  ![](images/modeler_33.png)

- Save changes and close the editor

## Add constant entity field

- In the bottom panel of IBM SPSS Modeler select **Field Ops** tab, choose **Derive** node and
add it to stream

  ![](images/modeler_34.png)
  
- Connect **Aggregate** and **Derive** nodes

  ![](images/modeler_35.png)
  
- Right click on the **Derive** node and select **Edit...**
  - Set **Derive field** to **entity**
  - Set field type to **Categorical**
  - Add formula **"bls.gov"**

  ![](images/modeler_36.png)
  
- Click **Preview** button to check result. **entity** column should be added.

  ![](images/modeler_37.png)
  
- Save changes and close the editor

## Create export metric in ATSD

- Log in ATSD web interface, go to **Metrics > Data Entry** page and execute command

```
metric m:inflation.cpi.composite.price
```

![](images/metric_creation.png)

## Export result to ATSD

- In the bottom panel of IBM SPSS Modeler select **Export** tab, choose **Database** node and
add it to stream

  ![](images/modeler_38.png)
  
- Connect **Derive** (entity) and **Database** nodes

  ![](images/modeler_39.png)
  
- Right click on the **Database** node and select **Edit...**
  - Choose **Data source**
  - Select **Insert into table** option
  - Click **Select...** and choose `inflation.cpi.composite.price` table
  - Set **Quote table and column names** to **Never**

  ![](images/modeler_40.png)
  
- Click **Advanced...** button

  ![](images/modeler_41.png)
  
- In Advanced Options window set **Use bulk loading** to **Via ODBC** and **Use binding** to
**Row-wise**. Click **OK** to save and exit.

  ![](images/modeler_42.png)
  
- Click **Run** to export data to ATSD

  ![](images/modeler_43.png)

- To check that data is successfully exported to ATSD go to ATSD web interface, click **SQL** and execute
following query

```sql
SELECT entity, datetime, value 
FROM 'inflation.cpi.composite.price'
```

  ![](images/atsd_query_result.png)