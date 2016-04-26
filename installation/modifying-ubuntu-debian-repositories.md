# Modifying Ubuntu and Debian Repositories


# Ubuntu OS

If the repositories are unavailable, check `sources.list` file:

```sh
 sudo nano /etc/apt/sources.list                                          
```

If `sources.list` file contains: `us.` at the start of each link,
remove: `us.` from each link and try installing the dependencies again.

# Debian OS

Modify `sources.list` file:

```sh
 sudo nano /etc/apt/sources.list                                          
```

Add the following two lines at the end of the file:

```sh
 deb http://http.us.debian.org/debian stable main contrib non-free        
 deb http://security.debian.org stable/updates main contrib non-free      
```

Try installing the dependencies again.