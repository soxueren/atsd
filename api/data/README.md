## Overview

The Data API lets you insert and retrieve time-series, properties, messages, and alerts from Axibase Time-Series Database (ATSD) server. 

The API uses standard HTTP requests, such as: `GET`, `POST`, and `PATCH`. 

All requests must be authorized using BASIC AUTHENTICATION. 

In response, the ATSD server sends an HTTP status code (such as a 200-type status for success or 400-type status for failure) that reflects the result of each request. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses. 

### Authentication

* User authentication is required.
* Authentication method: HTTP BASIC.
* Client may use session cookies to execute multiple requests without repeating authentication.
* Cross-domain requests are allowed. The server includes the following headers in each response:

`Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization`

`Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE`

`Access-Control-Allow-Origin: *`

### Compression

* Clients may send compressed data by specifying Content-Encoding: gzip

### Errors
[Error Codes](https://github.com/axibase/atsd-docs/blob/master/api/data/error-codes.md)
