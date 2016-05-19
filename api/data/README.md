## Overview

The Data API lets you insert and retrieve series, properties, messages, and alerts from Axibase Time Series Database server. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses. 

## Categories

* [Series](series)
  - [insert](series/insert.md)
  - [query](series/query.md)
  - [csv insert](series/csv-insert.md)
  - [url query](series/url-query.md)
* [Properties](properties)
  - [batch](properties/batch.md)
  - [insert](properties/insert.md)
  - [query](properties/query.md)
  - [property types](properties/property-types.md)
  - [query for entity and type](properties/query-for-entity-and-type.md)
* [Messages](messages)
  - [insert](messages/insert.md)
  - [query](messages/query.md)
* [Alerts](alerts)
  - [query](alerts/query.md)
  - [update](alerts/update.md)
  - [history query](alerts/history-query.md)
* [Combined](command.md)

## Request Methods

The API uses `GET`, `POST`, `PUT`, `PATCH`, and `DELETE` methods to read and write data.

The `PATCH` method is typically used to delete records based on a condition specified in payload, because payload is not allowed in the `DELETE` method. 

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
* Client may use session cookies to execute multiple requests without repeating authentication.

## Authorization

* User must have [**API_DATA_READ**/**API_DATA_WRITE**](/administration/user-authorization.md#available-api-roles) role.
* User must have read/write [**entity permission**](/administration/user-authorization.md#entity-permissions) for specific or all entities.
 
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

curl http://localhost:20088/api/v1/series/insert \
  -v -u {username}:{password} \
  -H "Content-Type: application/json" \
  -X POST \
  -d '[{"entity": "nurswgvml007", "metric": "mpstat.cpu_busy", "data": [{ "t": 1462427358127, "v": 22.0 }]}]'

cat json-in.log
```

## Errors

[Error Codes](https://github.com/axibase/atsd-docs/blob/master/api/data/error-codes.md)
