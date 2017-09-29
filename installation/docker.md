# Installation on Docker

## Host Requirements

* [Docker Engine](https://docs.docker.com/engine/installation/) 1.7+.

## Images

* Image name: `axibase/atsd:latest`
* Base Image: Ubuntu 16.04
* [Dockerfile](https://github.com/axibase/dockers/blob/master/Dockerfile)
* [Docker Hub](https://hub.docker.com/r/axibase/atsd/)

## Start Container

```properties
docker run -d --name=atsd -p 8088:8088 -p 8443:8443 -p 8081:8081 -p 8082:8082/udp \
  axibase/atsd:latest
```

To automatically create an [account](../administration/collector-account.md) for data collection agents, replace `cuser` and `cpassword` credential variables in the command below.

```properties
docker run -d ---name=atsd -p 8088:8088 -p 8443:8443 -p 8081:8081 -p 8082:8082/udp \
  --env COLLECTOR_USER_NAME=cuser \
  --env COLLECTOR_USER_PASSWORD=cpassword \
  --env COLLECTOR_USER_TYPE=api-rw \
  axibase/atsd:latest
```

The password is subject to the following [requirements](../administration/user-authentication.md#password-requirements). If the credentials contain special characters `$`, `&`, `#`, or `!`, escape them with backslash `\`.

## Start Container

Execute the launch command as described above.

```sh
$ docker run -d --name=atsd -p 8088:8088 -p 8443:8443 -p 8081:8081 -p 8082:8082/udp axibase/atsd:latest
Unable to find image 'axibase/atsd:latest' locally
latest: Pulling from axibase/atsd
...
Status: Downloaded newer image for axibase/atsd:latest
14d1f27bf0c139027b5f69009c0c5007d35be92d61b16071dc142fbc75acb36a
```

Check the installation progress.

```
docker logs -f atsd
```

You should see an **ATSD start completed** message once the database is ready.

```
...
[ATSD] Starting ATSD ...
...
[ATSD] Waiting for ATSD to bind to port 8088 ...( 1 of 20 )
...
[ATSD] Waiting for ATSD to bind to port 8088 ...( 5 of 20 )
[ATSD] ATSD web interface:
[ATSD] http://172.17.0.2:8088
[ATSD] https://172.17.0.2:8443
[ATSD] ATSD start completed.
```

The ATSD web interface is accessible on ports 8088/http and 8443/https.

## Launch Parameters

| **Name** | **Required** | **Description** |
|:---|:---|:---|
|`--detach` or `-d` | Yes | Run container in background and print container id. |
|`--publish` or `-p` | No | Publish a container's port to the host. |
|`--name` | No | Assign a unique name to the container. |
|`--restart` | No | Auto-restart policy, such as 'always'. |

## Environment Variables

| **Name** | **Required** | **Description** |
|:---|:---|:---|
|`--env ADMIN_USER_NAME` | No | User name for the built-in administrator account. |
|`--env ADMIN_USER_PASSWORD` | No | [Password](https://github.com/axibase/atsd-docs/blob/master/administration/user-authentication.md#password-requirements) for the built-in administrator.|
|`--env COLLECTOR_USER_NAME` | No | User name for a data collector account. |
|`--env COLLECTOR_USER_PASSWORD` | No | [Password](https://github.com/axibase/atsd-docs/blob/master/administration/user-authentication.md#password-requirements) for a data collector account.|
|`--env COLLECTOR_USER_TYPE` | No | User group for a data collector account, default value is `writer`.|
|`--env DB_TIMEZONE` | No | Database [timezone identifier](https://github.com/axibase/atsd/blob/master/api/network/timezone-list.md).|

View additional launch examples [here](https://github.com/axibase/atsd-docs/blob/master/installation/docker.md#option-1-configure-collector-account-automatically).

## Exposed Ports

* 8088 – http
* 8443 – https
* 8081 – [TCP network commands](../api/network#network-api)
* 8082 – [UDP network commands](../api/network#udp-datagrams)

## Port Mappings

Change port mappings in the launch command in case of port allocation error.

```sh
Cannot start container <container_id>: failed to create endpoint atsd on network bridge:
Bind for 0.0.0.0:8088 failed: port is already allocated
```

```properties
docker run -d --name=atsd \
  --publish 9088:8088 \
  --publish 9443:8443 \
  --publish 9081:8081 \
  --publish 9082:8082/udp \
  axibase/atsd:latest
```

## Troubleshooting

* Review [Troubleshooting Guide](troubleshooting.md).

## Validation

* [Verify database installation](verifying-installation.md).

## Post-installation Steps

* [Basic configuration](post-installation.md).
* [Getting Started guide](../tutorials/getting-started.md).
