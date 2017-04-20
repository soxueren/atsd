# Meta API: Metrics Methods

| **Name** | **Method** | **Path** | **Content-Type** | **Description** |
|:---|:---|:---|:---|:---|
| [Get](get.md) | GET | `/api/v1/metrics/{metric}` |  | Retrieve properties and tags for the specified metric. |
| [List](list.md) | GET | `/api/v1/metrics` |  | Retrieve a list of metrics matching the specified filter conditions. |
| [Update](update.md) | PATCH | `/api/v1/metrics/{metric}` | `application/json` |  Update fields and tags of the specified metric. |
| [Create or Replace](create-or-replace.md) | PUT | `/api/v1/metrics/{metric}` | `application/json` |  Create a metric with specified fields and tags or replace the fields and tags of an existing metric. |
| [Delete](delete.md) | DELETE | `/api/v1/metrics/{metric}` | `application/json` |  Delete the specified metric. |
| [Series](series.md) | GET | `/api/v1/metrics/{metric}/series` |  | Returns a list of series for the metric. |
