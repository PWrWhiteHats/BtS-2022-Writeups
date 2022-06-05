# Changing flag
If you want to change a flag, XOR it using [xor.py](solution/xor.py), change encrypted flag and key in [login.py](login.py)
then you need to compile it to .exe. To do so use pyinstaller and python in version <=3.8 (tested in 3.8).
```
pyinstaller --onefile login.py
```

.exe file should be in created dist folder.
