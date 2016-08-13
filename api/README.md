# Axibase Time Series Database API

## API Categories

* [Network](/api/network#network-api)
  * [series](/api/network/series.md)
  * [property](/api/network/property.md)
  * [message](/api/network/message.md)
  * [csv](/api/network/csv.md)
  * [nmon](/api/network/nmon.md)
  * [entity](/api/network/entity.md)  
  * [metric](/api/network/metric.md)
  * [extended](/api/network/extended-commands.md)
* [Data](/api/data#overview)
  * [Series](/api/data/series/README.md): [query](/api/data/series/query.md), [insert](/api/data/series/insert.md), [csv insert](/api/data/series/csv-insert.md), [url query](/api/data/series/url-query.md)
  * [Properties](/api/data/properties/README.md): [query](/api/data/properties/query.md), [insert](/api/data/properties/insert.md), [url query](/api/data/properties/url-query.md), [type query](/api/data/properties/type-query.md), [delete](/api/data/properties/delete.md)
  * [Messages](/api/data/messages/README.md): [query](/api/data/messages/query.md), [insert](/api/data/messages/insert.md), [statistics](/api/data/messages/stats-query.md)
  * [Alerts](/api/data/alerts/README.md): [query](/api/data/alerts/query.md), [update](/api/data/alerts/update.md), [delete](/api/data/alerts/delete.md), [history query](/api/data/alerts/history-query.md)
  * [Extended](/api/data/ext/README.md): [csv-upload](/api/data/ext/csv-upload.md), [nmon-upload](/api/data/ext/nmon-upload.md), [command](/api/data/ext/command.md)
* [Meta](/api/meta#overview)
  * [Metrics](/api/meta/metric/README.md): [list](/api/meta/metric/list.md), [get](/api/meta/metric/get.md), [update](/api/meta/metric/update.md), [delete](/api/meta/metric/delete.md), [create/replace](/api/meta/metric/create-or-replace.md), [series](/api/meta/metric/series.md)
  * [Entities](/api/meta/entity/README.md): [list](/api/meta/entity/list.md), [get](/api/meta/entity/get.md), [update](/api/meta/entity/update.md), [delete](/api/meta/entity/delete.md), [create/replace](/api/meta/entity/create-or-replace.md), [metrics](/api/meta/entity/metrics.md), [entity-groups](/api/meta/entity/entity-groups.md), [property-types](/api/meta/entity/property-types.md)
  * [Entity Groups](/api/meta/entity-group/README.md): [list](/api/meta/entity-group/list.md), [get](/api/meta/entity-group/get.md), [update](/api/meta/entity-group/update.md), [delete](/api/meta/entity-group/delete.md), [create/replace](/api/meta/entity-group/create-or-replace.md), [get-entities](/api/meta/entity-group/get-entities.md), [add-entities](/api/meta/entity-group/add-entities.md), [set-entities](/api/meta/entity-group/set-entities.md), [delete-entities](/api/meta/entity-group/delete-entities.md)
* [SQL](/api/sql#overview)  
	* [Syntax](/api/sql#syntax)
	* [Grouping](/api/sql#grouping)
	* [Partitioning](/api/sql#partitioning)
	* [Ordering](/api/sql#ordering)
	* [Limiting](/api/sql#limiting)
	* [Interpolation](/api/sql#interpolation)
	* [Joins](/api/sql#joins)
	* [API Endpoint](/api/sql/api.md#sql-query-api-endpoint)
	* [Examples](/api/sql#examples)

## API Clients

* [R language](https://github.com/axibase/atsd-api-r)
* [PHP](https://github.com/axibase/atsd-api-php)
* [Java](https://github.com/axibase/atsd-api-java)
* [Python](https://github.com/axibase/atsd-api-python)
* [Ruby](https://github.com/axibase/atsd-api-ruby)
* [NodeJS](https://github.com/axibase/atsd-api-nodejs)

## Drivers

* [JDBC Driver](https://github.com/axibase/atsd-jdbc)
