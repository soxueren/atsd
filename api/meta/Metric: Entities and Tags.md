## Metric: Entities and Tags

Returns a list of unique series tags for the metric. The list is based on data stored on disk for the last 24 hours.

### Request Parameters

```
GET /api/v1/metrics/{metric}/entity-and-tags
```

> Request

```
http://atsd_server.com:8088/api/v1/metrics/disk_used/entity-and-tags
```

| **Parameter** | **Required** | **Description**                 |
|---------------|--------------|---------------------------------|
| entity        | no       | Filter entities by entity name. |

### Response Fields

> Response

```json
[
{"entity":"nurswgvml007","tags":{"file_system":"/dev/mapper/vg_nurswgvml007-lv_root","mount_point":"/"},"lastInsertTime":1422873625000},
{"entity":"nurswgvml007","tags":{"disk_name":"/dev/mapper/vg_nurswgvml007-lv_root","mount_point":"/","operating-system":"Linux"},"lastInsertTime":1422873625000},
{"entity":"nurswgvml007","tags":{"disk_name":"sysfs","mount_point":"/sys","operating-system":"Linux"},"lastInsertTime":1422873625000},
{"entity":"nurswgvml006","tags":{"file_system":"/dev/mapper/vg_nurswgvml006-lv_root","mount_point":"/"},"lastInsertTime":1422873626000},
{"entity":"nurswgvml006","tags":{"file_system":"/dev/sdb1","mount_point":"/media/datadrive"}
}
]
```

| **Name**       | **Description**                                                                                        |
|----------------|--------------------------------------------------------------------------------------------------------|
| entity         | Entity name                                                                                            |
| lastInsertTime | Maximium last time for metric, entity and one of the tag names . Time specified in epoch milliseconds. |
| tags           | map of tag names and values                                                                            |

