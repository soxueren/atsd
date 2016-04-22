# Monitoting Metrics : Rule Engine


Rule Engine can monitor ATSD metrics to raise alerts in case of abnormal
deviations.

For example:

Monitoring the `metric_received_per_second` metric can be done based on
its forecast. In this example a forecast is generated for the
 `metric_received_per_second` metric using the built-in Forecasting
tool, then based on the forecast a rule is created with the following
expression:

```sh
 abs(forecast_deviation(wavg())) > 2                                      
```

This rule will raise an alert if the absolute forecast deviates from the
15 minute weighted average by more than 2 standard deviations.

Email notifications can be setup, so that alerts are delivered to the
chosen email when the rule is triggered.

![](images/rule_engine_atsd_jmx1.png "rule_engine_atsd_jmx")

If the rule is triggered and email alert it sent, below are example
contents of an email alert message:

![](images/alert_message_email.png "alert_message_email")