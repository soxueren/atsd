# Network Settings

If you’re anticipating a high data insertion rate with bursts of 100,000
packets per second or more, increase the maximum receiving buffer on Linux
OS as follows and restart ATSD:

```sh
 sudo sysctl -w net.core.rmem_max=8388608                                 
```

This setting would allow the operating system and ATSD to buffer up to 8
megabytes of received packets, in the case that the inserting rate is temporarily
higher than the ATSD throughput rate.

The increased buffer would also reduce or even eliminate the number of
UDP datagrams dropped due to buffer overflow.
