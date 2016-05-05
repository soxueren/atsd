### Last Value with Cache Example

> Request

```json
{
    "queries": [
        {
            "startDate": "now - 5 * minute",
            "endDate": "now",
            "timeFormat": "iso",
            "entity": "nurswgvml007",
            "last" : true,
            "cache": true,
            "metric": "df.disk_used"
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
            "metric": "df.disk_used",
            "tags": {
                "file_system": "/dev/mapper/vg_nurswgvml007-lv_root",
                "mount_point": "/"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "last": true,
            "cache": true,
            "data": [
                {
                    "d": "2015-09-22T10:56:38.000Z",
                    "v": 8450888
                }
            ]
        },
        {
            "entity": "nurswgvml007",
            "metric": "df.disk_used",
            "tags": {
                "file_system": "10.102.0.2:/home/store/share",
                "mount_point": "/mnt/share"
            },
            "type": "HISTORY",
            "aggregate": {
                "type": "DETAIL"
            },
            "last": true,
            "cache": true,
            "data": [
                {
                    "d": "2015-09-22T10:56:38.000Z",
                    "v": 132548224
                }
            ]
        }
    ]
}
```
