# IBM SPSS Modeler


## Overview

IBM SPSS Modeler provides a set of tools to build data transformations and analysis models for users without programming experience. The following guide includes examples of loading time series data from the Axibase Time Series Database (ATSD),
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

## Prerequisites

- Install [IBM SPSS Modeler](http://www-01.ibm.com/support/docview.wss?uid=swg24039399), version 18
- Install [ODBC-JDBC bridge](../../odbc/README.md).

## Create Data Source

- Create a new stream. Stream is a configuration that includes all the steps to load and analyze the data in SPSS Modeler. 

- Select **Sources** tab in the bottom panel and choose **Database** 

  ![](images/modeler_1.png)

- Add the **Database** source to the stream by drag-and-dropping into the stream workspace.

  ![](images/modeler_2.png)

- Right click on the source and click **Edit...**

  ![](images/modeler_3.png)

- Expand **Data source** and choose **Add new database connection**

  ![](images/modeler_4.png)
  
- Choose ATSD ODBC data source. If there are no data sources - create an ODBC-bridged connection to ATSD as described [here](../../odbc/README.md#configure-odbc-data-source) and click **Refresh**

  ![](images/modeler_5.png)
  
- Enter username and password and click **Connect**

  ![](images/modeler_6.png)
  
- New connection should appear in the **Connections** table

  ![](images/modeler_7.png)
  
- Click **OK**. Select the newly created ATSD data source.

  ![](images/modeler_8.png)
  
- Click **Select...** to view the list of available ATSD tables. The list of tables is based on the `tables=` property specified in the JDBC URL. If you don't see the desired table in the list, update ODBC data source as described [here](../../odbc/table-config.md), delete your connection in SPSS Modeler and create it again. Specify `tables=*` to view all tables in ATSD.

  ![](images/modeler_9.png)
  
- Disable **Show table owner** checkbox, select `inflation.cpi.categories.price` table and click **OK**

  ![](images/modeler_10.png)
  
- Check **Never** in **Quote table and column names** 

  ![](images/modeler_11.png)
  
- Go to **Filter** tab and click on arrow in the Filter column to disable `time`, `text` and `metric` columns.

  ![](images/modeler_12.png)
  
- Database source setup is finished. Click **Preview** to verify the results by reviewing the first 10 rows in the the table.

  ![](images/modeler_13.png)

  ![](images/modeler_14.png)
  
- Close the preview table and click **OK** in database source settings window to save changes.

- Repeat these steps to create another data source for table `inflation.cpi.categories.weight` 
  
  ![](images/modeler_15.png)
  
## Join Tables

- Select **Record Ops** tab in the bottom panel, choose **Merge** node and add it to the stream

  ![](images/modeler_16.png)
  
- Right click on one of the database source shapes and select **Connect...**

  ![](images/modeler_17.png)

- Select **Merge** shape. A link should appear between the source and the **Merge** shapes.

  ![](images/modeler_18.png)
  
- Connect the other source with the **Merge** shape using a similar technique.

  ![](images/modeler_19.png)
  
- Right click on the **Merge** shape and select **Edit...**. Set **Merge method** to **Keys**
and add `tags` field to **Keys for merge** field

  ![](images/modeler_20.png)
  
- Open the **Filter** tab and disable both entity fields and the datetime field for the `inflation.cpi.categories.weight` table.

  ![](images/modeler_21.png)
  
- Rename `value` field in `inflation.cpi.categories.price` table to `price` and `value` field
in `inflation.cpi.categories.weight` to `weight`

  ![](images/modeler_22.png)
  
- Click **Preview** button to check results

  ![](images/modeler_23.png)
  
- Close the **Preview** window and click **Ok** to save changes.

## Calculate Weighted Price

- Select **Field Ops** tab in the bottom panel, choose **Derive** shape and
add it to the stream

  ![](images/modeler_24.png)
  
- Connect **Merge** and **Derive** shapes

  ![](images/modeler_25.png)
  
- Right click on the **Derive** shape and select **Edit...**
  - Set **Derive field** to **weighted_price**
  - Set field type to **Continuous**
  - Add formula **price * weight / 1000**
  
  ![](images/modeler_26.png)
  
- Click **Preview** button to check results. You should be able to see the **weighted_price** column.

  ![](images/modeler_27.png)
  
- Close the **Preview** window and click **Ok** to save changes.

## Calculate Weighted Inflation per Year

- Select **Record Ops** tab, choose **Aggregate** shape and add it to the stream

  ![](images/modeler_28.png)
  
- Connect **Derive** (weighted_price) and **Aggregate** shapes

  ![](images/modeler_29.png)
  
- Right click on the **Aggregate** shape and select **Edit...**
  - Add `datetime` in **Key fields**
  - Disable **Include record count in field** checkbox. 

  ![](images/modeler_30.png)
  
- In **Aggregate expressions** table, enter field name `value` and click **Launch expression builder**

  ![](images/modeler_31.png)
  
- In Expression Builder window, enter formula `SUM('weighted_price')` and click **OK**

  ![](images/modeler_32.png)
  
- Click **Preview** button to check results

  ![](images/modeler_33.png)

- Close the preview table and click **OK** to save changes.

## Add Entity Field

- Select **Field Ops** tab, choose **Derive** shape and
add it to the stream

  ![](images/modeler_34.png)
  
- Connect **Aggregate** and **Derive** shapes

  ![](images/modeler_35.png)
  
- Right click on the **Derive** shape and select **Edit...**
  - Set **Derive field** to **entity**
  - Set field type to **Categorical**
  - Add formula **"bls.gov"**

  ![](images/modeler_36.png)
  
- Click **Preview** button to check results. **entity** column should be added.

  ![](images/modeler_37.png)
  
- Close the preview table and click **OK** to save changes.

## Export Results

- Select **Export** tab, choose **Database** shape and
add it to the stream

  ![](images/modeler_38.png)
  
- Connect **Derive** (entity) and **Database** shapes

  ![](images/modeler_39.png)
  
- Right click on the **Database** shape and select **Edit...**
  - Choose **Data source**
  - Type `inflation.cpi.composite.price` in **Table name**. This would be the name of the new metric inserted into ATSD.
  - Select **Insert into table** option
  - Set **Quote table and column names** to **Never**

  ![](images/modeler_40.png)
  
- Click **Advanced...** button

  ![](images/modeler_41.png)
  
- In Advanced Options window set **Use bulk loading** to **Via ODBC** and **Use binding** to
**Row-wise**. Click **OK** to save and exit.

  ![](images/modeler_42.png)
  
- Click **Run** to export data into ATSD

  ![](images/modeler_43.png)

## Verify Insertion

To check that data is successfully exported to ATSD go to ATSD web interface, click **SQL** and execute
following query

```sql
SELECT entity, datetime, value 
  FROM 'inflation.cpi.composite.price'
```

  ![](images/atsd_query_result.png)
  
## Stream File

Download the [stream file](resources/Stream.str) used for this guide for review in your own IBM SPSS Modeler installation.
