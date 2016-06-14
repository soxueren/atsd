# Scheduled File Upload to ATSD

### Before You Begin


1. Make sure that /opt/nmon/nmon binary exists and is executable.
2. Launch nmon console to make sure nmon works correctly:


```
/opt/nmon/nmon
```

## Upload Files to ATSD by API with Wget

To start uploading files to ATSD every hour with Wget, perform the following steps:

### Step 1.

Create a file /opt/nmon/nmon_script.sh and add the following row to the cron schedule:

```
0 * * * * /opt/nmon/nmon_script.sh
```

### Step 2.

After that, add the following content to the file /opt/nmon/nmon_script.sh replacing atsd_user, atsd_password, atsd_server with actual credentials:

## Upload Files to ATSD with UNIX Socket

To start uploading files to ATSD every hour with UNIX socket perform the following steps (note that you will require bash):

### Step 1.

Create a file /opt/nmon/nmon_script.sh and add the following row to the cron schedule:

```
0 * * * * /opt/nmon/nmon_script.sh
```

### Step 2.

Add the following contents to /opt/nmon/nmon_script.sh replacing atsd_server with ATSD hostname or IP address:

## Upload Files to ATSD with Netcat

To start uploading files to ATSD every hour with Netcat, perform the following steps:

### Step 1.

Create a file /opt/nmon/nmon_script.sh and add the following row to the cron schedule:

```
0 * * * * /opt/nmon/nmon_script.sh
```

### Step 2.

Add the following contents to the /opt/nmon/nmon_script.sh file replacing atsd_server with ATSD hostname or IP address:

