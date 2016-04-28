## Command: `ping`

```
ping
```

This command can be sent by clients to maintain an open connection by preventing an inactivity timeout.
The server responds `ok` to the ping command.

## Command: `exit`

> Request

```
exit
```
> Response

```
Goodbye
```

The server responds `Goodbye` and closes the connection.
