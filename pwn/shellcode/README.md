## shellcode

_Author: Jakub259

What if... you couldn't use syscalls... unless..?

## Flag

Check in [flag.txt](flag.txt) file

## Public files

- chall 

## Docker

```
docker build -t shellcode . && docker run -p 2440:2440 shellcode:latest
```


## Connect to server

```
nc destination 2440
```


# Compile

```bash
gcc -pie -fPIE -fstack-protector-all -Wl,-z,now -Wl,-z,relro chall.c -o chall

```


## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file




