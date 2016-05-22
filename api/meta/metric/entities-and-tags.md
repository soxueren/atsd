# Metric: Entities and Tags

## Description 

Returns a list of **series** for the metric. Each series contains entity name and optional series tags.

## Request

### Path

```elm
/api/v1/metrics/{metric}/entity-and-tags
```

### Method

```
GET 
```

### Headers

None.

### Parameters

| **Parameter** |**Type**| **Description** |
|:---|:---|:---|
| entity | string|Include series for the specified entity name. |
|minInsertDate|iso_date|Include series with `lastInsertDate` equal or greater than `minInsertDate`.|
|maxInsertDate|iso_date|Include series with `lastInsertDate` less than `maxInsertDate`.|

_All parameters are optional._

### Fields

None.

## Response

### Fields

| **Field** | **Description** |
|:---|:---|
| entity | Entity name.  |
| tags | An object containing **series** tags as names and values.<br>For example, `"tags": {"file_system": "/dev/sda"}` |
| lastInsertDate |Last time a value was received for this series. ISO date.|

### Errors

None.

## Example

### Request

#### URI

```elm
GET https://atsd_host:8443/api/v1/metrics/disk_used/entity-and-tags
```

#### Payload

None.

#### curl

None.

### Response

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

## Additional Examples




