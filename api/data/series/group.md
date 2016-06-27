# Group Processor

Group processing merges multiple series into one series before rate and aggregator are applied.

| **Parameter** | **Type** | **Description**  |
|:---|:---|:---|
| type          | string          | Statistical function applied to value array `[v-n, w-n]`. Possible values: `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| interpolate   | string           | Interpolation function used to compute missing values for a given input series at t-n. Possible values: `NONE`, `STEP`, `LINEAR`. Default value: STEP |
| truncate      | boolean           | Discards samples at the beginning and at the of the grouped series until values for all input series are established. Possible values: true, false. Default value: false  |
| period      | object           | [Period](period.md). Applies aggregation to merged series.  |
| order         | number           | Change the order in which `aggregate` and `group` are executed, the higher the value of `order` the later in the sequence the task will be executed.   |

```json
{   
     "group": {
        "type": "AVG",
        "interpolate": "STEP",
        "truncate": false,
        "period": {"count": 5, "unit": "MINUTE"},
        "order": 1
    }
}
