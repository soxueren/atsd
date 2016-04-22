# Enabling Swap Space


Linux divides its physical RAM (physical memory) into blocks of memory
called pages. Swapping is the process when a page of memory is copied to
the allocated hard drive space, called swap space, in order to free up
RAM. The combined size of physical memory and swap space is the amount
of virtual memory available.

If your server does not have swap space enabled, then execute the
following commands:

The amount of swap space is controlled by `count=` parameter, in
kilobytes.

For production servers (large amount of RAM), we recommend to set swap
space equal to half of the physical memory amount.

For staging/test systems (small amount of RAM), we recommend to set swap
space at least equal to the physical memory amount.

```sh
 sudo dd if=/dev/zero of=/swapfile bs=1024 count=1024000 //make swap-file 
```

```sh
 sudo chmod 0600 /swapfile                                                
```

```sh
 sudo mkswap /swapfile //setup the swap-file                              
```

```sh
 sudo swapon /swapfile //enable swap-file                                 
```

```sh
 sudo echo "/swapfile swap swap defaults 0 0" >> /etc/fstab //setup swap  
 on boot                                                                  
```

Verify that swap is enabled:

```sh
 free                                                                     
```

Output should contain a swap row with total and free columns not equal
to zero:

```sh
 Swap:       1024000          0     1024000                               
```