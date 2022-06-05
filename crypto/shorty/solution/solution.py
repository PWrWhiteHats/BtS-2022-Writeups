#!/usr/bin/python3
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
from Crypto.Util.Padding import unpad
import base64
import random
import math

def modexp(g, u, p):
    s = 1
    while u != 0:
        if u & 1:
            s = (s * g)%p
        u >>= 1
        g = (g * g)%p;
    return s

def generatePrime(bits):
    p = 0
    while (p%2 == 0 or pow(2, (p-1), p) != 1):
    	p = random.randint(2**(bits-1), 2**bits-1)
    return p

def extendedGCD(a, b):
    lastRemainder=abs(a)
    remainder=abs(b)
    x, lastX, y, lastY = 0, 1, 1, 0

    while remainder:
        lastRemainder, (quotient, remainder) = remainder, divmod(lastRemainder, remainder) 
        x, lastX = lastX - quotient*x, x
        y, lastY = lastY - quotient*y, y

    return lastRemainder, lastX*(-1 if a < 0 else 1), lastY*(-1 if b < 0 else 1)

def invmod(a, m):
    g, x, y = extendedGCD(a, m)
    if g != 1:
        raise ValueError
    return x % m

def encrypt(message, e, n):
    return modexp(message, e, n)

def decrypt(message, d, n):
    return modexp(message, d, n)

e = 3 # subsitute with e from the challenge
p = 218227697784777349009775650109687551529 # subsitute with factorization result 1
q = 264143542063184742430477539380057794493 # subsitute with factorization result 2
n = p*q # private key part 1
et = (p-1)*(q-1)
d = invmod(e, et) # private key part 2

encryptedFlagBase64 = "TVlfUkFORE9NX0lWX0NCQ4xji7hJw9ewBdF9iXIzk970XivXkVWF0tRRIxowdpbd" # subsitute with encrypted flag from the challenge
encryptedFlag = base64.b64decode(encryptedFlagBase64)
encryptedKey = 16332844947597651922741859227450388617184476160551042355499478042074023083480 # subsitute with encrypted key from the challenge

decrypted = decrypt(encryptedKey, d, n)
decryptedKey = decrypted.to_bytes(len(str(decrypted)), "big").decode("UTF-8").lstrip(chr(0))

print("Decrypted key:", decryptedKey)

blockSize = 16
IV = encryptedFlag[:blockSize]
encryptedFlag = encryptedFlag[blockSize:]
aes = AES.new(key=bytearray(decryptedKey, "UTF-8"), iv=IV, mode=AES.MODE_CBC)
decryptedFlag = aes.decrypt(encryptedFlag)
print("Decrypted flag:", unpad(decryptedFlag, blockSize).decode("UTF-8"))