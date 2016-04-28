### Tags as an Array Example

> Request

```json
{
    "queries": [
        {
            "startDate": "2015-02-22T13:37:00Z",
            "endDate": "2015-02-23T13:37:00Z",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "metric": "df.disk_used_percent",
            "tags": {
                "mount_point": [
                    "/",
                    "/mnt/share"
                ]
            }
        }
    ]
}
```

> Response

```json
{
    "series": [
        {
            "entity": "nurswgvml007",
            "metric": "df.disk_used_percent",
            "tags": {
                "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
                "mount_point": "/"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "data": [
                {
                    "d": "2015-02-22T13:37:14.000Z",
                    "v": 56.0642
                },
                {
                    "d": "2015-02-22T13:37:29.000Z",
                    "v": 56.0667
                },
                {
                    "d": "2015-02-22T13:37:44.000Z",
                    "v": 56.0703
                },
                {
                    "d": "2015-02-22T13:37:59.000Z",
                    "v": 56.079
                }
            ]
        }
    ]
}
```

