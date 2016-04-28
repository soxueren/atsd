# Client for Java


> Check out our [GitHub](https://github.com/axibase/atsd-api-java) for
more Axibase developments.

The ATSD Client for Java enables Java developers to easily read and
write statistics and metadata from the [Axibase Time-Series
Database](http://axibase.com/products/axibase-time-series-database/).
With minimal effort, you can build reporting, analytics, and alerting
solutions. Get started by importing this client with Maven or
downloading the zip file from
[GitHub](https://github.com/axibase/atsd-api-java/releases/download/0.3.3/atsd-api-java-0.3.3-bin.zip).

```xml
    <dependency>
            <groupId>com.axibase</groupId>
            <artifactId>atsd-api-java</artifactId>
            <version>0.3.3</version>
    </dependency>
```

### Implemented Methods

The ATSD Client for Java is an easy-to-use client for interfacing with
ATSD metadata and data REST API services. It has the ability to read
time-series values, statistics, properties, alerts, and messages.

-   Data API
    -   Series
        -   QUERY
        -   INSERT
        -   CSV INSERT
    -   Properties
        -   QUERY
        -   INSERT
    -   Alerts
        -   QUERY
    -   Alerts History
        -   QUERY

-   Metadata API
    -   Metrics
        -   Get Metrics
        -   Get Metric
        -   Create/Update Metric
        -   Delete Metric
        -   Get Entities and Series Tags for Metric
    -   Entities
        -   Get Entities
        -   Get Entity
        -   Create/Update Entity
        -   Delete Entity
        -   Get Metrics for Entity
    -   Entity Groups
        -   Get Entity Groups
        -   Get Entity Group
        -   Create/Update Entity Group
        -   Add Entities to Entity Group
        -   Set (Replace) Entities in Entity Group
        -   Delete Entities for Entity Group

### Getting Started

Before you begin installing ATSD Client for Java, you need to install a
copy of the [Axibase Time-Series
Database](http://axibase.com/products/axibase-time-series-database/).
Download the latest version of ATSD that is available for your Linux
distribution.

Minimum requirements for running the ATSD Client: Java 1.7+

We recommend installing the ATSD Client for Java by using Maven. Build
the ATSD Client with Maven after checking out the code from GitHub.

```sh
 git clone https://github.com/axibase/atsd-api-java.git                   
 cd atsd-api-java                                                         
 mvn clean dependency:copy-dependencies compile jar:jar                   
 java -cp "atsd-api-java-1.0-SNAPSHOT.jar:dependency/*" com.axibase.tsd.e 
 xample.AtsdClientExample                                                 
```

## Examples

See:

[AtsdClientReadExample](https://github.com/axibase/atsd-api-java/blob/master/src/main/java/com/axibase/tsd/example/AtsdClientReadExample.java)

[AtsdClientWriteExample](https://github.com/axibase/atsd-api-java/blob/master/src/main/java/com/axibase/tsd/example/AtsdClientWriteExample.java)

## Client Configuration

```java
 AtsdClientExample atsdClientExample = new AtsdClientExample();           
 atsdClientExample.configure();
 ```

## Metadata Processing

```java
 Metric metric = metaDataService.retrieveMetric(metricExample);           
 if (metric == null) {                                                    
       System.out.println("Unknown metric: " + metricExample);            
       return;                                                            
 }                                                                        
 List entityAndTagsList = metaDataService.retrieveEntityAndTags(metric.ge 
 tName(), null);                                                          
 System.out.println("===Metric MetaData===");                             
 System.out.println("Metric: " + metric.getName());                       
```

## Data Queries

```java
 GetSeriesCommand command = new GetSeriesCommand(entityName, metric.getNa 
 me(), tags);                                                             
 List getSeriesResults = dataService.retrieveSeries(new Interval(1, Inter 
 valUnit.MINUTE), 10, command);                                           
 for (GetSeriesResult getSeriesResult : getSeriesResults) {               
        System.out.println("Time Series Key: " + getSeriesResult.getTimeS 
 eriesKey());                                                             
        List data = getSeriesResult.getData();                            
        for (Series series : data) {                                      
            long ts = series.getT();                                      
            System.out.println(toISODate(ts) + "\t" + series.getV());     
       }                                                                  
 }                                                                        
```