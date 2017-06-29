# Pentaho Report Designer

- [Install ATSD Driver](#install-atsd-driver)
- [Configure Database Connection](#configure-database-connection)
- [Import data from ATSD](#import-data-from-atsd)

## Install ATSD Driver

- Download ATSD [JDBC driver](https://github.com/axibase/atsd-jdbc/releases) with dependencies
- Copy the driver JAR file into `lib/jdbc` directory in the Report Designer installation directory
- Restart the Report Designer

## Configure Database Connection

- Select 'Data' tab in the properties pane on the right.

![](resources/data_pane.png)

- Select 'Add Data Sources' button in the toolbar.
- Select 'Add a new connection' (green plus icon).

![](resources/new_connection.png)

- Select `General` in the left menu
- Select `Generic database` as Connection Type
- Select `Native (JDBC)` as Access

### Configure ATSD connection properties

- Enter JDBC URL into the `Custom Connection URL` field, for example:

  `jdbc:axibase:atsd:https://ATSD_HOSTNAME:8443/api/sql;catalog=atsd;tables=inflation*;expandTags=true;trustServerCertificate=true`

> `ATSD_HOSTNAME` is the hostname of the target ATSD instance
> Review ATSD JDBC [URL parameters](https://github.com/axibase/atsd-jdbc/blob/master/README.md) for additional details.

- Set Custom Driver Class Name field to `com.axibase.tsd.driver.jdbc.AtsdDriver`
- Set `User Name` and `Password` fields to your ATSD Username and Password
- Set `Connection Name` to `ATSD Connection`

![](resources/atsd_connection.png)

## Import Data

- Select 'Data' tab in the properties pane on the right.
- Select 'Add Data Sources' button in the toolbar.
- Click on 'ATSD Connection'
- Click on 'Add Query' in the Available Queries list

![](resources/add_query.png)

- Enter a SQL query in the Query editor, for example `SELECT datetime, entitym value FROM jvm_memory_used LIMIT 10`

![](resources/query_text.png)

- Click on the 'Preview' button to review the resultset.

![](resources/preview.png)

- Click 'OK'. The list of queries will be added to the 'Data' pane.
- Right-click on the query and choose 'Select Query'. The tree view will now display query results and fields.

![](resources/data_pane_updated.png)

- Drag and drop these field into the report canvas.
- Click `Preview` button in the top left corner (eye icon) to view query results.

![](resources/report.png)

Example of report preview:

![](resources/report_preview.png)

[File with example report](resources/report.prpt)
