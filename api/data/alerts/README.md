# Data API: Alerts Methods

| **Name** | **Method** | **Path** | **Content-Type** | **Description** |
|:---|:---|:---|:---|:---|
| [Query](query.md) | POST | `/api/v1/alerts/query` | `application/json` | Retrieve a list of open alerts matching specified filters. |
| [History Query](history-query.md) | POST | `/api/v1/alerts/history/query` | `application/json` | Retrieve a list of closed alerts matching specified fields. |
| [Update](update.md) | POST | `/api/v1/alerts/update` | `application/json` | Change acknowledgement status of the specified open alerts. |
| [Delete](delete.md) | POST | `/api/v1/alerts/delete` | `application/json` | Delete specified alerts by id from the memory store. |
