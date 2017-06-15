Weekly Change Log: May 15, 2017 - May 21, 2017
==================================================
### ATSD
| Issue| Category    | Type    | Subject              |
|------|-------------|---------|----------------------|
| 4207 | core | Bug | `HOST` tags added to sporadic ATSD entities that lacked them.|
| 4205 | UI | Bug | Refreshed SQL Console cache so that changes made in Configuration > Replacement Tables took effect.|
| 4202 | csv | Bug | Consolidated error notification messages into a single instance. |
| [4196](#Issue-4196) | api-network | Feature | Enabled support for gzip compression |
| 4191 | UI | Bug | Fixed the `CLONE` button in the SQL Query Editor, which failed to redirect the user to the newly cloned configuration. |
| 4189 | sql | Bug | Fixed a problem with the [`WHERE`](https://github.com/axibase/atsd/tree/master/api/sql#where-clause) clause that caused the[`NOT LIKE`](https://github.com/axibase/atsd/tree/master/api/sql#metrics) command to be ignored. |
| [4184](#Issue-4184) | core | Feature | Commenting enabled for Configuration > Replacement Tables |
| 4182 | sql | Bug |Fixed an IllegalArgumentException that occurred when [`CAST`](https://github.com/axibase/atsd/tree/master/api/sql/#cast) function was used with [`ISNULL`](https://github.com/axibase/atsd/blob/master/api/sql/README.md/#isnull) clause as a number.|
| [4179](#Issue-4179) | sql | Feature | Enabled Ignore Text feature on SQL Query Statistics page. |
| 4178 | export | Bug | Fixed a bug which notified subscribing users if Output Path was not specified in Configuration > Export Jobs. |
| 4175 | sql | Bug | Fixed a rounding protocol in the SQL Console that led to incorrect date alignment.  |
| 4169 | rule engine | Bug | Repaired FileNotFoundException which denied access to /tmp/atsd/alert.log to allow accurate logging. (As you can see I need a little help explaining this one, thank you!)
| 4161 | sql | Bug | Load sequence for the SQL Console modified to enhance interface appearance. |
| 4132 | collectd | Feature | Enabled [`collectd`](https://github.com/axibase/atsd-collectd-plugin) to use a short host name instead of a Fully Qualified Domain Name when FQDN is `localhost`.  |
| 4115 | UI | Bug | Repaired a bug which incorrectly rendered the user interface while performing data entry. |

#### ATSD

##### Issue 4196

ATSD containers command script:
```
 curl -X POST --data-binary @command.txt.gz http://server-name:8088/api/v1/command --header "Content-Encoding:gzip" --header "Content-Type:text/plain;charset=UTF-8"
```

##### Issue 4184

![4184](Images/4184.png)

##### Issue 4179

In SQL Console > Query Statistics

![4179](Images/4179.png)

Using an exclamation point in the Query Text search bar will hide results that include the
indicated text, in this case `!SELECT 1` hides results which include `SELECT 1` in the
query text, as shown below:

![4179.2](Images/4179.2.png)