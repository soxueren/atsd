# Placeholders

Placeholders are specified using ${name} format. Placeholder can reference a predefined parameter or a function value.

## Placeholder List

### All

* metric
* entity
* status
* tags
* rule
* rule_expression
* rule_filter
* expression
* window
* repeat_count
* repeat_interval
* alert_duration
* alert_duration_interval
* alert_open_time
* alert_repeat_count
* last_alert_time
* alert_open_time
* alert_type
* action_repeat_count
* last_action_time
* received_time
* schedule
* event_time
* min_interval_expired
* window_first_time
* threshold
* tags
* tags.tag_name
* entity_tags
* entity.tags
* entity_tags.tag_name
* entity.tags.tag_name

### Series

* open_value
* value

### Message

* message
* severity

### Properties

* properties
* properties.key_name
* properties.tag_name
* type

## Custom Columns

Columns defined on Overview tab can be referenced as placeholder by name.

```sh
property_compare_except(['name', '*starttime']) as propDiff

${propDiff}
```

## Functions

### [Aggregate Functions](expression.md#functions)

### Lookup Functions

```sh
${property_values('linux.disk:fstype=ext4:mount_point')}
```

```java
property_values('nurswgvml007', 'docker.container::image').contains('atsd/latest')
```

```java
property_compare_except(['name', '*time'])
```

## Examples

```sh
[${status}] ActiveMQ on ${entity}: Unauthorized connection from ${properties.remoteaddress}.
```

```sh
[${status}] JVM on ${entity}: Average system CPU usage ${round(avg()*100,1)} exceeds threshold.
```

```sh
${tags.file-system}
```

```sh
${entity.tags.location}
```

```sh
${tags}
```











