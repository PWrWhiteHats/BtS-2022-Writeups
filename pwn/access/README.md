# Access challenge

_Author: Karmaz95_  
_Tags: pwn_

AFINE login panel. Remember about the correct format!

## Flag
Check in [flag.txt](flag.txt) file

## Docker
```
docker build -t access . && docker run -p 1234:1234 access:latest
```

## Connect to server
```
nc destination 1234
```

# Compile
```bash
gcc -m32 -Wl,-z,execstack -no-pie --static access.c -o access
```

## Solution
Check the [solution.py](solution/solution.py) file.
