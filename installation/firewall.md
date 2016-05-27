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

#### Install the iptables-persistent package

```
apt-get install iptables-persistent
```

During the install process you will be asked to save exising rules.

Rules will be saved to ```/etc/iptables/rules.v4``` and ```/etc/iptables/rules.v6``` for ipv4 and ipv6 respectivly.

The saved rules can be updated by either:

* running ```dpkg-reconfigure iptables-persistent``` 

* by executing the following commands:

```
iptables-save > /etc/iptables/rules.v4
ip6tables-save > /etc/iptables/rules.v6
```



#### Installing the following script


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

cat > $iptblload <<EOFF
#!/bin/bash
/sbin/iptables-restore < /etc/iptables.rules
exit 0
EOFF

cat > $iptblsave <<EOFF
#!/bin/bash
/sbin/iptables-save -c > /etc/iptables.rules
if [ -f /etc/iptables.downrules ]; then
/sbin/iptables-restore < /etc/iptables.downrules
fi
exit 0
EOFF

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



