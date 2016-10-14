# Installation on Docker

## Host Requirements

* [Docker Engine](https://docs.docker.com/engine/installation/) 1.7+.

## Images

### Docker Hub Image Information

* Image name: `axibase/atsd:latest`
* Base: ubuntu:14.04
* [Dockerfile](https://github.com/axibase/dockers/blob/master/atsd/Dockerfile)
* [Docker Hub](https://hub.docker.com/r/axibase/atsd/)

### RedHat Enterprise Linux Activated Image Information

* Image name: `axibase/atsd:rhel7`
* Base: rhel7:latest
* [Dockerfile](https://github.com/axibase/dockers/blob/atsd-rhel7/Dockerfile)
* [readme](https://github.com/axibase/dockers/blob/atsd-rhel7/README.md)

## Start Container

### Option 1: Configure Сollector Account Automatically

Replace `collector-user` and `collector-password` to automatically create a built-in [collector account](../administration/collector-account.md). 

Minimum password length is **six** characters and the password is subject to the following [requirements](../administration/user-authentication.md#password-requirements).

```properties
docker run \
  --detach \
  --name=atsd \
  --restart=always \
  --publish 8088:8088 \
  --publish 8443:8443 \
  --publish 8081:8081 \
  --publish 8082:8082/udp \
  --env ATSD_USER_NAME=collector-user \
  --env ATSD_USER_PASSWORD=collector-password \
  axibase/atsd:latest
```

### Option 2: Configure User Accounts Manually

If `ATSD_USER_{NAME, PASSWORD}` credentials are not specified as part of ф `docker run` command, no collector account will be created.

In this case, you can configure both administrator and [collector](../administration/collector-account.md) accounts on initial login.

```properties
docker run \
  --detach \
  --name=atsd \
  --restart=always \
  --publish 8088:8088 \
  --publish 8443:8443 \
  --publish 8081:8081 \
  --publish 8082:8082/udp \
  axibase/atsd:latest
```

It may take up to 5 minutes to initialize the database.

## Launch Parameters

| **Name** | **Required** | **Description** |
|:---|:---|:---|
|`--detach` | Yes | Run container in background and print container id. |
|`--hostname` | No | Assign hostname to the container. |
|`--name` | No | Assign a unique name to the container. |
|`--restart` | No | Auto-restart policy. _Not supported in all Docker Engine versions._ |
|`--publish` | No | Publish a container's port to the host. |
|`--env ATSD_USER_NAME` | No | User name for the built-in collector account. |
|`--env ATSD_USER_PASSWORD` | No | Password for the built-in collector account, subject to [password requirements](../administration/user-authentication.md#password-requirements).|

## Check Installation

```
docker logs -f atsd
```

You should see a _ATSD start completed_ message at the end of the `start.log` file.

ATSD web interface is accessible on port 8088/http and 8443/https.

## Exposed Ports

* 8088 – http
* 8443 – https
* 8081 – [TCP network commands](https://axibase.com/atsd/api/#network-commands)
* 8082 – [UDP network commands](https://axibase.com/atsd/api/#network-commands)

## Port Mappings

Depending on your Docker host network configuration, you may need to change port mappings in case some of the published ports are already taken.

```sh
Cannot start container <container_id>: failed to create endpoint atsd on network bridge: 
Bind for 0.0.0.0:8088 failed: port is already allocated
```

```properties
docker run \
  --detach \
  --name=atsd \
  --restart=always \
  --publish 9088:8088 \
  --publish 9443:8443 \
  --publish 9081:8081 \
  --publish 9082:8082/udp \
  axibase/atsd:latest
```

## Troubleshooting

* Review [Troubleshooting Guide](troubleshooting.md).
* Kernel Incompatibility

Verify that your Docker host runs on a supported kernel level if the container fails to start or the installation script stalls.

```
uname -a
```

* 3.13.0-79.123+
* 3.19.0-51.57+
* 4.2.0-30.35+

See "Workarounds" in [#18180](https://github.com/docker/docker/issues/18180#issuecomment-193708192)

## Validation

* [Verify database installation](verifying-installation.md).

## Post-installation Steps

* [Basic configuration](post-installation.md).
* [Getting Started guide](/tutorials/getting-started.md).
