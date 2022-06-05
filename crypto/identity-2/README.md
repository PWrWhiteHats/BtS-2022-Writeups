# Identity-2

We managed to capture the identity verification in pcap. Retrieve the private key and authenticate to recive the flag from server.

## Public files

- server.py
- data.pcap

## Flag

Check in [flag.txt](flag.txt) file


## Docker

```
docker build -t identity-2 . && docker run -p 2438:2438 identity-2:latest
```


## Connect to server

```
nc destination 2438
```


# Run

```bash
python3 chal.py
```


## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file
