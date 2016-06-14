# Spring Boot

Axibase Time Series Database has a storage driver for Spring Boot.

[You can find the ATSD Spring Boot Storage Driver on GitHub.](https://github.com/axibase/spring-boot)

#### Settings

| Name | Required | Default Value | Description | 
| --- | --- | --- | --- | 
|  <p>`metrics.export.url`</p>  |  <p>No</p>  |  <p>`http://localhost:8088/api/v1/command`</p>  |  <p>ATSD API URL</p>  | 
|  <p>`metrics.export.username`</p>  |  <p>Yes</p>  |  <p>–</p>  |  <p>ATSD Username</p>  | 
|  <p>`metrics.export.password`</p>  |  <p>Yes</p>  |  <p>–</p>  |  <p>ATSD Password</p>  | 
|  <p>`metrics.export.bufferSize`</p>  |  <p>No</p>  |  <p>`64`</p>  |  <p>Size of metrics buffer. Metrics writer flushes the buffer if it is full or by schedule (configured by spring.metrics.export.* properties)</p>  | 
|  <p>`metrics.names.entity`</p>  |  <p>No</p>  |  <p>`atsd-default`</p>  |  <p>Entity name</p>  | 
|  <p>`metrics.names.metricPrefix`</p>  |  <p>No</p>  |  <p>–</p>  |  <p>A prefix to be added to original metric name</p>  | 
|  <p>`metrics.names.tags.*`</p>  |  <p>No</p>  |  <p>–</p>  |  <p>Optional set of key-value pairs in ATSD time series identifier</p>  | 


#### Configuration

Configuration settings are specified in the application.properties file.

`application.properties` file example:

```
metrics.export.username: admin
metrics.export.password: secret
metrics.export.url: http://localhost:8088/api/v1/command
metrics.export.bufferSize: 16
metrics.names.entity: spring-boot-sample
metrics.names.metricPrefix: spring-boot
metrics.names.tags.ip: 127.0.0.1
metrics.names.tags.organization: Axibase
```

#### Metrics

In order for the application to know about `metrics.export.` and `metrics.names`. they need to be specified in the configuration: [AtsdNamingStrategy and AtsdMetricWriter](https://github.com/axibase/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-metrics-atsd/src/main/java/sample/metrics/atsd/SampleAtsdExportApplication.java)

Enable public metrics export:

```java
@Bean
	public MetricsEndpointMetricReader metricsEndpointMetricReader(MetricsEndpoint metricsEndpoint) {
		return new MetricsEndpointMetricReader(metricsEndpoint);
	}
 
        @Bean
	@ExportMetricWriter
	@ConfigurationProperties("metrics.export")
	public MetricWriter atsdMetricWriter() {
		AtsdMetricWriter writer = new AtsdMetricWriter();
		writer.setNamingStrategy(namingStrategy());
		return writer;
	}
 
	@Bean
	@ConfigurationProperties("metrics.names")
	public AtsdNamingStrategy namingStrategy() {
		return new DefaultAtsdNamingStrategy();
	}
```

##### Wrapping methods using custom metrics.

Wrap all class methods using custom metrics:

```java
@Measured
public class JdbcCityRepository implements CityRepository
```

Wrap a single methods using custom metrics:

```java

@Measured
 public List<City> findCities()
```

