# Installing CA-signed Certificate Files into ATSD

## Prepare certificate files

* server.key             - SSL Certificate Key File
* server.ca-bundle       - SSL CA Certificate File
* server.crt             - SSL Certificate File

Combine the chained certificates:

```
cat server.ca-bundle server.crt > cert-chain.txt
```

Combine the key file and the certificates:

```
openssl pkcs12 -export -inkey server.key -in cert-chain.txt -out server.pkcs12
```

## Import certificate files into ATSD keystore

Change directory to `/opt/atsd/atsd/conf`:

```
cd /opt/atsd/atsd/conf
```

> Use the default `server.keystore` password when executing the steps below: atsd_sec_pwd

Delete the default certificates with `atsd` alias:

```
keytool -delete -alias atsd -keystore server.keystore
```

Import the `server.pkcs12` file into certificate store:

```
keytool -importkeystore -srckeystore example.pkcs12 -srcstoretype PKCS12 -destkeystore server.keystore -alias atsd
```
