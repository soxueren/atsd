# Placeholders

Placeholders are specified using ${name} format. Placeholder can reference a pre-defined parameter or a function value. 

### Function Values

```sh
${tags.file-system}
```

```sh
${entity.tags.location}
```

```sh
${tags}
```

```sh
${property_values('linux.disk:fstype=ext4:mount_point')}
```

```java
property_values('nurswgvml007', 'docker.container::image').contains('atsd/latest')
```

```java
property_compare_except(['name', '*time'])
```

### Pre-defined Parameters

#### All

* metric
* entity
* status
* tags
* rule
* rule_expression
* window
* repeat_count
* alert_duration
* alert_duration_interval
* alert_open_time
* alert_repeat_count
* last_alert_time
* alert_open_time
* action_repeat_count
* last_action_time

#### Series

* value

#### Message

* message

#### Properties

* properties
* type

### Custom Parameters

Columns defined on Overview tab can be referenced as placeholder by name.

```sh
property_compare_except(['name', '*starttime'], ['*Xloggc*']) as propDiff
```









