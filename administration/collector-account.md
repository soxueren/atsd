# Collector Account

We recommend creating a separate `collector` user account with permissions limited to data collection tasks. 

These credentials can be used in all HTTP clients when sending data into ATSD over http/https protocol.

![Collector Account](collector_user.png)

Follow these steps to create `collector` account:

* Login into ATSD as administrator
* Open **Admin>Users>User Groups>Create** page and create `collectors` group with **[All Entities] Write** permission.

![collectors group](all-entities-write.png)

* Open **Admin>Users>Create** page and create `collector` user with **API_DATA_WRITE** and **API_META_WRITE** roles. 
* Click on `collectors` group to add `collector` user as the member of the this group.

![collector user](collector-user.png)
