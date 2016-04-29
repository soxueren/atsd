## Dropped Commands

Reasons why the server can drop commands:

* If multiple commands are sent without including the end of line in the last command.
* If timestamp is earlier than `1970-01-01T00:00:00Z`.
* If ATSD cannot parse the timestamp, for example: if `s` or `ms` value is not numeric or if `d` value is not in ISO format.
* If multiple data points for the same entity, metric and tags have the same timestamp (commands are considered duplicate and dropped).
* If commands are sent without a timestamp, in this case ATSD will assign current server time as the timestamp - this case lead to the same entity, metric and tags having identical timestamps - resulting in duplicate commands being dropped.
* If data is sent using UDP protocol the receive buffer can sometimes become oversaturated.
* If metric 'Min/Max Value' setting is set together with the `DISCARD` 'Invalid Value Action' setting in ATSD, then values that fall outside the 'Min/Max Value' are discarded.
* If entity, metric or tag names are not valid.
* If value cannot be parsed into double - decimal point must be a period (`.`), scientific notation is supported.

