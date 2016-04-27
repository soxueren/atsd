## Metric: Update

```
PATCH /api/v1/metrics/{metric}
```
 > Request

```
{
    "tags": {
        "table": "CPU Detail"
    }
}
```

Update specified properties and tags for the given metric.
This method updates specified properties and tags for an existing metric. 

### Request Fields

 +See: [Metric: Create or Replace](#metric:-create-or-replace)

<aside class="notice">
Properties and tags that are not specified are left unchanged.
</aside>
