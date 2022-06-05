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

numberOfBits=128
e=3
p, q, n, et, d = 0, 0, 0, 0, 0
errorFlag=True
while errorFlag:
    errorFlag=False
    p=generatePrime(numberOfBits)
    q=generatePrime(numberOfBits)
    n=p*q
    et=(p-1)*(q-1)
    try:
        d=invmod(e, et)
    except ValueError:
        errorFlag=True

key = bytearray("ASlkj234A:324'a*", "UTF-8")
IV = bytearray("MY_RANDOM_IV_CBC", "UTF-8")
blockSize = 16
flag = bytearray(open("flag.txt").read()[:-1], "UTF-8")
aes = AES.new(key=key, iv=IV, mode=AES.MODE_CBC)
padded = pad(flag, blockSize)
encryptedFlag = bytearray(IV + aes.encrypt(padded))
encryptedFlagBase64 = base64.b64encode(encryptedFlag)

message = int.from_bytes(key, "big")
encryptedKey = encrypt(message, e, n)
print("Encrypted flag:", encryptedFlagBase64.decode("utf-8"))
print("Encrypted key:", encryptedKey)
print("Public key:")
print("e =", e)
print("n =", n)