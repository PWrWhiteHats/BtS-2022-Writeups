## rock-paper-scissors

_Author: Jakub259

Let's play a game, if you win I will give you an awesome reward Ü

## Public files
- libc-2.31.so 
- chall

## Flag

Check in [flag.txt](flag.txt) file


## Docker

```
docker build -t rock-paper-scissors . && docker run -p 2441:2441 rock-paper-scissors:latest
```

## Connect to server

```
nc destination 2441
```
# Compile


```bash
gcc -pie -fPIE -fstack-protector-all -Wl,-z,now -Wl,-z,relro chall.c -o chall

```


## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file




