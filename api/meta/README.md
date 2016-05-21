## Overview

The Meta API lets you query and update metadata about metrics, entities, and entity groups in Axibase Time Series Database. 

## Categories

* [Metric](metric)
    - [get](metric/get.md)
    - [list](metric/list.md)
    - [update](metric/update.md)
    - [create or replace](metric/create-or-replace.md)
    - [delete](metric/delete.md)
    - [entities and tags](metric/entities-and-tags.md)
* [Entity](entity)
    - [get](entity/get.md)
    - [list](entity/list.md)
    - [update](entity/update.md)
    - [create or replace](entity/create-or-replace.md)
    - [delete](entity/delete.md)
    - [entity groups](entity/entity-groups.md)
    - [metrics](entity/metrics.md)
    - [property types](entity/property-types.md)
* [Entity group](entity-group)
    - [get](entity-group/get.md)
    - [list](entity-group/list.md)
    - [update](entity-group/update.md)
    - [create or replace](entity-group/create-or-replace.md)
    - [delete](entity-group/delete.md)
    - [get entities](entity-group/get-entities.md)
    - [add entities](entity-group/add-entities.md)
    - [delete entities](entity-group/delete-entities.md)
    - [set or replace entities](entity-group/set-replace-entities.md)

## Request Methods

The API uses `GET`, `POST`, `PUT`, `PATCH`, and `DELETE` methods to read and write data.

## Response Codes

* `200` status code if the request is successful.
* `401` status code in case of unknown resource.
* `403` status code in case of access denied error.
* `4xx` status code in case of other client errors.
* `5xx` status code in case of server error. 

4xx or 5xx response codes are specific to each API methods.

## Authentication

* User [authentication](/administration/user-authentiication.md) is required.
* All requests must be authenticated using BASIC AUTHENTICATION.
* Authentication method is **HTTP BASIC**.
* Client may enable session cookies to execute multiple requests without re-sending BASIC authentication header.

## Authorization

* User must have [**API_META_READ**/**API_META_WRITE**](/administration/user-authorization.md#available-api-roles) role.
 
## Cross-domain Requests

Cross-domain requests are allowed. 

The server includes the following headers in each response:

```yaml
Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization
Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE
Access-Control-Allow-Origin: *
```

## Compression

* Clients may send compressed data by adding HTTP header **Content-Encoding: gzip** to the request.

## Troubleshooting

* Review error logs on **Admin:Server Logs** page in case the payload rejected
* To validate json received from a client, launch `netcat` utility in server mode, reconfigure the client to send data to netcat port, and dump incoming data to file:

```
nc -lk localhost 20088 > json-in.log &

curl http://localhost:20088/api/v1/metrics/cpu-used-total \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X PATCH \
  -d '{ "label": "CPU Busy Average" }'

cat json-in.log
```

