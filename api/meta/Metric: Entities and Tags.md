## Metric: Entities and Tags

Returns a list of unique series tags for the metric. The list is based on data stored on disk for the last 24 hours.


### Method
```
GET /api/v1/metrics/{metric}/entity-and-tags
```
### Basic Example
> Request

```
http://atsd_server:8088/api/v1/metrics/disk_used/entity-and-tags?timeFormat=iso
```
> Response

```json
[
{
       "entity": "nurswgvml007",
        "tags": {
            "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
            "mount_point": "/"
        },
        "lastInsertDate": "2015-09-04T15:48:58.000Z"
    },
    {
        "entity": "nurswgvml007",
        "tags": {
            "file_system": "10.102.0.2:/home/store/share",
            "mount_point": "/mnt/share"
        },
        "lastInsertDate": "2015-09-04T15:48:58.000Z"
    },
    {
        "entity": "nurswgvml006",
        "tags": {
            "file_system": "/dev/mapper/vg_nurswgvml006-lv_root",
            "mount_point": "/"
        },
        "lastInsertDate": "2015-09-04T15:48:47.000Z"
    },
    {
        "entity": "nurswgvml006",
        "tags": {
            "file_system": "/dev/sdc1",
            "mount_point": "/media/datadrive"
        },
        "lastInsertDate": "2015-09-04T15:48:47.000Z"
    }
]
```
### Request Parameters
| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| entity        | no       | Filter entities by entity name. |
|minInsertDate|no|return entities and tags with lastInsertTime equal or greater than specified time, accepts iso date format|
|maxInsertDate|no|return entities with lastInsertTime less than specified time, accepts iso date format|
|timeFormat|no|response time format. Possible values: `iso`, `milliseconds`. Default value: `milliseconds`|

### Response Fields


| **Field**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| entity         | Entity name                                                                                            |
| lastInsertTime | Maximium last time for metric, entity and one of the tag names . Time specified in epoch milliseconds. |
|lastInsertDate|Last time value was received by ATSD for this metric. Time specified in ISO format.|
| tags           | map of tag names and values                                                                            |

<aside class="notice">
If `timeFormat=iso` is set in the request, then `lastInsertDate` will be returned. If `timeFormat` is set to the default value (milliseconds), then `lastInsertTime` will be returned.
</aside>



