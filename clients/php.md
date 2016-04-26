# Client for PHP

    Check out our [GitHub](https://github.com/axibase/atsd-api-php) for
    more Axibase developments.

The ATSD Client for PHP enables PHP developers to easily read and write
statistics and metadata from the [Axibase Times-Series
Database](http://axibase.com/products/axibase-time-series-database/).
With minimal effort, you can build reporting, analytic, and alerting
solutions.
Use[Composer](https://packagist.org/packages/axibase/atsd-api-php) to
get started with this PHP API.

## Implemented Methods

The ATSD Client for PHP is an easy-to-use client for interfacing with
ATSD metadata and data REST API services. It has the ability to read
time-series values, statistics, properties, alerts, and messages.

-   Data API
    -   Series
        -   QUERY
    -   Properties
        -   QUERY
    -   Alerts
        -   QUERY
    -   Alerts History
        -   QUERY
-   Metadata API
    -   Metrics
        -   Get Metrics
        -   Get Metric
        -   Get Entities and Series Tags for Metric
    -   Entities
        -   Get Entities
        -   Get Metrics for Entity
    -   Entity Groups
        -   Get Entity Groups
        -   Entities for Entity Group

## Getting Started

Before you begin installing ATSD Client for PHP, you need to install a
copy of the [Axibase Time-Series
Database](http://axibase.com/products/axibase-time-series-database/).
Download the latest version of ATSD that is available for your Linux
distribution.

Minimum requirements for running the ATSD Client: PHP 5.3.2+

Installing the ATSD Client:

```php
 git clone https://github.com/axibase/atsd-api-php.git                    
 mv atsd-api-php /{your_documentroot_folder}/                             
 cd /{you_documentroot_folder}/atsd-api-php/examples                      
 firefox *.php                                                            
```

### **Composer**

Once in composer.json, specify the following:

```json
 {                                                                        
 "require": {                                                             
     "axbase/atsd-api-php": "dev-master"                                  
     }                                                                    
 }                                                                        
```

### Examples:

See:

[AtsdClientAlertsExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/AlertsExample.html)

[AtsdClientAlertsHistoryExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/AlertsHistoryExample.html)

[AtsdClientEntitiesExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/EntitiesExample.html)

[AtsdClientEntityAndTagsExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/EntityAndTagsExample.html)

[AtsdClientEntityGroupsExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/EntityGroupsExample.html)

[AtsdClientMetricsExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/MetricsExample.html)

[AtsdClientPropertiesExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/PropertiesExample.html)

[AtsdClientSeriesExample](http://htmlpreview.github.io/?https://github.com/axibase/atsd-api-php/blob/master/examples/SeriesExample.html)

### Client Configuration

```php
 $iniArray = parse_ini_file("atsd.ini");                                  
```

### Metadata Processing

```php
 $iniArray = parse_ini_file("atsd.ini");                                  
 $client = new HttpClient();                                              
 $client->connect($iniArray["url"], $iniArray["username"], $iniArray["pas 
 sword"]);                                                                
                                                                          
 $expression = 'name like \'nurs*\'';                                     
 $tags = 'app, os';                                                       
 $limit = 10;                                                             
                                                                          
 $entities = new Entities($client);                                       
                                                                          
 $params = array("limit" => $limit, 'expression' => $expression, 'tags' = 
 > $tags );                                                               
 $entitiesResponse = $entities->findAll($params);                         
                                                                          
 $viewConfig = new ViewConfiguration('Entities for expression: ' . $expre 
 ssion . "; tags: " . $tags . "; limit: " . $limit, 'entities', array('la 
 stInsertTime' => 'unixtimestamp'));                                      
 $entitiesTable = Utils::arrayAsHtmlTable($entitiesResponse, $viewConfig) 
 ;                                                                        
                                                                          
 Utils::render(array($entitiesTable));                                    
                                                                          
 $client->close();                                                        
```

## Data Queries

```php
 $iniArray = parse_ini_file("atsd.ini");                                  
 $client = new HttpClient();                                              
 $client->connect($iniArray["url"], $iniArray["username"], $iniArray["pas 
 sword"]);                                                                
                                                                          
 $endTime = time() * 1000;                                                
 $startTime = $endTime - 2*60 * 60 * 1000;                                
 $series = (new Series($client, $startTime, $endTime))                    
     ->addDetailSeries('s-detail', 'awsswgvml001', 'disk_used', array('mo 
 unt_point' => ['/']))                                                    
     ->addAggregateSeries('s-avg', 'awsswgvml001', 'disk_used', array('mo 
 unt_point' => ['/']), AggregateType::MIN, 1, TimeUnit::HOUR)             
     ->addAggregateSeries('s-min', 'awsswgvml001', 'disk_used', array('mo 
 unt_point' => ['/']), AggregateType::MAX, 1, TimeUnit::HOUR)             
     ->addAggregateSeries('s-max', 'awsswgvml001', 'disk_used', array('mo 
 unt_point' => ['/']), AggregateType::AVG, 1, TimeUnit::HOUR)             
     ->addSeries('s-multiple', array(                                     
         'entity' => 'awsswgvml001',                                      
         'metric' => 'disk_used',                                         
         'tags' => array(                                                 
             'mount_point' => ['*']                                       
         ),                                                               
         'type' => AggregateType::PERCENTILE_99,                          
         'intervalCount' => 1,                                            
         'intervalUnit' => TimeUnit::HOUR,                                
         'multipleSeries' => true                                         
     ));                                                                  
 $series->execQuery();                                                    
                                                                          
 $tables = array();                                                       
 $tables[] = Utils::seriesAsHtml($series->getSeries('s-avg'));            
 $tables[] = Utils::seriesAsHtml($series->getSeries('s-min'));            
 $tables[] = Utils::seriesAsHtml($series->getSeries('s-max'));            
 $tables[] = Utils::seriesAsHtml($series->getSeries('s-multiple'));       
 $tables[] = Utils::seriesAsHtml($series->getSeries('s-detail'));         
                                                                          
 Utils::render($tables);                                                  
 $client->close();                                                        
```

## Troubleshooting

If you get an error like the following, ensure that the variable
date.timezone in your php.ini is set.

```php
 Fatal error: Uncaught exception 'Exception' with message 'DateTime::__co 
 nstruct():                                                               
 It is not safe to rely on the system's timezone settings. You are requir 
 ed to use the date.timezone setting or the date_default_timezone_set() f 
 unction.                                                                 
 In case you used any of those methods and you are still getting this war 
 ning, you most likely misspelled the timezone identifier.                
 We selected the timezone 'UTC' for now, but please set date.timezone to  
 select your timezone.'
```