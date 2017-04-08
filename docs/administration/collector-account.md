# Collector Account

We recommend creating a separate `collector` user account with permissions limited to data collection tasks. 

These credentials can be used in all HTTP clients when sending data into ATSD over the http/https protocol.

![Collector Account](images/collector-account.png)

## Create `collectors` User Group 

* Login into ATSD as administrator
* Open the **Admin > Users > User Groups > Create** page
* Create the `collectors` group with **[All Entities] Write** permission

![collectors group](images/all-entities-write.png)

## Create `collector` User 

* Open the **Admin > Users > Create** page
* Create a `collector` user with **API_DATA_WRITE** and **API_META_WRITE** roles
* Check the `collectors` row in the User Groups table to add the user to the `collectors` group

![collector user](images/collector-user.png)
