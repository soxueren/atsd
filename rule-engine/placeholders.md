# Placeholders

Placeholders can be used to embed field and function values into email text or alert log.
The placeholders are referenced using the `${name}` syntax.

![](images/placeholders.png "placeholders")

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
* timestamp
* min_interval_expired
* window_first_time
* threshold
* tags
* tags.tag_name
* entity.label
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
* tags

### Properties

* properties
* properties.key_name
* properties.tag_name
* type
* tags

### Custom Variables

Variables defined on the Overview tab can be referenced by name similar to built-in fields.

```sh
${idle}
```

![](images/variables.png "variables")

## Examples

```sh
[${status}] ActiveMQ on ${entity}:
Unauthorized connection from ${tags.remoteaddress}.
```

```sh
[${status}] JVM on ${entity}:
Average CPU usage ${round(avg()*100,1)} exceeds threshold.
```

```sh
${tags.file_system}
```

```sh
${entity.tags.location}
```

```sh
${tags}
```
