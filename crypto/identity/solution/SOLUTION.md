# Solution

## Vulnerability

Script contains defective implementation of Schnorr Identification Scheme - author forgor 💀 about challenge value.  

## Exploit

Public values:  
- generator
- prime_p
- pubkey

To authenticate you need two numbers `x, y` such that  [x = generator ** y * pubkey (mod prime_p)](https://github.com/PWrWhiteHats/BtS-2022-Challenges/blob/crypto/identity/chal/crypto/identity/chal.py#L10)  

Take arbitrary number `y` and calculate `x` by substituting variables to `generator ** y * pubkey (mod prime_p)`.  
Send `x` and `y` to server to recive the flag.  
That's it :P  
  
  
[solve.py](solve.py)
