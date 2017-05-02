# Hello World – Getting Started with ATSD

The purpose of this article is to guide the user through the first steps of starting Axibase Time Series Database.

##### Step 1:

[Download and Install Axibase Time Series Database.](../installation/README.md)

##### Step 2:

Login into the ATSD web interface at `https://atsd_hostname:8443/`

##### Step 3:

Open the Data Entry page located under the Metrics tab in the top menu:

![](resources/series.png)

Complete the Entity, Metric, and Value fields. Press Send:

```properties
metric = my-metric
entity = my-entity
value = 24
```

To simplify this example, we will ignore tags. [Tags can be used to enrich the time series.](https://axibase.com/products/axibase-time-series-database/data-model/)

##### Step 4:

Execute multiple inserts with different timestamps (i.e. 5 minutes, 15 minutes, 1 hour) by modifying the Time field.

Make sure to enter different times for each insert.

Time format is set to current time by default in the [ISO format](https://en.wikipedia.org/wiki/ISO_8601).

You can simply change the minutes and hours:

2016-02-15T12:35:00Z

2016-02-15T12:20:00Z

2016-02-15T11:30:00Z

![](http://axibase.com/wp-content/uploads/2015/06/series_time.png)

##### Step 5:

Open a time chart using the following link: [http://atsd_server:8088/portals/series?entity=my-entity&metric=my-metric](http://atsd_server:8088/portals/series?entity=my-entity&metric=my-metric)

In the upper right corner switch to [detail] data type to view the values you previously inserted.

Click [all] in the timespan control to view all data instead of the last 24 hours.

![](http://axibase.com/wp-content/uploads/2015/06/hello_world_time_chart4.png)

##### Step 6:

Spend a moment learning the basic [time chart controls.](http://axibase.com/products/axibase-time-series-database/visualization/widgets/time-chart/)

[Continue to Next Page](getting-started-2.md)
