# Relocating ATSD Directory

By default, ATSD is installed in the `/opt/atsd` directory.

Execute the following steps if you need to move ATSD to a different file system.

1. Stop ATSD services

  ```sh
  /opt/atsd/atsd/atsd-all.sh stop
  ```

2. Move ATSD to another directory such as `/opt/data/`

  ```sh
  sudo mv /opt/atsd /opt/data/
  ```

3. Create a symbolic link to the new atsd directory

  ```sh
  ln -s /opt/data/atsd /opt/atsd
  ```

4. Start ATSD services

  ```sh
  /opt/atsd/atsd/atsd-all.sh start
  ```
