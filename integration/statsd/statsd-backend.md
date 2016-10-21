# StatsD Backend

ATSD backend for StatsD enables you to forward metrics collected by StatsD daemon into the Axibase Time Series Database for retention, analytics, visualization, and alerting.

[Learn more about StatsD.](README.md)

[Download the ATSD StatsD backend.](https://github.com/axibase/atsd-statsd-backend)

#### Configuration

Configuration file example:

```json
{
    atsd : {
        host: "atsd_server",
        port: 8081,
        protocol: "tcp",
        patterns: [
            {
                pattern: /^([^.]+\.){2}com\..+/,
                atsd_pattern: "<entity>.<>.<>.<metrics>"
            },
            {
                pattern: /.*/,
                atsd_pattern: "<entity>.<metrics>"
            }
        ]
    },
    port: 8125,
    backends: [ "./node_modules/atsd-statsd-backend/lib/atsd" ],
    debug: true
}
```
Possible variables:

| Variable | Description | Default Value | 
| --- | --- | --- | 
|  <p>debug</p>  |  <p>Enable debug logging : `true` or `false`</p>  |  <p>`false`</p>  | 
|  <p>keyNameSanitize</p>  |  <p>Sanitizing metric names (removing forbidden characters): `true` or `false`</p>  |  <p>`true`</p>  | 
|  <p>flush_counts</p>  |  <p>Processing flush counts: `true` or `false`</p>  |  <p>true</p>  | 
|  <p>atsd</p>  |  <p>Container for all backend-specific options</p>  |  <p>–</p>  | 
|  <p>atsd.host</p>  |  <p>ATSD hostname</p>  |  <p>–</p>  | 
|  <p>atsd.port</p>  |  <p>ATSD port</p>  |  <p>8081</p>  | 
|  <p>atsd.user</p>  |  <p>Username</p>  |  <p>“”</p>  | 
|  <p>atsd.password</p>  |  <p>Password to log into ATSD</p>  |  <p>“”</p>  | 
|  <p>atsd.protocol</p>  |  <p>Protocol: `"tcp"` or `"udp"`</p>  |  <p>“tcp”</p>  | 
|  <p>atsd.entity</p>  |  <p>Default entity</p>  |  <p>local hostname</p>  | 
|  <p>atsd.prefix</p>  |  <p>Global prefix for each metric</p>  |  <p>“”</p>  | 
|  <p>atsd.prefixCounter</p>  |  <p>Prefix for counter metrics</p>  |  <p>“counters”</p>  | 
|  <p>atsd.prefixTimer</p>  |  <p>Prefix for timer metrics</p>  |  <p>“timers”</p>  | 
|  <p>atsd.prefixGauge</p>  |  <p>Prefix for gauge metrics</p>  |  <p>“gauges”</p>  | 
|  <p>atsd.prefixSet</p>  |  <p>Prefix for set metrics</p>  |  <p>“sets”</p>  | 
|  <p>atsd.patterns</p>  |  <p>Patterns to parse statsd metric names</p>  |  <p>–</p>  | 


[Other variables used by StatsD can be specified.](http://github.com/etsy/statsd/blob/master/exampleConfig.js')

StatsD has an [open bug](https://github.com/etsy/statsd/issues/462) regarding the inability of the configuration to sometimes reload during operation. Changing the configuration file while StatsD is running may result in StatsD crashing. Until the bug is fixed, add `automaticConfigReload: false` to your configuration and restart StatsD for the changed configuration to take effect.

#### Patterns

Patterns enable the conversion of native StatsD metric names into ATSD entity/metric/tags.

If a metric name matches a regexp `pattern`, it will be parsed according to `atsd_pattern`.

> NOTE: every `\` in `pattern` must be duplicated.

If a metric name has more tokens than `atsd_pattern`, extra tokens are cropped.

`alfa.bravo.charlie.delta` is used as an example metric and the default example entity is `zulu`.

`metric` – metric token; multiple occurrences are combined.


`entity` – entity token to replace the default entity; multiple occurrences are combined.

```
[garbageCollections]
pattern = garbageCollections$
atsd-pattern = <tag:type>.<tag:dep>.<entity>.<metric>.<metric>.<metric>

# atsd-pattern = <tag:type>.<tag:dep>.<entity>.<metrics>     -Alternative Syntax
```

`tag:tag_name` – token for the tag named `tag_name`.

```
atsd-pattern = <entity>.<tag:test>.<metric>.<metric>
result = series e:alfa m:charlie.delta t:test=bravo ...
```

`metrics` – any number of metric tokens; can be used once per pattern; can also be omitted.

```sh
atsd-pattern = <entity>.<tag:test>.<metrics>
result = series e:alfa m:charlie.delta t:test=bravo ...
```

```
<> - token to be excluded
atsd-pattern = <entity>.<tag:test>.<>.<metric>
result = series e:alfa m:delta t:test=bravo ...
```
