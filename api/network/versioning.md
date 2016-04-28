## Versioning

```
series d:2015-08-13T10:00:00Z e:e-vers m:m-vers=13 t:$version_status=OK t:$version_source=collector:10.102.0.44
```

Versioning enables tracking of time-series value changes for the purpose of audit trail and traceable data reconciliation.

Versioning is disabled by default. It can be enabled for particular metrics by setting Versioning checkbox to selected on Metric Editor page.

To insert versioning fields use reserved series tags:

* `$version_source`
* `$version_status`

These tags will be removed by the server to populate corresponding versioning fields.

<aside class="notice">
Note that if metric is unversioned, `$version_source` and `$version_status` tags will be processed as regular tags.
</aside>

[Learn more about Versioning](http://axibase.com/products/axibase-time-series-database/data-model/versioning/)
