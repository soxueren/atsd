# Post-Installation Steps

## Verify System Time

> Open Admin \> Server Time tab in the ATSD web interface and verify the
time and timezone information.

Modify system time or setup NTP in order to keep the server time
accurate.

![Server\_time](images/Server_time.png)

## Adjust Network Buffers

If you’re anticipating high data insertion rate with bursts of 100000
packets per second or more, increase maximum receiving buffer on Linux
OS: [Read Network Settings
Guide](../administration/networking-settings.md "Network Settings")

## Setup Email Client

See [Setting up the Email Client
guide](../administration/setting-up-email-client.md "Email Client").
