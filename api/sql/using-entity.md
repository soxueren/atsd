## Using Entity

Calculate Cartesian product for table tags and fill each row with metric values based on entity and time.

> Request

```sql
SELECT entity, disk_used.time, cpu_busy.time, AVG(cpu_busy.value), AVG(disk_used.value), tags.*
FROM cpu_busy
JOIN USING entity disk_used
WHERE time > now - 1 * hour
GROUP BY entity, tags, period(15 minute)
```

> Response

```json
{
    "columns": [
        {
            "name": "entity",
            "metric": "cpu_busy",
            "label": "entity",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "time",
            "metric": "disk_used",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "time",
            "metric": "cpu_busy",
            "label": "time",
            "type": "LONG",
            "numeric": true
        },
        {
            "name": "AVG(cpu_busy.value)",
            "metric": "cpu_busy",
            "label": "AVG(cpu_busy.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "AVG(disk_used.value)",
            "metric": "disk_used",
            "label": "AVG(disk_used.value)",
            "type": "FLOAT",
            "numeric": true
        },
        {
            "name": "tags.mount_point",
            "metric": "cpu_busy",
            "label": "tags.mount_point",
            "type": "STRING",
            "numeric": false
        },
        {
            "name": "tags.file_system",
            "metric": "cpu_busy",
            "label": "tags.file_system",
            "type": "STRING",
            "numeric": false
        }
    ],
    "rows": [
        [
            "nurswgvml003",
            1447405200000,
            1447405200000,
            4.5,
            2421396,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447406100000,
            1447406100000,
            26.237499999999997,
            2431103,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447407000000,
            1447407000000,
            2.235,
            2431021,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447407900000,
            1447407900000,
            1.67,
            2414688,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml003",
            1447408800000,
            1447408800000,
            3.92,
            2414638,
            "/",
            "/dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff"
        ],
        [
            "nurswgvml102",
            1447405200000,
            1447405200000,
            1.99,
            1825316,
            "/",
            "/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a"
        ],
        [
            "nurswgvml102",
            1447406100000,
            1447406100000,
            0.75,
            1825347,
            "/",
            "/dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a"
        ]
    ]
}
```

**SQL Console Response**

| entity       | time          | time          | AVG(cpu_busy.value) | AVG(disk_used.value) | tags.mount_point | tags.file_system                                       | 
|--------------|---------------|---------------|---------------------|----------------------|------------------|--------------------------------------------------------| 
| nurswgvml003 | 1447405200000 | 1447405200000 | 4.5                 | 2421396.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447406100000 | 1447406100000 | 26.237499999999997  | 2431103.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447407000000 | 1447407000000 | 2.235               | 2431021.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447407900000 | 1447407900000 | 1.67                | 2414688.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml003 | 1447408800000 | 1447408800000 | 3.92                | 2414638.0            | /                | /dev/disk/by-uuid/28b8099a-f2fd-4b72-826c-4b270404deff | 
| nurswgvml102 | 1447405200000 | 1447405200000 | 1.99                | 1825316.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447406100000 | 1447406100000 | 0.75                | 1825347.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447407000000 | 1447407000000 | 0.9966666666666667  | 1825384.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447407900000 | 1447407900000 | 0.5025              | 1825418.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml102 | 1447408800000 | 1447408800000 | 1.9900000000000002  | 1825452.0            | /                | /dev/disk/by-uuid/8a5a178f-4dba-4282-803a-1fe43fc6220a | 
| nurswgvml006 | 1447405200000 | 1447405200000 | 18.384999999999998  | 7976100.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447406100000 | 1447406100000 | 10.2375             | 7976356.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447407000000 | 1447407000000 | 39.693333333333335  | 7976576.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447407900000 | 1447407900000 | 10.0125             | 7976867.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml006 | 1447408800000 | 1447408800000 | 28.035              | 7977008.0            | /                | /dev/mapper/vg_nurswgvml006-lv_root                    | 
| nurswgvml007 | 1447405200000 | 1447405200000 | 26.26               | 8867000.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447406100000 | 1447406100000 | 17.0375             | 8867733.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447407000000 | 1447407000000 | 15.406666666666666  | 8868142.666666666    | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447407900000 | 1447407900000 | 13.559999999999999  | 8868494.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml007 | 1447408800000 | 1447408800000 | 39.03               | 8868948.0            | /                | /dev/mapper/vg_nurswgvml007-lv_root                    | 
| nurswgvml010 | 1447405200000 | 1447405200000 | 17.653333333333332  | 5840140.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447406100000 | 1447406100000 | 14.18125            | 5840176.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447407000000 | 1447407000000 | 13.951666666666666  | 5840225.333333333    | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447407900000 | 1447407900000 | 22.338              | 5840268.8            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447408800000 | 1447408800000 | 26.314999999999998  | 5840300.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447405200000 | 1447405200000 | 2.5149999999999997  | 7013828.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447406100000 | 1447406100000 | 5.234999999999999   | 7014201.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447407000000 | 1447407000000 | 2.25                | 7014283.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447407900000 | 1447407900000 | 2.34                | 7014312.0            | /                | /dev/sda1                                              | 
| nurswgvml011 | 1447408800000 | 1447408800000 | 2.9850000000000003  | 7014716.0            | /                | /dev/sda1                                              | 
| nurswgvml010 | 1447405200000 | 1447405200000 | 17.653333333333332  | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447406100000 | 1447406100000 | 14.18125            | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447407000000 | 1447407000000 | 13.951666666666666  | 2.745202E7           | /app             | /dev/sdb1                                              | 
| nurswgvml010 | 1447407900000 | 1447407900000 | 22.338              | 2.7452024E7          | /app             | /dev/sdb1                                              | 

