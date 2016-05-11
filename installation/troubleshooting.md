# Troubleshooting

## Requirements

Verify that the target server meets hardware and OS [requirements](../administration/requirements.md).

## Review Logs

Review the following log files for errors:

* Startup log: `/opt/atsd/atsd/logs/start.log`
* Application log: `/opt/atsd/atsd/logs/atsd.log`

## 32-bit Error

`Package Not Found` error will be displayed when attempting an installation of atsd deb package on a 32-bit architecture. 
Retry installation on a supported [architecture](../administration/requirements.md).
