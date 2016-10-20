# Java Metrics

ATSD has a storage driver for Metrics, which captures JVM and application-level metrics.

[Learn more about Metrics on GitHub.](https://github.com/dropwizard/metrics)

The `metrics-atsd` module implements `AtsdReporter`, which allows Java applications to stream metrics into the Axibase Time Series Database .

[Learn more about ATSD’s Storage Driver for Metrics on GitHub.](https://github.com/axibase/metrics-atsd)

The Metrics library provides 5 types of metrics:


- [Gauge](https://dropwizard.github.io/metrics/3.1.0/getting-started/#gauges) – current value.
- [Counter](https://dropwizard.github.io/metrics/3.1.0/getting-started/#counters) – incrementing and decrementing integer.
- [Meter](https://dropwizard.github.io/metrics/3.1.0/getting-started/#meters) – rate of events over time.
- [Histogram](https://dropwizard.github.io/metrics/3.1.0/getting-started/#histograms) – statistical distribution of values.
- [Timer](https://dropwizard.github.io/metrics/3.1.0/getting-started/#timers) – rate at which the method is invoked and the distribution of its duration.


### Configurations and Settings

#### Configuring the Sender:

TCP

```
final AtsdTCPSender sender = new AtsdTCPSender(new InetSocketAddress("atsd.example.com", 8081));
```

UDP

```
final AtsdUDPSender sender = new AtsdUDPSender("atsd.example.com", 8082);
```

#### Configuring the Builder:

| Name | Required | Default | Description | 
| --- | --- | --- | --- | 
|  <p>`public Builder setEntity(String entity)`</p>  |  <p>No</p>  |  <p>`hostname` or `"defaultEntity"`</p>  |  <p>Application name or hostname.</p>  | 
|  <p>`public Builder withClock(Clock clock)`</p>  |  <p>No</p>  |  <p>`Clock.defaultClock()`</p>  |  <p>Clock instance.</p>  | 
|  <p>`public Builder setMetricPrefix(String prefix)`</p>  |  <p>No</p>  |  <p>`null`</p>  |  <p>Prefix metric names with the specified string.</p>  | 
|  <p>`public Builder convertRatesTo(TimeUnit rateUnit)`</p>  |  <p>No</p>  |  <p>`TimeUnit.SECONDS`</p>  |  <p>Convert rates to the specified period.</p>  | 
|  <p>`public Builder convertDurationsTo(TimeUnit durationUnit)`</p>  |  <p>No</p>  |  <p>`TimeUnit.MILLISECONDS`</p>  |  <p>Convert durations to the specified period.</p>  | 
|  <p>`public Builder filter(MetricFilter filter)`</p>  |  <p>No</p>  |  <p>`MetricFilter.ALL`</p>  |  <p>Only report metrics matching the specified filter.</p>  | 
|  <p>`public AtsdReporter build(AtsdSender sender)`</p>  |  <p>Yes</p>  |  <p>–</p>  |  <p>Sending metrics using the specified AtsdSender.</p>  | 


#### Add Metric

Add metric to monitor:

```java
public class UserLogin {
    static final MetricRegistry registry = new MetricRegistry();
    static Meter meter = registry.meter(new MetricName("login.meter"));;
    ...
}
```

Add metric to monitor with tags:

```java
public class UserLogin {
    static final MetricRegistry registry = new MetricRegistry();
    static Meter meter = null;
    static {
        HashMap<String, String> tags = new HashMap();
        tags.put("provider", "ldap");
        meter = registry.meter(new MetricName("login.meter", tags));
    }
    ...
}
```

#### Create Reporter

```java
static void atsdTCPReport() {
    final AtsdTCPSender sender = new AtsdTCPSender(new InetSocketAddress("atsd.example.com", 8081));
    //final AtsdUDPSender sender = new AtsdUDPSender("atsd.example.com", 8082);
    final AtsdReporter reporter = AtsdReporter.forRegistry(metrics)
            .setEntity("portal-app")
            .prefixedWith("portal")
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .filter(MetricFilter.ALL)
            .build(sender);
    reporter.start(1, TimeUnit.SECONDS);
}
```

#### Collect Metric Values

```java
static void login() {
    meter.mark();
    System.out.println("method `login` was called!");
}
```

#### Start Reporter

```java
static void startReporter() {
    atsdTCPReport();
    //atsdUDPReport();
}
```
