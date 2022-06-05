# babyheap

Memory management would not be so difficult if we used STL containers or smart pointers.

## Flag

Check in [flag.txt](flag.txt) file

## Public files
- public.zip

## Docker

```
docker build -t babyheap . && docker run -p 2442:2442 babyheap:latest
```


## Connect to server

```
nc destination 2442
```


# Compile

```bash
g++ -pie -fPIE -fstack-protector-all -Wl,-z,now -Wl,-z,relro chall.cpp -o chall

```


## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file




