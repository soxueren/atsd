Deploy nmon

In order to install nmon and the Axibase sender script on multiple Linux machines use the `nmon_deploy.sh` script.

The `nmon_deploy.sh` script will execute the following steps on each specified target machine:

- connect to remote machine over ssh
- copy nmon binary file
- copy atsd sender script
- add a cron task to restart nmon data collection on a schedule

Get the latest nmon files,
[nmon\_deploy.sh](https://github.com/axibase/nmon/blob/master/nmon_deploy.sh "nmon_deploy.sh on GitHub") and sender script from our GitHub: [https://github.com/axibase/nmon](https://github.com/axibase/nmon "Axibase nmon GitHub")

`nmon_deploy.sh` accepts the following flags:

| Flag | Description |
| --- | --- |
| -c | set path to `deploy.properties` file, that contains all the configurations required by `nmon_deploy.sh` script. |
| -n | does not modify cron, only updates nmon binary file and ATSD sender script. Useful to update nmon or sender script to a new version. |
| -d | comments out all nmon cron tasks, will stop nmon data collection and sending to ATSD |
| -i | while nmon does have any dependencies, the ATSD sender script has the following dependencies: crontab, telnet.

With -i flag the script will only check and install dependencies. Requires sudo credentials defined in `deploy.properties` file. After installing the dependencies, run the script again without the -i flag to install nmon and sender script. |

while nmon does have any dependencies, the ATSD sender script has the following dependencies: crontab, telnet.With -i flag the script will only check and install dependencies.Requires sudo credentials defined in `deploy.properties` file.\
 After installing the dependencies, run the script again without the `-i` flag to install nmon and sender script.

The `deploy.properties` file contains the target machine parameters, user details, path to nmon and crontab settings:

Use hashtags `#` to comment out optional parameters.

The following files must be located in the same directory as the
`nmon_deploy.sh` script (path to files is set in `deploy.properties` file):

- nmon binary file
- sender script
- target machine user ssh key
- atsdreadonly ssh key

| Setting | Description |
| --- | --- |
| nmon.s | pause between nmon snapshots |
| nmon.c | nmon snapshot count |
| nmon.cron.hour | cron task start hour |
| nmon.cron.minute | cron task start minute |
| atsd.protocol | data transfer protocol that will be used. Possible values: telnet, ssh |
| atsd.port | ATSD port that will receive the nmon data |
| atsd.key | path to ssh key for readonly account. id_rsa_atsdreadonly by default. Optional parameter |
| atsd.user | readonly user account. atsdreadonly by default. Optional parameter |
| atsd.hostname | ATSD server that will store the nmon data |
| deploy.user | user of target machines |
| deploy.key | path to ssh key to access target machines by the user set in deploy.user |
| deploy.password | password of target machine user set in deploy.user setting. Password takes priority over ssh key. |
| deploy.sudo.user | sudo user of target machines. Optional parameter |
| deploy.sudo.key | path to sudo users ssh key to access target machines. Optional parameter. |
| deploy.sudo.password | password of target machine sudo user set in deploy.sudo.user setting. Password takes priority over ssh key. Optional Parameter. |
| deploy.nmon-binary | path to nmon binary file that will be installed on target machines. |
| deploy.directory | directory that will be used on target machines to install nmon and the sender script. The user must have write access to this directory. |
| deploy.target | target server hostname or ip address and ssh connection port separated by `:`Can be set to multiple servers, one server per line. |

Example `deploy.properties` file:

```properties
 nmon.s = 60
 nmon.c = 1440
 nmon.cron.hour = 12
 nmon.cron.minute = 19
  
 atsd.protocol = telnet
 atsd.port = 8081
 #atsd.key=./id_rsa_atsdreadonly
 #atsd.user=atsdreadonly
 atsd.hostname = atsdserver.com
  
 deploy.user = nmonuser
 deploy.key = ./id_dsa_nmonuser
 deploy.password=secret-pwd
 #deploy.sudo.user = root
 #deploy.sudo.key = ./id_dsa_root
 #deploy.sudo.password=secret-root-pwd
 deploy.nmon-binary = nmon_x86_64_sles11
 deploy.directory = /home/nmonuser/nmon
 deploy.target = nurswgvml006:22
 deploy.target = nurswgvml007:22
 deploy.target = nurswgvml008:22
 deploy.target = nurswgvml009:22
```

Once the `deploy.properties` file is setup and paths to the required files are set, deploy nmon and the sender script to remote target machines:

```sh
 ./nmon_deploy.sh
```

The script will check each machine for dependencies and show installation progress results on screen.