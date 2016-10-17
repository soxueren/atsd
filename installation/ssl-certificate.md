# SSL Certificate Installation

The following instructions assume that you have obtained the following files from a certificate authority:

* `example.crt` - SSL Certificate for the DNS name where ATSD is running
* `example.key` - SSL Certificate Key File
* `example.ca-bundle` - Intermediate SSL certificates

### Generate a key pair and certificate into a keystore:

```sh
keytool -keystore /opt/atsd/atsd/conf/server.keystore -alias jetty -genkey
```

### Follow the prompts to generate a certificate

```
    Enter keystore password:  password
    What is your first and last name?
      [Unknown]:  atsd
    What is the name of your organizational unit?
      [Unknown]: Software Group
    What is the name of your organization?
      [Unknown]:  Axibase Corporation
    What is the name of your City or Locality?
      [Unknown]: Cupertino
    What is the name of your State or Province?
      [Unknown]: CA
    What is the two-letter country code for this unit?
      [Unknown]: US
    Is CN=atsd, OU=Software Group, O=Axibase Corporation,
    L=Cupertino, ST=CA, C=US correct?
      [no]:  yes

    Enter key password for 
            (RETURN if same as keystore password):  password
```

### Combine the Chained Certificates

```sh
cat example.crt intermediate.crt [intermediate2.crt]... rootCA.crt > cert-chain.txt
```

Example:

```sh
cat example.ca-bundle example.crt > cert-chain.txt
```

### Combine the Key and Certificates

```sh
openssl pkcs12 -export -inkey example.key -in cert-chain.txt -out example.pkcs12
```

### Import the Certificate	
	
Use the keytool to import the pkcs12 file into ATSD keystore file:

```
keytool -importkeystore -srckeystore jetty.pkcs12 -srcstoretype PKCS12 -destkeystore /opt/atsd/atsd/conf/server.keystore
```

### Restart ATSD

```sh
/opt/atsd/bin/stop-atsd.sh
/opt/atsd/bin/start-atsd.sh
```

### Verify SSL

Enter ATSD SSL url in the browser address bar: https://atsd.axibase.com:8443

### Troubleshooting

Verify the correct settings in the `/opt/atsd/atsd/conf/server.properties` file

```properties
https.port=8443
https.keyStorePassword=
https.keyManagerPassword=
https.trustStorePassword=
```
