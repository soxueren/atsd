## ROW_NUMBER with ORDER BY time

`tags.*` - every tag will have its own column in the response.

> Query

```sql
SELECT entity, tags.*, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY time DESC) <=1
  ORDER BY entity
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "df.disk_used",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.mount_point",
            "metric": "df.disk_used",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "df.disk_used",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "datetime",
            "metric": "df.disk_used",
            "label": "datetime",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "Avg(value)",
            "metric": "df.disk_used",
            "label": "Avg(value)",
            "type": "FLOAT",
            "numeric": true
        }
    ],
    "rows": [
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T08:30:00Z",
            2422637
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T08:30:00Z",
            65340120
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:30:00Z",
            24745145
        ],
        [
            "nurswgvml003",
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff",
            "2015-10-27T08:30:00Z",
            2424204.5714285714
        ],
        [
            "nurswgvml003",
            "/home/store",
            "/dev/sdb1",
            "2015-10-27T08:30:00Z",
            139204048
        ],
        [
            "nurswgvml003",
            "/",
            "rootfs",
            "2015-10-27T08:30:00Z",
            2424204.5714285714
        ],
        [
            "nurswgvml006",
            "/",
            "/dev/mapper/vg_nurswgvml006-lv_root",
            "2015-10-27T08:30:00Z",
            8311659.428571428
        ],
        [
            "nurswgvml006",
            "/media/datadrive",
            "/dev/sdc1",
            "2015-10-27T08:30:00Z",
            47807372
        ],
        [
            "nurswgvml007",
            "/",
            "/dev/mapper/vg_nurswgvml007-lv_root",
            "2015-10-27T08:30:00Z",
            9581887.42857143
        ],
        [
            "nurswgvml007",
            "/mnt/share",
            "10.102.0.2:/home/store/share",
            "2015-10-27T08:30:00Z",
            139204032
        ]
    ]
}
```

**SQL Console Response**

| entity       | tags.mount_point | tags.file_system                                       | datetime             | Avg(value)          | 
|--------------|------------------|--------------------------------------------------------|----------------------|---------------------| 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:30:00Z | 2433897.6363636362  | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T13:30:00Z | 6.5373272E7         | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T13:30:00Z | 2.4670692E7         | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:30:00Z | 2428761.913043478   | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-28T13:30:00Z | 1.39204048E8        | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:30:00Z | 2428761.913043478   | 
| nurswgvml006 | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 2015-10-28T13:30:00Z | 8313054.260869565   | 
| nurswgvml006 | /media/datadrive | /dev/sdc1                                              | 2015-10-28T13:30:00Z | 4.837551686956522E7 | 

