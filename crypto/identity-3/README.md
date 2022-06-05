# Identity-3

_Author: Jakub259

We managed to capture the identity verification in pcap. Retrieve the private key and authenticate to recive the flag from server.

## Public files

- server.py
- data.pcap

## Flag

Check in [flag.txt](flag.txt) file


## Docker

```
docker build -t identity-3 . && docker run -p 2538:2538 identity-3:latest
```


## Connect to server

```
nc destination 2538
```


# Run

```bash
python3 chal.py
```


## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file
