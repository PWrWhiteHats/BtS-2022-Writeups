## Solution
This is i think the simplest way to solve the challenge.
1. Get https://github.com/extremecoders-re/pyinstxtractor or similar to get .pyc files from .exe.
```
wget https://raw.githubusercontent.com/extremecoders-re/pyinstxtractor/master/pyinstxtractor.py
```
2. Use previous script to get .pyc files.
3. Find login.pyc in folder.
4. Get python 3.8.0 (earlier should work as well, but not tested). Newer version probably won't work or it will be harder.
5. Get uncompyle6 or similiar to get source code
```
pip install uncompyle6
```
6. Use uncompyle to get source code from login.pyc.
```
uncompyle6 login.pyc
```
7. Find xkey and secret variables, make xor or use provided script in [xor.py](xor.py) and you should see a flag.
