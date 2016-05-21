## Overview

The Meta API lets you query and update meta-data about metrics, entities, entity groups, rules, and tags from Axibase Time-Series Database (ATSD) server. 

The API uses standard HTTP requests, such as: `GET`, `POST`, and `PATCH`. 

All requests must be authorized using BASIC AUTHENTICATION. 

In response, the ATSD server sends an HTTP status code (such as a 200-type status for success or 400-type status for failure) that reflects the result of each request. 

You can use any programming language that lets you issue HTTP requests and parse JSON-based responses.

## Categories

* [Metric](metric)
    - [list](metric/list.md)
    - [delete](metric/delete.md)
    - [get](metric/get.md)
    - [update](metric/update.md)
    - [create or replace](metric/create-or-replace.md)
    - [entities and tags](metric/entities-and-tags.md)
* [Entity](entity)
    - [list](entity/list.md)
    - [delete](entity/delete.md)
    - [get](entity/get.md)
    - [update](entity/update.md)
    - [create or replace](entity/create-or-replace.md)
    - [entity groups](entity/entity-groups.md)
    - [metrics](entity/metrics.md)
    - [property types](entity/property-types.md)
* [Entity group](entity-group)
    - [list](entity-group/list.md)
    - [delete](entity-group/delete.md)
    - [get](entity-group/get.md)
    - [update](entity-group/update.md)
    - [create or replace](entity-group/create-or-replace.md)
    - [add entities](entity-group/add-entities.md)
    - [delete entities](entity-group/delete-entities.md)
    - [get entities](entity-group/get-entities.md)
    - [set (replace) entities](entity-group/set-replace-entities.md)




### Authentication

* User authentication is required.
* Authentication method: `HTTP BASIC`.
* Client may use session cookies to execute multiple requests without repeating authentication.
* Cross-domain requests are allowed. The server includes the following headers in each response:

`Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization`

`Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE`
    
`Access-Control-Allow-Origin: *`

### Compression

* Clients may send compressed data by specifying Content-Encoding: gzip

