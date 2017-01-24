# Logging

The database logs are located in the `/opt/atsd/atsd/logs` directory.

The logs can be also downloaded from the **Admin:Server Logs** page.

Logs are rolled over and archived according to the `/opt/atsd/atsd/conf/logging.properties` settings.

|**Log Name**|**Description**|
|---|:---|
|`atsd.log`|Application log.|
|`command.log`|Received command log.|
|`command_malformed.log`|Malformed commands with invalid syntax etc.| 
|`command_discarded.log`|Discarded commands for disabled entities/metrics.|
|`command_ignored.log`|Commands ignored by parsers, e.g. nmon. |
|`command_rule_engine_expired.log`|Commands with old timestamp, ignored by the rule engine.|
|`command_rule_engine_forward.log`|Commands with future timestamp, ignored by the rule engine.|
|`gc.log`|Garbage collection log.|
|`metrics.txt`|Current database metrics. Refreshed every 15 seconds.|
|`stopstart.log`|Start/stop log.|
|`stdout.log`|Standard out.|
|`err.log`|Standard error.|

* Rule Engine ignores commands that are 1 minute behind or 1 minute ahead of the current server time.<br>When enabled, the ignored commands are logged to `*_expired.log` and `*_forward.log` files, respectively.

![server logs](images/server_logs_atsd.png "server_logs_atsd")

Command logging is configured on the **Admin:Input Settings** page.

![](server-logs-command-files.png)

## Reloading Log Settings

Changes in logging properties can be made without restarting the database. They are automatically refreshed and applied every 60 seconds.

## Modifying `command.log`

The command log contains a record of all commands received by the database and is disabled by default. To turn it on, change the settings on the **Admin>Input Settings** page. Database restart is not required.

By the default, the command log is configured to store a maximum of 10 files of up to 10 megabytes each.  The maximum file count and size can be adjusted to store more commands on instances with a high write throughput.
	
1. Edit `/opt/atsd/atsd/conf/command.log.xml` file.

    ```
    nano /opt/atsd/atsd/conf/command.log.xml
    ```
    
    Increase the maximums accordingly.
    
    ```xml
        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <fileNamePattern>../logs/command.%i.log.zip</fileNamePattern>
            <minIndex>1</minIndex>
            <maxIndex>20</maxIndex>
        </rollingPolicy>

        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <maxFileSize>250MB</maxFileSize>
        </triggeringPolicy>
    ```

2. Edit `/opt/atsd/atsd/conf/logback.xml` file. Uncomment the reference to `command.log.xml`.

    ```
    nano /opt/atsd/atsd/conf/logback.xml
    ```
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration scan="true">
	
	<!-- override default command logging properties in command.log.xml -->
        <include resource="command.log.xml"/>
		
	<!-- remaining settings -->
    </configuration>
    ```

New logging settings will be applied within 60 seconds. No database restart is required.
