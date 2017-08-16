# Enabling Swap Space


Linux divides its RAM (physical memory) into blocks of memory
called pages. Swapping is the process when a page of memory is copied to
the allocated hard drive space, called swap space, in order to free up
RAM. The combined size of physical memory and swap space is the amount
of virtual memory available.

If your server does not have swap space enabled, then execute the
following commands.

The amount of swap space (in kilobytes) is controlled by the `count=` parameter.

For production servers (large amount of RAM), we recommend setting swap
space equal to half of the physical memory amount.

For staging/test systems (small amount of RAM), we recommend setting swap
space at least equal to the physical memory amount.

Create swap file.

```sh
sudo dd if=/dev/zero of=/swapfile bs=1024 count=1024000
```

Configure the swap file.

```sh
sudo chmod 0600 /swapfile                                                
```

```sh
sudo mkswap /swapfile                             
```

Enable swap.

```sh
 sudo swapon /swapfile                              
```

Enable swap on boot.

```sh
sudo echo "/swapfile swap swap defaults 0 0" >> /etc/fstab              
```

Verify that the swap is enabled.

```sh
free                                                                     
```

The output should contain a row with Swap total not equal to zero.

```
             total       used       free     shared    buffers     cached
Mem:       7697000    6104904    1592096         32      86628    3062424
-/+ buffers/cache:    2955852    4741148
Swap:      1023996          0    1023996                          
```
