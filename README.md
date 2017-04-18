# Axibase Time Series Database Documentation

## [API](/docs/api)

  * [Network](/docs/api/network#network-api)
    * [series](/docs/api/network/series.md)
    * [property](/docs/api/network/property.md)
    * [message](/docs/api/network/message.md)
    * [csv](/docs/api/network/csv.md)
    * [nmon](/docs/api/network/nmon.md)
    * [entity](/docs/api/network/entity.md)  
    * [metric](/docs/api/network/metric.md)
    * [extended](/docs/api/network/extended-commands.md)
  * [Data](/docs/api/data#overview)
    * [Series](/docs/api/data/series/README.md): [query](/docs/api/data/series/query.md), [insert](/docs/api/data/series/insert.md), [csv insert](/docs/api/data/series/csv-insert.md), [url query](/docs/api/data/series/url-query.md)
    * [Properties](/docs/api/data/properties/README.md): [query](/docs/api/data/properties/query.md), [insert](/docs/api/data/properties/insert.md), [url query](/docs/api/data/properties/url-query.md), [type query](/docs/api/data/properties/type-query.md), [delete](/docs/api/data/properties/delete.md)
    * [Messages](/docs/api/data/messages/README.md): [query](/docs/api/data/messages/query.md), [insert](/docs/api/data/messages/insert.md), [statistics](/docs/api/data/messages/stats-query.md)
    * [Alerts](/docs/api/data/alerts/README.md): [query](/docs/api/data/alerts/query.md), [update](/docs/api/data/alerts/update.md), [delete](/docs/api/data/alerts/delete.md), [history query](/docs/api/data/alerts/history-query.md)
    * [Extended](/docs/api/data/ext/README.md): [csv-upload](/docs/api/data/ext/csv-upload.md), [nmon-upload](/docs/api/data/ext/nmon-upload.md), [command](/docs/api/data/ext/command.md)
  * [Meta](/docs/api/meta#overview)
    * [Metrics](/docs/api/meta/metric/README.md): [list](/docs/api/meta/metric/list.md), [get](/docs/api/meta/metric/get.md), [update](/docs/api/meta/metric/update.md), [delete](/docs/api/meta/metric/delete.md), [create/replace](/docs/api/meta/metric/create-or-replace.md), [series](/docs/api/meta/metric/series.md)
    * [Entities](/docs/api/meta/entity/README.md): [list](/docs/api/meta/entity/list.md), [get](/docs/api/meta/entity/get.md), [update](/docs/api/meta/entity/update.md), [delete](/docs/api/meta/entity/delete.md), [create/replace](/docs/api/meta/entity/create-or-replace.md), [metrics](/docs/api/meta/entity/metrics.md), [entity-groups](/docs/api/meta/entity/entity-groups.md), [property-types](/docs/api/meta/entity/property-types.md)
    * [Entity Groups](/docs/api/meta/entity-group/README.md): [list](/docs/api/meta/entity-group/list.md), [get](/docs/api/meta/entity-group/get.md), [update](/docs/api/meta/entity-group/update.md), [delete](/docs/api/meta/entity-group/delete.md), [create/replace](/docs/api/meta/entity-group/create-or-replace.md), [get-entities](/docs/api/meta/entity-group/get-entities.md), [add-entities](/docs/api/meta/entity-group/add-entities.md), [set-entities](/docs/api/meta/entity-group/set-entities.md), [delete-entities](/docs/api/meta/entity-group/delete-entities.md)

## [API Clients](/docs/api#api-clients)

  * [R](https://github.com/axibase/atsd-api-r)
  * [PHP](https://github.com/axibase/atsd-api-php)
  * [Java](https://github.com/axibase/atsd-api-java)
  * [Python](https://github.com/axibase/atsd-api-python)
  * [Ruby](https://github.com/axibase/atsd-api-ruby)
  * [NodeJS](https://github.com/axibase/atsd-api-nodejs)

## [SQL](/docs/api/sql#overview)

  * [Syntax](/docs/api/sql#syntax)
  * [Grouping](/docs/api/sql#grouping)
  * [Partitioning](/docs/api/sql#partitioning)
  * [Ordering](/docs/api/sql#ordering)
  * [Limiting](/docs/api/sql#limiting)
  * [Interpolation](/docs/api/sql#interpolation)
  * [Joins](/docs/api/sql#joins)
  * [API Endpoint](/docs/api/sql/api.md#sql-query-api-endpoint)
  * [Examples](/docs/api/sql#examples)

## Drivers

  * [JDBC](https://github.com/axibase/atsd-jdbc)

## Rule Engine

  * [Overview](/docs/rule-engine/README.md)
  * [Expression](/docs/rule-engine/expression.md)
  * [Filters](/docs/rule-engine/filters.md)
  * [Functions](/docs/rule-engine/functions.md)
  * [Placeholders](/docs/rule-engine/placeholders.md)
  * [Editor](/docs/rule-engine/editor.md)
  * [Email Action](/docs/rule-engine/email-action.md)

## Installation

  * [Requirements](/docs/administration/requirements.md)
  * [Distributions](/docs/installation/#installation-guides)
    * [Ubuntu/Debian (apt)](/docs/installation/ubuntu-debian-apt.md)
    * [Ubuntu/Debian  (deb)](/docs/installation/ubuntu-debian-deb.md)
    * [RedHat/CentOS (yum)](/docs/installation/redhat-centos-yum.md)
    * [RedHat/CentOS (rpm)](/docs/installation/redhat-centos-rpm.md)
    * [SUSE Linux Enterprise Server (rpm)](/docs/installation/sles-rpm.md)
    * [Docker (image)](/docs/installation/docker.md)
    * [VMware VM](/docs/installation/vmware-esxi-server-vsphere.md)
    * [Oracle VirtualBox VM](/docs/installation/virtualbox.md)
    * [Other](/docs/installation/other-distributions.md)
  * Cluster Distributions
    * [Cloudera Hadoop Distribution](/docs/installation/cloudera.md)  	
    * [Ambari](/docs/installation/ambari.md)	  
    * [HBase Cluster](/docs/installation/hbase-cluster.md)
  * [Uninstalling](/docs/administration/uninstalling.md)
  * [Updating](/docs/administration/update.md)   

## Security

* [User Authentication](/docs/administration/user-authentication.md)
* [User Authorization](/docs/administration/user-authorization.md)

## Administration

  * [Overview](/docs/administration#administration)
  * [Configuring Email Client](/docs/administration/setting-up-email-client.md)  
  * [Configuring Time Zone](/docs/administration/timezone.md)  
  * [Allocating Memory](/docs/administration/allocating-memory.md)
  * [Changing Data Directory](/docs/administration/changing-data-directory.md)
  * [Compaction Test](/docs/administration/compaction-test.md)
  * [Compaction](/docs/administration/compaction.md)
  * [Deployment](/docs/administration/deployment.md)
  * [Configuration Files](/docs/administration/editing-configuration-files.md)
  * [Network Settings](/docs/administration/networking-settings.md)  
  * [Enabling Swap Space](/docs/administration/enabling-swap-space.md)
  * [Entity Lookup](/docs/administration/entity-lookup.md)
  * [Logging](/docs/administration/logging.md)
  * [Metric Persistence Filter](/docs/administration/metric-persistence-filter.md)
  * [Upgrading Java](/docs/administration/upgrade-java.md)
  * [Monitoring](/docs/administration/monitoring.md)
  * [Replication](/docs/administration/replication.md)
  * [Restarting](/docs/administration/restarting.md)
  * [Restoring Corrupted Zookeeper](/docs/administration/corrupted-zookeeper.md)
  * [Restoring Corrupted Files](/docs/administration/corrupted-file-recovery.md)

## Integration

  * [ActiveMQ](/docs/integration/activemq#monitoring-activemq-with-atsd)
  * [Axibase Enterprise Reporter](/docs/integration/aer#atsd-adapter)
  * [Derby](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/derby#overview)
  * [HP Openview](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/hp-openview#overview)
  * [IBM Tivoli Monitoring](/docs/integration/itm#ibm-tivoli-monitoring)
  * [JVM](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/jvm#overview)
  * [Jetty](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/jetty#overview)
  * [Microsoft System Center Operations Manager](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/scom#overview)
  * [MySQL Server](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/mysql#overview)
  * [NGINX](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/nginx#overview)
  * [Oracle Enterprise Manager](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/oracle-enterprise-manager#overview)
  * [PostgreSQL](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/postgres#overview)
  * [SolarWinds](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/solarwinds#overview)
  * [Tomcat Servlet Container](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/tomcat#overview)
  * [VMware](https://github.com/axibase/axibase-collector/blob/master/docs/jobs/examples/vmware#overview)
