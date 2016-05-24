# Firewall Configuration

## Allow Access

Allow access to particular ports on the target ATSD server:

* Login into ATSD server
* Add allow rules for target ATSD ports:

```sh
iptables -I INPUT -p tcp --dport 8081 -j ACCEPT
iptables -I INPUT -p udp --dport 8082 -j ACCEPT
iptables -I INPUT -p tcp --dport 8088 -j ACCEPT
iptables -I INPUT -p tcp --dport 8443 -j ACCEPT
```

## Make Rules Persistent

### Ubuntu/Debian

```sh
iptblload="/etc/network/if-pre-up.d/iptablesload"
iptblsave="/etc/network/if-post-down.d/iptablessave"
# "Creating scripts to save and load current iptables settings on reboot"
if [ -f $iptblload ]; then
	mv $iptblload ${iptblload}.backup
fi
if [ -f $iptblsave ]; then
	mv $iptblsave ${iptblsave}.backup
fi
touch $iptblload
echo "#!/bin/bash" >> $iptblload
echo "iptables-restore < /etc/iptables.rules" >> $iptblload
echo "exit 0" >> $iptblload
touch $iptblsave
echo "#!/bin/bash" >> $iptblsave
echo "iptables-save -c > /etc/iptables.rules" >> $iptblsave
echo "if [ -f /etc/iptables.downrules ]; then" >> $iptblsave
echo "iptables-restore < /etc/iptables.downrules" >> $iptblsave
echo "fi" >> $iptblsave
echo "exit 0" >> $iptblsave
chmod +x $iptblload
chmod +x $iptblsave
```

### RHEL / Centos

```
sed -i "s/IPTABLES_SAVE_ON_STOP=\"no\"/IPTABLES_SAVE_ON_STOP=\"yes\"/g" /etc/sysconfig/iptables-config
sed -i "s/IPTABLES_SAVE_ON_RESTART=\"no\"/IPTABLES_SAVE_ON_RESTART=\"yes\"/g" /etc/sysconfig/iptables-config
/etc/init.d/iptables save
```
### SUSE

```
echo "FW_SERVICES_EXT_TCP=\"8081 8082 8088 8443\"" >> /etc/sysconfig/SuSEfirewall2
/sbin/SuSEfirewall2
```



