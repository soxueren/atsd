# Update


Update ATSD to the latest version by running an `update.sh`. 
The script automates the process by downloading the latest ATSD artifacts, 
copying files, and gracefully restarting services.

Access to axibase.com from the ATSD is required to download installation files.

If the target machine does not have direct internet connection, 
use the [manual update guide](update-manual.md).

## Check Revision

* Open **Admin:Build Info** page.
* Take note of the current Revision Number.

## Update Command

```sh
/opt/atsd/bin/update.sh
```

### Update Command in Docker container

Assuming `atsd` is the name of the container:

```
docker exec -it atsd /opt/atsd/bin/update.sh
```

## Validation

Once update is completed, the script will print out the following message:

```
Update process finished successfully.
```

The update process may take a few minutes to complete.

To verify that ATSD is working correctly, open ATSD web interface:

```sh
 http://atsd_host:8088/
```

* Open **Admin:Build Info** page
* Verify that the Revision Number has been incremented compared to pre-installation stage.

![](images/ATSD_build_info.png "ATSD_build_info")
