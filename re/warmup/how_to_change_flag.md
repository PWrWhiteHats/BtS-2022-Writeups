# Changing flag
If you want to change a flag, modify source code [warmup.c](src/warmup.c) with secred variable xored. The key is "\x02\x03\x04\x05\x05\x04\x03\x02"
You can use [xor.py](solution/xor.py) to get new xored flag. Then you have to compile binary.
```
gcc -static warmup.c -o warmup
strip -s warmup
upx warmup
```

warmup file should be ready with flag changed.
