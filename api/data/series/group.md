# Group Processor

Group processing merges multiple series into one serie before rate and aggregator are applied.

| **Parameter** | **Type** | **Description**  |
|:---|:---|:---|
| type          | yes          | Statistical function applied to value array `[v-n, w-n]`. Possible values: `COUNT`, `MIN`, `MAX`, `AVG`, `SUM`, `PERCENTILE_999`, `PERCENTILE_995`, `PERCENTILE_99`, `PERCENTILE_95`, `PERCENTILE_90`, `PERCENTILE_75`, `PERCENTILE_50` or `MEDIAN`, `STANDARD_DEVIATION`, `MIN_VALUE_TIME`, `MAX_VALUE_TIME` |
| interpolate   | no           | Interpolation function used to compute missing values for a given input series at t-n. Possible values: `NONE`, `STEP`, `LINEAR`. Default value: STEP |
| truncate      | no           | Discards samples at the beginning and at the of the grouped series until values for all input series are established. Possible values: true, false. Default value: false  |
| period      | no           | Replaces input series timestamps with regular timestamps based on count=unit frequency. Possible values: count, unit  |
| order         | no           | Change the order in which `aggregate` and `group` is executed, the higher the value of `order` the later in the sequency will it be executed.   |

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
