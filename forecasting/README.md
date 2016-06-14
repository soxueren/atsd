# Data Forecasting

[Forecasting Example](#forecasting-example-with-abnormal-deviation)

[Forecast Settings](#forecast-settings)

[Key Advanced Settings](#key-advanced-settings)


- Axibase Time Series Database includes built-in forecasting algorithms that can predict abnormalities based on historical data.
- The accuracy of predictions and the percentage of false positives/negatives depends on the frequency of data collection, the retention interval, and algorithms.
- Built-in auto-regressive time series extrapolation algorithms (Holt-Winters, ARIMA, etc.) in ATSD can predict failures at early stages.
- Dynamic predictions eliminate the need to set manual thresholds.


#### Forecasting Example with Abnormal Deviation:

![](resources/forecasts.png)

![](resources/forecasts2.png)

#### Forecast Settings:


- Forecasting settings can be left in automatic mode for easy setup.
- ATSD selects the most accurate forecasting algorithm for each time-series separately based on a ranking system.
- The winning algorithm is used to compute forecasts for the next day, week, or month.
- Pre-computed forecasts can be used in the rule engine.
- Basic automatic adhoc forecasting can be used directly in graphs and widgets, and forecasts will be calculated for 1 week with automatic settings.


![](resources/forecasts3.png)

#### Key Advanced Settings:

| Setting | Description | 
| --- | --- | 
|  <p>Enabled</p>  |  <p>The forecast is enabled, new forecasts will be calculated automatically every 24 hours</p>  | 
|  <p>Metric</p>  |  <p>The metric to be forecast, for example, cpu_busy</p>  | 
|  <p>Entity</p>  |  <p>The entity that will be used. Exclusive with Entity Group.</p>  | 
|  <p>Entity Group</p>  |  <p>The entity group selected from a drop down. Forecast will be calculated for all entities contained within the Entity Group. Exclusive with Entity Group.</p>  | 
|  <p>Selection Interval</p>  |  <p>Amount of historical data analyzed when calculating the forecast.</p>  | 
|  <p>Full Scan</p>  |  <p>Full scan of historical data, used when there is no fresh data for the past 24 hours from the current entity and metric.</p>  <p>If Full Scan is not set, then ATSD will automatically look for metric keys from the past 24 hours.</p>  <p>Set to true when forecasting historical data that is no longer collected or when generating a forecast using “End Time”.</p>  | 
|  <p>Averaging Interval</p>  |  <p>Interval over which the data is normalized. When selecting the ARIMA algorithm, the averaging interval cannot be set to less than 1 hour.</p>  | 
|  <p>Retention Interval</p>  |  <p>How long for the forecast is stored. Forecasts older than the retention interval will be deleted.</p>  | 
|  <p>Store Interval</p>  |  <p>Forecast time-span. How far into the future the data is forecast.</p>  | 
|  <p>Auto Parameters</p>  |  <p>ATSD uses automatic settings to select the best forecast.</p>  | 
|  <p>Auto Averaging</p>  |  <p>Automatic averaging interval determined by ATSD.</p>  | 
|  <p>Algorithm</p>  |  <p>Holt-Winters or Arima algorithms.</p>  | 
|  <p>End Time</p>  |  <p>Used to calculate the forecast from an exact point in time. Useful when calculating a forecast for data that is not frequently updated. Possible values described on the [End Time](https://axibase.com/products/axibase-time-series-database/visualization/end-time/) page.</p>  <p>Important to select “Full Scan” when forecasting historical data that is no longer collected, if “Full Scan” is not set, then ATSD will automatically look for metric keys from the past 24 hours.</p>  | 
|  <p>Name</p>  |  <p>Unique forecast identifier.</p>  | 


![](resources/forecast_settings2.png)

![](resources/forecasts4-e1434358022671.png)

