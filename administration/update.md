# Update


Updating ATSD to the latest version is done using by running an update
script. The process is completely automatic, all new files are
downloaded, all processes are stopped and restarted, no interaction with
the system is required by the user.

This guide assumes that the target machine has direct internet
connection.

If the target machine does not have direct internet connection, please
use the [manual update
guide](update-manual.md "Update ATSD").

## Update command:

```sh
/opt/atsd/bin/update.sh
```

The update process can take a while.

## To verify that ATSD is working correctly, navigate to the ATSD user interface in your browser:

```sh
 http://"ip or hostname of ATSD server":8088/                             
```

Navigate to the Admin -\> Build Info page, verify that the Revision
Number is the same as the revision number of the installed ATSD update.

![](images/ATSD_build_info.png "ATSD_build_info")
