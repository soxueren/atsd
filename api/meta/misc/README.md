# Meta API: Misc Methods

| **Name** | **Method** | **Path** | **Content-Type** | **Description** |
|:---|:---|:---|:---|:---|
| [Version](version.md) | GET | `/api/v1/version` | `application/json` | Returns database version including licensing details as well as a date object with local time and offset. |
| [Ping](ping.md) | GET | `/ping` | `application/json` | Returns 200 status code. Typically used to check connectivity, authentication and to maintain an active session. |
