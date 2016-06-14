# Deploy nmon

In order to install nmon and the Axibase sender script on multiple Linux machines use the nmon_deploy.sh script.

The nmon_deploy.sh script will execute the following steps on each specified target machine:


- connect to remote machine over ssh
- copy nmon binary file
- copy atsd sender script
- add a cron task to restart nmon data collection on a schedule


Get the latest nmon files, [nmon_deploy.sh](resources/nmon_deploy.sh) and sender script from our GitHub: [https://github.com/axibase/nmon](https://github.com/axibase/nmon)

nmon_deploy.sh accepts the following flags:

| Flag | Description | 
| --- | --- | 
|  <p>-c</p>  |  <p>set path to deploy.properties file, that contains all the configurations required by nmon_deploy.sh script.</p>  | 
|  <p>-n</p>  |  <p>does not modify cron, only updates nmon binary file and ATSD sender script.</p>  <p></p>  <p>Useful to update nmon or sender script to a new version.</p>  | 
|  <p>-d</p>  |  <p>comments out all nmon cron tasks, will stop nmon data collection and sending to ATSD</p>  | 
|  <p>-i</p>  |  <p>while nmon does have any dependencies, the ATSD sender script has the following dependencies: crontab, telnet.</p>  <p></p>  <p>With -i flag the script will only check and install dependencies.</p>  <p></p>  <p>Requires sudo credentials defined in deploy.properties file.</p>  <p></p>  <p>After installing the dependencies, run the script again without the -i flag to install nmon and sender script.</p>  | 


The deploy.properties file contains the target machine parameters, user details, path to nmon and crontab settings:

Use hashtags # to comment out optional parameters.

The following files must be located in the same directory as the nmon_deploy.sh script (path to files is set in deploy.properties file):


- nmon binary file
- sender script
- target machine user ssh key
- atsdreadonly ssh key


| Setting | Description | 
| --- | --- | 
|  <p>nmon.s</p>  |  <p>pause between nmon snapshots</p>  | 
|  <p>nmon.c</p>  |  <p>nmon snapshot count</p>  | 
|  <p>nmon.cron.hour</p>  |  <p>cron task start hour</p>  | 
|  <p>nmon.cron.minute</p>  |  <p>cron task start minute</p>  | 
|  <p>atsd.protocol</p>  |  <p>data transfer protocol that will be used.</p>  <p></p>  <p>Possible values: telnet, ssh</p>  | 
|  <p>atsd.port</p>  |  <p>ATSD port that will receive the nmon data</p>  | 
|  <p>atsd.key</p>  |  <p>path to ssh key for readonly account.</p>  <p></p>  <p>id_rsa_atsdreadonly by default.</p>  <p></p>  <p>Optional parameter</p>  | 
|  <p>atsd.user</p>  |  <p>readonly user account.</p>  <p></p>  <p>atsdreadonly by default.</p>  <p></p>  <p>Optional parameter</p>  | 
|  <p>atsd.hostname</p>  |  <p>ATSD server that will store the nmon data</p>  | 
|  <p>deploy.user</p>  |  <p>user of target machines</p>  | 
|  <p>deploy.key</p>  |  <p>path to ssh key to access target machines by the user set in deploy.user</p>  | 
|  <p>deploy.password</p>  |  <p>password of target machine user set in deploy.user setting.</p>  <p></p>  <p>Password takes priority over ssh key.</p>  | 
|  <p>deploy.sudo.user</p>  |  <p>sudo user of target machines.</p>  <p></p>  <p>Optional parameter</p>  | 
|  <p>deploy.sudo.key</p>  |  <p>path to sudo users ssh key to access target machines.</p>  <p></p>  <p>Optional parameter.</p>  | 
|  <p>deploy.sudo.password</p>  |  <p>password of target machine sudo user set in deploy.sudo.user setting.</p>  <p></p>  <p>Password takes priority over ssh key.</p>  <p></p>  <p>Optional Parameter.</p>  | 
|  <p>deploy.nmon-binary</p>  |  <p>path to nmon binary file that will be installed on target machines.</p>  | 
|  <p>deploy.directory</p>  |  <p>directory that will be used on target machines to install nmon and the sender script.</p>  <p></p>  <p>The user must have write access to this directory.</p>  | 
|  <p>deploy.target</p>  |  <p>target server hostname or ip address and ssh connection port separated by :</p>  <p></p>  <p>Can be set to multiple servers, one server per line.</p>  | 


Example deploy.properties file:

```
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

Once the deploy.properties file is setup and paths to the required files are set, deploy nmon and the sender script to remote target machines:

```
./nmon_deploy.sh
```

The script will check each machine for dependencies and show installation progress/results on screen.

