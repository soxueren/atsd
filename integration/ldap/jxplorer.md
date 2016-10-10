##Retrieving an Active Directory Certificate

To establish SSL Connection one has to obtain certificate from AD Server.


There are 3 ways to do it:

* Using Window’s Certutil
   
* Using LDAP

* Using Firefox

First two ways are described in [Sun Java System Identity Synchronization for Windows 6.0 Installation and Configuration Guide](https://docs.oracle.com/cd/E19656-01/821-0422/aarjd/index.html)
The last one shown below:

* Go to https://nur.axibase.com:636/

* Press `Advanced` button and then `Add exception` to obtain certificate

![](resources/add_exception.png)

* Confirm security exception

![](resources/confirm_exception.png)

* Go to Firefox `Preferences` -> `Advanced` -> `Certificates` -> `View Certificates` 

![](resources/view_certificates.png)

* Select servers tab and click on required certificate to perform export 

![](resources/cert&export.png)

### Import certificate into jxplorer 

* Go to `Security` -> `Trusted Servers and CAs`

![](resources/security.png)

* Press on `Add Certificate`, select the required file obtained above and add it to keystore.

![](resources/add_cert.png)

[NB](http://jxplorer.org/help/Setting_a_Keystore_Password.htm). The cacerts keystore file has a default password of changeit 