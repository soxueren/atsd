## Metric: Create or Replace

```
PUT /api/v1/metrics/{metric}
```

Create a metric with specified properties and tags or replace an existing metric.
This method creates a new metric or replaces an existing metric. 

### Request Parameters

See: [Get Metrics](#metrics:-list)

<aside class="notice">
If only a subset of fields is provided for an existing metric, the remaining properties and tags will be deleted.
</aside>
