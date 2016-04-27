## ROW_NUMBER with ORDER BY avg

`tags.*` - every tag will have its own column in the response.

> Request

```sql
SELECT entity, tags.*, datetime, Avg(value)
  FROM df.disk_used WHERE time > now - 1 * day
  GROUP BY entity, tags, period(15 minute)
  WITH row_number(entity, tags ORDER BY avg(value) DESC) <=5
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
            "2015-10-27T06:15:00Z",
            2423001.2
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T06:00:00Z",
            2422891.7333333334
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T05:45:00Z",
            2422842.6666666665
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T05:30:00Z",
            2422774.2666666666
        ],
        [
            "awsswgvml001",
            "/",
            "/dev/xvda1",
            "2015-10-27T08:45:00Z",
            2422758.0606060605
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:00:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:15:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:30:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T04:45:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/backup",
            "/dev/xvdf",
            "2015-10-27T05:00:00Z",
            66650720
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:45:00Z",
            24865433.454545453
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:30:00Z",
            24724325.266666666
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T07:00:00Z",
            24695767.4
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T08:15:00Z",
            24682546.8
        ],
        [
            "awsswgvml001",
            "/data",
            "/dev/xvdh1",
            "2015-10-27T06:45:00Z",
            24680499.4
        ]
    ]
}
```

**SQL Console Response**

| entity       | tags.mount_point | tags.file_system                                       | datetime             | Avg(value)           | 
|--------------|------------------|--------------------------------------------------------|----------------------|----------------------| 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:30:00Z | 2433910.3636363638   | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:15:00Z | 2433866.6            | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T13:00:00Z | 2433769.1333333333   | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T12:45:00Z | 2433680.6            | 
| awsswgvml001 | /                | /dev/xvda1                                             | 2015-10-28T12:30:00Z | 2433610.7333333334   | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:00:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:15:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:30:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T04:45:00Z | 6.6684048E7          | 
| awsswgvml001 | /backup          | /dev/xvdf                                              | 2015-10-28T05:00:00Z | 6.6684048E7          | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T11:45:00Z | 2.4721712068965517E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-27T15:30:00Z | 2.46928654E7         | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-27T15:15:00Z | 2.4675727533333335E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T08:45:00Z | 2.4672177866666667E7 | 
| awsswgvml001 | /data            | /dev/xvdh1                                             | 2015-10-28T09:00:00Z | 2.4671984666666668E7 | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:30:00Z | 2428775.8181818184   | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 2015-10-28T12:30:00Z | 2428582.466666667    | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T13:30:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T13:45:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:00:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:15:00Z | 1.39204048E8         | 
| nurswgvml003 | /home/store      | /dev/sdb1                                              | 2015-10-27T14:30:00Z | 1.39204048E8         | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:30:00Z | 2428775.8181818184   | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:15:00Z | 2428752.8            | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T13:00:00Z | 2428658.6            | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T12:45:00Z | 2428627.066666667    | 
| nurswgvml003 | /                | rootfs                                                 | 2015-10-28T12:30:00Z | 2428582.466666667    | 
