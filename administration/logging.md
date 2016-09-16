# Logging

The database logs are located in `/opt/atsd/atsd/logs` directory.

The logs can be also downloaded from **Admin:Server Logs** page.

Logs are rolled over and archived according to `/opt/atsd/atsd/conf/logging.properties` settings.

|**Log Name**|**Description**|
|---|:---|
|atsd.log|Application log|
|command.log|Received command log|
|command_discarded.log|Discarded commands for disabled metrics.|
|command_malformed.log|Malformed commands with invalid syntax etc.| 
|command_ignored.log|Commands ignored by parsers, e.g. nmon. |
|command_rule_engine_expired.log|Commands with old timestamp, ignored by rule engine|
|command_rule_engine_forward.log|Commands with forward timestamp, ignored by rule engine|
|gc.log|Garbage collection log|
|metrics.txt|Current database metrics|
|stopstart.log|Start/stop log for ATSD, HBase, HDFS|
|stdout.log|Standard out|
|err.log|Standard error|

* Rule Engine ignores commands that are 1 minute behind or 1 minute ahead of the current server time.<br>When enabled, the ignored commands are logged to `*_expired.log` and `*_forward.log` files respectively.

![server logs](images/server_logs_atsd.png "server_logs_atsd")

Command processings logs should be enabled on **Admin:Input Settings** page:

![](server-logs-command-files.png)


## Modifying command log

1. Create configuration file command.log.xml:

    ```
    vim /opt/atsd/atsd/conf/command.log.xml
    ```
    
    ```xml
    <included>
        <appender name="commandsLogRoller" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>../logs/command.log</file>
    
            <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
                <fileNamePattern>../logs/command.%i.log.zip</fileNamePattern>
                <minIndex>1</minIndex>
                <maxIndex>${command.logger.max.file.count}</maxIndex>
            </rollingPolicy>
    
            <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
                <maxFileSize>${command.logger.max.file.size}</maxFileSize>
            </triggeringPolicy>
    
            <encoder>
                <pattern>%date{ISO8601};%logger;%message%n</pattern>
            </encoder>
        </appender>
    
        <logger name="atsd" level="DEBUG" additivity="false"><appender-ref ref="commandsLogRoller"/></logger>
        <logger name="atsd.internal.command" level="DEBUG" additivity="false"><appender-ref ref="commandsLogRoller"/></logger>
    </included>
    ```

2. Add required settings to logging properties:

    ```
    vim /opt/atsd/atsd/conf/logging.properties
    ```
    
    ```ls
    command.logger.max.file.count=100
    command.logger.max.file.size=100MB
    ```

3. Include created resource file `command.log.xml` into logback.xml:

    ```
    vim /opt/atsd/atsd/conf/logback.xml
    ```
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration scan="true">
        <property resource="logging.properties"/>
        <include resource="command.log.xml"/>
        <appender name="logRoller" class="ch.qos.logback.core.rolling.RollingFileAppender">
        ...
    </configuration>
    ```