# Alteryx Designer

- [Create connection](#create-connection)
- [Build SQL query to database](#create-connection)

## Create database connection

- Add **Input Data** tool to your workflow ![](images/input_data.png)
- Choose **Other Databases→ODBC...** in Input Data configuration, you will see
  **ODBC Connection** dialog.
  ![](images/choose_odbc.png)

  ![](images/no_dsn.png)
- In case you haven't configured ODBC connection with ATSD you follow
- instructions in [ODBC-JDBC Bridge](../odbc/README.md), and for completing
  [Configure ODBC Data Source](../odbc/README.md#configure-odbc-data-source)
  step of this instuction you can open **ODBC Data Source Administrator** window
  by pressing **ODBC Admin** button in **ODBC Connection** dialog. Apparently
  you'll want to have `tables` connection property in DSN URL set to some
  value. It allows you choose table from metrics list that satisfy some pattern.
  Read about it in [JDBC driver](https://github.com/axibase/atsd-jdbc#jdbc-connection-properties-supported-by-driver) documentation.
  When you're done, choose the name you specified for DSN during bridge
  configuration from **Data Source Name** list and press **OK**.

> **Note:**
> In this example `tables=*`

## Build SQL query to database

After creating connection you'll see **Choose Table or Specify Query** dialog.

![](images/choose_table.png)

It allows you to build query by choosing table or specifying it's text
manually.

- All the metric names you see at **Tables** tab satisfy `tables` pattern in
  your DSN URL. To be sure, that this list is in it's actual state, click
  **Refresh** button.
  ![](images/metrics_list.png)

- At **Visual Query Tab** you can select columns that you need, add initial
  sorting, grouping, etc.
  ![](images/visual_builder.png)

- In **SQL Editor** you can review and edit the query that was built or write
  your own query. To check that the query is valid you can press **Test Query**
  ![](images/sql_editor.png)

Press **OK** when your query to ATSD is ready.

### Check query result

Optionally, to see result of the query press **Run Workflow**.

![](images/run_workflow.png)

![](images/results.png)
